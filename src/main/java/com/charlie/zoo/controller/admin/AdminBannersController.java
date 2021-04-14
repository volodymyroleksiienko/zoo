package com.charlie.zoo.controller.admin;


import com.charlie.zoo.service.BannerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String getBanners(Model model){
        model.addAttribute("ban1",bannerService.findById(1));
        model.addAttribute("ban2",bannerService.findById(2));
        model.addAttribute("ban3",bannerService.findById(3));
        model.addAttribute("ban4",bannerService.findById(4));
        return "admin/banners";
    }

    @PostMapping("/edit")
    public String editBanner(Integer id,MultipartFile multipartFile,String url){
        System.out.println("id" + id);
        if(id==null){
            id = 0;
        }
        bannerService.update(id,multipartFile,url);
        return "redirect:/admin/banners";
    }
}
