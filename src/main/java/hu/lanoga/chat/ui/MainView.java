package hu.lanoga.chat.ui;


import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import hu.lanoga.chat.entity.ChatMessage;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;


import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import hu.lanoga.chat.spring.MessageList;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@StyleSheet("frontend://styles/styles.css")
@Route
@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
@Push
public class MainView extends VerticalLayout {

    private final UnicastProcessor<ChatMessage> publisher;
    private final Flux<ChatMessage> messages;

    public MainView(UnicastProcessor<ChatMessage> publisher,
                    Flux<ChatMessage> messages) {
        this.publisher = publisher;
        this.messages = messages;

        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        addClassName("main-view");

        H1 header = new H1("Vaadin Chat");
        header.getElement().getThemeList().add("dark");

        add(header);

        doLogin();
    }

    private String username;
    private static List<String> users = new ArrayList<String>();

    private void doLogin()
    {
        VerticalLayout layout = new VerticalLayout();
        TextField usernameField = new TextField("User Name");
        PasswordField passwordField = new PasswordField("Password");
        Button loginButton = new Button("Log in");
        Button fPasswordButton = new Button("Forgot my password");
        layout.add(usernameField, passwordField, loginButton, fPasswordButton);

        add(layout);

        loginButton.addClickListener(click -> {

            //authentication
            users.add(usernameField.getValue());
            username = usernameField.getValue();
            remove(layout);
            showChat();
        });

        fPasswordButton.addClickListener(e -> {
            requestPassword();
        });

    }

    private void requestPassword() {
        HorizontalLayout layout = new HorizontalLayout();
        EmailField emailField = new EmailField();
        emailField.setPlaceholder("Your email address");
        Button submitButton = new Button("Request new password");
        layout.add(emailField, submitButton);

        add(layout);

        submitButton.addClickListener(click -> {
            //send the email
        });
    }

    private void showChat(){
        MessageList messageList = new MessageList();
        add(messageList, createInputLayout(), createAsideLayout());
        expand(messageList);


        messages.subscribe(message -> {
            getUI().ifPresent(ui ->
                    ui.access(() ->
                            messageList.add(
                                    new Paragraph(message.getFrom() + ": " +
                                            message.getMessage())
                            )
                    ));
        });
    }

    private Component createAsideLayout() {
        VerticalLayout layout = new VerticalLayout();

        ListBox<String> listBox = new ListBox<>();

        listBox.setItems(users);

        layout.add(listBox);
        return layout;
    }

    private Component createInputLayout() {
        HorizontalLayout layout = new HorizontalLayout();

        TextField messageField = new TextField();
        Button sendButton = new Button("Send");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        layout.add(messageField, sendButton);
        layout.setWidth("70%");
        layout.expand(messageField);

        sendButton.addClickListener(click -> {
            publisher.onNext(new ChatMessage(username, messageField.getValue()));
            messageField.clear();
            messageField.focus();
        });
        messageField.focus();

        return layout;
    }

}
