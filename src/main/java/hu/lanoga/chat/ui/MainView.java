package hu.lanoga.chat.ui;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.textfield.TextField;
import hu.lanoga.chat.entity.ChatMessage;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.page.Push;


import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import hu.lanoga.chat.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

@StyleSheet("frontend://styles/styles.css")
@Route
@PWA(name = "Vaadin Chat with Spring", shortName = "Vaadin Chat")
@Push
public class MainView extends VerticalLayout {

    public final UnicastProcessor<ChatMessage> publisher;
    public final Flux<ChatMessage> messages;

    @Autowired
    private ChatMessageService chatMessageService;

    public MainView(UnicastProcessor<ChatMessage> publisher,
                    Flux<ChatMessage> messages) {
        this.publisher = publisher;
        this.messages = messages;
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        addClassName("main-view");

        VerticalLayout main = new VerticalLayout();
        HorizontalLayout center = new HorizontalLayout();

        LoginView loginView = new LoginView(main);
        UsersView usersView = new UsersView(loginView.getUsers());

        center.setWidth("100%");
        center.add(usersView.CreateUsers(), CreateChat());
        center.setFlexGrow(1, usersView.CreateUsers());
        expand(center);
        main.setVisible(false);

        H1 header = new H1("Vaadin Chat");
        header.getElement().getThemeList().add("dark");

        main.setWidth("100%");
        add(header, loginView.CreateLogin());
        main.add(center, CreateInput(loginView.getUsername()));
        add(main);
        expand(main);
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

    public Component CreateInput(String username)
    {
        HorizontalLayout layout = new HorizontalLayout();

        TextField messageField = new TextField();
        Button sendButton = new Button("Send");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        layout.add(messageField, sendButton);
        layout.expand(messageField);
        layout.setWidth("70%");

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
