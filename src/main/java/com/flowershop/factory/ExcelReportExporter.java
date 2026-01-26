package com.flowershop.factory;

import com.flowershop.model.dto.ProductDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelReportExporter implements ReportExporter {

    @Override
    public void export(List<ProductDTO> data, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Danh sách sản phẩm");
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Tên Sản Phẩm", "Danh Mục", "SKU", "Giá Bán (VND)", "Tồn Kho"};
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (ProductDTO p : data) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(p.getProductId());
                row.createCell(1).setCellValue(p.getProductName());
                row.createCell(2).setCellValue(p.getCategoryName());
                row.createCell(3).setCellValue(p.getSku());
                row.createCell(4).setCellValue(p.getPrice().doubleValue());
                row.createCell(5).setCellValue(p.getReorderLevel());
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                System.out.println("Xuất file Excel thành công: " + filePath);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi xuất file Excel: " + e.getMessage());
        }
    }
}