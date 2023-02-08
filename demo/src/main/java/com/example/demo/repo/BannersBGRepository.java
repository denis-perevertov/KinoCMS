package com.example.demo.repo;

import com.example.demo.models.Banner;
import com.example.demo.models.Banner_BG;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannersBGRepository extends CrudRepository<Banner_BG, Long> {
}
