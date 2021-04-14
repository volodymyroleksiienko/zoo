package com.charlie.zoo.controller.admin;


import com.charlie.zoo.entity.Producer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/banners")
@AllArgsConstructor
public class AdminBannersController {

    @GetMapping
    public String getBanners(){
        return "admin/banners";
    }

    @PostMapping({"/edit"})
    public String editBanner(MultipartFile multipartFile){
//        bannerService.save(multipartFile);
        return "redirect:/admin/banners";
    }
}
