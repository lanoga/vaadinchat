package hu.lanoga.chat.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;


public class UsersView
{

    //@Autowired
    //private ChatUserService chatUserService;

    public UsersView()
    {
    }

    @Autowired
    public Component CreateUsers()
    {
        VerticalLayout layout = new VerticalLayout();

        H3 listLabel = new H3();
        ListBox<String> listBox = new ListBox<>();

        listLabel.setText("Users");
        listBox.setItems("admin", "user");

        layout.setWidth("200px");
        layout.add(listLabel, listBox);

        return layout;
    }
}
