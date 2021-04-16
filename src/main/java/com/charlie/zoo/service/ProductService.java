package com.charlie.zoo.service;


import com.charlie.zoo.entity.*;
import com.charlie.zoo.enums.StatusOfEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ProductService {
    Product save(Product product);
    List<Product> saveAll(List<Product> product);
    Product save(Product product, List<MultipartFile> multipartFile, List<PackageType>  packageTypes,String category,String subCategory);
    Product update(Product product, List<MultipartFile> multipartFile, List<PackageType>  packageTypes,String category,String subCategory);
    Product findById(int id);
    List<Product> findByStatus(StatusOfEntity status);
    List<Product> findByStatusByOpt(StatusOfEntity status,Boolean opt);
    Set<Product> findByAnimal(Animal animal);
    Set<Product> findByAnimalByOpt(Animal animal,Boolean opt);
    Set<Product> findByAnimalByCategory(Category ct);
    Set<Product> findByAnimalByCategoryByOpt(Category ct,Boolean opt);
    Set<Product> findByAnimalByCategoryBySubCategory(CategoryItem item);
    Set<Product> findByAnimalByCategoryBySubCategoryByOpt(CategoryItem item,Boolean opt);
    List<Product> find15ByName(String name);
    Product changeStatus(int id, boolean status);
    Set<Product> getFilteredProduct(Integer[] categoryIdArr,Integer[] categoryItemIdArr,Integer[] producerId, Double[] packSizeArr);
    List<Product> findAll();

    Integer getMaxPrice(Set<Product> products);
    Integer getMinPrice(Set<Product> products);
    Set<Producer> getProducers(Set<Product> products);
    Set<String> getPackSize(Set<Product> products);
    List<Product> getFiltered(Set<Product> products, Integer minPrice,Integer maxPrice,String packSize,Integer producerId,String sortType);

//    List<Product> getFilteredProducts(Integer type, Integer size,Integer page);
//    int getCountOfElements(Integer type, Integer size,Integer page);
//    List<Product> findByStatus(StatusOfEntity status);
//    List<Product> findSameProducts(Product product);
    void deleteByID(int id);
}
