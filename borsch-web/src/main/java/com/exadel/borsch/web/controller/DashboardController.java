package com.exadel.borsch.web.controller;

import com.exadel.borsch.domain.*;
import com.exadel.borsch.service.impl.MessageService;
import com.exadel.borsch.service.impl.UserMessageService;
import com.exadel.borsch.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/dashboard")
public class DashboardController {
    public static final int COMMON_USER_ID = 0;

    public static final int COMMON_ADMIN_ID = -1;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserMessageService userMessageService;

    @RequestMapping
    public ModelAndView handleDashboardRequest(Principal principal) {
        ModelAndView model = new ModelAndView("dashboard");
        setModelObjects(model, principal);
        return model;
    }

    private void setModelObjects(ModelAndView model, Principal principal) {
        User currentUser = userService.getByName(principal.getName());
        List<MessageForView> messages = getCurrentUserMessages(currentUser);
        List<User> contacts = getUsers(currentUser);
        model.addObject("messages", messages);
        model.addObject("contacts", contacts);
        model.addObject("role", currentUser.getRole().toString());
    }

    private List<MessageForView> getCurrentUserMessages(User currentUser) {
        int receiverID = currentUser.getId();
        return userMessageService.getMessagesForView(receiverID);
    }

    private List<User> getUsers(User currentUser) {
        List<User> contacts;
        Role roleToFind = (currentUser.getRole() == Role.USER) ? Role.ADMIN : Role.USER;
        contacts = userService.findUsersByRole(roleToFind);
        return contacts;
    }

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public void handleNewMessageRequest(HttpServletResponse response, int receiverID,
                                        String messageText, Principal principal) {
        User currentUser = userService.getByName(principal.getName());
        if (receiverID == COMMON_USER_ID) {
            sendMessageToAllUsersByRole(messageText, currentUser, Role.USER);
        } else if (receiverID == COMMON_ADMIN_ID) {
            sendMessageToAllUsersByRole(messageText, currentUser, Role.ADMIN);
        } else {
            sendMessageToSingleUser(messageText, currentUser, receiverID);
        }
        response.setStatus(200);
    }

    private void sendMessageToSingleUser(String messageText, User currentUser, int receiverID) {
        Message message = createMessage(messageText);
        int senderID = currentUser.getId();
        MessageType messageType = getMessageType(currentUser);
        messageService.sendMessage(message, senderID, receiverID, messageType);
    }

    private void sendMessageToAllUsersByRole(String messageText, User currentUser, Role role) {
        List<User> users = userService.findUsersByRole(role);
        Message message = createMessage(messageText);
        int senderID = currentUser.getId();
        MessageType messageType = getMessageType(currentUser);
        messageService.sendMessageToAll(message, senderID, users, messageType);
    }

    private MessageType getMessageType(User currentUser) {
        MessageType messageType = MessageType.FROM_USER;
        if (currentUser.getRole() == Role.ADMIN) {
            messageType = MessageType.FROM_ADMIN;
        }
        return messageType;
    }

    @RequestMapping(value = "/readMessage", method = RequestMethod.POST)
    public void handleMessageReadRequest(HttpServletResponse response, int messageID) {
        userMessageService.updateReadStatus(messageID);
        response.setStatus(200);
    }

    private Message createMessage(String messageText) {
        Message message = new Message();
        message.setText(messageText);
        return message;
    }

    @RequestMapping(value = "/getMessages", method = RequestMethod.POST)
    public @ResponseBody List<MessageForView> handleGetMessagesRequest(Principal principal, int oldMessagesCount) {
        User currentUser = userService.getByName(principal.getName());
        List<MessageForView> messages = userMessageService.getMessagesForView(currentUser.getId());
        return getNewMessages(messages, oldMessagesCount);
    }

    private List<MessageForView> getNewMessages(List<MessageForView> allMessages, int oldMessagesCount) {
        List<MessageForView> newMessages = new ArrayList<>();
        for (int i = oldMessagesCount; i < allMessages.size(); i++) {
            newMessages.add(allMessages.get(i));
        }
        return newMessages;
    }

    @RequestMapping(value = "/getMessage", method = RequestMethod.POST)
    public @ResponseBody String handleGetMessageRequest(int messageID) {
        return messageService.get(messageID).getText();
    }
}