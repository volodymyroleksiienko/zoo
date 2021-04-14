package com.charlie.zoo.service;

import com.charlie.zoo.entity.Banner;
import org.springframework.web.multipart.MultipartFile;

public interface BannerService {
    Banner save(MultipartFile file, String url);
    Banner update(int id,MultipartFile file, String url);
    Banner findById(int id);
    void deleteById(int id);
}
