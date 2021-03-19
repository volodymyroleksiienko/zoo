package com.charlie.zoo.service;


import com.charlie.zoo.entity.PackageType;
import com.charlie.zoo.entity.Product;
import com.charlie.zoo.enums.StatusOfEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Product save(Product product);
    Product save(Product product, MultipartFile multipartFile, List<PackageType>  packageTypes,String category,String subCategory);
    Product update(Product product, MultipartFile multipartFile, List<PackageType>  packageTypes,String category,String subCategory);
    Product findById(int id);
    Product changeStatus(int id, boolean status);
    List<Product> findAll();
//    List<Product> getFilteredProducts(Integer type, Integer size,Integer page);
//    int getCountOfElements(Integer type, Integer size,Integer page);
//    List<Product> findByStatus(StatusOfEntity status);
//    List<Product> findSameProducts(Product product);
    void deleteByID(int id);
}
