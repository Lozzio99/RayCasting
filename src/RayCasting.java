import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RayCasting extends JPanel
{
    Particle particle;
    Boundary[] walls;
    int radius = 8,r = radius/2;
    ArrayList<Point2D.Double> intersect;
    double [] scene;
    static Field f;

    public RayCasting()
    {
        f = new View();
        setSize(Frame.width,Frame.height);
        setFocusable(true);
        this.walls = new Boundary[14];
        this.walls[0] = new Boundary(0,3,getWidth()-3,3);
        this.walls[1] = new Boundary(0,3,0,getHeight()-3);
        this.walls[2] = new Boundary(getWidth()-3,3,getWidth()-3,getHeight()-3);
        this.walls[3] = new Boundary(0,getHeight()-3,getWidth()-3,getHeight()-3);

        for (int i = 4; i< this.walls.length; i++)
        {
            int x1 = new Random().nextInt(getWidth());
            int x2 = new Random().nextInt(getWidth());
            int y1 = new Random().nextInt(getHeight());
            int y2 = new Random().nextInt(getHeight());
            this.walls[i] = new Boundary(x1,y1,x2,y2);
        }

        this.particle = new Particle(200,200);
    }

    public void createWalls()
    {

        for (int i = 4; i< this.walls.length; i++)
        {
            int x1 = new Random().nextInt(getWidth());
            int x2 = new Random().nextInt(getWidth());
            int y1 = new Random().nextInt(getHeight());
            int y2 = new Random().nextInt(getHeight());
            this.walls[i] = new Boundary(x1,y1,x2,y2);
        }
    }
    public void update()
    {
        this.intersect = new ArrayList<>();
        this.scene = new double[this.particle.getRays().length];
        int i = 0;
        for (Ray r : this.particle.getRays())
        {
            Point2D.Double closest = null;
            double minDistance = Integer.MAX_VALUE;
            for (Boundary wall : this.walls)
            {
                Point2D.Double pt = r.cast(wall);
                if (pt != null)
                {
                    double d = pt.distance(this.particle.getPointer());
                    if (d<minDistance)
                    {
                        closest = pt;
                        minDistance = d;
                    }
                }
            }

            if (closest != null)
            {
                this.intersect.add(closest);
            }
            this.scene[i] = minDistance;
            i++;
        }
        f.setField(this.scene);
    }
    public void paint(Graphics graphics)
    {
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(new Color(0x5858CE));
        this.particle.paint(g);

        g.setColor(Color.red);
        for (Boundary wall : this.walls)
            wall.paint(g);

        g.setStroke(new BasicStroke(.5f));
        g.setColor(new Color(132, 212, 136));
        if (this.intersect!= null)
            for (Point2D.Double r : this.intersect)
                g.draw(new Line2D.Double(r,this.particle.getPointer()));
    }
    public ArrayList<Point2D.Double> getIntersect()
    {
        return this.intersect;
    }


    static Field get()
    {
        return f;
    }


}

