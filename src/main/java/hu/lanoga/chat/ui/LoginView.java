package hu.lanoga.chat.ui;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import java.util.Collections;

@Tag("login-view")
@Route(value = LoginView.ROUTE)
@PageTitle("login")
public class LoginView extends VerticalLayout /*implements AfterNavigationObserver*/implements BeforeEnterObserver {
    public static final String ROUTE = "login";

    private LoginForm login = new LoginForm();

    public LoginView() {
        login.setAction("login");
        getElement().appendChild(login.getElement());

        login.addLoginListener(e -> {
            UI.getCurrent().navigate("");
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if(!event.getLocation().getQueryParameters().getParameters().getOrDefault("error", Collections.emptyList()).isEmpty()) {
            login.setError(true);
        }
    }
}
