package com.charlie.zoo.entity.dto;

import com.charlie.zoo.entity.PackageType;
import com.charlie.zoo.enums.StatusOfEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageTypeDto {
    private int id;

    private BigDecimal price;
    private BigDecimal newPrice;
    private BigDecimal packSize;
    private String packType;
    private boolean onSale;
    private int countOfProduct;
    private boolean statusOfEntity;

    public static PackageType convertToPackageType(PackageTypeDto packageTypeDto){
        PackageType packageType = new PackageType();

        packageType.setId(packageTypeDto.id);
        packageType.setPrice(packageTypeDto.price);
        packageType.setNewPrice(packageTypeDto.newPrice);
        packageType.setPackSize(packageTypeDto.packSize);
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
