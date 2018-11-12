//package com.simple;
//
//import com.alibaba.fastjson.JSON;
//import com.simple.support.RedisCache;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by chenkaixiang on 2017-12-26.
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class RedisTest {
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @Autowired
//    private RedisCache redisCache;
//
//
//    @Test
//    public void test() throws Exception {
//        stringRedisTemplate.opsForValue().set("aaa", "111");
//        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
//    }
//
//
//    @Test
//    public void testObj() throws Exception {
//        User user = new User("aa@126.com", "aa", "aa123456", "aa", "123");
//        ValueOperations<String, User> operations = redisTemplate.opsForValue();
//        operations.set("com.simple", user);
//        operations.set("com.simple.f", user, 1, TimeUnit.MINUTES);
//        //Thread.sleep(1000);
//        //redisTemplate.delete("com.simple.f");
//        boolean exists = redisTemplate.hasKey("com.simple.f");
//        if (exists) {
//            System.out.println("exists is true");
//        } else {
//            System.out.println("exists is false");
//        }
//        final User user1 = operations.get("com.simple.f");
//        Assert.assertEquals("aa", user1.getUserName());
//
//    }
//
//    @Test
//    public void testRedisCache() throws Exception {
//        User user = new User("aa@126.com", "aa", "aa123456", "aa", "123");
//        String redisKey = "m" + User.class.getSimpleName().toString();
//        redisCache.set(redisKey, user);
//
//        if (redisCache.exists(redisKey)) {
//            System.out.println(JSON.toJSONString(redisCache.get(redisKey)));
//        } else {
//            System.out.println("啥都没有");
//        }
//    }
//
//    private static class User {
//        private String email;
//        private String userName;
//        private String password;
//        private String address;
//        private String age;
//
//        public User() {
//        }
//
//        public User(String email, String userName, String password, String address, String age) {
//            this.email = email;
//            this.userName = userName;
//            this.password = password;
//            this.address = address;
//            this.age = age;
//        }
//
//        public String getEmail() {
//            return email;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }
//
//        public String getUserName() {
//            return userName;
//        }
//
//        public void setUserName(String userName) {
//            this.userName = userName;
//        }
//
//        public String getPassword() {
//            return password;
//        }
//
//        public void setPassword(String password) {
//            this.password = password;
//        }
//
//        public String getAddress() {
//            return address;
//        }
//
//        public void setAddress(String address) {
//            this.address = address;
//        }
//
//        public String getAge() {
//            return age;
//        }
//
//        public void setAge(String age) {
//            this.age = age;
//        }
//    }
//}
//
