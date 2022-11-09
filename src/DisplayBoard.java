import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")

public class DisplayBoard extends JPanel{
    int componentWidth = 240;
    int marginWidth = 50;
    int ballX = 0;
    int ballY = 0;
    int flash = 0;

    public DisplayBoard() {
        setBackground(Color.black);
    }

    @Override
    public void paint(Graphics g) {
        int atBottom = 2 * marginWidth + componentWidth;
        int atRight = 3 * marginWidth + 3 * componentWidth;
        int width;
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.white);

        // paint net
        g.drawRect(marginWidth, atBottom, componentWidth, componentWidth);

        // paint hypernet
        width = componentWidth / 3;
        g.drawRect(marginWidth, marginWidth, componentWidth, componentWidth);
        g.drawOval(marginWidth, marginWidth, componentWidth, componentWidth);
        g.drawOval(marginWidth + width, marginWidth, width, width);
        g.drawOval(marginWidth + width, marginWidth + width, width, width);
        g.drawOval(marginWidth + width, marginWidth + 2 * width, width, width);
        g.drawOval(marginWidth, marginWidth + width / 2, width, width);
        g.drawOval(marginWidth, marginWidth + 3 * (width / 2), width, width);
        g.drawOval(marginWidth + width * 2, marginWidth + width / 2, width, width);
        g.drawOval(marginWidth + width * 2, marginWidth + 3 * (width / 2), width, width);

        // paint board
        width = (componentWidth * 2) / 3;
        g.drawRect(atBottom, marginWidth, width, width * 3);
        g.drawRect(atBottom + width * 2, marginWidth, width, width * 3);
        g.drawRect(atBottom, marginWidth, width * 3, width);
        g.drawRect(atBottom, marginWidth + width * 2, width * 3, width);

        // paint atlas
        if(flash < 250) {
            g.fillRect(atRight, marginWidth, componentWidth, componentWidth);
        } else {
            g.drawRect(atRight, marginWidth, componentWidth, componentWidth);
        }

        // paint compass
        width = componentWidth / 4;
        g.drawRect(atRight, atBottom, componentWidth, componentWidth);
        g.drawOval(atRight + width, atBottom, width, width);
        g.drawOval(atRight + 2 * width, atBottom, width, width);
        g.drawOval(atRight + width, atBottom + 3 * width, width, width);
        g.drawOval(atRight + 2 * width, atBottom + 3 * width, width, width);
        g.drawOval(atRight, atBottom + width, width, width);
        g.drawOval(atRight, atBottom + 2 * width, width, width);
        g.drawOval(atRight + 3 * width, atBottom + width, width, width);
        g.drawOval(atRight + 3 * width, atBottom + 2 * width, width, width);
        g.drawRect(atRight + width, atBottom + width, 2 * width, 2 * width);

        // paint ball
        g.fillOval(ballX,ballY,marginWidth,marginWidth);
    }

    private void updateGraphics() {
        if(flash < 500) {
            flash++;
        } else {
            flash = 0;
        }
        ballX++;
        ballY++;
    }

    /*
    private static void initializeWindow() {
        int componentWidth = 240;
        int gap = 50;
        JFrame window = new JFrame("Tic Tac Tesseract");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize( new Dimension(4 * componentWidth + 4 * gap, 2 * componentWidth + 3 * gap));
        DisplayBoard display = new DisplayBoard();
        window.add(display);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
     */

    public static void main(String[] args) throws InterruptedException{
        int componentWidth = 240;
        int gap = 50;
        JFrame window = new JFrame("Tic Tac Tesseract");
        DisplayBoard display = new DisplayBoard();
        window.add(display);
        window.setSize( new Dimension(4 * componentWidth + 4 * gap, 2 * componentWidth + 3 * gap));
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while(true) {
            display.updateGraphics();
            display.repaint();
        }
    }
}
