import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.time.chrono.IsoChronology;

public class VisualField extends JPanel
{
    static Field field;
    double [] scene;

    public VisualField()
    {
        super();
        setSize(Scene.width,Scene.height);
        setFocusable(true);
        field = RayCasting.get();
    }
    public void paint(Graphics graphics)
    {
        Graphics2D g = (Graphics2D) graphics;
        if (field.getField()!= null)
        {
            this.scene = field.getField();
            //double w = (double)Scene.width/this.scene.length;
            double w = (double)Frame.width/this.scene.length;

            for (int i = 0; i< this.scene.length; i++)
            {
                int b = (255  - 2*  (int) (((scene[i]*scene[i]) /(Scene.width*Scene.width) )*255));
                //double h = ((scene[i]/Scene.width)*Scene.height);
                double h = ((scene[i]/Frame.width)*Scene.height);

                if (b>=0&&b<=255)
                {
                    g.setStroke(new BasicStroke(.001f));
                    g.setColor(new Color(b,b,b));

                    //Rectangle2D.Double r = new Rectangle2D.Double((Scene.width - (i * w + w / 2)) , h, w, (Scene.height - h *1.8));
                    Rectangle2D.Double r = new Rectangle2D.Double((Scene.width - (i * w + w / 2)) , h, w, (Scene.height - h *1.5));

                    g.fill(r);


                }
            }
        }
    }
    public void getScene()
    {

    }
    protected void update()
    {

    }



}
