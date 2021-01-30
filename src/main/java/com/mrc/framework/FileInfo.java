/**
 * 
 */
package com.mrc.framework;

import com.mrc.db.dto.common.UploadFiles;
import com.mrc.db.dto.file.VideoFileCond;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.DemuxerTrack;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;



/**
 * @author jsh
 *
 */
public class FileInfo {
	/**
	 * 디렉코리생성
	 * 
	 * @param path 폴더
	 * @return 성공유무
	 */
	public Boolean makeDirectory(String path) {
		try {
			File Folder = new File(path);
			// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
			if (!Folder.exists()) {
				try {
					Folder.mkdir(); // 폴더 생성합니다.
					System.out.println("폴더가 생성되었습니다.");
				} catch (Exception e) {
					e.getStackTrace();
				}
			} else {
				System.out.println("이미 폴더가 생성되어 있습니다.");
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
	}
	public Boolean moveFile(String originPath, String deatinationPath)
	{
		try {
			Path filePath = Paths.get(originPath);
			Path filePathToMove = Paths.get(deatinationPath);
			Files.move(filePath, filePathToMove);
		}catch(Exception ex){
			return false;
		}
		return true;
	}
	/**
	 * 
	 * @param path  파일 주소
	 * @param msg   글내용
	 * @param bnext true 이어쓰기 false 새로 쓰기
	 * @return
	 */
	public Boolean makeFileWrite(String path, String msg, Boolean bnext) {
		File file = new File(path);
		FileWriter writer = null;

		try {
			// 기존 파일의 내용에 이어서 쓰려면 true를, 기존 내용을 없애고 새로 쓰려면 false를 지정한다.
			writer = new FileWriter(file, bnext);
			writer.write(msg);
			writer.flush();

			System.out.println("DONE");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}


	/**
	 * 파일의 물리적 주소를 가져오는 함수 예) C:/source/files/202010/test.png
	 * @param filePath
	 * @return
	 */
	public String getRealFilePath(String filePath)
	{
		 
		try {
			if(filePath == null) return "";
			if(filePath.indexOf("/files/") == 0)
				filePath = filePath.replace("/files/", "");
			filePath = (filePath.indexOf("/source/files/") >=0 || filePath.indexOf("\\source\\files\\") >= 0) ? filePath :  Global.getConfigInfo().getFileBasePath() + URLDecoder.decode(filePath, "UTF-8");
			return filePath;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 디렉토리와 그 안의 파일삭제
	 * @param directoryPath
	 * @return
	 */
	public Boolean directoryAndFilesDelete(String directoryPath) {
		File file = new File(directoryPath);
		try {
			if (file.exists()) { // 파일존재여부확인
				if (file.isDirectory()) {
					// 파일이 디렉토리인지 확인
					File[] files = file.listFiles();
					for (int i = 0; i < files.length; i++) {
						if (files[i].delete())
							System.out.println(files[i].getName() + " 삭제성공");
						else
							System.out.println(files[i].getName() + " 삭제실패");
					}
				}
				if (file.delete())
					System.out.println("파일삭제 성공");
				else
					System.out.println("파일삭제 실패");
			}

			else {
				System.out.println("파일이 존재하지 않습니다.");
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 파일삭제
	 * 
	 * @param path
	 * @return
	 */
	public Boolean fileDelete(String path) {
		File file = new File(path);
		try {
			if (file.exists()) {
				if (file.delete()) {
					System.out.println("파일삭제 성공");
				} else {
					System.out.println("파일삭제 실패");
					FileDeleteStrategy.FORCE.delete(file);
				}
			} else {
				System.out.println("파일이 존재하지 않습니다.");
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 파일 업로드
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public List<UploadFiles> uploadFile(MultipartFile[] file) throws IOException {
		return this.uploadFile(file, "");
	}

	public List<UploadFiles> uploadFile(MultipartFile[] file, String fileType) throws IOException {
		return this.uploadFile(file, fileType, false, null);
	}

	public List<UploadFiles> uploadFile(MultipartFile[] file, String fileType, Boolean isThumbnail, Integer nSize)
			throws IOException {
		List<UploadFiles> list = new ArrayList<UploadFiles>();
		String dirPath = "";


		for (MultipartFile filedata : file) {
			String extention = "." + FilenameUtils.getExtension(filedata.getOriginalFilename());
			if (extention.equals(".") && fileType.equals("image")) {
				extention = ".png";
			}
			dirPath = Global.getConfigInfo().getFileBasePath();
			dirPath = dirPath + new SimpleDateFormat("yyyyMM").format(new Date()) + "/";
			String refileName = UUID.randomUUID().toString() + extention;
			Global.getFileInfo().makeDirectory(dirPath);
			String fileUrl = new SimpleDateFormat("yyyyMM").format(new Date()) + "/" + refileName;

			String filepath = dirPath + refileName;
			UploadFiles uploadData = new UploadFiles();
			File tmp = new File(filepath);

			try {
				tmp.setLastModified(new Date().getTime());

				FileUtils.copyInputStreamToFile(filedata.getInputStream(), tmp);
			} catch (IOException e) {
				throw e;
			}

			if (isThumbnail) {
				if (this.getFileType(filepath) == enFileType.image) {
					try {

						nSize = nSize == null ? 150 : nSize;
						uploadData.setThumbNailImage(
								this.makeImageToThumbnail(tmp, dirPath, refileName, extention, nSize));

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (this.getFileType(filepath) == enFileType.video) {

					
					String thumbPath = dirPath + "thumb_" + refileName;
					try {
						List<String> arrThumbNail = this.makeVideoToThumbnail(tmp, thumbPath);
						if(arrThumbNail.size() > 0) uploadData.setThumbNailImage(arrThumbNail.get(0));
						uploadData.setArrThumbNailImage(arrThumbNail);
					} catch (IOException | JCodecException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			uploadData.setFileName(refileName);
			uploadData.setOriFileName(filedata.getOriginalFilename());
			uploadData.setFileSize(filedata.getSize());
			uploadData.setFileContentType(filedata.getContentType());

			uploadData.setAttachmentUrl(fileUrl);

			list.add(uploadData);

		}
		return list;
	}

	public List<UploadFiles> multiImgProcess(@RequestParam MultipartFile[] add, @RequestParam List<String> del)
			throws IOException {
		List<UploadFiles> list = new ArrayList<UploadFiles>();
		String dirPath = Global.getConfigInfo().getFileBasePath();

		if (null != add) {
			for (MultipartFile filedata : add) {
				// image/png -> [0]image, [1]png
				String extention = "." + (filedata.getContentType()).split("/")[1];
				String refileName = UUID.randomUUID().toString() + extention;
				Global.getFileInfo().makeDirectory(dirPath + new SimpleDateFormat("yyyyMM").format(new Date()) + "/");
				String fileUrl = new SimpleDateFormat("yyyyMM").format(new Date()) + "/" + refileName;

				String filepath = dirPath + new SimpleDateFormat("yyyyMM").format(new Date()) + "/" + refileName;

				File tmp = new File(filepath);
				try {
					FileUtils.copyInputStreamToFile(filedata.getInputStream(), tmp);
				} catch (IOException e) {
					throw e;
				}
				UploadFiles uploadData = new UploadFiles();

				uploadData.setFileName(refileName);
				uploadData.setOriFileName(filedata.getOriginalFilename());
				uploadData.setFileSize(filedata.getSize());
				uploadData.setFileContentType(filedata.getContentType());
				uploadData.setAttachmentUrl(fileUrl);
				list.add(uploadData);
			}
		}

		if (null != del) {
			for (String path : del) {
				String filePath = Global.getConfigInfo().getFileBasePath() + URLDecoder.decode(path, "UTF-8");
				File file = new File(filePath);
				try {
					if (file.exists()) {
						if (file.delete()) {
							System.out.println("파일삭제 성공");
						} else {
							System.out.println("파일삭제 실패");
						}
					} else {
						System.out.println("파일이 존재하지 않습니다.");
					}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		}

		return list;
	}

	/**
	 * 파일다운로드
	 * 
	 * @param response
	 * @param dirpath
	 * @param fileName
	 * @throws IOException
	 */
	public void downloadFile(HttpServletResponse response, String dirpath, String fileName) throws IOException {
		File file = new File(Global.getConfigInfo().getFileBasePath()
				+ (Global.getDataInfo().isNullEmpty(dirpath) ? "" : dirpath + "/") + fileName);

		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			// response.setContentType(mimeType);/*이미지 보기로 이동함*/
			response.setContentType("application/unknown");/* 무조건 다운로드 */
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}
	
	 public StreamingResponseBody stream(String fileName) throws Exception {
        File file = new File(Global.getConfigInfo().getFileBasePath() + fileName);
        final InputStream is = new FileInputStream(file);
        return os -> {
            readAndWrite(is, os);
        };
    }
	 
	 private void readAndWrite(final InputStream is, OutputStream os) throws IOException {
	        byte[] data = new byte[2048];
	        int read = 0;
	        while ((read = is.read(data)) > 0) {
	            os.write(data, 0, read);
	        }
	        os.flush();
	    }

	/**
	 * 원본 비율 유지 썸네일
	 * 
	 * @param file
	 * @param fileDir
	 * @param fileName
	 * @param fileExt
	 * @return
	 * @throws Exception
	 */
	public String makeImageToThumbnail(File file, String fileDir, String fileName, String fileExt) throws Exception {
		return this.makeImageToThumbnail(file, fileDir, fileName, fileExt, Global.getConfigInfo().getThumbImageSize());
	}

	public String makeImageToThumbnail(File file, String fileDir, String fileName, String fileExt, Integer nSize)
			throws Exception {

		nSize = (nSize == null) ? Global.getConfigInfo().getThumbImageSize() : nSize;
		fileExt = fileExt.replace(".", "");
		// 저장된 원본파일로부터 BufferedImage 객체를 생성합니다.
		BufferedImage srcImg = ImageIO.read(file);
		BufferedImage destImg = Scalr.resize(srcImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, nSize);
		// 썸네일을 저장합니다. 이미지 이름 앞에 "THUMB_" 를 붙여 표시했습니다.
		String thumbName = fileDir + "thumb_" + fileName;
		File thumbFile = new File(thumbName);
		ImageIO.write(destImg, fileExt.toUpperCase(), thumbFile);
		return thumbName.replace(Global.getConfigInfo().getFileBasePath(), "");
	}

	/**
	 * 이미지 crop 썸네일
	 * 
	 * @param file
	 * @param fileDir
	 * @param fileName
	 * @param fileExt
	 * @return
	 * @throws Exception
	 */
	public String makeImageCropToThumbnail(File file, String fileDir, String fileName, String fileExt)
			throws Exception {
		return this.makeImageCropToThumbnail(file, fileDir, fileName, fileExt, 150, 150);
	}

	public String makeImageCropToThumbnail(File file, String fileDir, String fileName, String fileExt, int nWidth,
			int nHeight) throws Exception {
		fileExt = fileExt.replace(".", "");
		// 저장된 원본파일로부터 BufferedImage 객체를 생성합니다.
		BufferedImage srcImg = ImageIO.read(file);
		// 썸네일의 너비와 높이 입니다.
		int dw = nWidth, dh = nHeight;
		// 원본 이미지의 너비와 높이 입니다.
		int ow = srcImg.getWidth();
		int oh = srcImg.getHeight();
		// 원본 너비를 기준으로 하여 썸네일의 비율로 높이를 계산합니다.
		int nw = ow;
		int nh = (ow * dh) / dw;
		// 계산된 높이가 원본보다 높다면 crop이 안되므로
		// 원본 높이를 기준으로 썸네일의 비율로 너비를 계산합니다.
		if (nh > oh) {
			nw = (oh * dw) / dh;
			nh = oh;
		}
		// 계산된 크기로 원본이미지를 가운데에서 crop 합니다.
		BufferedImage cropImg = Scalr.crop(srcImg, (ow - nw) / 2, (oh - nh) / 2, nw, nh);
		// crop된 이미지로 썸네일을 생성합니다.
		BufferedImage destImg = Scalr.resize(cropImg, dw, dh);
		// 썸네일을 저장합니다. 이미지 이름 앞에 "THUMB_" 를 붙여 표시했습니다.
		String thumbName = fileDir + "thumb_" + fileName;
		File thumbFile = new File(thumbName);
		ImageIO.write(destImg, fileExt.toUpperCase(), thumbFile);
		return thumbName;
	}


	/**

	 * @param source    mp4 file.
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws JCodecException
	 */
	public List<String> makeVideoToThumbnail(File source, String filePath) throws IOException, JCodecException {
		return this.makeVideoToThumbnail(source, filePath, null);
	}

	public List<String> makeVideoToThumbnail(File source, String filePath, Integer nSize)
			throws IOException, JCodecException {
		// Double nTime = this.getVideoDuration(filePath);
		double nTotFile = 6d;
		
		Integer nFrames = getTotalFrames(source);
		
		Integer nAddFrame =(int)Math.floor(nFrames /(nTotFile -1d));
		ArrayList<String> arrFilePath = new ArrayList<String>();
		
		String ext = FilenameUtils.getExtension(filePath);
		int frameNumber = 0;
		int idx = 1;
		while(true)
		{
			String filepath2 = filePath.replace("." + ext, "_" + Integer.toString(idx) + ".png");
			File thumbnail = new File(filepath2);
			arrFilePath.add(filepath2.replace(Global.getConfigInfo().getFileBasePath(), ""));
			nSize = (nSize == null) ? Global.getConfigInfo().getThumbImageSize() : nSize;
			if(idx == (int)nTotFile) frameNumber = nFrames -1;
			Picture picture = FrameGrab.getFrameFromFile(source,frameNumber);
	
			BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
			BufferedImage destImg = Scalr.resize(bufferedImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, nSize);
			ImageIO.write(destImg, "png", thumbnail);
			if(frameNumber == nFrames -1) break;
			frameNumber = frameNumber + nAddFrame;
			if(frameNumber >= nFrames ) frameNumber = nFrames-1;
			System.out.print("makeVideoToThumbnail >> " + Integer.toString(idx) );
			idx++;
		}
		return arrFilePath;
	}
	
	public String makeVideoPictureToThumbnail(VideoFileCond cond )throws IOException, JCodecException {
		String filePath = cond.getFilePath(); 
		double second = cond.getSecond(); 
		Integer nSize = cond.getSize();
		filePath = Global.getConfigInfo().getFileBasePath() + filePath;
		File file = new File(filePath);
		double totDuaration = this.getDuaration(file)-0.1d;
		if(totDuaration < second) second = totDuaration;
		String ext = FilenameUtils.getExtension(filePath);
		
		String filepath2 = filePath.replace("." + ext, "_takeP.png");
		
		File thumbnail = new File(filepath2);
		Picture picture = FrameGrab.getFrameAtSec(file, second);
		BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
		BufferedImage destImg = Scalr.resize(bufferedImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, nSize);
		ImageIO.write(destImg, "png", thumbnail);
		
		return filepath2.replace(Global.getConfigInfo().getFileBasePath(), "");
	}


	/**
	 * 비디오 파일의 Total Frame 수 가져오기
	 * @param file
	 * @return
	 */
	private Integer getTotalFrames(File file) 
	{
		
		 try {
	            FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(file));
	            DemuxerTrack vt = grab.getVideoTrack();
	            
	            return vt.getMeta().getTotalFrames();
	        } catch (IOException | JCodecException e) {
	            throw new RuntimeException(e);
	        }
	}

	/**
	 * 비디오파일의 시간 가져오기
	 * @param file
	 * @return
	 */
	private double getDuaration(File file) {
		 try {
	            FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(file));
	            DemuxerTrack vt = grab.getVideoTrack();
	            
	            return vt.getMeta().getTotalDuration();
	        } catch (IOException | JCodecException e) {
	            throw new RuntimeException(e);
	        }
	}

	public enFileType getFileType(String filePath) {
		enFileType enType = enFileType.file;
		File f = new File(filePath);
		String mimetype = new MimetypesFileTypeMap().getContentType(f);
		String type = mimetype.split("/")[0];
		String ext = FilenameUtils.getExtension(filePath);
		if (type.equals("image") || ext == "png" || ext == "jpg" || ext == "gif") {
			enType = enFileType.image;
		} else if (type.equals("video") || ext.equals("mp4") || ext.equals("avi") || ext.equals("mov") || ext.contains("wmv")) {
			enType = enFileType.video;
		}

		else if (FilenameUtils.getExtension(filePath) == "xls") {
			enType = enFileType.excel;
		} else {
			enType = enFileType.file;
		}

		return enType;
	}

	/**
	 * 텍스트 파일 데이터 읽기 인터페이스
	 * 
	 * @author : jsh
	 * @Date : 2020. 4. 3.
	 * @Version :
	 */
	public interface textfileToArrayListCallBack {
		public void result(Map<String, Object> map) throws Exception;
	}

	/**
	 * 텍스트 파일 데이터 읽기 콜백
	 */
	private textfileToArrayListCallBack callback;

	public void setCallBack(textfileToArrayListCallBack callback) {
		this.callback = callback;
	}

	/**
	 * 텍스트파일 데이터 읽기 실행
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public String textfileToArrayList(String filePath) throws Exception {
		return this.textfileToArrayList(filePath, StandardCharsets.UTF_8);
	}
	public String textfileToArrayList(String filePath, Charset charset) throws Exception {
		return this.textfileToArrayList(filePath, (Pattern.quote("|")), false,  charset);
	}
	public String textfileToArrayList(String filePath, String gubun, Boolean bFirstHead, Charset charset) throws Exception {
		gubun = gubun == null ? "|" : gubun;

		try {
			filePath = (filePath.indexOf("/source/files/") > 0 || filePath.indexOf("\\source\\files\\") > 0) ? filePath :   (Global.getConfigInfo().getFileBasePath() + filePath);
			// 파일 객체 생성
			File file = new File(filePath);
			// 입력 스트림 생성
			FileReader filereader = new FileReader(file);
			// 입력 버퍼 생성
			//BufferedReader bufReader = new BufferedReader(filereader);

			if(charset == null) charset = StandardCharsets.UTF_8;

			BufferedReader bufReader  =  new BufferedReader(new InputStreamReader(new FileInputStream(file),charset.name()));

			String line = "";
			int n = 0;
			List<String> keyList = new ArrayList<String>();
			while ((line = bufReader.readLine()) != null) {
				System.out.println(line);
				if (bFirstHead && n == 0) {
					String[] arrColumn = line.split(gubun);
					keyList = Arrays.asList(arrColumn);
				} else {
					LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
					String[] arrData = line.split(gubun);
					for (int i = 0; i < arrData.length; i++) {
						map.put(bFirstHead ? keyList.get(i) : "F" + Integer.toString(i), arrData[i]);
					}
					// rtn.add(map);
					this.callback.result(map);
				}
				n++;
			}
			// .readLine()은 끝에 개행문자를 읽지 않는다.
			bufReader.close();
		} catch (FileNotFoundException e) {
			// TODO: handle exception
		} catch (IOException e) {
			System.out.println(e);
		}

		return "success";
	}

	/**
	 * 디렉토리 안의 파일
	 * @param path
	 * @return
	 */
	public File[] filesInDirectory(String path)
	{
		File dir = new File(path);
		return dir.listFiles();
	}


	/**
	 * 파일유형
	 */
	public enum enFileType {
		image, video, file, excel
	}
}