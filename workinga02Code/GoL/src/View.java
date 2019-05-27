import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;

public class View extends JFrame {

    Cell[][] cells;
    JPanel view;
    CardLayout cl;
    Controller controller;
    Grid GoL;
    ArrayList<Player> players;


    public View(ModelInterface model, int playerId) throws java.rmi.RemoteException {
        // Initialize instance variables
        cl = new CardLayout();
        view = new JPanel(cl);
        controller = new Controller(model, this);

        // Set up the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1024, 1024);
        this.setVisible(true);
        this.setResizable(false);
        this.add(view);

        // Screen for displaying the game
        JPanel gameScreen = new JPanel(new GridLayout(1, 1));
        GoL = new Grid(this.cells, players, playerId, model);
        gameScreen.add(GoL);
        view.add(gameScreen, "GameScreen");

        this.cl.show(this.view, "GameScreen");
        JOptionPane.showMessageDialog(GoL, "Game Rules: \n" +
                                                    "Move: UP, LEFT, RIGHT, DOWN Arrows \n" +
                                                    "Eat Food: SPACE BAR \n" +
                                                    "Check Player scores: S key \n" +
                                                    "You are the circle others are the crosshairs!");
    }

}
