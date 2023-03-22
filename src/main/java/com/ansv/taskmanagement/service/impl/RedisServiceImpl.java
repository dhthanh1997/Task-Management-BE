package com.ansv.taskmanagement.service.impl;

import com.ansv.taskmanagement.dto.redis.AccessToken;
import com.ansv.taskmanagement.repository.RedisRepository;
import com.ansv.taskmanagement.repository.RedisTokenRepository;
import com.ansv.taskmanagement.util.DataUtils;
import com.ansv.taskmanagement.service.RedisService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    private static final String BEARER_PREFIX = "Bearer ";

//    private final RedisTemplate<String, String> redisTemplate;

//    public Optional<Authentication> authenticate(HttpServletRequest request) {
//        return extractBearTokenHeader(request).flatMap(this::lookup);
//    }

//    @Autowired
//    private RedisRepository redisRepository;

    @Autowired
    private RedisTokenRepository redisTokenRepository;

//
//    protected Optional<String> lookUpToken(String token) {
//        try {
//            String userId = this.redisTemplate.opsForValue().get(token);
//            if(DataUtils.notNull(userId)) {
//                return Optional.of(userId);
//            }
//            return Optional.empty();
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return Optional.empty();
//        }
//    }


    // using for extract token
    private static Optional<String> extractBearTokenHeader(@NonNull HttpServletRequest request) {
        try {
            String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(DataUtils.notNull(authorization)) {
                if(authorization.startsWith(BEARER_PREFIX)) {
                    String token = authorization.substring(BEARER_PREFIX.length()).trim();
                    if(!token.isBlank()) {
                        return Optional.of(token);
                    }
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }


    @Override
    public void saveTokenToRedis(AccessToken token) {
        try {
            redisTokenRepository.save(token);
        } catch(Exception e) {
            logger.error("An error when save token into redis db", e);
        }
    }

    @Override
    public void updateTokenToRedis(AccessToken token) {
        try {
            redisTokenRepository.save(token);
        } catch(Exception e) {
            logger.error("An error when update token into redis db", e);
        }
    }

    @Override
    public Optional<AccessToken> getTokenToRedis(String uuid) {
        try {
            Optional<AccessToken> token = redisTokenRepository.findOneById(uuid);
            Iterable<AccessToken> tokenList = redisTokenRepository.findByUsername("trantuanvu8594");
            if(token.isPresent()) {
                return token;
            }
            return Optional.empty();
        } catch(Exception e) {
            logger.error("An error when get token into redis db", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteTokenToRedis(String uuid) {
        try {
            redisTokenRepository.deleteById(uuid);
        } catch(Exception e) {
            logger.error("An error when get token into redis db", e);
        }
    }

    @Override
    public Optional<AccessToken> getToken(String id) {
        try {
            Optional<AccessToken> token = redisTokenRepository.findById("73308aec-7049-4a89-aebf-7dc1d5835b4b");
            Iterable<AccessToken> tokenList = redisTokenRepository.findByUsername("trantuanvu8594");
//            Iterable<String> ids = redisTokenRepository.getIds();


//            Optional<AccessToken> token = ;
            if(token.isPresent()) {
                return token;
            }
            return Optional.empty();
        } catch(Exception e) {
            logger.error("An error when get token into redis db", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<AccessToken> getTokenRedis(String uuid) {
        try {
            Optional<AccessToken> token = redisTokenRepository.findOneById(uuid);
            if(token.isPresent()) {
                return token;
            }
            return Optional.empty();
        } catch(Exception e) {
            logger.error("An error when get token into redis db", e);
            return Optional.empty();

        }
    }

    @Override
    public String generateUUIDVersion1() {
        long most64SigBits = get64MostSignificantBitsForVersion1();
        long least64SigBits = get64LeastSignificantBitsForVersion1();
        return new UUID(most64SigBits, least64SigBits).toString();
    }

    private static long get64LeastSignificantBitsForVersion1() {
        Random random = new Random();
        long random63BitLong = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
        long variant3BitFlag = 0x8000000000000000L;
        return random63BitLong | variant3BitFlag;
    }

    private static long get64MostSignificantBitsForVersion1() {
        final long currentTimeMillis = System.currentTimeMillis();
        final long time_low = (currentTimeMillis & 0x0000_0000_FFFF_FFFFL) << 32;
        final long time_mid = ((currentTimeMillis >> 32) & 0xFFFF) << 16;
        final long version = 1 << 12;
        final long time_hi = ((currentTimeMillis >> 48) & 0x0FFF);
        return time_low | time_mid | version | time_hi;
    }


}
