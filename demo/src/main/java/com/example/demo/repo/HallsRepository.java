package com.example.demo.repo;

import com.example.demo.models.Hall;
import com.example.demo.models.HallId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallsRepository extends CrudRepository<Hall, HallId> {
}
