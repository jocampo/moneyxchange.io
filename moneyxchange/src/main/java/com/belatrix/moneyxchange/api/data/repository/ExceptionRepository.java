package com.belatrix.moneyxchange.api.data.repository;

import com.belatrix.moneyxchange.api.data.entities.Exception;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceptionRepository extends CrudRepository<Exception, Long> {

}