package com.bexos.authorization_server.repositories;

import com.bexos.authorization_server.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
