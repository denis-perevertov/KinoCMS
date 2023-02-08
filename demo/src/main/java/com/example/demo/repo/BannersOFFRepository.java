package com.example.demo.repo;

import com.example.demo.models.Banner;
import com.example.demo.models.Banner_OFF;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannersOFFRepository extends CrudRepository<Banner_OFF, Long> {
}
