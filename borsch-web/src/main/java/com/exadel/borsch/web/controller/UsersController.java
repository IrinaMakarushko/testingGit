package com.exadel.borsch.web.controller;

import com.exadel.borsch.domain.Role;
import com.exadel.borsch.domain.User;
import com.exadel.borsch.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class UsersController {

    public static final int UPDATE_FAILED =0;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView handleSuperAdminRequest() {
        ModelAndView model = new ModelAndView("users");
        setModelSuperAdmin(model);
        return model;
    }


    @RequestMapping(value = "/users/changeRole*", method = RequestMethod.POST)
    public @ResponseBody void handleChangeRole(HttpServletResponse response,int userId,Role role){
        int countOfUpdatedRecords = userService.updateRole(userId,role);
        if(countOfUpdatedRecords == UPDATE_FAILED){
            response.setStatus(230);
        }else{
            response.setStatus(200);
        }
    }

    private void setModelSuperAdmin(ModelAndView model) {
        List<User> users = userService.findUsersWithoutSuperAdmin();
        model.addObject("users", users);
    }
}
