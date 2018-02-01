package com.belatrix.moneyxchange.api.data.repository;

import com.belatrix.moneyxchange.api.data.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserName(String username);
}