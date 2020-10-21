package com.smart.dao.impl;

import com.smart.dao.UserDao;
import com.smart.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.FilteredImageSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //根据用户名和密码获取匹配的用户数
    public int getMatchCount(String userName, String password) {
        String sqlStr = "select count(*) from t_user " + " where user_name=? and password=? ";
        return jdbcTemplate.queryForObject(sqlStr,new Object[]{userName,password},Integer.class);
    }

    //根据用户名获取用户对象
    private final static String MATCH_COUNT_SQL = "select * from " + " t_user where user_name=?";

    public User findUserByUserName(final String userName) {
        final User user = new User();
        jdbcTemplate.query(MATCH_COUNT_SQL, new Object[]{userName}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserName(userName);
                user.setCredits(resultSet.getInt("credits"));
            }
        });
        System.out.println(user.toString());
        return user;
    }

    //更新用户信息
    private final static String UPDATE_LOGIN_INFO_SQL = "update t_user set "+" last_visit=?,last_ip=?,credits=? "+" where user_id=?";

    public void updateLoginInfo(User user) {
        System.out.println(user.toString());
        jdbcTemplate.update(UPDATE_LOGIN_INFO_SQL,new Object[] {user.getLastVisit(),user.getLastIp(),user.getCredits(),user.getUserId()});

    }
}
