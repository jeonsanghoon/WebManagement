/**
 * 
 */
package com.mrc.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrc.db.dao.interfaces.GlobalDao;
import com.mrc.db.dto.address.AddressLocation;
import com.mrc.db.dto.address.AddressLocationBase;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.common.UploadFiles;
import com.mrc.db.dto.common.ResultType;
import com.mrc.db.dto.file.FileCond;
import com.mrc.db.dto.file.File;
import com.mrc.db.dto.file.VideoFileCond;
import com.mrc.framework.ExcelInfo;
import com.mrc.framework.ExcelParser;
import com.mrc.framework.FileInfo;
import com.mrc.framework.Global;
import org.apache.commons.io.FilenameUtils;
import org.jcodec.api.JCodecException;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * @author jsh
 *
 */

@RestController
public class FileUploadController extends BaseController {
	
    
	@PostMapping("/file/uploadfilepond")
	public List<UploadFiles> uploadfilepond(@RequestParam MultipartFile[] filepond
			                              , @RequestParam(value= "isThumbnail", required = false) Boolean isThumbnail
			                              , @RequestParam(value= "size", required = false) int size) throws IOException {
		if(isThumbnail != null)
		{
			return Global.getFileInfo().uploadFile(filepond,"", isThumbnail,size);
		}
		else
			return Global.getFileInfo().uploadFile(filepond);
	}
	
	
	@PostMapping("/file/addressAreaexcelUpload")
	public String excelUpload(@RequestParam MultipartFile[] filepond) throws Exception {
		List<UploadFiles> list = Global.getFileInfo().uploadFile(filepond);
		
		@SuppressWarnings("rawtypes")
		ExcelInfo excelUPload = new ExcelInfo();
		excelUPload.setCallBack(new ExcelInfo.IExcelToMapCallBack() {
			@Override
			public void result(Map<String, Object> map) {
				// TODO Auto-generated method stub
				ObjectMapper mapper = new ObjectMapper();
				String json;
				try {
					json = mapper.writeValueAsString(map);
					System.out.println("addressAreaexcelUpload 일반엑셀 업로드 리턴 " +json); 
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		String fileUrl = Global.getConfigInfo().getFileBasePath() + list.get(0).getAttachmentUrl();
		excelUPload.convertExcelToList( fileUrl);
		return "완료";
	}
	@PostMapping("/file/fileupload")
	public List<UploadFiles> fileupload(@RequestParam MultipartFile[] filepond) throws Exception {
		return Global.getFileInfo().uploadFile(filepond);
	}
	@PostMapping("/file/excelUpload")
	public ResultData excelUpload2(@RequestParam MultipartFile[] filepond) throws Exception {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		List<UploadFiles> list = Global.getFileInfo().uploadFile(filepond);
		
		if(list.size()> 0 )
		{
			Global.getExcelParser().setCallback(new ExcelParser.IExcelRowDataHandler() {
				
				@Override
				public void rowData(int rowNum, Map<String, Object> valueByHeader, List<String> headerList) throws Exception {
					// TODO Auto-generated method stub
					ObjectMapper mapper = new ObjectMapper();
					String json = mapper.writeValueAsString(valueByHeader);
					System.out.println("엑셀 업로드 리턴 " +json); 
				}
			});
			String fileUrl = Global.getConfigInfo().getFileBasePath() + list.get(0).getAttachmentUrl();
			Global.getExcelParser().parseExcel(fileUrl);
		}
		return rtn;
	}

	
	@PostMapping("/file/uploadimge")
	public List<UploadFiles> uploadimage(@RequestParam MultipartFile[] image) throws IOException {
		return Global.getFileInfo().uploadFile(image,"image");
	}
	
	@PostMapping("/file/multiimgprocess")
	public List<UploadFiles> multiimgprocess(@RequestParam(required=false) MultipartFile[] add , @RequestParam(required=false) List<String> del) throws IOException {
		return Global.getFileInfo().multiImgProcess(add, del);
	}
	
	@ResponseBody
	@PostMapping("/file/deletefile")
	public ResultData deletefile(@NotNull @RequestBody UploadFiles fileinfo) throws IOException {
		String filePath = Global.getFileInfo().getRealFilePath(fileinfo.getAttachmentUrl());
		
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			if(filePath != "") Global.getFileInfo().fileDelete(filePath);
			filePath =Global.getFileInfo().getRealFilePath(fileinfo.getPickThumb());
			if(filePath != "") Global.getFileInfo().fileDelete(filePath);
			filePath = Global.getFileInfo().getRealFilePath(fileinfo.getThumbNailImage());
			if(filePath != "") Global.getFileInfo().fileDelete(filePath);
			if(fileinfo.getArrThumbNailImage() != null)
			{
				List<String> thumblist = fileinfo.getArrThumbNailImage();
				
				for (String data : thumblist) {
					filePath = Global.getFileInfo().getRealFilePath(data);
					if(filePath != "") Global.getFileInfo().fileDelete(filePath);
				}
			}
		
		}catch(Exception ex) {
			rtn.setResultType(ResultType.Error);
			rtn.setMessage(ex.getMessage());
		}
		return rtn;
	}
	
	@ResponseBody
	@PostMapping("/file/deletefile2")
	public ResultData deletefile2(@RequestBody UploadFiles fileinfo) throws IOException {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		String filePath = fileinfo.getAttachmentUrl();
		filePath = filePath.replace("/files/", "").replace("_takeP", "").replace("thumb_", "");
		
		String[] arr2 = filePath.split("\\/");
		String Extention =FilenameUtils.getExtension(filePath);
		String DirName = arr2[0];
		if(filePath != "") Global.getFileInfo().fileDelete(Global.getFileInfo().getRealFilePath(filePath));
		
		if(Extention.contains("mp4") ||Extention.contains("avi") || Extention.contains("mov") ||Extention.contains("wmv") ) {
			String fileName =filePath.replace("." + Extention, "").replace(DirName + "/","");
			filePath = DirName + "/" + fileName + ".png";
			if(filePath != "") Global.getFileInfo().fileDelete(Global.getFileInfo().getRealFilePath(filePath));
			filePath =  DirName + "/" + "thumb_" +fileName + ".png";
			if(filePath != "") Global.getFileInfo().fileDelete(Global.getFileInfo().getRealFilePath(filePath));
			
			filePath = DirName + "/" + fileName +"_takeP.png";
			if(filePath != "") Global.getFileInfo().fileDelete(Global.getFileInfo().getRealFilePath(filePath));
			for(int i=1; i<=6; i++)
			{
				filePath = DirName + "/thumb_" +fileName +"_" + Integer.toString(i) + ".png";
				if(filePath != "") Global.getFileInfo().fileDelete(Global.getFileInfo().getRealFilePath(filePath));
			}
		}
		else {
			String fileName =filePath.replace("."  + Extention, "").replace(DirName + "/","");
			//filePath = DirName + "/" + fileName + "." + Extention;
			//if(filePath != "") Global.getFileInfo().FileDelete(Global.getFileInfo().getRealFilePath(filePath));
			filePath = DirName + "/thumb_" + fileName + "." + Extention;
			if(filePath != "") Global.getFileInfo().fileDelete(Global.getFileInfo().getRealFilePath(filePath));
		}
		
		return rtn;
	}
	

	@PostMapping("/file/upload")
	public List<UploadFiles> upload(@RequestParam MultipartFile[] file) throws IOException {
		return Global.getFileInfo().uploadFile(file);
	}
	@RequestMapping("/file/download/{dirpath}/{fileName:.+}")
	public void download(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("dirpath") String dirpath, @PathVariable("fileName") String fileName) throws IOException {
		Global.getFileInfo().downloadFile(response, dirpath, fileName);
	}
	
	@RequestMapping("/filestream/{dirpath}/{fileName:.+}")
	 public StreamingResponseBody stream(HttpServletRequest req
			 								, @PathVariable("dirpath") String dirpath
			 								, @PathVariable("fileName") String fileName) throws Exception {
	  String filePath = dirpath + "/" + fileName; 
      return Global.getFileInfo().stream(filePath);
   }
	
	@PostMapping("/file/getVideoTakePicture")
	public String getVideoTakePicture(@RequestBody VideoFileCond cond) throws IOException, JCodecException
	{
		return Global.getFileInfo().makeVideoPictureToThumbnail(cond);
	}

	@PostMapping("/file/getList")
	public List<File> getFileList(@RequestBody FileCond cond)
	{
		return GlobalDao.getFileDao().getList(cond);
	}


	@PostMapping("/file/delete")
	public ResultData fileDelete(@RequestBody File data)
	{
		return GlobalDao.getFileDao().delete(data);
	}
}
