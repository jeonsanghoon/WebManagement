/**
 * 
 */
package com.mrc.framework;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.*;


/**
 * 엑셀 다운로드 /엑셀 업로드
 * @author jsh
 *
 */
public class ExcelInfo<T> {
	
	private Class<T> type;
	public ExcelInfo() {}
	@Autowired
	public ExcelInfo(Class<T> type)
	{
		this.type = type;
	}

	/**
	 * 리스트를 엑셀로 변환
	 * @param response
	 * @param list
	 * @param headers
	 * @throws Exception
	 */
	public void listToExcel(HttpServletResponse response, List<T> list, List<String> headers) throws Exception {
		listToExcel(response, list, headers, "엑셀다운로드");
	}

	public void listToExcel(HttpServletResponse response, List<T> list, List<String> headers, String title)
			throws Exception {
		listToExcel(response, list, headers, "엑셀다운로드", "excelDownload");
	}

	public void listToExcel(HttpServletResponse response, List<T> list, List<String> headers, String title,
			String fileName) throws Exception {

		try {
			if (Global.getDataInfo().isNullEmpty(title)) {
				title = "엑셀다운로드";
			}
			// 워크북 생성
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(title);
			Row row = null;
			Cell cell = null;
			int rowNo = 0;

			// 테이블 헤더용 스타일
			HSSFCellStyle headStyle = wb.createCellStyle();
			// 배경색은 노란색입니다.
			headStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
			headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			// 데이터는 가운데 정렬합니다.
			headStyle.setAlignment(HorizontalAlignment.CENTER);
			headStyle.setBorderTop(BorderStyle.THIN);
			headStyle.setBorderBottom(BorderStyle.THIN);
			headStyle.setBorderLeft(BorderStyle.THIN);
			headStyle.setBorderRight(BorderStyle.THIN);

			row = sheet.createRow(rowNo++);
			for (int i = 0; i < headers.size(); i++) {
				cell = row.createCell(i);
				cell.setCellStyle(headStyle);
				cell.setCellValue(headers.get(i));
			}

			List<LinkedHashMap<String, Object>> maplist = Global.getDataInfo().convertListToMap(list);
			for (int i = 0; i < maplist.size(); i++) {

				row = sheet.createRow(rowNo++);
				@SuppressWarnings("rawtypes")
				Set set = maplist.get(i).entrySet();
				@SuppressWarnings("rawtypes")
				Iterator iterator = set.iterator();

				int j = 0;

				while (iterator.hasNext()) {
					// 데이터용 경계 스타일 테두리만 지정
					HSSFCellStyle bodyStyle = wb.createCellStyle();
					bodyStyle.setBorderTop(BorderStyle.THIN);
					bodyStyle.setBorderBottom(BorderStyle.THIN);
					bodyStyle.setBorderLeft(BorderStyle.THIN);
					bodyStyle.setBorderRight(BorderStyle.THIN);

					@SuppressWarnings("rawtypes")
					Map.Entry entry = (Map.Entry) iterator.next();
					@SuppressWarnings("unused")
					String key = (String) entry.getKey();
					Object obj = entry.getValue();

					// String value = (String) (entry.getValue() == null ? null : entry.getValue().toString());
					cell = row.createCell(j);

					if (obj instanceof Integer) {
						cell.setCellValue(Integer.parseInt(obj.toString()));
						bodyStyle.setAlignment(HorizontalAlignment.RIGHT);
					} else if (obj instanceof Double) {
						cell.setCellValue(Double.parseDouble(obj.toString()));

						bodyStyle.setAlignment(HorizontalAlignment.RIGHT);
					} else if (obj instanceof Float) {
						cell.setCellValue(Float.parseFloat(obj.toString()));
						bodyStyle.setAlignment(HorizontalAlignment.RIGHT);
					} else if (obj instanceof String || obj instanceof String[]) {
						cell.setCellValue((obj == null) ? "" : obj.toString());
						bodyStyle.setAlignment(HorizontalAlignment.LEFT);
					} else if (obj instanceof Date) {

						cell.setCellValue((Date) obj);
						bodyStyle.setAlignment(HorizontalAlignment.CENTER);
					} else {
						cell.setCellValue((obj == null) ? "" : obj.toString());
						bodyStyle.setAlignment(HorizontalAlignment.CENTER);
					}

					cell.setCellStyle(bodyStyle);

					j++;
				}
			}
			// 컨텐츠 타입과 파일명 지정
			if (Global.getDataInfo().isNullEmpty(fileName)) {
				fileName = "excelDownload";
			}

			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
			// 엑셀 출력
			wb.write(response.getOutputStream());
			wb.close();
		} catch (Exception ex) {
			throw ex;
		}
	}

