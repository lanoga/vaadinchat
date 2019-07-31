package hu.lanoga.chat.service;

import hu.lanoga.chat.entity.ChatUser;
import hu.lanoga.chat.repository.ChatUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatUserService {

    @Autowired
    private ChatUserRepository chatUserRepository;

    public List<ChatUser> getAllUsers(){
        return chatUserRepository.findAll();
    }
}
