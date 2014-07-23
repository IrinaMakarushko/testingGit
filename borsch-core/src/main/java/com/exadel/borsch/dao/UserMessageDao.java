package com.exadel.borsch.dao;

import com.exadel.borsch.domain.MessageForView;
import com.exadel.borsch.domain.MessageType;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 55
 * Date: 6.8.13
 * Time: 0.03
 */
public interface UserMessageDao {
    int addRecord(int messageID, int fromUserID, int toUserID, MessageType type);

    List<MessageForView> get(int receiverID);

    void updateReadStatus(int userMessageID);
}
