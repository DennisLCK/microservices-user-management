package com.dennislck.user.query.api.repositories;

import com.dennislck.user.core.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

}
