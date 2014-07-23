package com.exadel.borsch.service.impl;

import com.exadel.borsch.dao.UserDao;
import com.exadel.borsch.domain.Role;
import com.exadel.borsch.domain.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: apolozkov
 * Date: 8/15/11
 * Time: 5:34 PM
 */
@Service("userDetailsService")
public class UserSecurityService implements UserDetailsService, InitializingBean {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SaltSource saltSource;

    public void afterPropertiesSet() throws Exception {
        if (userDao.getByName("super") == null) {
            createFakeUser(new User("super", "super", Role.SUPER_ADMIN));
        }
        if (userDao.getByName("admin") == null) {
            createFakeUser(new User("admin", "admin", Role.ADMIN));
        }
        if (userDao.getByName("user") == null) {
            createFakeUser(new User("user", "user", Role.USER));
        }
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException, DataAccessException {
        User user = userDao.getByName(name);
        if (user != null) {
            ArrayList<GrantedAuthority> authorities = newArrayList();
            authorities.add(new GrantedAuthorityImpl(user.getRole().name()));
            return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), true, true, true, true, authorities);
        } else {
            throw new UsernameNotFoundException("Can't locate user '" + name + "'");
        }
    }

    public void createFakeUser(User user) {
        org.springframework.security.core.userdetails.User springUser = new org.springframework.security.core.userdetails.User(user.getName(), "", true, true, true, true, Collections.<GrantedAuthority>emptyList());
        String encodedPassword = passwordEncoder.encodePassword(user.getPassword(), saltSource.getSalt(springUser));
        user.setPassword(encodedPassword);
        userService.createUser(user);
    }


}