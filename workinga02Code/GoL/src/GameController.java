import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

public class GameController {

  float aClusterPerc;
  float bClusterPerc;
  int aClusterCenterX = 64;
  int aClusterCenterY = 64;
  int bClusterCenterX = 192;
  int bClusterCenterY = 192;
  SimpleCellFactory factory;

  Cell[][] grid;
  int totalStartingResources;

  public GameController(int aClusterPerc, int totalStartingResources) {
    this.aClusterPerc = aClusterPerc;
    this.bClusterPerc = 100 - aClusterPerc;
    this.totalStartingResources = totalStartingResources;
    factory = new SimpleCellFactory();
  }

  /*
  public void getPercentagesFromUser() {
    Scanner s = new Scanner(System.in);
    System.out.println("Enter percentage for cluster A (format e.g. \"55.2\"):");
    this.aClusterPerc = Float.parseFloat(s.next());
    this.bClusterPerc = 100 - this.aClusterPerc;
    s.close();
  }
  */

  public static int rando(int lowerBound, int upperBound) {
    Random r = new Random();
    return r.nextInt(upperBound-lowerBound) + lowerBound;
  }

  public static boolean inRadius(int centerX, int centerY, int candidateX, int candidateY, int maxDist) {
    return Math.sqrt(Math.pow((centerY - candidateY), 2) + Math.pow((centerX - candidateX), 2)) < maxDist;
  }

  /*
  public void printGrid() {
    for (int i = 0; i < 256; i++) {
      System.out.println(Arrays.toString(this.grid[i]));
    }
  }
  */

  public void generateResourcesForCluster(int centerX, int centerY, int totalResources) {
    int lowerBoundX = centerX - 12;
    int upperBoundX = centerX + 12;
    int lowerBoundY = centerY - 12;
    int upperBoundY = centerY + 12;
    int numAdded = 0;
    while (numAdded < totalResources) {
      int candidateX = rando(lowerBoundX, upperBoundX);
      int candidateY = rando(lowerBoundY, upperBoundY);
      // If the candidate Cell is in the radius of the cluster center
      // and it is not at max resource capacity (7)...
      if (inRadius(centerX, centerY, candidateX, candidateY, 12) && this.grid[candidateX][candidateY].getResources() < 7) {
        // Add 1 to the resource count
        this.grid[candidateX][candidateY].setResource(this.grid[candidateX][candidateY].getResources() + 1);
        // Set the cell to "alive"
        this.grid[candidateX][candidateY].setIsAlive(true);
        numAdded++;
      }
    }
  }

  public Cell[][] createCells() {
    // Initalize grid to all dead Cells
    this.grid = new Cell[256][256];
    for (int i = 0; i < 256; i++) {
      for (int j = 0; j < 256; j++) {
        this.grid[i][j] = factory.createCell(i, j, false, 0);
      }
    }

    // Add resources for clusters A and B
    int totalResources = this.totalStartingResources;
    this.generateResourcesForCluster(this.aClusterCenterX,
                                     this.aClusterCenterY,
                                    (int) (totalResources * (this.aClusterPerc / 100)));
    this.generateResourcesForCluster(this.bClusterCenterX,
                                     this.bClusterCenterY,
                                     (int) (totalResources * (this.bClusterPerc / 100)));
    return this.grid;
  }

  public void startGame() {

  }

  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    int myAClusterPerc = 0;

    try {

      System.out.println("Enter the percentage of total resources allocated to Cluster A. (Cluster B will be 100 - your value):");
      myAClusterPerc = s.nextInt();

      if (myAClusterPerc < 1 || myAClusterPerc > 99)
        throw new IllegalArgumentException();

      GameController cg = new GameController(myAClusterPerc, 100);
      cg.createCells();

    } catch(Exception e) {
      System.out.println("Your percentage must be an integer between [1 and 99]");
    }
  }

}
