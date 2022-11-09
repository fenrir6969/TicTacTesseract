import javax.swing.*;
import java.awt.*;


public class Visuals extends JPanel{
    int ballX = 0;
    int ballY = 0;

    public Visuals() {
        setBackground(Color.black);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.white);
        g.drawOval(ballX,ballY,50,50);
    }

    private void updateGraphics() {
        ballX++;
        ballY++;
    }

    public static void main(String[] args) throws InterruptedException{
        JFrame window = new JFrame("Tic Tac Tesseract");
        Visuals visuals = new Visuals();
        window.add(visuals);
        window.setSize( new Dimension(500, 500));
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while(true) {
            visuals.updateGraphics();
            visuals.repaint();
            Thread.sleep(10);
        }
    }
}