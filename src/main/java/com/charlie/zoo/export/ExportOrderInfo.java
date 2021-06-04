package com.charlie.zoo.export;

import com.charlie.zoo.entity.OrderDetails;
import com.charlie.zoo.entity.OrderInfo;
import com.charlie.zoo.entity.PackageType;
import com.charlie.zoo.service.OrderService;
import lombok.AllArgsConstructor;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ExportOrderInfo {
    private final OrderService orderService;

    public String generateReport(UUID id) {
        OrderInfo orderInfo = orderService.findById(id);

        XWPFDocument doc = new XWPFDocument();

        CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();
        pageMar.setLeft(BigInteger.valueOf(720L));
        pageMar.setTop(BigInteger.valueOf(1440L));
        pageMar.setRight(BigInteger.valueOf(720L));
        pageMar.setBottom(BigInteger.valueOf(1440L));

//        XWPFHeader header = doc.createHeader(HeaderFooterType.DEFAULT);
        XWPFParagraph num = doc.createParagraph();
        XWPFRun head =num.createRun();
        setRun(head,14,
                "Видаткова накладна "+orderInfo.getIndex()+" від "+orderInfo.getDate().replace("-","."),
                true,false);
        XWPFParagraph borderTop1 = doc.createParagraph();
        borderTop1.setBorderTop(Borders.SINGLE);

//        Producer info start
        XWPFParagraph producerInfo1 = doc.createParagraph();
        XWPFRun producerInfoRun1 = producerInfo1.createRun();
        producerInfoRun1.setUnderline(UnderlinePatterns.SINGLE);
        producerInfoRun1.setText("Постачальник:");
        producerInfoRun1.setTextPosition(0);

        XWPFRun producerInfoRun2 = producerInfo1.createRun();
        producerInfoRun2.setBold(true);
        producerInfoRun2.setText(" \t\tФізична особа підприємець Стахів Григорій Павлович\n");
        producerInfoRun2.setTextPosition(1);
        producerInfoRun2.addCarriageReturn();

        XWPFRun producerInfoRun3 = producerInfo1.createRun();
        producerInfoRun3.setText("\t\t\tп/р UA83052990000026007011003025 у банку АТ КБ \"Приватбанк\", МФО,\n");
        producerInfoRun3.setTextPosition(2);
        producerInfoRun3.addCarriageReturn();

        XWPFRun producerInfoRun4 = producerInfo1.createRun();
        producerInfoRun4.setText("\t\t\tкод за ЄДРПОУ 3462207638");
        producerInfoRun4.setTextPosition(3);
//        Producer info end

//        Buyer Info start
        XWPFParagraph buyerInfo1 = doc.createParagraph();
        XWPFRun buyerInfoRun1 = buyerInfo1.createRun();
        buyerInfoRun1.setUnderline(UnderlinePatterns.SINGLE);
        buyerInfoRun1.setText("Покупець:");
        buyerInfoRun1.setTextPosition(0);

        XWPFRun buyerInfoRun2 = buyerInfo1.createRun();
        buyerInfoRun2.setBold(true);
        buyerInfoRun2.setText("\t\t"+orderInfo.getClient().getName());
        buyerInfoRun2.setTextPosition(1);
//        Buyer Info end

        //        Buyer Info phone start
        XWPFParagraph phoneInfo1 = doc.createParagraph();
        XWPFRun phoneInfoRun1 = phoneInfo1.createRun();
        phoneInfoRun1.setUnderline(UnderlinePatterns.SINGLE);
        phoneInfoRun1.setText("Телефон:");
        phoneInfoRun1.setTextPosition(0);

        XWPFRun phoneInfoRun2 = phoneInfo1.createRun();
        phoneInfoRun2.setText("\t\t"+orderInfo.getClient().getPhones().toString());
        phoneInfoRun2.setTextPosition(1);
        phoneInfoRun2.addCarriageReturn();
//        Buyer Info end

//          Create table
        XWPFTable table = doc.createTable();

        XWPFTableRow rowHeader= table.getRow(0);
        rowHeader.getCell(0).addParagraph().setAlignment(ParagraphAlignment.CENTER);
        rowHeader.getCell(0).getParagraphArray(1).createRun().setText("№");

        rowHeader.createCell().addParagraph().setAlignment(ParagraphAlignment.CENTER);
        rowHeader.getCell(1).getParagraphArray(1).createRun().setText("Код");

        rowHeader.createCell().addParagraph().setAlignment(ParagraphAlignment.CENTER);
        rowHeader.getCell(2).getParagraphArray(1).createRun().setText("Товар");

        rowHeader.createCell().addParagraph().setAlignment(ParagraphAlignment.CENTER);
        rowHeader.getCell(3).getParagraphArray(1).createRun().setText("Фасування");

        rowHeader.createCell().addParagraph().setAlignment(ParagraphAlignment.CENTER);
        rowHeader.getCell(4).getParagraphArray(1).createRun().setText("К-сть");

        rowHeader.createCell().addParagraph().setAlignment(ParagraphAlignment.CENTER);
        rowHeader.getCell(5).getParagraphArray(1).createRun().setText("Ціна");

        rowHeader.createCell().addParagraph().setAlignment(ParagraphAlignment.CENTER);
        rowHeader.getCell(6).getParagraphArray(1).createRun().setText("Сума без знижки");

        rowHeader.createCell().addParagraph().setAlignment(ParagraphAlignment.CENTER);
        rowHeader.getCell(7).getParagraphArray(1).createRun().setText("Знижка");

        rowHeader.createCell().addParagraph().setAlignment(ParagraphAlignment.CENTER);
        rowHeader.getCell(8).getParagraphArray(1).createRun().setText("Сума");
        rowHeader.getCell(0).setWidth("7%");
        rowHeader.getCell(1).setWidth("7%");
        rowHeader.getCell(2).setWidth("44%");
        rowHeader.getCell(3).setWidth("7%");
        rowHeader.getCell(4).setWidth("7%");
        rowHeader.getCell(5).setWidth("7%");
        rowHeader.getCell(6).setWidth("7%");
        rowHeader.getCell(7).setWidth("7%");
        rowHeader.getCell(8).setWidth("7%");

        double sumALl=0;
        double sumDiscount = 0;
        double sum=0;

        for(int i=0; i<orderInfo.getOrderDetails().size();i++){
            XWPFTableRow row = table.createRow();
            OrderDetails details = orderInfo.getOrderDetails().get(i);

            row.getCell(0).setText(""+(i+1));
            row.getCell(1).setText(details.getPackageType().getBarcode());
            row.getCell(2).setText(details.getPackageType().getProduct().getName());
            row.getCell(3).setText(details.getPackageType().getPackSize().doubleValue()+details.getPackageType().getPackType());
            row.getCell(4).setText(details.getCount()+" шт");
            BigDecimal price = getPrice(details);
            row.getCell(5).setText(details.getPrice().doubleValue()+"");
            row.getCell(6).setText(price.doubleValue()*details.getCount()+"");
            row.getCell(7).setText((price.doubleValue()-details.getPrice().doubleValue())*details.getCount()+"");
            row.getCell(8).setText(details.getPrice().setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()*details.getCount()+"");

            sumALl+=price.doubleValue()*details.getCount();
            sumDiscount+=(price.doubleValue()-details.getPrice().doubleValue())*details.getCount();
            sum+= details.getPrice().doubleValue()*details.getCount();


            row.getCell(0).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
            row.getCell(1).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
            row.getCell(2).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
            row.getCell(3).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
            row.getCell(4).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
            row.getCell(5).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
            row.getCell(6).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
            row.getCell(7).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
            row.getCell(8).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);

            row.getCell(0).getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
            row.getCell(1).getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
            row.getCell(2).getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
            row.getCell(3).getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
            row.getCell(4).getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
            row.getCell(5).getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
            row.getCell(6).getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
            row.getCell(7).getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
            row.getCell(8).getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
        }

//        Total row
        XWPFTableRow totalRow = table.createRow();

        XWPFRun name = totalRow.getCell(5).getParagraphArray(0).createRun();
        XWPFRun runAll = totalRow.getCell(6).getParagraphArray(0).createRun();
        XWPFRun runDiscount = totalRow.getCell(7).getParagraphArray(0).createRun();
        XWPFRun run = totalRow.getCell(8).getParagraphArray(0).createRun();
        setRun(name,9,"Разом",true, false);
        setRun(runAll,9,String.format("%.2f",sumALl),true, false);
        setRun(runDiscount,9,String.format("%.2f",sumDiscount),true, false);
        setRun(run,9, String.format("%.2f",sum),true, false);


        for(int i=0; i<totalRow.getTableCells().size(); i++) {
            CTTc ctTc = totalRow.getCell(i).getCTTc();
            CTTcPr tcPr = ctTc.addNewTcPr();
            CTHMerge hMerge = tcPr.addNewHMerge();
            hMerge.setVal(STMerge.RESTART);
            totalRow.getCell(i).getCTTc().getTcPr().addNewTcBorders();
            totalRow.getCell(i).getCTTc().getTcPr().getTcBorders().addNewRight().setVal(STBorder.NIL);
            totalRow.getCell(i).getCTTc().getTcPr().getTcBorders().addNewLeft().setVal(STBorder.NIL);
            totalRow.getCell(i).getCTTc().getTcPr().getTcBorders().addNewBottom().setVal(STBorder.NIL);
        }

//        Total info decription
//        Всього найменувань 3, на суму 1 101,52 грн.
        XWPFParagraph totalDescription = doc.createParagraph();
        XWPFRun desc = totalDescription.createRun();
        desc.setText("Всього найменувань "+orderInfo.getOrderDetails().size()+", на суму "+new BigDecimal(sum).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()+" грн.:");

        XWPFParagraph totalDescriptionBorder = doc.createParagraph();
        totalDescriptionBorder.setBorderTop(Borders.SINGLE);

//        Sign info

        XWPFTable footer = doc.createTable();
        footer.removeBorders();
        CTTblWidth width = footer.getCTTbl().addNewTblPr().addNewTblW();
        width.setType(STTblWidth.DXA);
        width.setW(BigInteger.valueOf(12000));

        XWPFTableRow footerRow1= footer.getRow(0);
        footerRow1.getCell(0).addParagraph().setAlignment(ParagraphAlignment.LEFT);
        XWPFRun col1 = footerRow1.getCell(0).getParagraphArray(0).createRun();
        setRun(col1,12,"Від постачальника*", true,false);

        footerRow1.createCell().addParagraph().setAlignment(ParagraphAlignment.LEFT);
        XWPFRun col2 = footerRow1.getCell(1).getParagraphArray(0).createRun();
        setRun(col2,12,"Отримав(ла)*", true,false);

        XWPFRun col2_2 = footerRow1.getCell(1).getParagraphArray(1).createRun();
        setRun(col2_2,22,"________________", false,false);

        XWPFRun col1_2 = footerRow1.getCell(0).getParagraphArray(1).createRun();
        setRun(col1_2,22,"________________", false,false);

        footerRow1.getCell(0).setWidth("50%");
        footerRow1.getCell(1).setWidth("50%");

        setRun(footerRow1.getCell(0).addParagraph().createRun(),8,
                "Відповідальний за здійснення господарської операції і правильність її оформлення",false,false);
        setRun(footerRow1.getCell(1).addParagraph().createRun(),8,
                "За довіреністю\t№\tвід",false,false);





        try {
            File exportFile = new File("report.docx");
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