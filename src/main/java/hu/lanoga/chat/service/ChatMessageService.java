package hu.lanoga.chat.service;

import hu.lanoga.chat.entity.ChatMessage;
import hu.lanoga.chat.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public List<ChatMessage> getAllMessages(){

        return chatMessageRepository.findAll();
    }


}

