package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.PackageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageTypeJPA extends JpaRepository<PackageType,Integer> {
}
