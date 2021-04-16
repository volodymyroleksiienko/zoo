package com.charlie.zoo.controller.user;

import com.charlie.zoo.entity.Animal;
import com.charlie.zoo.entity.Category;
import com.charlie.zoo.entity.CategoryItem;
import com.charlie.zoo.entity.Product;
import com.charlie.zoo.enums.StatusOfEntity;
import com.charlie.zoo.service.*;
import com.sun.corba.se.impl.encoding.CDROutputObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/shop")
@AllArgsConstructor
public class FilterShopController {
    private final static int pageOfProductSize = 12;
    private final ProductService productService;
    private final OrderService orderService;
    private final CategoryService categoryService;
    private final CategoryItemService subCategoryService;
    private final AnimalService animalService;
    private final CookieService cookieService;
    private final ProducerService producerService;

    @GetMapping
    public String getShop(@CookieValue(value = "id", defaultValue = "") String username, Model model,
                          HttpServletResponse httpServletResponse,String sortType, Integer max, Integer min,
                          String packSize, Integer producerId, Integer page,Boolean opt){
        username = cookieService.checkCookie(username,httpServletResponse,model);
        List<Animal> animalList = animalService.findAll();
        model.addAttribute("categoryBtn", animalList);
        model.addAttribute("currentUrl","/shop/");
        model.addAttribute("currentAll","Всі товари");
            Set<Product> beforeFilter = new TreeSet<>(productService.findByStatusByOpt(StatusOfEntity.ACTIVE,opt));
        config(model,username);
        configFilter(model,"/shop",beforeFilter,packSize,min,max,producerId,sortType,page,opt);
        return "user/shop";
    }

    @GetMapping("/{animalUrl}")
    public String getByAnimal(@CookieValue(value = "id", defaultValue = "") String username,
                              HttpServletResponse httpServletResponse,@PathVariable String animalUrl, Model model,
                              String sortType, Integer max, Integer min,String packSize, Integer producerId, Integer page,Boolean opt){
        username = cookieService.checkCookie(username,httpServletResponse,model);
        Animal animal = animalService.findByUrl(animalUrl);
        if(animal!=null) {
            Set<Product> beforeFilter = productService.findByAnimalByOpt(animal,opt);
            model.addAttribute("categoryBtn", animal.getCategories());
            model.addAttribute("currentAll",animal.getName());

            config(model,username);
            configFilter(model,"/shop/"+animalUrl,beforeFilter,packSize,min,max,producerId,sortType,page,opt);
        }
        return "user/shop";
    }

    @GetMapping("/{animalUrl}/{categoryUrl}")
    public String getByAnimalByCategory(@CookieValue(value = "id", defaultValue = "") String username,
                                        HttpServletResponse httpServletResponse,@PathVariable String animalUrl,
                                        @PathVariable String categoryUrl,Model model,String sortType, Integer max,
                                        Integer min,String packSize, Integer producerId, Integer page,Boolean opt){
        username = cookieService.checkCookie(username,httpServletResponse,model);
        Category category = categoryService.findByUrl(animalUrl,categoryUrl);
        model.addAttribute("animal",animalService.findByUrl(animalUrl));
        model.addAttribute("currentUrl","/shop/"+animalUrl+"/"+categoryUrl+"/");
        if(category!=null) {
            Set<Product> products = productService.findByAnimalByCategoryByOpt(category,opt);
            model.addAttribute("categoryBtn", category.getCategoryItems());
            model.addAttribute("currentAll",category.getName());
            config(model,username);
            configFilter(model,"/shop/"+animalUrl+"/"+categoryUrl,products,packSize,min,max,producerId,sortType,page,opt);
        }
        return "user/shop";
    }

