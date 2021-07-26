
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.text.DefaultEditorKit;

class Fireball {

    private int x;
    private int y;

    public Fireball(int x, int y) {
        this.x = x;
        this.y = y;
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

public class Game extends JPanel implements KeyListener, ActionListener {

    Timer timer = new Timer((int)0.8, this);
    private int time = 0;
    private int fireball = 0;
    private BufferedImage image;
    private ArrayList<Fireball> fireballs = new ArrayList<Fireball>();
    private int fireball_Y = 1;
    private int ballX = 0;
    private int ball_X = 2;

    private int RocketX = 0;
    private int RocketY = 630;
    private int _CosmosX = 30;
    private int _CosmosY = 30;
    
    public boolean control(){
        for (Fireball fireball : fireballs) {
            if (new Rectangle(fireball.getX(),fireball.getY(),8,15).intersects(new Rectangle(ballX,0,15,15))) {
                return true;
            }
        }
        return false;
    }

    public Game() {
        try {
            image = ImageIO.read(new FileImageInputStream(new File("rocket1.png")));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackground(Color.BLACK);

        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        time += 5;
        g.setColor(Color.MAGENTA);
        g.fillOval(ballX, 0, 15, 15);
        g.drawImage(image, RocketX, RocketY, image.getWidth() / 13, image.getHeight() / 13, this);
        
        for (Fireball fireball : fireballs) {
            if (fireball.getY() < 0) {
                fireballs.remove(fireball);
            }
        }
        
        g.setColor(Color.GREEN);
        
        for (Fireball fireball : fireballs) {
                g.fillRoundRect(fireball.getX(), fireball.getY(), 9, 15,15,15);
        }
        
        if (control()) {
            timer.stop();
            String message = "Congratulations, you won!\n"+
                            "The fireball was spent : "+ fireball+
                            "\nThe time wasted : "+time/1000.0+" sec";
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        if (c == KeyEvent.VK_LEFT) {
            if (RocketX <= 0) {
                RocketX = 0;
            } else {
                RocketX -= _CosmosX;
            }
        } else if (c == KeyEvent.VK_RIGHT) {
            if (RocketX >= 937) {
                RocketX = 937;
            } else {
                RocketX += _CosmosX;
            }
        } else if (c == KeyEvent.VK_UP) {
            if (RocketY <= 0) {
                RocketY = 800;
            } else {
                RocketY -= _CosmosY;
            }
        } else if (c == KeyEvent.VK_DOWN) {
            if (RocketY >= 800) {
                RocketY = 800;
            } else {
                RocketY += _CosmosY;
            }
        } else if(c == KeyEvent.VK_CONTROL){
             fireballs.add(new Fireball(RocketX+19,RocketY-25));
             fireball++;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for( Fireball fireball : fireballs){
            fireball.setY(fireball.getY() - fireball_Y);
        }
        
        ballX += ball_X;
        if (ballX >= 970) {
            ball_X = -ball_X;
        }
        if (ballX <= 0) {
            ball_X = -ball_X;
        }

        repaint();
    }

}
