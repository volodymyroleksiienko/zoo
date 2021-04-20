package com.charlie.zoo.entity.dto;

import com.charlie.zoo.entity.PackageType;
import com.charlie.zoo.enums.StatusOfEntity;
import com.google.gson.Gson;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class PackageTypeDto {
    private int id;

    private double price;
    private double newPrice;
    private double packSize;
    private double discount;

    private double wholeSalePrice;
    private double wholeSaleNewPrice;
    private double wholeSaleDiscount;
    private boolean wholeSaleOnSale;
    private boolean wholeSaleStatus;


    private String packType;
    private boolean onSale;
    private int countOfProduct;
    private boolean statusOfEntity;

    private ProductDto productDto;

    public PackageTypeDto() {
    }

//    public static PackageType convertToPackageType(String[] packArray){
//        if(packArray.length>=8) {
//            PackageType packageType = new PackageType();
//            packageType.setPackType(packArray[0]);
//            packageType.setPackSize(BigDecimal.valueOf(Double.parseDouble(packArray[1])));
//            packageType.setCountOfProduct(Integer.parseInt(packArray[2]));
//            if (packArray[3].toLowerCase().equals("true")) {
//                packageType.setStatusOfEntity(StatusOfEntity.ACTIVE);
//            } else {
//                packageType.setStatusOfEntity(StatusOfEntity.ARCHIVE);
//            }
//            packageType.setPrice(BigDecimal.valueOf(Double.parseDouble(packArray[4])));
//            packageType.setOnSale(packArray[5].toLowerCase().equals("true"));
//            packageType.setNewPrice(BigDecimal.valueOf(Double.parseDouble(packArray[6])));
//            if (!packArray[7].isEmpty()) {
//                packageType.setId(Integer.parseInt(packArray[7]));
//            }
//            return packageType;
//        }
//        return null;
//    }

    public static List<PackageType> getArrayOfPackageTypes(String types){
        List<PackageType> packageTypes = new ArrayList<>();
        System.out.println(types);
        PackageTypeDto[] typeDtos = new Gson().fromJson(types,PackageTypeDto[].class);

        for(PackageTypeDto packageTypeDto : typeDtos){
            PackageType packageType = convertToPackageType(packageTypeDto);
            packageTypes.add(packageType);
        }
        return packageTypes;
    }

    public static PackageTypeDto convertToDto(PackageType packageType){
        PackageTypeDto packageTypeDto = new PackageTypeDto();
        packageTypeDto.id=packageType.getId();
        packageTypeDto.price=packageType.getPrice().doubleValue();
        if(packageType.getNewPrice()!=null) {
            packageTypeDto.newPrice = packageType.getNewPrice().doubleValue();
        }
        packageTypeDto.packSize=packageType.getPackSize().doubleValue();
        if(packageType.getDiscount()!=null) {
            packageTypeDto.discount = packageType.getDiscount().doubleValue();
        }
        packageTypeDto.packType=packageType.getPackType();
        packageTypeDto.onSale=packageType.isOnSale();
        packageTypeDto.countOfProduct=packageType.getCountOfProduct();

        if(packageType.getWholeSaleDiscount()!=null) {
            packageTypeDto.wholeSaleDiscount = packageType.getWholeSaleDiscount().doubleValue();
        }
        if(packageType.getWholeSalePrice()!=null) {
            packageTypeDto.wholeSalePrice = packageType.getWholeSalePrice().doubleValue();
        }
        if(packageType.getWholeSaleNewPrice()!=null) {
            packageTypeDto.wholeSaleNewPrice = packageType.getWholeSaleNewPrice().doubleValue();
        }
        packageTypeDto.wholeSaleOnSale=packageType.isWholeSaleOnSale();
        packageTypeDto.wholeSaleStatus=packageType.isWholeSaleStatus();

        if(packageType.getStatusOfEntity()==StatusOfEntity.ACTIVE) {
            packageTypeDto.statusOfEntity=true;
        }else {
            packageTypeDto.statusOfEntity=false;
        }
        if(packageType.getProduct()!=null){
            packageTypeDto.productDto=ProductDto.convertToDto(packageType.getProduct());
        }


        return packageTypeDto;
    }

    public static List<PackageTypeDto> convertToListDto(List<PackageType> packageTypes){
        List<PackageTypeDto> dtos = new ArrayList<>();
        for(PackageType type: packageTypes){
            PackageTypeDto dto = PackageTypeDto.convertToDto(type);
            dto.setProductDto(ProductDto.convertToDto(type.getProduct()));
            dtos.add(dto);
        }
        return dtos;
    }

    public static PackageType convertToPackageType(PackageTypeDto packageTypeDto){
        PackageType packageType = new PackageType();

        packageType.setId(packageTypeDto.id);
        packageType.setPrice(BigDecimal.valueOf(packageTypeDto.price));
        packageType.setNewPrice(BigDecimal.valueOf(packageTypeDto.newPrice));
        packageType.setPackSize(BigDecimal.valueOf(packageTypeDto.packSize));
        packageType.setPackType(packageTypeDto.packType);
        packageType.setOnSale(packageTypeDto.onSale);
        packageType.setCountOfProduct(packageTypeDto.countOfProduct);
        packageType.setDiscount(BigDecimal.valueOf(packageTypeDto.discount));


        packageType.setWholeSalePrice(BigDecimal.valueOf(packageTypeDto.wholeSalePrice));
        packageType.setWholeSaleNewPrice(BigDecimal.valueOf(packageTypeDto.wholeSaleNewPrice));
        packageType.setWholeSaleDiscount(BigDecimal.valueOf(packageTypeDto.wholeSaleDiscount));

        packageType.setWholeSaleOnSale(packageTypeDto.wholeSaleOnSale);
        packageType.setWholeSaleStatus(packageTypeDto.wholeSaleStatus);

        if(packageTypeDto.statusOfEntity){
            packageType.setStatusOfEntity(StatusOfEntity.ACTIVE);
        }else{
            packageType.setStatusOfEntity(StatusOfEntity.ARCHIVE);
        }
        return packageType;
    }
}
