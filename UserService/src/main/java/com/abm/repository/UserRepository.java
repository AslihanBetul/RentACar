package com.abm.repository;

import com.abm.entity.enums.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
    User findByAuthId(Long authId);
}
