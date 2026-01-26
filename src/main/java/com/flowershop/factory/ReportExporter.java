package com.flowershop.factory;

import com.flowershop.model.dto.ProductDTO;
import java.util.List;

public interface ReportExporter {
    void export(List<ProductDTO> data, String filePath);
}