import javax.swing.*;
import java.awt.*;
import java.rmi.Naming;
import java.rmi.Remote;
import java.util.concurrent.atomic.AtomicInteger;

public class Runner {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Remote robj = Naming.lookup("//localhost/DistributedGoL");
                ModelInterface model = (ModelInterface) robj;
                int playerId = model.registerNewPlayer();

                View view = new View(model, playerId);

                Timer timer = new Timer(50, e -> {
                    try {view.controller.update(model.getCells(), model.getPlayers());}
                    catch (java.rmi.RemoteException re) { System.out.println(re.getMessage()); }
                });

                timer.start();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
