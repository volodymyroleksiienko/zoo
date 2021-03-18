package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.Category;
import com.charlie.zoo.entity.CategoryItem;
import com.charlie.zoo.entity.PackageType;
import com.charlie.zoo.entity.Product;
import com.charlie.zoo.entity.dto.ProductDto;
import com.charlie.zoo.enums.StatusOfEntity;
import com.charlie.zoo.jpa.ProductJpa;
import com.charlie.zoo.service.CategoryItemService;
import com.charlie.zoo.service.CategoryService;
import com.charlie.zoo.service.ProducerService;
import com.charlie.zoo.service.ProductService;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductJpa productJpa;
    private final CategoryService categoryService;
    private final CategoryItemService categoryItemService;

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
    public Product update(Product product, MultipartFile multipartFile) {
        if(product!=null && product.getId()>0){
            Product productDB = findById(product.getId());

            productDB.setName(product.getName());
            productDB.setShortDescription(product.getShortDescription());
//            productDB.setCountOfProduct(product.getCountOfProduct());
//            productDB.setOnSale(product.isOnSale());
//            productDB.setPrice(product.getPrice());
//            productDB.setNewPrice(product.getNewPrice());
//            productDB.setProducer(product.getProducer());
            productDB.setStatusOfEntity(product.getStatusOfEntity());

            if(multipartFile.getSize()>0){
                try {
                    productDB.setImg(multipartFile.getBytes());
                    productDB.setImgName(multipartFile.getOriginalFilename());
                    productDB.setImgType(multipartFile.getContentType());
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }

            return save(productDB);
        }
        return null;
    }

    @Override
    public Product findById(int id) {
        return productJpa.findById(id).orElse(null);
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
        return product;
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
