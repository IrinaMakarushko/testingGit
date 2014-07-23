package com.exadel.borsch.dao.impl;

import com.exadel.borsch.dao.UserMessageDao;
import com.exadel.borsch.domain.MessageForView;
import com.exadel.borsch.domain.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 55
 * Date: 5.8.13
 * Time: 19.59
 */
@Repository
public class JdbcUserMessageDao extends JdbcGenericDao implements UserMessageDao {
    public static final int INITIAL_CAPACITY = 5;

    private SimpleJdbcInsert insertMessage;

    @Override
    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        super.setDataSource(dataSource);
        this.insertMessage =
                new SimpleJdbcInsert(dataSource)
                        .withTableName("user_message")
                        .usingGeneratedKeyColumns("id");
    }

    @Override
    public int addRecord(int messageID, int fromUserID, int toUserID, MessageType type) {
        Map<String, Object> parameters = new HashMap(INITIAL_CAPACITY);
        setParameters(messageID, fromUserID, toUserID, type, parameters);
        int newID = insertMessage.executeAndReturnKey(parameters).intValue();
        return newID;
    }

    @Override
    public List<MessageForView> get(int receiverID) {
        String sql = "select m.message, um.read_status, u.name, um.id from user_message as um " +
                "left join message as m on m.message_id = um.message_id " +
                "left join user as u on u.user_id = um.from_user_id where um.to_user_id = ? ";
        return jdbc().query(sql, new UserMessageRowMapper(), receiverID);
    }

    @Override
    public void updateReadStatus(int userMessageID) {
        String sql = "update user_message set read_status = ? where id = ?";
        jdbc().update(sql, Boolean.TRUE, userMessageID);
    }

    private void setParameters(int messageID, int fromUserID, int toUserID,
                               MessageType type, Map<String, Object> parameters) {
        parameters.put("message_id", messageID);
        parameters.put("message_type", type);
        parameters.put("read_status", Boolean.FALSE);
        parameters.put("to_user_id", toUserID);
        parameters.put("from_user_id", fromUserID);
    }

    private static class UserMessageRowMapper  implements RowMapper<MessageForView> {
        @Override
        public MessageForView mapRow(ResultSet rs, int rowNum) throws SQLException {
            MessageForView userMessage = new MessageForView();
            userMessage.setMessageText(rs.getString(1));
            userMessage.setReadStatus(rs.getBoolean(2));
            userMessage.setSenderName(rs.getString(3));
            userMessage.setId(rs.getInt(4));
            return userMessage;
        }
    }
}
