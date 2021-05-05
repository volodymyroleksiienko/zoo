package com.charlie.zoo.export;

import com.charlie.zoo.entity.OrderDetails;
import com.charlie.zoo.entity.OrderInfo;
import com.charlie.zoo.entity.PackageType;
import com.charlie.zoo.entity.dto.StatisticDto;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Component
public class ExportStatistic {
    public String generateReport(List<StatisticDto> statisticDtos) {

        XWPFDocument doc = new XWPFDocument();

        CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();
        pageMar.setLeft(BigInteger.valueOf(720L));
        pageMar.setTop(BigInteger.valueOf(1440L));
        pageMar.setRight(BigInteger.valueOf(720L));
        pageMar.setBottom(BigInteger.valueOf(1440L));

//          Create table
        XWPFTable table = doc.createTable();

        XWPFTableRow rowHeader= table.getRow(0);
        rowHeader.getCell(0).addParagraph().setAlignment(ParagraphAlignment.CENTER);
        rowHeader.getCell(0).getParagraphArray(1).createRun().setText("№");

        rowHeader.createCell().addParagraph().setAlignment(ParagraphAlignment.CENTER);
        rowHeader.getCell(1).getParagraphArray(1).createRun().setText("Товар");

        rowHeader.createCell().addParagraph().setAlignment(ParagraphAlignment.CENTER);
        rowHeader.getCell(2).getParagraphArray(1).createRun().setText("Пакування");

        rowHeader.createCell().addParagraph().setAlignment(ParagraphAlignment.CENTER);
        rowHeader.getCell(3).getParagraphArray(1).createRun().setText("Було");

        rowHeader.createCell().addParagraph().setAlignment(ParagraphAlignment.CENTER);
        rowHeader.getCell(4).getParagraphArray(1).createRun().setText("Прийшло");

        rowHeader.createCell().addParagraph().setAlignment(ParagraphAlignment.CENTER);
        rowHeader.getCell(5).getParagraphArray(1).createRun().setText("Продали");

        rowHeader.createCell().addParagraph().setAlignment(ParagraphAlignment.CENTER);
        rowHeader.getCell(6).getParagraphArray(1).createRun().setText("На складі");

        rowHeader.createCell().addParagraph().setAlignment(ParagraphAlignment.CENTER);
        rowHeader.getCell(7).getParagraphArray(1).createRun().setText("Витратили");
        rowHeader.createCell().addParagraph().setAlignment(ParagraphAlignment.CENTER);
        rowHeader.getCell(8).getParagraphArray(1).createRun().setText("Заробили");
        rowHeader.getCell(0).setWidth("7%");
        rowHeader.getCell(1).setWidth("34%");
        rowHeader.getCell(2).setWidth("7%");
        rowHeader.getCell(3).setWidth("7%");
        rowHeader.getCell(4).setWidth("7%");
        rowHeader.getCell(5).setWidth("7%");
        rowHeader.getCell(6).setWidth("7%");
        rowHeader.getCell(7).setWidth("7%");
        rowHeader.getCell(8).setWidth("7%");

        for(int i=0; i<statisticDtos.size();i++){
            XWPFTableRow row = table.createRow();
            StatisticDto st = statisticDtos.get(i);

            row.getCell(0).setText(""+(i+1));
            row.getCell(1).setText(st.getNameOfProduct());
            row.getCell(2).setText(st.getPackageType());
            row.getCell(3).setText(st.getBeforeCount()+"");
            row.getCell(4).setText(st.getGetCount()+"");
            row.getCell(5).setText(st.getSellCount()+"");
            row.getCell(6).setText(st.getTotalCount()+"");
            row.getCell(7).setText(st.getSpendMoney()+"");
            row.getCell(8).setText(st.getEarnMoney()+"");




            row.getCell(0).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
            row.getCell(1).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
            row.getCell(2).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
            row.getCell(3).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
            row.getCell(4).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
            row.getCell(5).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
            row.getCell(6).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
            row.getCell(7).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
            row.getCell(8).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
        }

//        Total row
        XWPFTableRow totalRow = table.createRow();

        BigDecimal earn = statisticDtos.stream()
                .flatMap(statisticDto -> Stream.of(statisticDto.getEarnMoney()))
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal(0));

        BigDecimal spend = statisticDtos.stream()
                .flatMap(statisticDto -> Stream.of(statisticDto.getSpendMoney()))
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal(0));

        XWPFRun runEarn = totalRow.getCell(7).getParagraphArray(0).createRun();
        XWPFRun runSpend = totalRow.getCell(8).getParagraphArray(0).createRun();

        setRun(runEarn,10,earn.toString(),true,false);
        setRun(runSpend,10,spend.toString(),true,false);

        try {
            File exportFile = new File("statistic.docx");
            FileOutputStream fos = new FileOutputStream(exportFile);
            doc.write(fos);
            fos.close();
            return exportFile.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public BigDecimal getPrice(OrderDetails details){
        PackageType type  = details.getPackageType();
        if(details.getOpt()!=null && details.getOpt()){
            return type.getWholeSalePrice();
        }else {
            return type.getPrice();
        }
    }

    private static void setRun (XWPFRun run , int fontSize , String text , boolean bold , boolean addBreak) {
        run.setFontFamily("Arial");
        run.setFontSize(fontSize);
        run.setText(text);
        run.setBold(bold);
        if (addBreak) run.addBreak();
    }
}
