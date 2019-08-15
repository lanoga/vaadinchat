package hu.lanoga.chat.service;

import hu.lanoga.chat.config.security.MyUserPrincipal;
import hu.lanoga.chat.entity.ChatUser;
import hu.lanoga.chat.repository.ChatUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatUserService implements UserDetailsService {

    @Autowired
    private ChatUserRepository chatUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
    {
        ChatUser user = chatUserRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }
}