	public interface IExcelToMapCallBack {
	    public void result(Map<String, Object> map);
	}
	
	private IExcelToMapCallBack callback;
	public void setCallBack(IExcelToMapCallBack callback) {
        this.callback = callback;
    }
	/**
	 * 엑셀 데이터 추출
	 * 
	 * @param excelFile
	 * @return
	 * @throws Exception
	 */
	public String convertExcelToList(String excelFile) throws Exception {
		
		try {
			Workbook wbs = WorkbookFactory.create(new FileInputStream(excelFile));
			Sheet sheet = (Sheet) wbs.getSheetAt(0);

			return this.convertExcelToMapList(sheet);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("오류가 있는 데이터가 있습니다." + e.getMessage());
		}
	}

	private String convertExcelToMapList(Sheet sheet) {
		if (sheet.getLastRowNum() > 0) {
			String className = "";
					try {
						className = newIstance().getClass().getName();
					}catch(Exception e)
					{
						
					}
			
			Row firstrow = sheet.getRow(0);
			;
			for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				int maxColIx = row.getLastCellNum();
				LinkedHashMap<String, Object> resultMap = new LinkedHashMap<String, Object>();
				for (int colIx = row.getFirstCellNum(); colIx < maxColIx; colIx++) {
					String columnName = "";
					Cell cell = row.getCell(colIx);
					
					if (className == "com.mrc.db.dto.common.ExcelUploadData") {
						columnName = "F" + Integer.toString(colIx);
						if (cell.getCellType() == CellType._NONE || cell.getCellType() == CellType.ERROR)
							resultMap.put(columnName, null);
						else if (cell.getCellType() == CellType.BLANK)
							resultMap.put(columnName, "");
						else if (cell.getCellType() == CellType.FORMULA)
							resultMap.put(columnName, cell.getCellFormula() + "");
						else if (cell.getCellType() == CellType.BOOLEAN)
							resultMap.put(columnName, cell.getBooleanCellValue() + "");
						else if (cell.getCellType() == CellType.NUMERIC)
							resultMap.put(columnName, cell.getNumericCellValue() + "");
						//else if (cell.getCellType() == CellType.STRING)
						//	resultMap.put(columnName, cell.getStringCellValue());
						else
							resultMap.put(columnName, cell.getStringCellValue());
					} else {
						columnName = firstrow.getCell(colIx).toString();
						if (cell.getCellType() == CellType._NONE || cell.getCellType() == CellType.ERROR)
							resultMap.put(columnName, null);
						else if (cell.getCellType() == CellType.BLANK)
							resultMap.put(columnName, "");
						else if (cell.getCellType() == CellType.FORMULA)
							resultMap.put(columnName, cell.getCellFormula());
						else if (cell.getCellType() == CellType.BOOLEAN)
							resultMap.put(columnName, cell.getBooleanCellValue());
						else if (cell.getCellType() == CellType.NUMERIC)
							resultMap.put(columnName, cell.getNumericCellValue());
						//else if (cell.getCellType() == CellType.STRING)
						//	resultMap.put(columnName, cell.getStringCellValue());
						else
							resultMap.put(sheet.getRow(0).getCell(colIx).toString(), cell.getStringCellValue());
					}
				}
				//resultList.add(this.convertMapToObject(resultMap, cond));
				this.callback.result(resultMap);
			}
		}
		return "";
	}
	private T newIstance()
	{
		
		try {
			return this.type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("rawtypes")
	public T convertMapToObject(LinkedHashMap<String, Object> map) {
		String keyAttribute = null;
		String setMethodString = "set";
		String methodString = null;
		Iterator itr = map.keySet().iterator();
	    T obj = newIstance();
	    
	   
		while (itr.hasNext()) {
			keyAttribute = (String) itr.next();
			methodString = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);
			Method[] methods;
			
			methods = obj.getClass().getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				if (methodString.equals(methods[i].getName())) {
					try {
						methods[i].invoke(obj, map.get(keyAttribute));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return obj;
	}
}