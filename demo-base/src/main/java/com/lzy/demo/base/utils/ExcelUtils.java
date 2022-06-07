package com.lzy.demo.base.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.common.POIFSConstants;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExcelUtils {
    //xls工作表最大的行数
    private static final int XSL_MAX_SHEET_ROW_SIZE = 65536;

    /**
     * 获取工作薄
     *
     * @param file the file
     * @return the work book
     * @throws IOException the io exception
     */
    public static Workbook getWorkBook(File file) throws IOException {
        return getWorkBook(new FileInputStream(file));
    }

    /**
     * 获取工作薄
     *
     * @param isSrc the is src
     * @return the work book
     * @throws IOException the io exception
     */
    public static Workbook getWorkBook(InputStream isSrc) throws IOException {
        InputStream is;
        //使用bufferedInputStream支持标志流
        if (!isSrc.markSupported()) {
            is = new BufferedInputStream(isSrc);
        } else {
            is = isSrc;
        }
        is.mark(POIFSConstants.SMALLER_BIG_BLOCK_SIZE);
        try {
            //xlsx
            return new XSSFWorkbook(is);
        } catch (Exception e) {
            //xls
            is.reset();
            return new HSSFWorkbook(is);
        } finally {
            is.close();
        }
    }

    /**
     * 读取excel的内容
     *
     * @param file the file
     * @return the list
     * @throws IOException the io exception
     */
    public static List<List<String>> readExcel(File file) throws IOException {
        return getExcelContent(getWorkBook(file));
    }

    /**
     * 读取excel的内容
     *
     * @param isSrc the is src
     * @return the list
     * @throws IOException the io exception
     */
    public static List<List<String>> readExcel(InputStream isSrc) throws IOException {
        return getExcelContent(getWorkBook(isSrc));
    }

    /**
     * 读取excel的内容
     *
     * @param file   the file
     * @param header the header
     * @return the list
     * @throws IOException the io exception
     */
    public static List<List<String>> readExcel(File file, List<String> header) throws IOException {
        return getExcelContent(getWorkBook(file), header);
    }


    /**
     * 读取excel的内容
     *
     * @param isSrc  the is src
     * @param header the header
     * @return the list
     * @throws IOException the io exception
     */
    public static List<List<String>> readExcel(InputStream isSrc, List<String> header) throws IOException {
        return getExcelContent(getWorkBook(isSrc), header);
    }

    /**
     * 获取工作薄的内容(不获取标题)
     *
     * @param book the book
     * @return the excel content
     */
    public static List<List<String>> getExcelContent(Workbook book) {
        return getExcelContent(book, null);
    }

    /**
     * 按照给定的标题顺序,获取工作薄的内容
     *
     * @param book    the book
     * @param pHeader 给定的标题
     * @return the excel content
     */
    public static List<List<String>> getExcelContent(Workbook book, List<String> pHeader) {
        List<Integer> getCellSort = null;
        if (pHeader != null && !pHeader.isEmpty()) {
            List<String> header = getExcelHeader(book);
            getCellSort = pHeader.stream().map(header::indexOf).collect(Collectors.toList());
        }
        List<Integer> finalGetCellSort = getCellSort;
        return IntStream.range(0, book.getNumberOfSheets()).mapToObj(book::getSheetAt)
                .filter(Objects::nonNull).map(sheet ->
                        IntStream.range(1, sheet.getLastRowNum() + 1).mapToObj(sheet::getRow)
                                .filter(Objects::nonNull).map(row -> {
                                            if (finalGetCellSort != null) {
                                                return finalGetCellSort.stream().map(row::getCell)
                                                        .filter(Objects::nonNull)
                                                        .map(ExcelUtils::getCellValue)
                                                        .collect(Collectors.toList());
                                            } else {
                                                return IntStream.range(0, row.getLastCellNum()).mapToObj(row::getCell)
                                                        .filter(Objects::nonNull)
                                                        .map(ExcelUtils::getCellValue)
                                                        .collect(Collectors.toList());
                                            }
                                        }
                                ).collect(Collectors.toList()))
                .reduce((left, right) -> {
                    left.addAll(right);
                    return left;
                }).orElseGet(ArrayList::new);
    }

    /**
     * 获取excel的标题
     *
     * @param book the book
     * @return the excel header
     */
    public static List<String> getExcelHeader(Workbook book) {
        Row row = book.getSheetAt(0).getRow(0);
        return IntStream.range(0, row.getLastCellNum())
                .mapToObj(row::getCell)
                .filter(Objects::nonNull)
                .map(ExcelUtils::getCellValue)
                .collect(Collectors.toList());
    }


    /**
     * 把数据写入Excel
     *
     * @param file      the file
     * @param data      <code>List<List<Object>></code>或者<code> List<Map<String,Object>></code>
     * @param header    the header
     * @param sheetName the sheet name
     * @throws IOException            the io exception
     * @throws InvalidFormatException the invalid format exception
     */
    public static void writeExcel(File file, List<Map<String, Object>> data, LinkedHashMap<String, String> header, String sheetName) throws IOException, InvalidFormatException {
        writeExcel(new FileOutputStream(file), data, header, sheetName, getExcelType(file));
    }

    /**
     * 把数据写入Excel
     * 要保证数据的顺序和标题的顺序是一致的
     *
     * @param file      the file
     * @param data      <code>List<List<Object>></code>或者<code> List<Map<String,Object>></code>
     * @param header    the header
     * @param sheetName the sheet name
     * @throws IOException            the io exception
     * @throws InvalidFormatException the invalid format exception
     */
    public static void writeExcel(File file, List<List<Object>> data, List<String> header, String sheetName) throws IOException, InvalidFormatException {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        if (header != null) {
            IntStream.range(0, header.size()).boxed().forEach(i -> {
                map.put(i.toString(), header.get(i));
            });
        }
        writeExcel(new FileOutputStream(file), data, map, sheetName, getExcelType(file));
    }

    private static void writeExcel(OutputStream os, List<?> data, LinkedHashMap<String, String> header, String sheetName, ExcelType excelType) throws IOException {
        Workbook workbook = getWorkBook(excelType);
        //是否有标题头
        boolean hasHeader = header != null && !header.isEmpty();
        //需要几张工作表
        int sheetCount = 1;
        //sheet最大的行数
        int maxRowSize = data.size();
        if (workbook instanceof HSSFWorkbook) {
            maxRowSize = hasHeader ? XSL_MAX_SHEET_ROW_SIZE - 1 : XSL_MAX_SHEET_ROW_SIZE;
            sheetCount = (int) Math.ceil(data.size() * 1.0 / maxRowSize);
        }
        if (sheetCount == 1) {
            Sheet sheet = workbook.createSheet(sheetName);
            setExcelHeader(workbook, sheet, header);
            setExcelContent(sheet, data, header, 0, data.size());
        } else {
            for (int i = 1; i <= sheetCount; i++) {
                Sheet sheet = workbook.createSheet(sheetName + i);
                setExcelHeader(workbook, sheet, header);
                int startIndex = (i - 1) * maxRowSize;
                int endIndex = i == sheetCount ? data.size() : startIndex + maxRowSize;
                setExcelContent(sheet, data, header, startIndex, endIndex);
            }
        }
        workbook.write(os);
    }


    private static ExcelType getExcelType(File file) {
        if (file.getName().endsWith(ExcelType.XLS.getValue())) {
            return ExcelType.XLS;
        } else {
            return ExcelType.XLSX;
        }
    }


    private static Workbook getWorkBook(ExcelType excelType) {
        if (excelType == ExcelType.XLS) {
            return new HSSFWorkbook();
        } else {
            return new XSSFWorkbook();
        }
    }


    private static String getCellValue(Cell cell) {
        if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.NUMERIC) {
            //判断日期
            if (DateUtil.isCellDateFormatted(cell)) {
                return DateUtils.formatSimplateDateTime(cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()));
            }
            return NumberToTextConverter.toText(cell.getNumericCellValue());
        } else {
            return cell.getStringCellValue();
        }
    }


    private static void setExcelHeader(Workbook workbook, Sheet sheet, LinkedHashMap<String, String> header) {
        if (header != null && !header.isEmpty()) {
            CellStyle cellStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            cellStyle.setFont(font);
            Row row = sheet.createRow(0);
            int i = 0;
            for (Map.Entry<String, String> entry : header.entrySet()) {
                Cell cell = row.createCell(i++);
                cell.setCellValue(entry.getValue());
                cell.setCellStyle(cellStyle);
            }
        }
    }

    private static void setExcelContent(Sheet sheet, List<?> data, LinkedHashMap<String, String> header, int startIndex, int endIndex) {
        int size = endIndex - startIndex;
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            Object object = data.get(startIndex++);
            if (object instanceof Map) {
                int cellIndex = 0;
                Map<String, Object> rowData = (Map<String, Object>) object;
                if (header != null && !header.isEmpty()) {
                    //有标题头
                    Row row = sheet.createRow(rowIndex + 1);
                    for (String key : header.keySet()) {
                        Cell cell = row.createCell(cellIndex++);
                        cell.setCellValue(ConvertUtils.asString(rowData.get(key)));
                    }
                } else {
                    //无标题头
                    Row row = sheet.createRow(rowIndex);
                    for (Map.Entry<String, Object> entry : rowData.entrySet()) {
                        Cell cell = row.createCell(cellIndex++);
                        cell.setCellValue(ConvertUtils.asString(entry.getValue()));
                    }
                }
            } else if (object instanceof List) {
                List<Object> rowData = (List<Object>) object;
                Row row = header != null && !header.isEmpty() ? sheet.createRow(rowIndex + 1) : sheet.createRow(rowIndex);
                IntStream.range(0, rowData.size()).forEach(cellIndex -> {
                    Cell cell = row.createCell(cellIndex);
                    cell.setCellValue(ConvertUtils.asString(rowData.get(cellIndex)));
                });
            }
        }
    }

    public enum ExcelType {

        /**
         * xls 2003版
         */
        XLS("xls"),
        /**
         * xlsx 2007以上版
         */
        XLSX("xlsx");
        private String value;

        ExcelType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
