package com.bda.analyticsplatform.models;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import com.bda.analyticsplatform.utils.BDAConstants;
import com.google.gson.Gson;

public class ExcelFileDSObject extends FileDSObject implements Serializable {

	private static final long serialVersionUID = 8961612687893160132L;
	private int rowIndex = 0;

	public ExcelFileDSObject(String fileName, String dbType, String dataSourceName, MultipartFile fileLocation,
			String dsFileType, String dsType) throws Exception {
		super(fileName, dbType, dataSourceName, fileLocation, dsFileType, dsType);
		ByteArrayInputStream in = new ByteArrayInputStream(fileLocation.getBytes());
		Workbook workbook = new HSSFWorkbook(in);

        FileOutputStream out = new FileOutputStream((BDAConstants.DATA_FILES_PATH + dataSourceName + "File-1.txt"));
        workbook.write(out);
		setFileLocation(BDAConstants.DATA_FILES_PATH + dataSourceName + "File-1.txt");

	}

	public ExcelFileDSObject(String fileName, String dbType, String dsName, String fileLocation,
			Map<String, List<String>> fileColumns, String dsFileType, String dsType) throws Exception {
		super(fileName, dbType, dsName, fileLocation, fileColumns, dsFileType, dsType);
	}

	@Override
	public void populateFileColumns() throws Exception {
		HSSFSheet sheet = (HSSFSheet) initializeReader();
		List<String> columns = new ArrayList<String>();
		HSSFRow row = sheet.getRow(rowIndex);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			System.out.println(row.getCell(i).getStringCellValue());
			columns.add(row.getCell(i).getStringCellValue());
		}
		System.out.println(columns);
		getFileColumns().put("File-1", columns);
	}

	@Override
	public String[] readRecord(Object sheet) throws Exception {
		if (rowIndex == ((HSSFSheet) sheet).getLastRowNum() + 1) {
			return null;
		} else {
			HSSFRow row = ((HSSFSheet) sheet).getRow(rowIndex);
			List<String> columns = new ArrayList<String>();
			for (int i = 0; i < row.getLastCellNum(); i++) {
				row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
				columns.add(row.getCell(i).getStringCellValue());
			}
			rowIndex++;
			return columns.toArray(new String[0]);
		}
	}

	@Override
	public String toString() {

		return new Gson().toJson(this);
	}


	@Override
	public Object initializeReader() throws Exception {
		rowIndex = 0;
		System.out.println(getFileLocation());
		FileInputStream file = new FileInputStream(new File(super.getFileLocation()));
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheetAt(0);
		return sheet;
	}

	@Override
	public String getColumns(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeReader(Object workbook) throws Exception {
		((HSSFWorkbook) workbook).close();
	}

}
