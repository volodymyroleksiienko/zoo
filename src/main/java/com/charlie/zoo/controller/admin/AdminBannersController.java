package com.charlie.zoo.controller.admin;


import com.charlie.zoo.service.BannerService;
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
    private BannerService bannerService;

    @GetMapping
    public String getBanners(){
        return "admin/banners";
    }

    @PostMapping({"/edit"})
    public String editBanner(int id,MultipartFile multipartFile,String url){
        bannerService.update(id,multipartFile,url);
        return "redirect:/admin/banners";
    }
}
