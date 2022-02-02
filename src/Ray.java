import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Ray extends JPanel
{
    Point2D.Double direction;
    Point2D.Double pointer;
    Line2D.Double ray;

    public Ray(double centerX,double centerY)
    {
        this.pointer = new Point2D.Double(centerX,centerY);
    }

    public void paint(Graphics graphics)
    {
        Graphics2D g = (Graphics2D) graphics;
        g.setStroke(new BasicStroke(.5f));
        //g.draw(this.ray);
    }
    public void setDir(double x, double y)
    {
        this.direction = new Point2D.Double(x,y);
        this.ray = new Line2D.Double(this.direction,this.pointer);
    }
    public Point2D.Double cast(Boundary boundary)
    {
        double x1 =  boundary.getBound().getX1();
        double y1 =  boundary.getBound().getY1();
        double x2 =  boundary.getBound().getX2();
        double y2 =  boundary.getBound().getY2();

        double x3 =  this.pointer.getX();
        double y3 =  this.pointer.getY();
        double x4 =  x3+ this.direction.getX();
        double y4 =  y3+ this.direction.getY();

        double denominator = (x1-x2)*(y3-y4)-(y1-y2)*(x3-x4);
        if (denominator == 0)
            return null;

        double t = ((x1-x3)*(y3-y4)-(y1-y3)*(x3-x4))/denominator;
        double u = -((x1-x2)*(y1-y3)-(y1-y2)*(x1-x3))/denominator;
        if ((t > 0 && t < 1 )&& (u > 0))
        {
            return new Point2D.Double((x1+t*(x2-x1)),(y1+t*(y2-y1)));
        }
        return null;
    }
}
