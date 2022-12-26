package com.example.demo.repo;

import com.example.demo.models.Banner;
import com.example.demo.models.Hall;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannersRepository extends CrudRepository<Banner, Long> {
}
