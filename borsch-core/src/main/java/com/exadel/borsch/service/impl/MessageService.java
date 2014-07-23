package com.exadel.borsch.service.impl;

import com.exadel.borsch.dao.MessageDao;
import com.exadel.borsch.domain.Message;
import com.exadel.borsch.domain.MessageType;
import com.exadel.borsch.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: a-dudko
 * Date: 2.8.13
 * Time: 1.40
 */
@Service
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class MessageService {
    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UserMessageService userMessageService;

    @Transactional
    public void sendMessage(Message message, int senderID, int receiverID, MessageType messageType) {
        int messageId = messageDao.add(message);
        userMessageService.addRecord(messageId, senderID, receiverID, messageType);
    }

    @Transactional
    public void sendMessageToAll(Message message, int senderID, List<User> receivers, MessageType messageType) {
        int messageId = messageDao.add(message);
        for (User receiver : receivers) {
            userMessageService.addRecord(messageId, senderID, receiver.getId(), messageType);
        }
    }

    public Message get(int messageID) {
        return messageDao.get(messageID);
    }
}
