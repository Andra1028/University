package com.example.java_mpp_bun.repository;



import com.example.java_mpp_bun.domain.User;

import java.util.List;

public interface UserRepo extends Repository<Integer, User> {
     User findOne(Integer id);
    User findByName(String userName);
    List<User> findAll();
    void save(User entity);
}