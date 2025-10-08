package com.example.ui.login;

import com.example.auth.AppSession;

import java.awt.*;
import javax.swing.*;

import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

@TopComponent.Description(
    preferredID = "LoginTopComponent",
    persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@Messages({
    "CTL_Login=Login",
    "MSG_Wrong=Wrong username or password"
})
public final class LoginTopComponent extends TopComponent {
    private final JTextField user = new JTextField(16);
    private final JPasswordField pass = new JPasswordField(16);
    private final JButton submit = new JButton("Sign in");

    public LoginTopComponent() {
        setName("Login");
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);
        c.gridx = 0; c.gridy = 0;
        form.add(new JLabel("Username:"), c);
        c.gridx = 1;
        form.add(user, c);
        c.gridx = 0; c.gridy = 1;
        form.add(new JLabel("Password:"), c);
        c.gridx = 1;
        form.add(pass, c);
        c.gridx = 1; c.gridy = 2;
        form.add(submit, c);

        add(form, BorderLayout.CENTER);

        submit.addActionListener(e -> doLogin());
    }

    private void doLogin() {
        if (AppSession.login(user.getText(), pass.getPassword())) {
            // Open the explorer (menu) window and close this login
            TopComponent explorer = WindowManager.getDefault()
                    .findTopComponent("MenuExplorerTC");
            if (explorer != null) {
                explorer.open();
                explorer.requestActive();
            }
            close();
        } else {
            JOptionPane.showMessageDialog(this, "Wrong username or password",
                    "Login", JOptionPane.ERROR_MESSAGE);
        }
    }
}