package com.example.ui.menu;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.lookup.Lookups;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

final class MenuChildFactory extends ChildFactory<MenuItem> {

    @Override
    protected boolean createKeys(List<MenuItem> toPopulate) {
        // Minimal static menu; replace with your own model later
        toPopulate.addAll(Arrays.asList(
            new MenuItem("Users",    "UserEditorTC"),
            new MenuItem("Internal Frames", "InternalFramesTC")
        ));
        return true;
    }

    @Override
    protected Node createNodeForKey(MenuItem key) {
        return new EditorNode(key);
    }

    static final class EditorNode extends AbstractNode {
        private final MenuItem item;

        EditorNode(MenuItem item) {
            super(Children.LEAF, Lookups.singleton(item));
            this.item = item;
            setDisplayName(item.display());
        }

        @Override public Action getPreferredAction() {
            // Double-click / Enter triggers this in the BeanTreeView
            return new AbstractAction() {
                @Override public void actionPerformed(ActionEvent e) {
                    TopComponent tc = WindowManager.getDefault().findTopComponent(item.preferredId());
                    if (tc != null) { tc.open(); tc.requestActive(); }
                }
            };
        }
    }
}