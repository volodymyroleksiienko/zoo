package com.charlie.zoo.entity.dto;

import com.charlie.zoo.entity.PackageType;
import com.charlie.zoo.enums.StatusOfEntity;
import com.google.gson.Gson;
import lombok.Data;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class PackageTypeDto {
    private int id;

    private double price;
    private double newPrice;
    private double packSize;
    private String packType;
    private boolean onSale;
    private int countOfProduct;
    private boolean statusOfEntity;

    public PackageTypeDto() {
    }

    public static PackageType convertToPackageType(String[] packArray){
        if(packArray.length>=8) {
            PackageType packageType = new PackageType();
            packageType.setPackType(packArray[0]);
            packageType.setPackSize(BigDecimal.valueOf(Double.parseDouble(packArray[1])));
            packageType.setCountOfProduct(Integer.parseInt(packArray[2]));
            if (packArray[3].toLowerCase().equals("true")) {
                packageType.setStatusOfEntity(StatusOfEntity.ACTIVE);
            } else {
                packageType.setStatusOfEntity(StatusOfEntity.ARCHIVE);
            }
            packageType.setPrice(BigDecimal.valueOf(Double.parseDouble(packArray[4])));
            packageType.setOnSale(packArray[5].toLowerCase().equals("true"));
            packageType.setNewPrice(BigDecimal.valueOf(Double.parseDouble(packArray[6])));
            if (!packArray[7].isEmpty()) {
                packageType.setId(Integer.parseInt(packArray[7]));
            }
            return packageType;
        }
        return null;
    }

    public static List<PackageType> getArrayOfPackageTypes(String types){
        List<PackageType> packageTypes = new ArrayList<>();
        PackageTypeDto[] typeDtos = new Gson().fromJson(types,PackageTypeDto[].class);

        for(PackageTypeDto packageTypeDto : typeDtos){
            PackageType packageType = convertToPackageType(packageTypeDto);
            packageTypes.add(packageType);
        }
        return packageTypes;
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
        if(packageTypeDto.statusOfEntity){
            packageType.setStatusOfEntity(StatusOfEntity.ACTIVE);
        }else{
            packageType.setStatusOfEntity(StatusOfEntity.ARCHIVE);
        }
        return packageType;
    }
}
