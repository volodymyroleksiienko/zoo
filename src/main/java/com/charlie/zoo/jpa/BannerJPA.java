package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerJPA extends JpaRepository<Banner,Integer> {
}
