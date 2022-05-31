package gui;

import game.Game;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;

public class JPanel extends javax.swing.JPanel implements ActionListener, MouseListener {
    public static final Color GREEN_HIGHLIGHT = new Color(0,176,15,30);
    public static final Color WHITE_BACKGROUND = Color.decode("#cdcdcd");

    private int lastMove;
    private final Game game;
    private final Timer t;


    private final Font font = Font.decode(Font.SANS_SERIF).deriveFont(15f);
    public JPanel() {
        t = new Timer(1000/30,this);
//        t.start();

        game = new Game();
        lastMove = -1;

        game.playMove(1, game.aiMove());

        setBackground(WHITE_BACKGROUND);

        addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == t) {
            repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        Point clickLocation = e.getPoint();
        if (clickLocation.x > 300 || clickLocation.y > 400 || clickLocation.x < 0 || clickLocation.y < 100) return;
        int index = (clickLocation.y - 100) / 100 * 3 + clickLocation.x / 100;

//        System.out.println(index);
        if (game.playMove(game.currentPlayer, index)) {
            lastMove = index;

            Pair<Integer, Integer> result = game.checkGameCondition();

            if (result.getKey() != 0) {

                String message;
                switch (result.getKey()) {
                    case 1:
                        message = "Player 1 (X) Won";
                        break;
                    case 2:
                        message = "Player 2 (O) Won";
                        break;
                    default:
                        message = "The game was a draw";
                }

                int choice = JOptionPane.showOptionDialog(this, message + ", continue?",
                        "Game Dialog", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                        new Object[] {"Exit","Play Again"},0);

                if (choice == 0) System.exit(1);
                else {
                    lastMove = -1;
                    game.reset();
                }
            }
            else {
                game.playMove(game.currentPlayer, game.aiMove());

                result = game.checkGameCondition();

                if (result.getKey() != 0) {

                    String message;
                    switch (result.getKey()) {
                        case 1:
                            message = "Player 1 (X) Won";
                            break;
                        case 2:
                            message = "Player 2 (O) Won";
                            break;
                        default:
                            message = "The game was a draw";
                    }

                    int choice = JOptionPane.showOptionDialog(this, message + ", continue?",
                            "Game Dialog", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                            new Object[]{"Exit", "Play Again"}, 0);

                    if (choice == 0) System.exit(1);
                    else {
                        lastMove = -1;
                        game.reset();
                    }
                }
            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        t.restart();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        t.stop();
    }

    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);

        Graphics2D g = (Graphics2D) g1;

        g.setStroke(new BasicStroke(3));

        g.drawLine(99,100,99,400);
        g.drawLine(198,100,198,400);
        g.drawLine(0,199,300,199);
        g.drawLine(0,298,300,298);

        g.setColor(Color.WHITE);
        g.fillRect(0,0,300,100);

        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString("Player " + game.currentPlayer + " (" + (game.currentPlayer == 1 ? "X" : "O") + "), it is your turn to move", 20,70);

        int xToDraw, yToDraw, player;
        for (int i = 0;i < game.board.length; i++) {
            player = game.board[i];
            if (player == 0) continue;
            xToDraw = i % 3 * 100;
            yToDraw = i / 3 * 100 + 100;

            if (player == 1) {
                g.drawLine(xToDraw + 10,yToDraw + 10, xToDraw + 90, yToDraw + 90);
                g.drawLine(xToDraw + 10,yToDraw + 90, xToDraw + 90, yToDraw + 10);
            }
            else {
                g.drawOval(xToDraw + 10, yToDraw + 10, 80,80);
            }
        }



        if (lastMove != -1) {
            //Highlighting last move
            int xToHighlight = lastMove % 3 * 100;
            int yToHighlight = lastMove / 3 * 100 + 100;

            g.setColor(GREEN_HIGHLIGHT);
            g.fillRect(xToHighlight, yToHighlight, 100, 100);
        }
    }
}
