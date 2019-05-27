import java.util.Random;
import java.io.Serializable;

public class Player implements Serializable {

    private int id;
    private int resourcesCollected;
    private int x,y;

    private boolean canSeePlayer;
    private boolean canSeeResources;

    public Player(int id) {
        Random rand = new Random();
        this.id = id;
        this.setX(rand.nextInt(30) + 10);
        this.setY(rand.nextInt(30) + 10);
    }


    public void move(int dx, int dy) {
        this.setX(this.getX() + dx);
        this.setY(this.getY() + dy);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResourcesCollected() {
        return resourcesCollected;
    }

    public void setResourcesCollected(int resourcesCollected) {
        this.resourcesCollected = resourcesCollected;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
