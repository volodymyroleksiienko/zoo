package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.Banner;
import com.charlie.zoo.jpa.BannerJPA;
import com.charlie.zoo.service.BannerService;
import com.charlie.zoo.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class BannerServiceImpl implements BannerService {
    private BannerJPA bannerJPA;
    private ImageService imageService;

    @Override
    public Banner save(MultipartFile file, String url) {
        Banner banner = new Banner();
        banner.setImage(imageService.save(file));
        banner.setUrl(url);
        return bannerJPA.save(banner);
    }

    @Override
    public Banner update(int id, MultipartFile file, String url) {
        Banner banner = findById(id);
        if(banner==null){
            banner = new Banner();
        }
        banner.setId(id);
        banner.setImage(imageService.save(file));
        banner.setUrl(url);
        return bannerJPA.save(banner);
    }

    @Override
    public Banner findById(int id) {
        return bannerJPA.findById(id).orElse(null);
    }

    @Override
    public void deleteById(int id) {
        bannerJPA.deleteById(id);
    }
}
