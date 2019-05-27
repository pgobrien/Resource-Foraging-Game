import java.util.ArrayList;

public class Controller {

    private View view;

    public Controller(ModelInterface model, View view) throws java.rmi.RemoteException {
        this.view = view;
        this.view.cells = model.getCells();
        this.view.players = model.getPlayers();
    }

    public void update(Cell[][] cells, ArrayList<Player> players) {
	    this.view.GoL.setCells(cells);
	    this.view.GoL.setPlayers(players);
        this.view.repaint();
    }

}
