import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class Particle extends JPanel
{
    Point2D.Double pointer;
    double x,y;
    static Ray[] rays = new Ray[320];
    double angle = 0.0045;
    static double radius = 1300;

    public Particle(double x, double y)
    {
        this.pointer = new Point2D.Double(x,y);
        this.x = x;
        this.y = y;
        expand();
    }
    public void expand()
    {
        double newx,newy;
        for (int i = 40; i< 360; i++)
        {
            this.angle+= 0.0045;
            rays[i-40] = new Ray(this.x,this.y);
            newx = (this.x+radius*Math.cos(this.angle));
            newy = (this.y-radius*Math.sin(this.angle));
            rays[i-40].setDir(newx,newy);
        }
    }
    public void paint(Graphics graphics)
    {
        Graphics2D g = (Graphics2D) graphics;
        for (Ray r : rays)
            r.paint(g);
    }
    public Point2D.Double getPointer()
    {
        return this.pointer;
    }

    public Ray[] getRays() {
        return rays;
    }
}
