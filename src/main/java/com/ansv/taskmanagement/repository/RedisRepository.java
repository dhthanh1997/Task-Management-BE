//package com.ansv.taskmanagement.repository;
//
//import com.ansv.taskmanagement.dto.redis.AccessToken;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.Map;
//
//@Repository
//public class RedisRepository {
//
//    private static final Logger logger = LoggerFactory.getLogger(RedisRepository.class);
//
//    private HashOperations hashOperations;
//
//    private RedisTemplate redisTemplate;
//
//    private static final String ACCESSTOKEN = "accessToken";
//    private static final String REFRESHTOKEN = "refreshToken";
//
//    public RedisRepository(RedisTemplate redisTemplate) {
//        this.redisTemplate = redisTemplate;
//        this.hashOperations = this.redisTemplate.opsForHash();
//    }
//
//    public void saveToken(AccessToken token) {
//        try {
//            hashOperations.put(ACCESSTOKEN, token.getUuid(), token);
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        }
//    };
//
//    public void updateToken(AccessToken token) {
//        saveToken(token);
//    };
//
//    public AccessToken getToken(String uuid) {
//        Map<String, Object> hashMaps = hashOperations.entries(ACCESSTOKEN);
//        Object token = hashOperations.get(ACCESSTOKEN,  uuid);
//        return (AccessToken) hashOperations.get(ACCESSTOKEN,  uuid);
//    };
//
//
//    public void getTokenToString(String uuid) {
//        Map<String, Object> hashMaps = hashOperations.entries(ACCESSTOKEN);
////        Map<String, Object> mapsObject = hashOperations.get(ACCESSTOKEN,  uuid);
//        Object token = hashOperations.get(ACCESSTOKEN,  uuid);
//
//    };
//
//    public void deleteToken(String uuid) {
//        hashOperations.delete(ACCESSTOKEN, uuid);
//    };
//
//}
