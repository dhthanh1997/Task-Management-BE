package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.redis.AccessToken;
import com.ansv.taskmanagement.dto.redis.RefreshToken;

import java.util.Optional;

public interface RedisService {
     void saveAccessToken(AccessToken token);
     void updateAccessToken(AccessToken token);
     Optional<AccessToken> getAccessToken(String uuid);
     void deleteAccessToken(String uuid);
     void saveRefreshToken(RefreshToken token);
     void updateRefreshToken(RefreshToken token);
     Optional<RefreshToken> getRefreshToken(String uuid);
     void deleteRefreshToken(String uuid);
     String generateUUIDVersion1();

}
