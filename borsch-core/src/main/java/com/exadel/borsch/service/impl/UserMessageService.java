package com.exadel.borsch.service.impl;

import com.exadel.borsch.dao.UserMessageDao;
import com.exadel.borsch.domain.MessageForView;
import com.exadel.borsch.domain.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 55
 * Date: 6.8.13
 * Time: 0.07
 */
@Service
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class UserMessageService {
    @Autowired
    private UserMessageDao userMessageDao;

    public void addRecord(int messageID, int fromUserID, int toUserID, MessageType type) {
        userMessageDao.addRecord(messageID, fromUserID, toUserID, type);
    }

    public List<MessageForView> getMessagesForView(int receiverID) {
        return userMessageDao.get(receiverID);
    }

    public void updateReadStatus(int messageID) {
        userMessageDao.updateReadStatus(messageID);
    }
}
