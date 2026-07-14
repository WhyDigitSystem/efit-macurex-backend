package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}

