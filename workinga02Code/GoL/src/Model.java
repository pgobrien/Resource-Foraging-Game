import javax.swing.Timer;
import java.io.File;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Model extends UnicastRemoteObject implements Runnable, ModelInterface {

    private Cell[][] cells;
    private int ticks;
    private Timer timer;
    private ArrayList<Player> players;
    private static Model uniqueInstance;


    public Model() throws java.rmi.RemoteException {
        players = new ArrayList<>();
        this.ticks = 0;
        timer = new Timer(100, e -> this.tick());
        this.run();
    }

    private static synchronized Model getUniqueInstance() throws java.rmi.RemoteException {
        if (uniqueInstance == null) {
            uniqueInstance = new Model();
        }
        return uniqueInstance;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    private int liveNeighborCount(Cell[][] cells, int row, int col) {
        if (cells.length != 256 || cells[0].length != 256)
          throw new IllegalArgumentException("'cells' dimensions must be 256 x 256");

        int totalActiveNeighbors = 0;
        // Add respective neighbor to total if it exists and is alive (i.e. the cell
        // value is greater than 0)
        if (col > 0)
          totalActiveNeighbors += cells[row][col-1].getResources() > 0 ? 1 : 0;  // Left of cell
        if (col < cells[0].length-1)
          totalActiveNeighbors += cells[row][col+1].getResources() > 0 ? 1 : 0;  // Right of cell
        if (row > 0)
          totalActiveNeighbors += cells[row-1][col].getResources() > 0 ? 1 : 0;  // Above cell
        if (row < cells.length-1)
          totalActiveNeighbors += cells[row+1][col].getResources() > 0 ? 1 : 0;  // Below cell

        return totalActiveNeighbors;
    }

    /*
      This is the resource growth rule in CPR
      nc = activeNeighbors
      p = growthParam
      N = 4-connected neighborhood because getLiveNeighborhood checks all 4
      pc(t) = growth rate
      t = resources in previous tick

      pc(t) = p*((nc*(t-1)/N))
    */
    public float growthThreshold(float growthParam, int activeNeighbors){
        float rate = growthParam * activeNeighbors / 4;
        return rate;
    }

    /* Determines if the cell should make a new resource or not */
    public void resourceGrowth(Cell cell, float threshold){
        int cellResources = cell.getResources();
        //a cell state of 7 means no more resources can be grown
        if(cellResources == 7) {
            return;
        } else {
            Random rand = new Random();
            float cellGrowth = rand.nextFloat();
            if(cellGrowth < threshold){
                cell.setResource(cell.getResources() + 1);
            }
        }
    }

    public void tick() {
        if (ticks == 4200) {
            System.out.print("Game Over!");
            timer.stop();
        }
        // Advance the game by one tick
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells.length; j++) {
                int activeNeighbors = this.liveNeighborCount(this.cells, i, j);
                Cell currentCell = this.cells[i][j];
                float growthThreshold = this.growthThreshold(0.008f, activeNeighbors);
                this.resourceGrowth(currentCell, growthThreshold);
            }
        }
        ticks++;
    }

    public void run() {
        timer.start();
    }

    public static void main(String[] args) {
        System.setSecurityManager(new SecurityManager());

        try {
            // Create the cells
            int totalStartingResources = 100;
            Scanner s = new Scanner(System.in);

            System.out.println("Enter the percentage of total resources allocated to Cluster A. (Cluster B will be 100 - your value):");
            int myAClusterPerc = s.nextInt();
            if (myAClusterPerc < 1 || myAClusterPerc > 99)
              throw new IllegalArgumentException("Your percentage must be an integer between [1 and 99]");
            GameController cg = new GameController(myAClusterPerc, totalStartingResources);
            Cell[][] cells = cg.createCells();

            Model model = Model.getUniqueInstance();
            model.setCells(cells);
            Naming.rebind("/DistributedGoL", model);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Player> getPlayers() throws RemoteException {
        return players;
    }

    @Override
    public int registerNewPlayer() throws RemoteException {
        int newPlayerId = players.size();

        if (players.size() > 4) {
            System.out.println("Player limit reached");
        } else {
            players.add(new Player(newPlayerId));
        }
        return newPlayerId;
    }

    @Override
    public void movePlayer(int id, int dx, int dy) throws RemoteException {
        for (Player aPlayer : players) {
            if (id == aPlayer.getId()) {
                aPlayer.setX(aPlayer.getX() + dx);
                aPlayer.setY(aPlayer.getY() + dy);
            }
        }
    }

    @Override
    public void playerCollectsResource(int id, int x, int y) throws RemoteException {
        for (Player aPlayer : players) {
            if (id == aPlayer.getId()) {
                int xP = aPlayer.getX() / 4;
                int yP = aPlayer.getY() / 4;
                if (this.cells[xP][yP].getResources() > 0) {
                    this.cells[xP][yP].setResource(this.cells[xP][yP].getResources() - 1);
                    aPlayer.setResourcesCollected(aPlayer.getResourcesCollected() + 1);
                    System.out.println("Resource Eaten");
                } else {
                    System.out.println("No Resource in this Cell, can't eat!");

                }
            }
        }
    }

    @Override
    public int getPlayerResourceCount(int id) throws RemoteException {
        return 0;
    }
}
