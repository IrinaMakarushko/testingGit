package com.exadel.borsch.dao;

import com.exadel.borsch.domain.Role;
import com.exadel.borsch.domain.User;

import java.util.List;


public interface UserDao {

    List<User> findUsers();

    User get(int userID);

    List<User> findUsersByRole(Role role);

    User getByName(String name);

    int createUser(User user);

    List<User> findUsersWithoutSuperAdmin();

    int updateRole(int userId,Role role);
}
