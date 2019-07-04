package hu.lanoga.chat.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import hu.lanoga.chat.entity.ChatMessage;
import reactor.core.publisher.UnicastProcessor;

public class InputView {

    public final UnicastProcessor<ChatMessage> publisher;
    public String username;

    public InputView(UnicastProcessor<ChatMessage> publisher, String username)
    {
        this.publisher = publisher;
        this.username = username;
    }

    public Component CreateInput()
    {
        HorizontalLayout layout = new HorizontalLayout();

        TextField messageField = new TextField();
        Button sendButton = new Button("Send");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        layout.add(messageField, sendButton);
        layout.setWidth("70%");
        layout.expand(messageField);

        sendButton.addClickListener(click -> {
            if(messageField.getValue() != null && messageField.getValue() != "") {
                publisher.onNext(new ChatMessage(username, messageField.getValue()));
                messageField.clear();
                messageField.focus();
            }
        });
        sendButton.addClickShortcut(Key.ENTER);

        messageField.focus();

        return layout;
    }

}
