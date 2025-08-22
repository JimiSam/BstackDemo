package com.Nopcommerce.utilities;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excelutils {
	public static String getCellValue(String xl,String Sheet,int r,int c) {
		try {
			FileInputStream fi = new  FileInputStream(xl);
			
			XSSFWorkbook wb = new XSSFWorkbook(fi);
			XSSFCell cell = wb.getSheet(Sheet).getRow(r).getCell(c);
			
			if(cell.getCellType()==CellType.STRING) {
				return cell.getStringCellValue();
			}
			else {
	//			double v = cell.getNumericCellValue();
	//			int val =(int)v;//type casting
	//			return String.valueOf(val);
				return cell.getRawValue();
			}
		}
		catch(Exception e) {
			return "";
		}
			
	}
	
	public static int getRowCount(String xl, String Sheet) {
		try {
			
			FileInputStream fi = new FileInputStream(xl);
			XSSFWorkbook wb = new XSSFWorkbook(fi);
			return wb.getSheet(Sheet).getLastRowNum();
		}
		catch(Exception e){
			return 0;
		}
	}
	public static int getCellCount(String xl, String Sheet,int r) {
		try {
			
			FileInputStream fi = new FileInputStream(xl);
			XSSFWorkbook wb = new XSSFWorkbook(fi);
			return wb.getSheet(Sheet).getRow(r).getLastCellNum();
		}
		catch(Exception e){
			return 0;
		}
	}
}
