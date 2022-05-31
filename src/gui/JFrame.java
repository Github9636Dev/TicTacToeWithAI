package gui;

import java.awt.*;

public class JFrame extends javax.swing.JFrame {
    public JFrame() {
        setTitle("Tictactoe AI");
        setBounds(5,5,300,400);

        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        JPanel frameContent = new JPanel();

        Container visibleArea = getContentPane();
        visibleArea.add(frameContent);
        frameContent.setPreferredSize(new Dimension(300,400));
        pack();
        frameContent.requestFocusInWindow();
        setVisible(true);
    }
}
