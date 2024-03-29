package hu.lanoga.chat;

import hu.lanoga.chat.entity.ChatMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    UnicastProcessor<ChatMessage> publisher(){
        return UnicastProcessor.create();
    }
    @Bean
    Flux<ChatMessage> messages(UnicastProcessor<ChatMessage> publisher) {
        return publisher.replay(30).autoConnect();
    }

}
