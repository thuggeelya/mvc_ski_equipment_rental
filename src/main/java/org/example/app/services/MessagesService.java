package org.example.app.services;

import org.example.web.dto.Message;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessagesService {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MessagesService(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Message> getMessages() {
        List<Message> messageList = jdbcTemplate.query("SELECT * FROM messages", (ResultSet rs, int rowNum) -> {
            Message message = new Message();
            message.setId(rs.getInt("id"));
            message.setName(rs.getString("name"));
            message.setEmail(rs.getString("email"));
            message.setTopic(rs.getString("topic"));
            message.setText(rs.getString("text"));
            return message;
        });
        return new ArrayList<>(messageList);
    }
}