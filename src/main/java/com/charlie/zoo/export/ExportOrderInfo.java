package com.charlie.zoo.export;

import com.charlie.zoo.service.OrderService;
import lombok.AllArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ExportOrderInfo {
    private final OrderService orderService;

    public String generateReport(UUID id){
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet("id");
        XSSFCellStyle style = book.createCellStyle();
        try {
            String filePath = System.getProperty("user.home")+ File.separator+"Кругляк.xlsx";
            File file = new File(filePath);
            FileOutputStream fos = new FileOutputStream(file);
//            book.write(fos);
            fos.close();
            return filePath;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
