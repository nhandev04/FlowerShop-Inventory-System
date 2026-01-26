package com.flowershop.factory;

public class ReportFactory {

    public static ReportExporter getExporter(String type) {
        if (type.equalsIgnoreCase("EXCEL")) {
            return new ExcelReportExporter();
        } else if (type.equalsIgnoreCase("PDF")) {
            return new PdfReportExporter();
        } else if (type.equalsIgnoreCase("CSV")) {
            return new CsvReportExporter();
        }
        throw new IllegalArgumentException("Loại báo cáo không hỗ trợ: " + type);
    }
}