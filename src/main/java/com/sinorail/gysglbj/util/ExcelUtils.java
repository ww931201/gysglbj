package com.sinorail.gysglbj.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jfinal.plugin.activerecord.Record;

public class ExcelUtils {
	
	/**
	 * 对外提供读取excel 的方法
	 * @param file
	 * @param startRowNum 起始行数,从0开始
	 * @return
	 * @throws IOException
	 */
    public static List<List<Object>> readExcel(File file, Integer startRowNum) throws IOException {
        String fileName = file.getName();
        String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName.substring(fileName.lastIndexOf(".") + 1);
        if ("xls".equals(extension)) {
            return readHSSFExcel(file, startRowNum);
        } else if ("xlsx".equals(extension)) {
            return readXSSFExcel(file, startRowNum);
        } else {
            throw new IOException("不支持的文件类型");
        }
    }

    /**
     *  读取Office HSSF excel
     * @param file
     * @param startRowNum 起始行数,从0开始
     * @return
     */
	private static List<List<Object>> readHSSFExcel(File file, Integer startRowNum)  {

        //创建一个HSSF workbook
        POIFSFileSystem fs;
        
        List<List<Object>> list = new LinkedList<List<Object>>();
        
		try {
			
			fs = new POIFSFileSystem(file);
			
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			
			try {
				//System.out.println(file.getPath()+file.getName());
				
				for (int k = 0; k < wb.getNumberOfSheets(); k++) {
					
					HSSFSheet sheet = wb.getSheetAt(k);
					int rows = sheet.getPhysicalNumberOfRows();
					
					//System.out.println("Sheet " + k + " \"" + wb.getSheetName(k) + "\" has " + rows + " row(s).");
					
					
					for (int r = startRowNum; r < rows; r++) {
						HSSFRow row = sheet.getRow(r);
						if (row == null) {
							continue;
						}

						List<Object> linked = new LinkedList<Object>();
						//System.out.println("\nROW " + row.getRowNum() + " has " + row.getPhysicalNumberOfCells() + " cell(s).");
						for (int c = 0; c < row.getLastCellNum(); c++) {
							HSSFCell cell = row.getCell(c);
							String value1;
							Object value = null;
							if(cell != null) {
								switch (cell.getCellTypeEnum()) {

									case FORMULA:
										value1 = "FORMULA value=" + cell.getCellFormula();
										value = cell.getCellFormula();
										break;

									case NUMERIC:
										value1 = "NUMERIC value=" + cell.getNumericCellValue();
										value = cell.getNumericCellValue();
										break;

									case STRING:
										value1 = "STRING value=" + cell.getStringCellValue();
										value = cell.getStringCellValue();
										break;

									case BLANK:
										value1 = "<BLANK>";
										value = null;
										break;

									case BOOLEAN:
										value1 = "BOOLEAN value-" + cell.getBooleanCellValue();
										value = cell.getBooleanCellValue();
										break;

									case ERROR:
										value1 = "ERROR value=" + cell.getErrorCellValue();
										value = cell.getErrorCellValue();
										break;

									default:
										value1 = "UNKNOWN value of type " + cell.getCellTypeEnum();
										value = cell.getCellTypeEnum();
								}
								//System.out.print("CELL col=" + cell.getColumnIndex() + " VALUE="+ value1);
							}
							linked.add(value);
						}
						list.add(linked);
						//System.out.println();
					}
				}
			} finally {
				wb.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
    }

	/**
	 * 读取Office XSSF excel
	 * @param file
	 * @return
	 */
    private static List<List<Object>> readXSSFExcel(File file, Integer startRowNum) {
	
	    //创建一个XSSF workbook
	    
	    List<List<Object>> list = new LinkedList<List<Object>>();
	    
		try {
			
			XSSFWorkbook wb = new XSSFWorkbook(file);
			
			try {
				//System.out.println(file.getPath()+file.getName());
				
				for (int k = 0; k < wb.getNumberOfSheets(); k++) {
					
					XSSFSheet sheet = wb.getSheetAt(k);
					int rows = sheet.getPhysicalNumberOfRows();
					
					//System.out.println("Sheet " + k + " \"" + wb.getSheetName(k) + "\" has " + rows + " row(s).");
					
					
					for (int r = startRowNum; r < rows; r++) {
						XSSFRow row = sheet.getRow(r);
						if (row == null) {
							continue;
						}
	
						List<Object> linked = new LinkedList<Object>();
						//System.out.println("\nROW " + row.getRowNum() + " has " + row.getPhysicalNumberOfCells() + " cell(s).");
						for (int c = 0; c < row.getLastCellNum(); c++) {
							XSSFCell cell = row.getCell(c);
							String value1;
							Object value = null;
							if(cell != null) {
								switch (cell.getCellTypeEnum()) {
	
									case FORMULA:
										value1 = "FORMULA value=" + cell.getCellFormula();
										value = cell.getCellFormula();
										break;
	
									case NUMERIC:
										value1 = "NUMERIC value=" + cell.getNumericCellValue();
										value = cell.getNumericCellValue();
										break;
	
									case STRING:
										value1 = "STRING value=" + cell.getStringCellValue();
										value = cell.getStringCellValue();
										break;
	
									case BLANK:
										value1 = "<BLANK>";
										value = null;
										break;
	
									case BOOLEAN:
										value1 = "BOOLEAN value-" + cell.getBooleanCellValue();
										value = cell.getBooleanCellValue();
										break;
	
									case ERROR:
										value1 = "ERROR value=" + cell.getErrorCellValue();
										value = cell.getErrorCellValue();
										break;
	
									default:
										value1 = "UNKNOWN value of type " + cell.getCellTypeEnum();
										value = cell.getCellTypeEnum();
								}
								//System.out.print("CELL col=" + cell.getColumnIndex() + " VALUE="+ value1);
							}
							linked.add(value);
						}
						list.add(linked);
						//System.out.println();
					}
				}
			} finally {
				wb.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		return list;
    }
   
    public static void export(List<Record> list, String[] title, String[] field, File file) throws IOException{
    	
		HSSFWorkbook wb = new HSSFWorkbook();
				
		try {
			HSSFSheet s = wb.createSheet();
			
			HSSFCellStyle cs = wb.createCellStyle();
			HSSFCellStyle csbody = wb.createCellStyle();
			HSSFFont f = wb.createFont();
			f.setFontHeightInPoints((short) 12);
			f.setBold(true);
			
			cs.setVerticalAlignment(VerticalAlignment.CENTER);
			csbody.setVerticalAlignment(VerticalAlignment.CENTER);
			

			cs.setFont(f);
			cs.setFillForegroundColor((short) 0xA);
			
			wb.setSheetName(0, "sheet1");
			int rownum;
			//System.out.println("***************"+list.size());
			for (rownum = 0; rownum <= list.size(); rownum++) {
				HSSFRow r = null;
				Record es = null;
				int titleRows = 1;
				if(rownum < titleRows) { //表头
					r = s.createRow(rownum);
					r.setHeight((short) 0x240);
					for (int cellnum = 0; cellnum < title.length; cellnum ++) {
						HSSFCell c = r.createCell(cellnum);
						c.setCellValue(title[cellnum]);
						c.setCellStyle(cs);
						s.setColumnWidth(cellnum, (int) (title[cellnum].length()*1000));
					}
				} else {					
					r = s.createRow(rownum);
					r.setHeight((short) 0x160);
					es = list.get(rownum-titleRows);
					for (int cellnum = 0; cellnum < title.length; cellnum ++) {
						HSSFCell c = r.createCell(cellnum);
						Object value = es.get(field[cellnum]);
						if(value == null) {
							c.setCellValue("");
						} else {
							c.setCellValue(value.toString());
						}
						c.setCellStyle(csbody);
					}
				}
				
			}
			// create a sheet, set its title then delete it
			wb.createSheet();	
			wb.setSheetName(1, "DeletedSheet");
			wb.removeSheetAt(1);
			// end deleted sheet
			FileOutputStream out = new FileOutputStream(file);
			try {
				wb.write(out);
			} finally {
				out.close();
			}
		} finally {
			wb.close();
		}
    }
}
