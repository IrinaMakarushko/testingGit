package com.exadel.borsch.dao.impl;

import com.exadel.borsch.dao.MessageDao;
import com.exadel.borsch.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: a-dudko
 * Date: 2.8.13
 * Time: 1.01
 */
@Repository
public class JdbcMessageDao extends JdbcGenericDao implements MessageDao     {

    private SimpleJdbcInsert insertMessage;

    @Override
    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        super.setDataSource(dataSource);
        this.insertMessage =
                new SimpleJdbcInsert(dataSource)
                        .withTableName("message")
                        .usingGeneratedKeyColumns("message_id");
    }

    @Override
    public int add(Message message) {
        Map<String, Object> parameters = new HashMap(1);
        parameters.put("message", message.getText());
        int newId = insertMessage.executeAndReturnKey(parameters).intValue();
        message.setId(newId);
        return newId;
    }

    @Override
    public Message get(int messageID) {
        String sql = "select * from message where message_id = ?";
        return jdbc().queryForObject(sql, new MessageRowMapper(), messageID);
    }


    private static class MessageRowMapper implements RowMapper<Message> {
        @Override
        public Message mapRow(ResultSet rs, int i) throws SQLException {
            Message message = new Message();
            message.setId(rs.getInt("message_id"));
            message.setText(rs.getString("message"));
            return message;
        }
    }
}
