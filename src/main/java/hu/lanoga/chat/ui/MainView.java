package hu.lanoga.chat.ui;


import hu.lanoga.chat.entity.ChatMessage;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.page.Push;


import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
//import hu.lanoga.chat.service.ChatMessageService;
//import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

@StyleSheet("frontend://styles/styles.css")
@Route
@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
@Push
public class MainView extends VerticalLayout {

    private final UnicastProcessor<ChatMessage> publisher;
    private final Flux<ChatMessage> messages;

    //@Autowired
    //private ChatMessageService chatMessageService;

    public MainView(UnicastProcessor<ChatMessage> publisher,
                    Flux<ChatMessage> messages) {
        this.publisher = publisher;
        this.messages = messages;

        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        addClassName("main-view");

        HorizontalLayout center = new HorizontalLayout();

        LoginView loginView = new LoginView();
        ChatView chatView = new ChatView(messages);
        InputView inputView = new InputView(publisher, loginView.username);
        UsersView usersView = new UsersView(loginView.users);

        center.setWidth("100%");
        center.add(usersView.CreateUsers(), chatView.CreateChat(), inputView.CreateInput());
        center.setFlexGrow(1, usersView.CreateUsers());
        add(center);
        expand(center);

        H1 header = new H1("Vaadin Chat");
        header.getElement().getThemeList().add("dark");

        add(header);
        add(loginView.CreateLogin());
        add(center);

    }

}
