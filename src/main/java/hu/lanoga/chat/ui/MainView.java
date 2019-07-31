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
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

@Route //value = MainView.ROUTE
@StyleSheet("frontend://styles/styles.css")
@PWA(name = "Vaadin Chat with Spring", shortName = "Vaadin Chat")
@Push
public class MainView extends VerticalLayout {

    public static final String ROUTE = "Chat";

    private final UnicastProcessor<ChatMessage> publisher;
    private final Flux<ChatMessage> messages;

    public MainView(UnicastProcessor<ChatMessage> publisher,
                    Flux<ChatMessage> messages) {
        this.publisher = publisher;
        this.messages = messages;

        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        addClassName("main-view");

        VerticalLayout main = new VerticalLayout();
        HorizontalLayout center = new HorizontalLayout();

        UsersView usersView = new UsersView();

        center.setWidth("100%");
        center.add(usersView.CreateUsers(), CreateChat());
        center.setFlexGrow(1, usersView.CreateUsers());
        expand(center);
        H1 header = new H1("Vaadin Chat");
        header.getElement().getThemeList().add("dark");

        main.setWidth("100%");
        add(header);
        main.add(center, CreateInput());
        add(main);
        expand(main);
    }

    private Component CreateChat()
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

    private Component CreateInput()
    {
        HorizontalLayout layout = new HorizontalLayout();

        TextField messageField = new TextField();
        Button sendButton = new Button("Send");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        messageField.setWidth("70%");

        layout.add(messageField, sendButton);
        layout.expand(messageField);
        layout.setWidth("100%");

        sendButton.addClickListener(click -> {
            if(messageField.getValue() != null && messageField.getValue() != "") {
                publisher.onNext(new ChatMessage("admin", messageField.getValue()));
                messageField.clear();
                messageField.focus();
            }
        });
        sendButton.addClickShortcut(Key.ENTER);

        messageField.focus();

        return layout;
    }
}
