import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Grid extends JPanel implements KeyListener {

    private Cell[][] cells;
    private ArrayList<Player> players;
    private int myPlayerId;
    private ModelInterface model;

    public Grid(Cell[][] cells, ArrayList<Player> players, int myPlayerId, ModelInterface model) {

        // Inherit from Super Class
        super();
        // Add KeyListener Functionality
        this.setFocusable(true);
        super.addKeyListener(this);

        // Assign instance Variables
        this.cells = cells;
        this.players = players;
        this.myPlayerId = myPlayerId;
        this.model = model;
    }

    @Override
    public void paintComponent(Graphics g) {

        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells.length; j++) {
                Cell currentCell = this.cells[i][j];
                int resources = currentCell.getResources();
                switch(resources) {
                case 0:
                    g.setColor(Color.WHITE);
                    break;
                case 1:
                    g.setColor(new Color(60,0,0));
                    break;
                case 2:
                    g.setColor(new Color(80,0,0));
                    break;
                case 3:
                    g.setColor(new Color(108,0,0));
                    break;
                case 4:
                    g.setColor(new Color(144,0,0));
                    break;
                case 5:
                    g.setColor(new Color(180,0,0));
                    break;
                case 6:
                    g.setColor(new Color(216,0,0));
                    break;
                case 7:
                    g.setColor(new Color(252,0,0));
                    break;
                default:
                    g.setColor(Color.WHITE);
                    break;
                    }
                g.fillRect(i * 4, j * 4, 4, 4);
            }
        }

        // loop through player list to display players
        for (Player aPlayer : players) {

            if (aPlayer.getId() == myPlayerId) {
                // Draw current player using location against grid as a dot
                g.setColor(Color.BLACK);
                g.drawOval(aPlayer.getX(), aPlayer.getY(), 4, 4);

            } else {
                // draw other as a cross hair, two lines
                g.setColor(Color.BLACK);
                g.drawLine(aPlayer.getX() - 2, aPlayer.getY(), aPlayer.getX() + 2, aPlayer.getY());
                g.drawLine(aPlayer.getX(), aPlayer.getY() - 2, aPlayer.getX(), aPlayer.getY() + 2);

            }
        }

    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
        this.repaint();
    }


    public void setCells(Cell[][] cells) {
        this.cells = cells;
        this.repaint();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int dx;
        int dy;

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            dx = 0;
            dy = -1;
            try {
                this.model.movePlayer(myPlayerId, dx, dy);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
            System.out.println("----------------UP-----------");
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            dx = 1;
            dy = 0;
            try {
                this.model.movePlayer(myPlayerId, dx, dy);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
            System.out.println("----------------RIGHT-----------");
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            dx = -1;
            dy = 0;
            try {
                this.model.movePlayer(myPlayerId, dx, dy);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
            System.out.println("----------------LEFT-----------");
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            dx = 0;
            dy = 1;
            try {
                this.model.movePlayer(myPlayerId, dx, dy);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
            System.out.println("----------------DOWN-----------");
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            try {
                this.model.playerCollectsResource(myPlayerId, 0, 0);
                this.setCells(this.model.getCells());
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }

        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            StringBuilder scores = new StringBuilder();
            for (Player aPlayer : players) {
                scores.append("PLAYER SCORES: \n");
                scores.append("Player ");
                scores.append(aPlayer.getId());
                scores.append(": ");
                scores.append(aPlayer.getResourcesCollected());
                scores.append("\n");
            }
            JOptionPane.showMessageDialog(this, scores);
        }
        // sends player movement to the model.
    }

    // Unused
    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
