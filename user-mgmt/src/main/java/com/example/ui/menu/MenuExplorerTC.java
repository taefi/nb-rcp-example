package com.example.ui.menu;

import java.awt.BorderLayout;
import javax.swing.ActionMap;
import javax.swing.JScrollPane;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

@TopComponent.Description(
    preferredID = "MenuExplorerTC",
    persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "explorer", openAtStartup = false)
@Messages("CTL_MenuExplorerTC=Menu")
public final class MenuExplorerTC extends TopComponent implements ExplorerManager.Provider {

    private final ExplorerManager mgr = new ExplorerManager();

    public MenuExplorerTC() {
        setName("Menu");
        setLayout(new BorderLayout());

        // A JTree-like explorer view
        BeanTreeView tree = new BeanTreeView();
        tree.setRootVisible(false);
        add(new JScrollPane(tree), BorderLayout.CENTER);

        // Root with our dynamic children
        AbstractNode root = new AbstractNode(Children.create(new MenuChildFactory(), true));
        root.setDisplayName("root");
        mgr.setRootContext(root);

        // Hook global actions (Delete, Open on Enter, etc.)
        ActionMap map = getActionMap();
        associateLookup(ExplorerUtils.createLookup(mgr, map));
    }

    @Override
    public ExplorerManager getExplorerManager() { return mgr; }
}