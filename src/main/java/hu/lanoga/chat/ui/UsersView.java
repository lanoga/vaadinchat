package hu.lanoga.chat.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;
import java.util.List;

public class UsersView
{

    public List<String> users = new ArrayList<>();

    public UsersView(List<String> users)
    {
        this.users = users;
    }

    public Component CreateUsers()
    {
        VerticalLayout layout = new VerticalLayout();

        H3 listLabel = new H3();
        ListBox<String> listBox = new ListBox<>();

        listLabel.setText("Users");
        listBox.setItems(users);

        layout.setWidth("200px");
        layout.add(listLabel, listBox);

        return layout;
    }

}
