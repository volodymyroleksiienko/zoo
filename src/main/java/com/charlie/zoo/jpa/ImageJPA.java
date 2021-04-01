package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageJPA extends JpaRepository<Image,Integer> {
    Image findFirstByProductIdAndMainTrue(int id);
    List<Image> findAllByProductId(int id);
}
