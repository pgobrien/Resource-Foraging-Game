public class SimpleCellFactory {
    public Cell createCell(int x, int y, boolean isAlive, int resources) {
        return new Cell(x,y,isAlive,resources);
    }
}
