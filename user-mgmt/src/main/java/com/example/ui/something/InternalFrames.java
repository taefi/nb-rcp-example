package com.example.ui.something;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyVetoException;

import org.openide.windows.TopComponent;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

@TopComponent.Description(
        preferredID = "InternalFramesTC",
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
public class InternalFrames extends TopComponent {
    private final JDesktopPane desktop = new JDesktopPane();
    private int frameCounter = 0;

    public InternalFrames() {
        setLayout(new BorderLayout());
        setName("Internal Frames");
        JTree menuTree = new JTree();
        menuTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) menuTree.getLastSelectedPathComponent();
            if (node == null || !node.isLeaf()) return;
            String title = node.getUserObject().toString();
            addDemoFrame(title);
        });
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menuTree, desktop);
        splitPane.setDividerLocation(200);
        add(splitPane, BorderLayout.CENTER);

        // Menu
        var bar = new JMenuBar();
        var win = new JMenu("Window");
        var newItem = new JMenuItem("New Frame");
        var tileItem = new JMenuItem("Tile");
        var cascadeItem = new JMenuItem("Cascade");
        var closeAllItem = new JMenuItem("Close All");
        win.add(newItem);
        win.addSeparator();
        win.add(tileItem);
        win.add(cascadeItem);
        win.addSeparator();
        win.add(closeAllItem);
        bar.add(win);
        //setJMenuBar(bar);

        newItem.addActionListener(e -> addDemoFrame(null));
        tileItem.addActionListener(e -> tileFrames());
        cascadeItem.addActionListener(e -> cascadeFrames());
        closeAllItem.addActionListener(e -> closeAll());

        // Create some starter frames
        for (int i = 0; i < 6; i++) addDemoFrame(null);

        // Re-tile on resize
        desktop.addComponentListener(new ComponentAdapter() {
            @Override public void componentResized(ComponentEvent e) { tileFrames(); }
        });
    }

    private void addDemoFrame(String title) {
        title = title == null ? "Frame " + (++frameCounter) : title;
        JInternalFrame f = new JInternalFrame(title, true, true, true, true);
        f.setVisible(true);

        // Demo content: a simple panel with a label and a button
        JPanel content = new JPanel(new BorderLayout(8, 8));
        content.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        content.add(new JLabel("Hello from " + f.getTitle(), SwingConstants.CENTER), BorderLayout.CENTER);
        JButton b = new JButton("Show Info");
        b.addActionListener(evt -> JOptionPane.showMessageDialog(f, "You're in " + f.getTitle()));
        content.add(b, BorderLayout.SOUTH);
        f.setContentPane(content);

        desktop.add(f);
        f.pack(); // pack to compute minimum size, but we'll override bounds in tiling
        tileFrames();
        try { f.setSelected(true); } catch (PropertyVetoException ignored) {}
    }

    private void closeAll() {
        for (JInternalFrame f : desktop.getAllFrames()) {
            try { f.setClosed(true); } catch (PropertyVetoException ignored) {}
        }
        frameCounter = 0;
    }

    /** Tile all frames to fill the desktop area in a near-square grid. */
    private void tileFrames() {
        JInternalFrame[] frames = desktop.getAllFrames();
        if (frames.length == 0) return;

        Rectangle d = desktop.getVisibleRect();
        int count = frames.length;

        // Choose rows/cols to be as square as possible
        int rows = (int) Math.round(Math.sqrt(count));
        int cols = (int) Math.ceil((double) count / rows);

        // If the desktop is wider than tall, prefer more columns; else more rows
        if (d.width > d.height && cols < rows) {
            int tmp = rows;
            rows = cols;
            cols = tmp;
        }

        int w = d.width / cols;
        int h = d.height / rows;

        int i = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols && i < count; c++, i++) {
                int x = d.x + c * w;
                int y = d.y + r * h;
                frames[i].setBounds(x, y, (c == cols - 1 ? d.x + d.width - x : w),
                        (r == rows - 1 ? d.y + d.height - y : h));
            }
        }
    }

    /** Cascade the frames with an offset, sizing them reasonably. */
    private void cascadeFrames() {
        JInternalFrame[] frames = desktop.getAllFrames();
        if (frames.length == 0) return;

        Rectangle d = desktop.getVisibleRect();
        int offset = 32;
        int w = Math.max(d.width * 3 / 4, 300);
        int h = Math.max(d.height * 3 / 4, 200);

        int x = d.x, y = d.y;
        for (JInternalFrame f : frames) {
            f.setBounds(x, y, w, h);
            x += offset; y += offset;
            if (x + w > d.x + d.width || y + h > d.y + d.height) {
                x = d.x; y = d.y; // wrap
            }
        }
    }
}
