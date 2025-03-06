package com.hms.service;

import com.hms.entity.cars.Brand;
import com.hms.repository.BrandRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class BulkUploadService {

    private BrandRepository brandRepository;

    public BulkUploadService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Brand> uploadFile(MultipartFile file)throws IOException {

        List<Brand> brands = new ArrayList<>();
        try {
            InputStream fis =  file.getInputStream();
            Workbook book = new XSSFWorkbook(fis);
            Sheet sheet = book.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() > 0) {
                    Brand brand = new Brand();
                    Cell cell = row.getCell(1);
                    if (cell != null) {
                        brand.setName(getCellValue(cell));
                        brands.add(brand);
                        brandRepository.save(brand);
                    }
                }
            }
        }catch (Exception e) {
            throw new RuntimeException("Failed to process Excel file: " + e.getMessage());
        }
            return brands;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue()); // Convert numbers to String
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
