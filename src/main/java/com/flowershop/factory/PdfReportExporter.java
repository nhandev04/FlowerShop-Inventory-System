package com.flowershop.factory;

import com.flowershop.model.dto.ProductDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class PdfReportExporter implements ReportExporter {

    @Override
    public void export(List<ProductDTO> data, String filePath) {
        if (!filePath.endsWith(".pdf")) {
            filePath += ".pdf";
        }

        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            BaseFont bf = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fontTitle = new Font(bf, 18, Font.BOLD);
            Font fontHeader = new Font(bf, 12, Font.BOLD);
            Font fontData = new Font(bf, 11, Font.NORMAL);
            Paragraph title = new Paragraph("DANH SÁCH SẢN PHẨM", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1, 4, 3, 3, 2, 2});

            String[] headers = {"ID", "Tên Sản Phẩm", "Danh Mục", "SKU", "Giá", "Tồn"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, fontHeader));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setPadding(5);
                table.addCell(cell);
            }

            DecimalFormat df = new DecimalFormat("#,###");
            for (ProductDTO p : data) {
                table.addCell(new Phrase(String.valueOf(p.getProductId()), fontData));
                table.addCell(new Phrase(p.getProductName(), fontData));
                table.addCell(new Phrase(p.getCategoryName(), fontData));
                table.addCell(new Phrase(p.getSku(), fontData));

                PdfPCell priceCell = new PdfPCell(new Phrase(df.format(p.getPrice()), fontData));
                priceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(priceCell);

                PdfPCell stockCell = new PdfPCell(new Phrase(String.valueOf(p.getReorderLevel()), fontData));
                stockCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(stockCell);
            }

            document.add(table);
            System.out.println("Xuất PDF thành công: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi xuất PDF (Kiểm tra lại Font chữ): " + e.getMessage());
        } finally {
            document.close();
        }
    }
}