import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Scene extends JFrame
{
    private Rendering rendering;
    static int width = 720, height = 700;

    public Scene()
    {
        super();
        rendering = createRendering();
        setWindowProperties();
    }
    private Rendering createRendering()
    {
        Container cp = getContentPane();
        Rendering render = new Rendering(new VisualField());//name class as the parameter
        int canvasWidth = width;
        int canvasHeight = height;
        setBackground(new Color(0, 0, 0));
        render.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        cp.add(render);
        return render;
    }
    Rendering getScene()
    {
        return this.rendering;
    }

    private void setWindowProperties()
    {
        WindowAdapter closed = new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                WindowEvent listen = new WindowEvent(Frame.getWindows()[0], 201);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(listen);
                System.out.println("System closed by user");
                System.exit(0);
            }
        };
        this.addWindowListener(closed);
        setResizable(false);
        pack();
        setVisible(true);
        setLocation(800,50);// Center window
    }

    private class Rendering extends JPanel implements Runnable {

        VisualField visualization;
        public Rendering(VisualField visualization)
        {

            this.visualization = visualization;
        }

        @Override
        public void run()
        {
            long lastTime = System.nanoTime();
            double elapsedTime = 0.0;
            double FPS = 120.0;
            while (true) {
                long now = System.nanoTime();
                elapsedTime += ((now - lastTime) / 1_000_000_000d) * FPS;
                lastTime = System.nanoTime();

                if (elapsedTime >= 1) {
                    visualization.update();
                    repaint();
                    elapsedTime--;
                }
                sleep();

            }
        }
        @Override
        protected void paintComponent(Graphics graphics)
        {
            super.paintComponent(graphics);
            setBackground(new Color(0, 0, 0));
            visualization.paint(graphics);
        }
        private void sleep() {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
