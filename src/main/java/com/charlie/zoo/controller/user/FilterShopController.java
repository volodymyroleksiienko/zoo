package com.charlie.zoo.controller.user;

import com.charlie.zoo.entity.Animal;
import com.charlie.zoo.entity.Category;
import com.charlie.zoo.entity.CategoryItem;
import com.charlie.zoo.entity.Product;
import com.charlie.zoo.enums.StatusOfEntity;
import com.charlie.zoo.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/shop")
@AllArgsConstructor
public class FilterShopController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final CategoryItemService subCategoryService;
    private final AnimalService animalService;
    private final CookieService cookieService;

    @GetMapping
    public String getShop(@CookieValue(value = "id", defaultValue = "") String username, Model model,
                          HttpServletResponse httpServletResponse, Integer[] category, Integer[] categoryItem, Integer[] producer, Double[] packSize){
        cookieService.checkCookie(username,httpServletResponse,model);
        List<Animal> animalList = animalService.findAll();
        model.addAttribute("categoryBtn", animalList);
        model.addAttribute("currentUrl","/shop/");
        model.addAttribute("currentAll","Всі товари");
        Set<Product> products = new HashSet<>(productService.findByStatus(StatusOfEntity.ACTIVE));
        model.addAttribute("products",products);
        config(model,products);
        return "user/shop";
    }

    @GetMapping("/{animalUrl}")
    public String getByAnimal(@PathVariable String animalUrl, Model model, String sortType, Integer max, Integer min,String packSize, Integer producerId ){
        Animal animal = animalService.findByUrl(animalUrl);
        System.out.println(max);
        System.out.println(min);
        if(animal!=null) {
            Set<Product> products = productService.findByAnimal(animal);
            model.addAttribute("categoryBtn", animal.getCategories());
            model.addAttribute("currentUrl","/shop/"+animalUrl+"/");
            model.addAttribute("currentAll",animal.getName());
            model.addAttribute("products", productService.getFiltered(products,min,max,packSize,producerId));
            config(model,products);
        }
        return "user/shop";
    }

    @GetMapping("/{animalUrl}/{categoryUrl}")
    public String getByAnimalByCategory(@PathVariable String animalUrl, @PathVariable String categoryUrl,Model model){
        Category category = categoryService.findByUrl(animalUrl,categoryUrl);
        model.addAttribute("animal",animalService.findByUrl(animalUrl));
        model.addAttribute("currentUrl","/shop/"+animalUrl+"/"+categoryUrl+"/");
        if(category!=null) {
            Set<Product> products = productService.findByAnimalByCategory(category);
            model.addAttribute("categoryBtn", category.getCategoryItems());
            model.addAttribute("currentAll",category.getName());
            model.addAttribute("products", products);
            config(model,products);
        }
        return "user/shop";
    }

    @GetMapping("/{animalUrl}/{categoryUrl}/{subCategoryUrl}")
    public String getBySubCategory(@PathVariable String animalUrl, @PathVariable String categoryUrl,@PathVariable String subCategoryUrl,Model  model){
        CategoryItem item = subCategoryService.findByUrl(animalUrl,categoryUrl,subCategoryUrl);
        model.addAttribute("animal",animalService.findByUrl(animalUrl));
        model.addAttribute("category",categoryService.findByUrl(animalUrl,categoryUrl));
        if(item!=null) {
            Set<Product> products = productService.findByAnimalByCategoryBySubCategory(item);
            model.addAttribute("currentAll", item.getName());
            model.addAttribute("products", products);
            config(model,products);
        }
        return "user/shop";
    }

    private void config(Model  model,Set<Product> products){
        model.addAttribute("animals",animalService.findAll());
        model.addAttribute("categories",categoryService.findAll());
//        model.addAttribute("orderInfo",orderService.findById(UUID.fromString(username)));
        model.addAttribute("packSizes",productService.getPackSize(products));
        model.addAttribute("producerList",productService.getProducers(products));
        model.addAttribute("maxPrice",productService.getMaxPrice(products));
        model.addAttribute("minPrice",productService.getMinPrice(products));
    }
}