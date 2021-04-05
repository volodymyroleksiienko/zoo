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
    private final ImageService imageService;

    @Override
    public Product save(Product product) {
        return productJpa.save(product);
    }

    @Override
    public Product save(Product product, List<MultipartFile> multipartFile,List<PackageType>  packageTypes,
                        String category,String subCategory) {
        save(product);
        List<Image> imageList = imageService.save(multipartFile,product);
        product.setImages(imageList);

        product = productInit(product,packageTypes,category,subCategory);

        return productJpa.save(product);
    }

    private Product productInit(Product product,List<PackageType>  packageTypes,
                                String category,String subCategory){
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
        return product;
    }

    @Override
    public Product update(Product product, List<MultipartFile> multipartFile, List<PackageType> packageTypes, String category, String subCategory) {
        imageService.update(multipartFile,product);
        if(product.getId()>0){
            packageTypeService.deleteAllByProductId(product.getId());
        }
        product = productInit(product,packageTypes,category,subCategory);
        return productJpa.save(product);
    }


    @Override
    public Product findById(int id) {
        return productJpa.findById(id).orElse(null);
    }

    @Override
    public List<Product> findByStatus(StatusOfEntity status) {
        return productJpa.findByStatusOfEntity(status);
    }

    @Override
    public Set<Product> findByAnimal(Animal animal) {
        return productJpa.findByCategoriesIn(animal.getCategories());
    }

    @Override
    public Set<Product> findByAnimalByCategory(Category ct) {
        List<Integer> category = new ArrayList<>();
        category.add(ct.getId());
        return productJpa.findByCategoriesIdInAndStatusOfEntity(category,StatusOfEntity.ACTIVE);
    }

    @Override
    public Set<Product> findByAnimalByCategoryBySubCategory(CategoryItem item) {
        List<Integer> category = new ArrayList<>();
        category.add(item.getId());
        return productJpa.findByCategoryItemsIdInAndStatusOfEntity(category,StatusOfEntity.ACTIVE);
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
    public Integer getMaxPrice(Set<Product> products) {
        int maxPrice = 0;
        for (Product product:products){
            for(PackageType type:product.getPackageType()){
                if(type.isOnSale()){
                    if(maxPrice<type.getNewPrice().doubleValue()){
                        maxPrice = type.getNewPrice().intValue();
                    }
                }else{
                    if(maxPrice<type.getPrice().doubleValue()){
                        maxPrice = type.getPrice().intValue();
                    }
                }
            }
        }
        return maxPrice;
    }

    @Override
    public Integer getMinPrice(Set<Product> products) {
        int maxPrice = Integer.MAX_VALUE;
        for (Product product:products){
            for(PackageType type:product.getPackageType()){
                if(type.isOnSale()){
                    if(maxPrice>type.getNewPrice().doubleValue()){
                        maxPrice = type.getNewPrice().intValue();
                    }
                }else{
                    if(maxPrice>type.getPrice().doubleValue()){
                        maxPrice = type.getPrice().intValue();
                    }
                }
            }
        }
        return maxPrice;
    }

    @Override
    public Set<Producer> getProducers(Set<Product> products) {
        Set<Producer> producers = new HashSet<>();
        for(Product product:products){
            producers.add(product.getProducer());
        }
        return producers;
    }

    @Override
    public Set<String> getPackSize(Set<Product> products) {
        Set<String> packSizes = new TreeSet<>();
        for(Product product:products){
            for(PackageType packageType:product.getPackageType()){
                packSizes.add(packageType.getPackSize().doubleValue()+packageType.getPackType());
            }
        }
        return packSizes;
    }

    @Override
    public List<Product> getFiltered(Set<Product> products, Integer minPrice, Integer maxPrice, String packSize, Integer producerId) {
        List<Product> filteredList = new ArrayList<>(products);
        boolean price,producer,size;
        for(Product product:filteredList){
            producer = false;


            for(PackageType  type:product.getPackageType()) {
                if (minPrice != null && maxPrice != null) {
                    int priceValue = 0;
                    if (type.isOnSale()) {
                        priceValue = type.getNewPrice().intValue();
                    }else
                    {priceValue = type.getPrice().intValue();}

                    if(priceValue<=minPrice && priceValue>=maxPrice){
                        product.getPackageType().remove(type);
                        continue;
                    }
                }
                if(packSize!=null && !packSize.isEmpty()){
                    String packValue = type.getPackSize().doubleValue()+type.getPackType();
                    if(!packSize.equals(packValue)){
                        product.getPackageType().remove(type);
                    }
                }
            }
            if (producerId!=null && product.getProducer().getId() != producerId) {
                products.remove(product);
            }
            if(product.getPackageType().size()==0){
                products.remove(product);
            }
        }
        return filteredList;
    }

    @Override
    public void deleteByID(int id) {
        productJpa.deleteById(id);
    }
}
