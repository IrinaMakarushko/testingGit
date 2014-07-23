package com.exadel.borsch.service.impl;

import com.exadel.borsch.dao.UserDao;
import com.exadel.borsch.domain.Role;
import com.exadel.borsch.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alpoloz
 * Date: 7/28/13
 * Time: 9:09 PM
 */
@Service
@PreAuthorize("isAuthenticated()")
public class UserService {

    @Autowired
    private UserDao userDao;

    public List<User> findUsers() {
        return userDao.findUsers();
    }

    @Transactional
    public int createUser(User user) {
        return userDao.createUser(user);
    }

    public List<User> findUsersByRole(Role role) {
        return userDao.findUsersByRole(role);
    }

    public User get(int userID) {
        return userDao.get(userID);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public List<User> findUsersWithoutSuperAdmin(){
        return userDao.findUsersWithoutSuperAdmin();
    }

    @Transactional
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public int updateRole(int userId, Role newRole){
        return userDao.updateRole(userId,newRole);
    }

    public User getByName(String name) {
        return userDao.getByName(name);
    }
}
