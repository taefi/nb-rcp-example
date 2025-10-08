package com.example.ui.person;

import java.awt.BorderLayout;
import org.openide.windows.TopComponent;

@TopComponent.Description(
        preferredID = "UserEditorTC",
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
public final class UsersCrud extends TopComponent {
    public UsersCrud() {
        setLayout(new BorderLayout());
        setName("Users List View");
        add(new PersonView(), BorderLayout.CENTER);
    }
}
