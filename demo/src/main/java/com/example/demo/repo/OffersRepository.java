package com.example.demo.repo;

import com.example.demo.models.Offer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffersRepository extends CrudRepository<Offer, Long> {
}
