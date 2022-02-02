import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

public class Boundary extends JPanel
{

    Line2D.Double vector;

    public Boundary(double x1,double y1,double x2,double y2)
    {
        this.vector = new Line2D.Double(x1,y1,x2,y2);
    }
    public Line2D.Double getBound()
    {
        return this.vector;
    }
    public void paint(Graphics graphics)
    {
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.MAGENTA);
        g.setStroke(new BasicStroke(2.0f));
        g.draw(vector);
    }
}
