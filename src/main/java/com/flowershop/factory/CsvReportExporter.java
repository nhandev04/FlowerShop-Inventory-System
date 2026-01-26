package com.flowershop.factory;

import com.flowershop.model.dto.ProductDTO;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvReportExporter implements ReportExporter {

    @Override
    public void export(List<ProductDTO> data, String filePath) {
        if (!filePath.endsWith(".csv")) {
            filePath += ".csv";
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("\uFEFFID,Tên Sản Phẩm,Danh Mục,Mã Kho,Giá Bán,Tồn Kho");
            writer.newLine();
            for (ProductDTO p : data) {
                String line = String.format("%d,%s,%s,%s,%.0f,%d",
                        p.getProductId(),
                        escapeSpecialCharacters(p.getProductName()),
                        escapeSpecialCharacters(p.getCategoryName()),
                        p.getSku(),
                        p.getPrice(),
                        p.getReorderLevel());
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Xuất CSV thành công: " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi xuất CSV: " + e.getMessage());
        }
    }

    private String escapeSpecialCharacters(String data) {
        if (data == null) return "";
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}