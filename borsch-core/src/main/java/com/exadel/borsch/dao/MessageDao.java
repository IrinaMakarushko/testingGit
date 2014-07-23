package com.exadel.borsch.dao;

import com.exadel.borsch.domain.Message;

/**
 * Created with IntelliJ IDEA.
 * User: 55
 * Date: 31.7.13
 * Time: 17.42
 */
public interface MessageDao {
    int add(Message message);
    Message get(int messageID);
}
