package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.*;
import com.charlie.zoo.entity.dto.ProductDto;
import com.charlie.zoo.enums.StatusOfEntity;
import com.charlie.zoo.jpa.ProductJpa;
import com.charlie.zoo.service.*;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductJpa productJpa;
    private final PackageTypeService packageTypeService;
    private final CategoryService categoryService;
    private final CategoryItemService categoryItemService;
    private final ProducerService producerService;

    @Override
    public Product save(Product product) {
        return productJpa.save(product);
    }

    @Override
    public Product save(Product product, MultipartFile multipartFile,List<PackageType>  packageTypes,
                        String category,String subCategory) {
        if(multipartFile!=null && multipartFile.getSize()>0){
            try {
                product.setImg(multipartFile.getBytes());
                product.setImgName(multipartFile.getOriginalFilename());
                product.setImgType(multipartFile.getContentType());
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        for(PackageType type:packageTypes){
            type.setProduct(product);
        }
        if(!category.isEmpty()) {
            List<Category> categories = new ArrayList<>();
            for (Integer cat : new Gson().fromJson(category, Integer[].class)) {
                categories.add(categoryService.findById(cat));
            }
            product.setCategories(categories);
        }
        if(!subCategory.isEmpty()) {
            List<CategoryItem> categoryItems = new ArrayList<>();
            for (Integer cat : new Gson().fromJson(subCategory, Integer[].class)) {
                categoryItems.add(categoryItemService.findById(cat));
            }
            product.setCategoryItems(categoryItems);
        }
        product.setPackageType(packageTypes);
        return productJpa.save(product);
    }

    @Override
    public Product update(Product product, MultipartFile multipartFile, List<PackageType> packageTypes, String category, String subCategory) {
        if(multipartFile==null){
           Product productDB = findById(product.getId());
           if(productDB!=null){
               try {
                   product.setImg(productDB.getImg());
                   product.setImgName(productDB.getImgName());
                   product.setImgType(productDB.getImgType());
               }
               catch(Exception e) {
                   e.printStackTrace();
               }
           }
        }
        if(product.getId()>0){
            packageTypeService.deleteAllByProductId(product.getId());
        }
        return save(product,multipartFile,packageTypes,category,subCategory);
    }


    @Override
    public Product findById(int id) {
        return productJpa.findById(id).orElse(null);
    }

    @Override
    public Set<Product> findByAnimal(Animal animal) {
        return productJpa.findByCategoriesIn(animal.getCategories());
    }

    @Override
    public Set<Product> findByAnimalByCategory(Category category) {
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        return productJpa.findByCategoriesIn(categories);
    }

    @Override
    public Product changeStatus(int id, boolean status) {
        Product product = findById(id);
        if(product!=null){
            if(status){
                product.setStatusOfEntity(StatusOfEntity.ACTIVE);
            }else {
                product.setStatusOfEntity(StatusOfEntity.ARCHIVE);
            }
        }
        save(product);
        return product;
    }

    @Override
    public Set<Product> getFilteredProduct(Integer[] categoryIdArr,Integer[] categoryItemIdArr,Integer[] producerId, Double[] packSizeArr){
        Set<Product> products = new HashSet<>();
        List<Integer> category,categoryItem,producer;
        List<BigDecimal> packSize=new ArrayList<>();
        if(categoryIdArr==null || categoryIdArr.length==0){
            category = categoryService.getListOfId();
        }else{
            category = Arrays.asList(categoryIdArr);
        }
        if(categoryItemIdArr==null || categoryItemIdArr.length==0){
            categoryItem = categoryItemService.getListOfId();
        }else{
            categoryItem = Arrays.asList(categoryItemIdArr);
        }
        if(producerId==null || producerId.length==0){
            producer = producerService.getListOfId();
        }else{
            producer = Arrays.asList(producerId);
        }
        System.out.println(category);
        System.out.println(packSize);
        System.out.println(producer);
        if(packSizeArr==null || packSizeArr.length==0){
            packSize = packageTypeService.getListOfSizes();
        }else {
            for(Double val:packSizeArr) {
                packSize.add(new BigDecimal(val));
            }
        }
        products = productJpa.findByCategoriesIdInAndCategoryItemsIdInAndPackageTypePackSizeInAndProducerIdInAndStatusOfEntity(category,categoryItem,packSize,producer,StatusOfEntity.ACTIVE);

        return products;
    }

    @Override
    public List<Product> findAll() {
        return productJpa.findAll();
    }

    @Override
    public void deleteByID(int id) {
        productJpa.deleteById(id);
    }
}
