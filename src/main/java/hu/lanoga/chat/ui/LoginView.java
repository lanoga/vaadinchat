package hu.lanoga.chat.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.ArrayList;
import java.util.List;


public class LoginView
{

    public String username;
    public static List<String> users = new ArrayList<>();
    public VerticalLayout main;


    public LoginView(VerticalLayout main)
    {
        this.main = main;
    }

    public Component CreateLogin()
    {
        VerticalLayout layout = new VerticalLayout();

        TextField usernameField = new TextField("User Name");
        PasswordField passwordField = new PasswordField("Password");
        Button loginButton = new Button("Log in");
        Button fPasswordButton = new Button("Forgot my password");

        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        layout.add(usernameField, passwordField, loginButton, fPasswordButton);

        loginButton.addClickListener(click -> {
            //authentication
            users.add(usernameField.getValue());
            username = usernameField.getValue();
            layout.setVisible(false);
            main.setVisible(true);
        });
        loginButton.addClickShortcut(Key.ENTER);

        fPasswordButton.addClickListener(e -> {
            layout.add(ForgottenPassword());
        });

        return layout;
    }

    public Component ForgottenPassword()
    {
        HorizontalLayout layout = new HorizontalLayout();

        EmailField emailField = new EmailField();
        emailField.setPlaceholder("Your email address");

        Button submitButton = new Button("Request new password");
        layout.add(emailField, submitButton);

        submitButton.addClickListener(click -> {
            //send the email
        });

        return layout;
    }

    public String getUsername() {
        return username;
    }

    public static List<String> getUsers() {
        return users;
    }
}
