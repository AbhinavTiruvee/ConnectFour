import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;

public class ConnectFourFrame extends JFrame implements KeyListener {
    // Display message
    private String text = "";
    // the letter you are playing as
    private char player;
    // stores all the game data
    private GameData gameData = null;
    // output stream to the server
    ObjectOutputStream os;

    public ConnectFourFrame(GameData gameData, ObjectOutputStream os, char player)
    {
        super("Connect 4");
        this.gameData = gameData;
        this.os = os;
        this.player = player;

        addKeyListener(this);

        // makes closing the frame close the program
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set initial frame message
        if(player == 'R')
            text = "Waiting for Y to Connect";
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    System.out.println("Closing");
                    os.writeObject(new CommandFromClient(CommandFromClient.CLOSED, null));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        setSize(900,900);
        setResizable(false);
        setAlwaysOnTop(true);
        setVisible(true);
    }

    public void paint(Graphics g)
    {
        // draws the background
        g.setColor(new Color(0, 126, 182));
        g.fillRect(0,0,getWidth(),getHeight());

        // draws the display text to the screen
        g.setColor(Color.BLACK);
        g.setFont(new Font("Times New Roman",Font.BOLD,30));
        g.drawString(text,20,55);

        for(int i = 0; i < gameData.getGrid().length; i++)
        {
            for(int j = 0; j <  gameData.getGrid()[0].length; j++)
            {
                char spot = gameData.getGrid()[i][j];
                if(spot == 'R')
                {
                    g.setColor(Color.RED);
                }
                else if(spot == 'Y')
                {
                    g.setColor(Color.YELLOW);
                }
                else if(spot == ' ')
                {
                    g.setColor(Color.WHITE);
                }
                else
                {
                    g.setColor(Color.PINK);
                }
                g.drawOval(j*100+100,i*100+100, 80, 80);
                g.fillOval(j*100+100,i*100+100, 80, 80);
            }
        }
    }

    public void setText(String text) {
        this.text = text;
        repaint();
    }


    public void setTurn(char turn) {
        if(turn==player)
            text = "Your turn";
        else
        {
            text = turn+"'s turn.";
        }
        repaint();
    }

    public void makeMove(int c, int r, char letter)
    {
        gameData.getGrid()[r][c] = letter;
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent event) {
        char key = event.getKeyChar();
        int r;
        int c;
        System.out.println("pressed: "+event.getKeyChar());

        //reset
        if(key == 'r')
        {
            System.out.println("r pressed");
            try {
                os.writeObject(new CommandFromClient(CommandFromClient.RESTART, null));
                gameData.reset();
            } catch (Exception e) {
                e.printStackTrace();
            }
            setText("R's turn");
            repaint();
        }
        else
        {
            switch(key)
            {
                case '1':
                    c=0;
                    break;
                case '2':
                    c=1;
                    break;
                case '3':
                    c=2;
                    break;
                case '4':
                    c=3;
                    break;
                case '5':
                    c=4;
                    break;
                case '6':
                    c=5;
                    break;
                case '7':
                    c=6;
                    break;
                default:
                    c=-1;
            }
            // if a valid enter was entered, send the move to the server
            if(c!=-1) {
                r = -1;
                for(int i = gameData.getGrid().length-1; i >=0; i--)
                {
                    System.out.println(i+""+c);
                    if(gameData.getGrid()[i][c] == ' ')
                    {
                        r =i;
                        break;
                    }
                }
                try {
                    System.out.println("move at: "+r+""+c);
                    os.writeObject(new CommandFromClient(CommandFromClient.MOVE, "" + c + r + player));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
