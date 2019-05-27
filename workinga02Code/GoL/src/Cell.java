import java.io.Serializable;

public class Cell implements Serializable {
    private int x;
    private int y;
    private boolean isAlive;
    private int resources;

    public Cell(int x, int y, boolean isAlive, int resources) {
        this.x = x;
        this.y = y;
        this.isAlive = isAlive;
        this.resources = resources;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean getIsAlive() {
        return this.isAlive;
    }
    
    public int getResources(){
        return this.resources;
    }

    public void setIsAlive(boolean b) {
        this.isAlive = b;
    }
    
    public void setResource(int value){
        this.resources = value;
    }


}
