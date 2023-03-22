package com.ansv.taskmanagement.repository;


import com.ansv.taskmanagement.dto.redis.AccessToken;
//import com.redis.om.spring.repository.RedisDocumentRepository;
import com.redis.om.spring.repository.RedisDocumentRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository
public interface RedisTokenRepository extends RedisDocumentRepository<AccessToken, String> {
//public interface RedisTokenRepository extends CrudRepository<AccessToken, String> {

    Optional<AccessToken> findOneByUsername(String name);

    Iterable<AccessToken> findByUsername(String name);

    Iterable<AccessToken> findAllByUsername(String name);

    Optional<AccessToken> findOneById(String uuid);

    Optional<AccessToken> findById(String Id);

    Iterable<AccessToken> findByUuid(String uuid);

//    Iterable<AccessToken> findAll();


}


