package com.mrc.framework;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFReader.SheetIterator;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;



/**
 * <pre>
 *  참조 사이트 : https://poi.apache.org/components/spreadsheet/index.html
 *  목적 : 엑셀의 대용량 데이터 처리시 메모리 최적으로 사용
 *  확장자 : .xlsx
 *  API 적용방식 (eventmodel) : XSSF(.xlsx)의 스트리밍(ala SAX) 읽기 전용
 *  사용법 [ ExcelParser excelParser = new ExcelParser(new ExcelRowDataHandler() {
 *  public void rowData(int rowNum, Map<String, String> valueByHeader, List<String> headerList) {
 *  System.out.println("rowNum: " + rowNum + ", ValueInCellByHeader: " + valueByHeader + ", headerList: " + headerList);
 *  // DB 처리 구현
 *  }
 *  });
 *  // file, startRow, startCell
 *  excelParser.parseExcel(String fileUrl);
 *  ]
 * </pre>
 * @author sdh, jsh
 * @since 2020.04.03 완료
 */
public class ExcelParser {
	
	public interface IExcelRowDataHandler {
		/**
		 * <pre>
		 *    rowNum row 위치
		 *    valueByHeader row 별 header, value 예) {header=cellvalue, header1=cellvalue1}
		 *    headerList header array list 예) [header, header1]
		 * </pre>
		 * 
		 * @param rowNum
		 * @param valueByHeader
		 * @param headerList
		 * @throws Exception 
		 */
		void rowData(int rowNum, Map<String, Object> valueByHeader, List<String> headerList) throws Exception;
	}
	IExcelRowDataHandler callBack;
	public void setCallback(IExcelRowDataHandler callback)
	{
		this.callBack = callback;
	}
	/** SLF4J. */
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * <pre>
	 * 	ExcelParser 생성자 선언
	 * </pre>
	 * @param excelRowDataHandler callback interface
	 */
	public ExcelParser() {}

	/**
	 * <pre>
	 * 	엑셀 파서 error
	 * </pre>
	 */
	@SuppressWarnings("serial")
	public static class ParserException extends RuntimeException {
		public ParserException(String message) {
			super(message);
		}

		public ParserException(String message, Throwable t) {
			super(message, t);
		}
	}

	/**
	 * <pre>
	 * 	엑셀 파서에 대한 data missing 에러 메시지 전달
	 * </pre>
	 */
	@SuppressWarnings("serial")
	public static final class MissingDataException extends ParserException {
		public MissingDataException(String message) {
			super(message);
		}
	}

	/**
	 * 엑셀 Parsing 설정 및 대용량 업로드 파서 방식 : SAX 방식 (조건-읽기전용)
	 * 
	 * <pre>
	 *  multipartFile 역할 : File 객체 또는 MultipartFile 사용
	 * </pre>
	 * @param file 업로드 file 객체
	 * @param startRow row 시작위치
	 * @param startCell cell 시작위치
	 * @throws ParserConfigurationException
	 * @throws FileNotFoundException 
	 */
	
	public void parseExcel(String pathUrl) throws ParserConfigurationException, FileNotFoundException {
		 File file = ResourceUtils.getFile(pathUrl);
		this.parseExcel(file, 0, -1);
	}
	
	public void parseExcel(String pathUrl, int startRow, int startCell) throws ParserConfigurationException, FileNotFoundException {
		 File file = ResourceUtils.getFile(pathUrl);
		this.parseExcel(file, startRow, startCell);
	}
	
