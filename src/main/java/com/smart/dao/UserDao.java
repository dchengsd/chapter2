package com.smart.dao;

import com.smart.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    public int getMatchCount(String userName, String password);

    public User findUserByUserName(final String userName);

    public void updateLoginInfo(User user);

}
