package com.exadel.borsch.domain;

/**
 * Created with IntelliJ IDEA.
 * User: alpoloz
 * Date: 7/21/13
 * Time: 9:17 PM
 */
public class User {

    private int id;
    private String name;
    private String password;
    private Role role;

    public User() {
    }

    public User(String name, String password, Role role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