	public void parseExcel(File file) throws ParserConfigurationException {
		this.parseExcel(file, 0, -1);
	}
	public void parseExcel(File file, int startRow, int startCell) throws ParserConfigurationException {
		try {
			// OPCPackage 파일을 읽거나 쓸수있는 상태의 컨테이너를 생성함
			OPCPackage opc = OPCPackage.open(file);
			
			// 메모리를 적게 사용하며 sax형식을 사용할수 있게 함
			XSSFReader xssfReader = new XSSFReader(opc);

			// org.apache.poi.xssf.model.StylesTable
			// 읽어온 Table에 적용되어 있는 Style
			StylesTable styles = xssfReader.getStylesTable();

			// org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable
			// 파일의 데이터를 Table형식으로 읽을 수 있도록 지원
			ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(opc);

			// 엑셀의 시트를 가져오기입니다.
			// 여러개일경우 while문으로 추출하셔야 됩니다.
			// Sheet 별 collection 으로 분할해서 가져옴
			SheetIterator itr = (SheetIterator) xssfReader.getSheetsData();

			// sheet 개수만큼 처리
			while (itr.hasNext()) {
				InputStream inputStream = itr.next();

				// XMLHandler 생성
				// new XSSFSheetXMLHandler(StylesTable styles, ReadOnlySharedStringsTable strings, SheetContentsHandler data, boolean formulasNotResults)
				// Sheet의 row 및 cell 이벤트를 생성
				ContentHandler excelSheetHandler = new XSSFSheetXMLHandler(styles, strings, new SheetContentsHandler() {

					// 빈 값을 체크하기 위해 사용할 셀번호
					// 기본값 셋팅 '-1'
					private int currentCol = -1;
					// 현재 읽고 있는 Cell의 Col
					@SuppressWarnings("unused")
					private int currRowNum = 0;

					// cell 호출시 마다 쌓아놓을 1 row List
					private List<String> row = new ArrayList<String>();
					// Header 정보를 입력
					private List<String> header = new ArrayList<String>();
					// 실제 엑셀을 파싱해서 담아지는 데이터
					// 예) {header=data, header1=data1}
					private Map<String, Object> cellValueByHeader = new HashMap<String, Object>();

					@Override
					public void headerFooter(String text, boolean isHeader, String tagName) {
						// 사용안함
					}

					@Override
					public void startRow(int rowNum) {
						rowNum = startRow;
						
						// empty 값을 체크하기 위한 초기 셋팅값
						this.currentCol = startCell;
						this.currRowNum = rowNum;
					}

					@Override
					public void cell(String cellRef/* e.g. C23 */, String formattedVal/* Value in the Cell */, XSSFComment comment) {
						int iCol = (new CellReference(cellRef)).getCol();
						int emptyCol = iCol - currentCol - 1;

						// 읽은 Cell의 번호를 이용하여 빈 Cell 자리에 빈값을 강제로 저장시켜줌
						for (int i = 0; i < emptyCol; i++) {
							row.add("");
						}
						currentCol = iCol;
						row.add(formattedVal);
					}

					@SuppressWarnings("unused")
					@Override
					public void endRow(int rowNum) {
						if (rowNum == startRow) {
							header = new ArrayList<String>(row);
						} else if (rowNum > startRow) {
							// 헤더의 길이가 현재 로우보다 더 길다면 cell의 뒷자리가 빈값임으로 해당값만큼 공백
							if (row.size() < header.size()) {
								for (int i = row.size(); i < header.size(); i++) {
									row.add("");
									throw new MissingDataException("Missing header(s): " + itr.getSheetName());
								}
							}

							// 실제 엑셀을 파싱해서 담아지는 데이터 객체 선언
							cellValueByHeader = new HashMap<String, Object>();
							// 엑셀 헤더=셀 데이터 매핑
							for (int i = 0; i < header.size(); i++) {
								// header 헤더 데이터
								// row 셀의 row 데이터
								cellValueByHeader.put(header.get(i).toLowerCase(), row.get(i));
							}
						}

						// 엑셀 row 데이터 건별 핸들러 하기위한 callback interface 담아서 처리
						try {
							callBack.rowData(rowNum, cellValueByHeader, header);
						} catch (Exception e) {
							logger.debug("{}", e.fillInStackTrace());
						}

						// row/cellValueByHeader clear
						row.clear();
						cellValueByHeader.clear();
					}
				}, false/* Take formulas directly, not their results */);

				// SAX 형식의 XMLReader 생성
				XMLReader xmlReader = XMLReaderFactory.createXMLReader();
				xmlReader.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

				// XMLReader에 재정의하여 구현한 인터페이스 설정
				xmlReader.setContentHandler(excelSheetHandler);
				// 파싱하여 처리
				xmlReader.parse(new InputSource(inputStream));
				
				inputStream.close();
			}
			
			opc.close();
		} catch (IOException | OpenXML4JException | ParserException | SAXException e) {
			throw (e instanceof ParserException) ? (ParserException) e : new ParserException(e.getMessage(), e);
		}
	}
}
