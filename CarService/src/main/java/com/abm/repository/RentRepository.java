package com.abm.repository;

import com.abm.entity.Car;
import com.abm.entity.Rent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository extends MongoRepository<Rent,String> {
}
