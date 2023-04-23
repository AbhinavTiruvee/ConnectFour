import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeUnit;

public class ClientsListener implements Runnable
{
    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;
    private ConnectFourFrame frame = null;

    public ClientsListener(ObjectInputStream is,
                           ObjectOutputStream os,
                           ConnectFourFrame frame) {
        this.is = is;
        this.os = os;
        this.frame = frame;

    }

    @Override
    public void run() {
        try
        {
            while(true)
            {
                CommandFromServer cfs = (CommandFromServer)is.readObject();

                // processes the received command
                if(cfs.getCommand() == CommandFromServer.R_TURN)
                    frame.setTurn('R');
                else if(cfs.getCommand() == CommandFromServer.Y_TURN)
                    frame.setTurn('Y');
                else if(cfs.getCommand() == cfs.MOVE)
                {
                    String data = cfs.getData();
                    // pulls data for the move from the data field
                    int c = data.charAt(0) - '0';
                    int r = data.charAt(1) - '0';

                    // changes the board and redraw the screen
                    frame.makeMove(c,r,data.charAt(2));
                }
                // handles the various end game states
                else if(cfs.getCommand() == CommandFromServer.TIE)
                {
                    frame.setText("Tie game.");
                }
                else if(cfs.getCommand() == CommandFromServer.R_WINS)
                {
                    frame.setText("R wins!, r to restart");
                }
                else if(cfs.getCommand() == CommandFromServer.Y_WINS)
                {
                    frame.setText("Y wins!, r to restart");
                }
                else if(cfs.getCommand() == CommandFromServer.RESTART)
                {
                    frame.setText("Restart");
                }
                else if(cfs.getCommand() == CommandFromServer.CLOSED)
                {
                    System.out.print("closed");
                    frame.setText("Closing in 5");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    frame.setText("Closing in 4");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    frame.setText("Closing in 3");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    frame.setText("Closing in 2");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    frame.setText("Closing in 1");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    frame.dispose();
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


}