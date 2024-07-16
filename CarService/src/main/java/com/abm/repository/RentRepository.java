package com.abm.repository;

import com.abm.dto.response.RentResponseDto;
import com.abm.entity.Car;
import com.abm.entity.Rent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends MongoRepository<Rent,String> {


   List<Rent> getRentByUserId(String userId);
}
