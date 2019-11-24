package com.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisPoolConfig;


@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
	
    
	
  @Bean
  @Override
  public KeyGenerator keyGenerator() {
    return (target, method, params) -> {
      StringBuilder sb = new StringBuilder();
      sb.append(target.getClass().getName());
      sb.append(method.getName());
      for (Object obj : params) {
        sb.append(obj.toString());
      }
      return sb.toString();
    };
  }
  @Bean
  public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
    RedisCacheManager redisCacheManager = RedisCacheManager.create(connectionFactory);
    return redisCacheManager;
  }

  @Value("${spring.redis.host}")
  private String host;
  @Value("${spring.redis.port}")
  private int port;
  @Value("${spring.redis.database}")
  private int database;
  @Value("${spring.redis.jedis.pool.max-idle}")
  private int maxIdle;
  @Value("${spring.redis.jedis.pool.max-wait}")
  private long maxWaitMillis;
  @Value("${spring.redis.jedis.pool.max-active}")
  private int maxActive;
  
  @Bean
  public JedisPoolConfig jedisPoolConfig() {
	    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
	    jedisPoolConfig.setMaxTotal(maxActive);
	    jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
	    jedisPoolConfig.setMaxIdle(maxIdle);
	    return jedisPoolConfig;
	  }


/**
   * 重新实现RedisTemplate：解决序列化问题
   * @param redisConnectionFactory
   * @return
   */
  @Bean
  @SuppressWarnings({"rawtype", "unchecked"})
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
    RedisTemplate<String, Object> template = new RedisTemplate();
    template.setConnectionFactory(redisConnectionFactory);
    Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
    ObjectMapper om = new ObjectMapper();
    // 设置任何字段可见
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    // 设置不是final的属性可以转换
    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

    jackson2JsonRedisSerializer.setObjectMapper(om);
    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    // key采用String的序列化方式
    template.setKeySerializer(stringRedisSerializer);
    // hash的key采用String的序列化方式
    template.setHashKeySerializer(stringRedisSerializer);
    // value序列化方式采用jackson序列化方式
    template.setValueSerializer(jackson2JsonRedisSerializer);
    // hash的value序列化方式采用jackson序列化方式
    template.setHashValueSerializer(jackson2JsonRedisSerializer);
    template.afterPropertiesSet();
    template.setEnableTransactionSupport(true);
    return template;
  }
 
  /**
   * 重新实现StringRedisTmeplate：键值都是String的的数据
   * @param redisConnectionFactory
   * @return
   */
  @Bean
  public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    StringRedisTemplate template = new StringRedisTemplate();
    Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
    template.setConnectionFactory(redisConnectionFactory);
    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    // key采用String的序列化方式
    template.setKeySerializer(stringRedisSerializer);
    // hash的key采用String的序列化方式
    template.setHashKeySerializer(stringRedisSerializer);
    // value序列化方式采用jackson序列化方式
    template.setValueSerializer(jackson2JsonRedisSerializer);
    // hash的value序列化方式采用jackson序列化方式
    template.setHashValueSerializer(jackson2JsonRedisSerializer);
    return template;
  }
  /**
   * 注入RedisConnectionFactory
   * @return
   */
  @Bean
  public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
    /* 在Spring Boot 1.x中已经过时，采用RedisStandaloneConfiguration配置
    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig);
    jedisConnectionFactory.setHostName(host);
    jedisConnectionFactory.setDatabase(database);*/
 
    // JedisConnectionFactory配置hsot、database、password等参数
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
    redisStandaloneConfiguration.setHostName(host);
    redisStandaloneConfiguration.setPort(port);
    redisStandaloneConfiguration.setDatabase(database);
    // JedisConnectionFactory配置jedisPoolConfig
    JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisPoolConfigBuilder =
        (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
    jedisPoolConfigBuilder.poolConfig(jedisPoolConfig);
    return new JedisConnectionFactory(redisStandaloneConfiguration);
 
  }
  
}