    @GetMapping("/{animalUrl}/{categoryUrl}/{subCategoryUrl}")
    public String getBySubCategory(@CookieValue(value = "id", defaultValue = "") String username,
                                   HttpServletResponse httpServletResponse,@PathVariable String animalUrl,
                                   @PathVariable String categoryUrl,@PathVariable String subCategoryUrl,Model  model,
                                   String sortType, Integer max,Integer min,String packSize, Integer producerId,
                                   Integer page,Boolean opt){
        username = cookieService.checkCookie(username,httpServletResponse,model);
        username = cookieService.checkCookie(username,httpServletResponse,model);
        CategoryItem item = subCategoryService.findByUrl(animalUrl,categoryUrl,subCategoryUrl);
        model.addAttribute("animal",animalService.findByUrl(animalUrl));
        model.addAttribute("category",categoryService.findByUrl(animalUrl,categoryUrl));
        if(item!=null) {
            Set<Product> products = productService.findByAnimalByCategoryBySubCategoryByOpt(item,opt);
            model.addAttribute("currentAll", item.getName());
            model.addAttribute("products", products);
            config(model,username);
            configFilter(model,"/shop/"+animalUrl+"/"+categoryUrl+"/"+subCategoryUrl,products,
                    packSize,min,max,producerId,sortType,page,opt);
        }
        return "user/shop";
    }

    private void config(Model  model,String username){
        model.addAttribute("animals",animalService.findAll());
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("orderInfo",orderService.findById(UUID.fromString(username)));
    }

    private void configFilter(Model model,String currentUrl,Set<Product> products,String packSize, Integer min,
                              Integer max,Integer providerId,String sortType,Integer page,Boolean opt) {
        List<Product> afterFilter = productService.getFiltered(products,min,max,packSize,providerId,sortType);
        if(packSize==null || packSize.isEmpty()){
            model.addAttribute("packSizes",productService.getPackSize(new TreeSet<>(afterFilter)));
        }else {
            model.addAttribute("withoutPackUrl",generateUrl(currentUrl,sortType,max,min,null,providerId,opt));
            model.addAttribute("packName",packSize);
        }
        if(providerId==null){
            model.addAttribute("producerList",productService.getProducers(new TreeSet<>(afterFilter)));
        }else{
            {
                model.addAttribute("withoutProducerUrl",generateUrl(currentUrl,sortType,max,min,packSize,null,opt));
                model.addAttribute("producerName",producerService.findById(providerId));
            }
        }
        model.addAttribute("maxPrice",productService.getMaxPrice(products));
        model.addAttribute("minPrice",productService.getMinPrice(products));



        model.addAttribute("currentUrl",currentUrl+"/");
        model.addAttribute("currentUrlFiltered",generateUrl(currentUrl,sortType,max,min,packSize,providerId,opt));

        model.addAttribute("products", getPage(afterFilter,page,pageOfProductSize));

        if(sortType!=null){
            model.addAttribute("sortName",sortType);
        }else{
            model.addAttribute("sortName","");
        }

        if(min!=null && max!=null){
            model.addAttribute("filterPriceValue","Від "+min +" до "+max);
        }
        model.addAttribute("withoutPriceUrl",generateUrl(currentUrl,sortType,null,null,packSize,providerId,opt));
        model.addAttribute("countOfPages",getCountOfPages(afterFilter,pageOfProductSize));
        model.addAttribute("opt",opt);
        model.addAttribute("currentPage",(page==null || page==0)?1:page);
        model.addAttribute("sortingUrl",generateUrl(currentUrl,null,max,min,packSize,providerId,opt));
    }

    public static <T> List<T> getPage(List<T> sourceList, Integer page, int pageSize) {
        if (page==null || pageSize <= 0 || page <= 0 ) {
            page=1;
        }

        int fromIndex = (page - 1) * pageSize;
        if (sourceList == null || sourceList.size() <= fromIndex) {
            return Collections.emptyList();
        }

        // toIndex exclusive
        return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
    }

    public static <T> int getCountOfPages(List<T> source, int pageSize){
        double sz = source.size();
        double ps = pageSize;
        return (int) Math.ceil(sz/ps);
    }

    private String generateUrl(String current,String sortType, Integer max, Integer min,String packSize,
                               Integer producerId,Boolean opt){
        current = current+"?";
        if(sortType!=null && !sortType.isEmpty()){
            current+="sortType="+sortType+"&";
        }
        if(max!=null && min!=null){
            current+="min="+min+"&max="+max+"&";
        }
        if(packSize!=null && !packSize.isEmpty()){
            current+="packSize="+packSize+"&";
        }
        if(producerId!=null){
            current+="producerId="+producerId+"&";
        }
        if(opt!=null){
            current+="opt="+opt.toString()+"&";
        }
        return current;
    }
}
