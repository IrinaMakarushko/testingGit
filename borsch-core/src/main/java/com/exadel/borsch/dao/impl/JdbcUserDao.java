package com.exadel.borsch.dao.impl;

import com.exadel.borsch.dao.UserDao;
import com.exadel.borsch.domain.Role;
import com.exadel.borsch.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: alpoloz
 * Date: 7/21/13
 * Time: 9:56 PM
 */
@Repository
public class JdbcUserDao extends JdbcGenericDao implements UserDao {
    private SimpleJdbcInsert insertMessage;

    @Override
    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        super.setDataSource(dataSource);
        this.insertMessage =
                new SimpleJdbcInsert(dataSource)
                        .withTableName("user")
                        .usingGeneratedKeyColumns("user_id");
    }

    public int createUser(User user) {
        Map<String, Object> parameters = new HashMap(3);
        parameters.put("name", user.getName());
        parameters.put("password", user.getPassword());
        parameters.put("role", user.getRole().toString());
        int newId = insertMessage.executeAndReturnKey(parameters).intValue();
        user.setId(newId);
        return newId;
    }

    @Override
    public List<User> findUsers() {
        String sql = "select * from user";
        return jdbc().query(sql, new UserRowMapper());
    }

   @Override
    public int updateRole(int id,Role newRole){
       String sql = "update user set role=? where user_id=?";
       return jdbc().update(sql, newRole.name(),id);
    }

    @Override
    public List<User> findUsersWithoutSuperAdmin(){
        String sql ="select * from user where role != ?";
        return jdbc().query(sql, new UserRowMapper(),Role.SUPER_ADMIN.toString());
    }

    @Override
    public User getByName(String name) {
        try {
            String sql = "select * from user where name = ?";
            return jdbc().queryForObject(sql, new Object[]{name}, new UserRowMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public User get(int userID) {
        String sql = "select * from user where user_id = ?";
        return jdbc().queryForObject(sql, new UserRowMapper(), userID);
    }

    @Override
    public List<User> findUsersByRole(Role role) {
        String sql = "select * from user where role = ?";
        return jdbc().query(sql, new UserRowMapper(), role.toString());
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setRole(Role.valueOf(rs.getString("role")));
            return user;
        }
    }
}
