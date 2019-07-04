package hu.lanoga.chat.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import hu.lanoga.chat.entity.ChatMessage;
import reactor.core.publisher.Flux;

public class ChatView extends VerticalLayout
{

    public final Flux<ChatMessage> messages;

    public ChatView(Flux<ChatMessage> messages)
    {
        this.messages = messages;
    }

    public Component CreateChat()
    {
        MessageList messageList = new MessageList();


        messages.subscribe(message -> {
            getUI().ifPresent(ui ->
                    ui.access(() ->
                            messageList.add(
                                    new Paragraph(message.getFrom() + ": " +
                                            message.getMessage())
                            )
                    ));
        });

        return messageList;
    }
}
