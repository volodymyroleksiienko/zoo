package com.charlie.zoo.controller.user;

import com.charlie.zoo.entity.Animal;
import com.charlie.zoo.entity.Category;
import com.charlie.zoo.entity.CategoryItem;
import com.charlie.zoo.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

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
        model.addAttribute("animals",animalService.findAll());
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("categoryBtn", animalService.findAll());
        model.addAttribute("currentUrl","/shop/");
        model.addAttribute("products",productService.getFilteredProduct(category,categoryItem,producer,packSize));
        return "user/shop";
    }

    @GetMapping("/{animalUrl}")
    public String getByAnimal(@PathVariable String animalUrl, Model model, String sortType, Double maxPrice, Double minPrice, Integer producerId ){
        Animal animal = animalService.findByUrl(animalUrl);
        model.addAttribute("categoryBtn", animal.getCategories());
        model.addAttribute("currentUrl","/shop/"+animalUrl+"/");
        if(animal!=null) {
            model.addAttribute("products", productService.findByAnimal(animal));
        }
        return "user/shop";
    }

    @GetMapping("/{animalUrl}/{categoryUrl}")
    public String getByAnimalByCategory(@PathVariable String animalUrl, @PathVariable String categoryUrl,Model model){
        Category category = categoryService.findByUrl(animalUrl,categoryUrl);
        model.addAttribute("categoryBtn", category.getCategoryItems());
        model.addAttribute("currentUrl","/shop/"+animalUrl+"/"+categoryUrl+"/");
        if(category!=null) {
            model.addAttribute("products", productService.findByAnimalByCategory(category));
        }
        return "user/shop";
    }

    @GetMapping("/{animalUrl}/{categoryUrl}/{subCategoryUrl}")
    public String getBySubCategory(@PathVariable String animalUrl, @PathVariable String categoryUrl,@PathVariable String subCategoryUrl,Model  model){
        CategoryItem item = subCategoryService.findByUrl(animalUrl,categoryUrl,subCategoryUrl);
        if(item!=null) {
            model.addAttribute("products", productService.findByAnimalByCategoryBySubCategory(item));
        }
        return "user/shop";
    }
}
