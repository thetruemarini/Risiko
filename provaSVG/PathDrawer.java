package provaSVG;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.AffineTransform;
import java.awt.Color;


public class PathDrawer extends JPanel{
   
    public void drawPath(Graphics2D g2d, String pathData) {
        Path2D path = new Path2D.Double();
        String[] commands = pathData.split(" ");
        int i = 0;
        while (i < commands.length) {
            String command = commands[i];
            switch (command) {
                case "M":
                    double moveToX = Double.parseDouble(commands[++i]);
                    double moveToY = Double.parseDouble(commands[++i].replace(",", ""));
                    path.moveTo(moveToX, moveToY);
                    break;
                case "C":
                        double ctrl1X = Double.parseDouble(commands[++i]);
                        double ctrl1Y = Double.parseDouble(commands[++i].replace(",", ""));
                        double ctrl2X = Double.parseDouble(commands[++i]);
                        double ctrl2Y = Double.parseDouble(commands[++i].replace(",", ""));
                        double endX = Double.parseDouble(commands[++i]);
                        double endY = Double.parseDouble(commands[++i].replace(",", ""));
                        path.curveTo(ctrl1X, ctrl1Y, ctrl2X, ctrl2Y, endX, endY);
                        break;
                    case "c":
                        double relCtrl1X = Double.parseDouble(commands[++i]);
                        double relCtrl1Y = Double.parseDouble(commands[++i].replace(",", ""));
                        double relCtrl2X = Double.parseDouble(commands[++i]);
                        double relCtrl2Y = Double.parseDouble(commands[++i].replace(",", ""));
                        double relEndX = Double.parseDouble(commands[++i]);
                        double relEndY = Double.parseDouble(commands[++i].replace(",", ""));
                        path.curveTo(relCtrl1X, relCtrl1Y, relCtrl2X, relCtrl2Y, relEndX, relEndY);
                    break;
                case "Z":
                    path.closePath();
                    break;
            }
            i++;
        }
        g2d.draw(path);
        }

        @Override
        protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        String pathData = "M1073.11,604.66 c0-.34,1.62-4.09,5.7-3.91s3.57,1.36,3.49,2.3-7.23,9.45-8.77,10.13-9.97,1.19-10.86.34-.46-2.64,1.24-3.06,3.57-1.52,3.83-2.29-.85-1.8,1.79-2.05,3.57-1.45,3.57-1.45 Z";
        AffineTransform transform = new AffineTransform();
        double scaleX = getWidth() / 400.0;
        double scaleY = getHeight() / 400.0;
        transform.scale(scaleX, scaleY);
        g2d.setTransform(transform);
        drawPath(g2d, pathData);
        }

        public static void main(String[] args) {
        JFrame frame = new JFrame("Path Drawer");
        PathDrawer panel = new PathDrawer();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
    
}