package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.*;
import com.charlie.zoo.entity.dto.PackSizeDto;
import com.charlie.zoo.entity.dto.SliderDto;
import com.charlie.zoo.enums.StatusOfEntity;
import com.charlie.zoo.jpa.ProductJpa;
import com.charlie.zoo.service.*;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductJpa productJpa;
    private final PackageTypeService packageTypeService;
    private final CategoryService categoryService;
    private final CategoryItemService categoryItemService;
    private final ProducerService producerService;
    private final AnimalService animalService;
    private final ImageService imageService;

    @Override
    public Product save(Product product) {
        return productJpa.save(product);
    }

    @Override
    public List<Product> saveAll(List<Product> product) {
        return productJpa.saveAll(product);
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
//        if(product.getId()>0){
//            packageTypeService.deleteAllByProductId(product.getId());
//        }
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
    public List<Product> findByStatusByOpt(StatusOfEntity status, Boolean opt) {
        if (opt == null || !opt) {
            return productJpa.findByStatusOfEntity(status);
        } else {
            return productJpa.findByStatusOfEntity(status).stream()
                    .peek(product -> product.setPackageType(
                            product.getPackageType()
                                    .stream()
                                    .filter(PackageType::isWholeSaleStatus).collect(Collectors.toList())))
                    .filter(product -> product.getPackageType().size()>0)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Set<Product> findByAnimal(Animal animal) {
        return productJpa.findByCategoriesIn(animal.getCategories());
    }

    @Override
    public Set<Product> findByAnimalByOpt(Animal animal, Boolean opt) {
        if (opt == null || !opt) {
            return productJpa.findByCategoriesInAndStatusOfEntity(animal.getCategories(),StatusOfEntity.ACTIVE);
        }else {
            return productJpa.findByCategoriesInAndStatusOfEntity(animal.getCategories(),StatusOfEntity.ACTIVE)
                    .stream()
                    .peek(product -> product.setPackageType(
                            product.getPackageType()
                                    .stream()
                                    .filter(PackageType::isWholeSaleStatus).collect(Collectors.toList())))
                    .filter(product -> product.getPackageType().size()>0)
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public Set<Product> findByAnimalByCategory(Category ct) {
        List<Integer> category = new ArrayList<>();
        category.add(ct.getId());
        return productJpa.findByCategoriesIdInAndStatusOfEntity(category,StatusOfEntity.ACTIVE);
    }

    @Override
    public Set<Product> findByAnimalByCategoryByOpt(Category ct, Boolean opt) {
        List<Integer> category = new ArrayList<>();
        category.add(ct.getId());
        if (opt == null || !opt) {
            return productJpa.findByCategoriesIdInAndStatusOfEntity(category,StatusOfEntity.ACTIVE);
        }else {
            return productJpa.findByCategoriesIdInAndStatusOfEntity(category,StatusOfEntity.ACTIVE)
                    .stream()
                    .peek(product -> product.setPackageType(
                            product.getPackageType()
                                    .stream()
                                    .filter(PackageType::isWholeSaleStatus).collect(Collectors.toList())))
                    .filter(product -> product.getPackageType().size()>0)
                    .collect(Collectors.toSet());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Product> findByAnimalByCategoryBySubCategory(CategoryItem item) {
        List<Integer> category = new ArrayList<>();
        category.add(item.getId());
        return productJpa.findByCategoryItemsIdInAndStatusOfEntity(category,StatusOfEntity.ACTIVE);
    }

    @Override
    public Set<Product> findByAnimalByCategoryBySubCategoryByOpt(CategoryItem item, Boolean opt) {
        List<Integer> category = new ArrayList<>();
        category.add(item.getId());
        if (opt == null || !opt) {
            return productJpa.findByCategoryItemsIdInAndStatusOfEntity(category,StatusOfEntity.ACTIVE);
        }else {
            return productJpa.findByCategoryItemsIdInAndStatusOfEntity(category,StatusOfEntity.ACTIVE)
                    .stream()
                    .peek(product -> product.setPackageType(
                            product.getPackageType()
                                    .stream()
                                    .filter(PackageType::isWholeSaleStatus).collect(Collectors.toList())))
                    .filter(product -> product.getPackageType().size()>0)
                    .collect(Collectors.toSet());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> find15ByName(String name) {
        return productJpa.findFirst15ByNameContainingIgnoreCaseOrShortDescriptionContainingIgnoreCaseAndStatusOfEntity(name,name,StatusOfEntity.ACTIVE);
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
    public Set<PackSizeDto> getPackSize(Set<Product> products) {
        Set<PackSizeDto> packSizes = new TreeSet<>();
        for(Product product:products){
            for(PackageType packageType:product.getPackageType()){
                PackSizeDto p = new PackSizeDto();
                p.setCount(packageType.getPackSize());
                p.setType(packageType.getPackType());
                packSizes.add(p);
            }
        }
        return packSizes;
    }

//    @Override
//    public List<Product> getFiltered(Set<Product> products, Integer minPrice, Integer maxPrice, String packSize, Integer producerId) {
//        List<Product> filteredList = new ArrayList<>();
//
//        for(Product product:products){
//            for(int i=0;i<product.getPackageType().size();i++) {
//                PackageType type = product.getPackageType().get(i);
//                if (minPrice != null && maxPrice != null) {
//                    int priceValue = 0;
//                    if (type.isOnSale()) {
//                        priceValue = type.getNewPrice().intValue();
//                    }else
//                    {priceValue = type.getPrice().intValue();}
//                    System.out.println("Price value "+priceValue);
//                    if(priceValue<minPrice || priceValue>maxPrice){
//                        System.out.println("check");
//                        product.getPackageType().remove(type);
//                        continue;
//                    }
//                }
//                System.out.println(product.getPackageType());
//                if(packSize!=null && !packSize.isEmpty()){
//                    String packValue = type.getPackSize().doubleValue()+type.getPackType();
//                    if(!packSize.equals(packValue)){
//                        product.getPackageType().remove(type);
//                    }
//                }
//            }
//            if (producerId!=null && product.getProducer().getId() != producerId) {
//                filteredList.remove(product);
//            }
//            if(product.getPackageType().size()==0){
//                filteredList.remove(product);
//            }
//        }
//        return filteredList;
//    }


    @Override
    public List<Product> getFiltered(Set<Product> products, Integer minPrice, Integer maxPrice, String packSize, Integer producerId,String sortType) {
        List<Product> productList = new ArrayList<>(products);
        if(packSize!=null && !packSize.isEmpty()){
            productList = filteredBySize(productList,packSize);
        }
        if(producerId!=null){
            productList = filteredByProducer(productList,producerId);
        }
        if(maxPrice!=null && minPrice!=null) {
            productList = filteredByPrice(productList,maxPrice,minPrice);
        }
        if(sortType!=null && !sortType.isEmpty()){
            productList = sort(productList,sortType);
        }
        return productList;
    }

    @Override
    public List<SliderDto> configureSlider() {
        List<SliderDto>  sliders = new ArrayList<>();
        List<Animal> animals = animalService.findAll();
        for(Animal animal:animals){
            SliderDto sliderDto = new SliderDto();
            sliderDto.setAnimal(animal.getName());
            sliderDto.setDescription(animal.getDescription());
            sliderDto.setProducts(
                  new HashSet<>(productJpa.findByCategoriesIn(animal.getCategories())).stream().limit(15).collect(Collectors.toSet())
            );
            System.out.println(sliderDto.getProducts().size());
            sliders.add(sliderDto);
        }
        return sliders;
    }

    private List<Product> filteredByProducer(List<Product> products, Integer id ){
        return products.parallelStream().filter(product -> product.getProducer().getId()==id).collect(Collectors.toList());
    }

    private  List<Product> filteredBySize(List<Product> products,String packSize){
        List<Product> filtered = new ArrayList<>();
        for(int i=0;i<products.size();i++){
            Product product = products.get(i);
            for(int j=0;j<products.get(i).getPackageType().size();j++){
                PackageType type = products.get(i).getPackageType().get(j);
                String packValue = type.getPackSize().doubleValue()+type.getPackType();
                if(packSize.equals(packValue)){
                    filtered.add(product);
                    break;
                }
            }
        }
        return filtered;
    }

    private  List<Product> filteredByPrice(List<Product> products,int max,int min){
        List<Product> filtered = new ArrayList<>();
        for(int i=0;i<products.size();i++){
            Product product = products.get(i);
            for(int j=0;j<products.get(i).getPackageType().size();j++){
                PackageType type = products.get(i).getPackageType().get(j);
                int packValue;
                if(type.isOnSale()) {
                    packValue = type.getNewPrice().intValue();
                }else{
                    packValue = type.getPrice().intValue();
                }
                if(packValue>=min && packValue<=max){
                    filtered.add(product);
                    break;
                }
            }
        }
        return filtered;
    }

    private List<Product> sort(List<Product> products,String sortType){
        if(sortType.equals("cheap")) {
            products.sort((o1, o2) -> {
                if(o1.getPackageType().size()>0 && o2.getPackageType().size()>0){
                    PackageType type1 = o1.getPackageType().get(0);
                    PackageType type2 = o2.getPackageType().get(0);
                    double o1Value = (type1.isOnSale())?type1.getNewPrice().doubleValue():type1.getPrice().doubleValue();
                    double o2Value = (type2.isOnSale())?type2.getNewPrice().doubleValue():type2.getPrice().doubleValue();
                    if(o1Value>o2Value){
                        return 1;
                    }else
                    {return -1;}
                }
                return 0;
            });
        }
        if(sortType.equals("expensive")) {
            products.sort((o1, o2) -> {
                if(o1.getPackageType().size()>0 && o2.getPackageType().size()>0){
                    PackageType type1 = o1.getPackageType().get(0);
                    PackageType type2 = o2.getPackageType().get(0);
                    double o1Value = (type1.isOnSale())?type1.getNewPrice().doubleValue():type1.getPrice().doubleValue();
                    double o2Value = (type2.isOnSale())?type2.getNewPrice().doubleValue():type2.getPrice().doubleValue();
                    if(o1Value<o2Value){
                        return 1;
                    }else
                    {return -1;}
                }
                return 0;
            });
        }
        if(sortType.equals("new")) {
            products.sort(Comparator.comparingInt(Product::getId));
        }
        return products;
    }

    @Override
    public void deleteByID(int id) {
        productJpa.deleteById(id);
    }
}
