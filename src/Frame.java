import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Frame extends JFrame
{
    private Engine engine;
    private Scene scene;
    static int width = 700, height = 700;
    private WindowEvent listen;

    public Frame()
    {
        engine = createEngine();
        scene = new Scene();
        setWindowProperties();
    }

    private Engine createEngine() {
        Container cp = getContentPane();
        Engine engine = new Engine(new RayCasting());
        int canvasWidth = width;
        int canvasHeight = height;
        engine.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        addKeyListener(new MyKeyAdapter());
        addMouseMotionListener(new MyMouseAdapter());
        cp.add(engine);
        return engine;
    }

    private void startThread(Engine engine) {
        Thread th = new Thread(engine);
        th.start();
        Thread th2 = new Thread(scene.getScene());
        th2.start();
    }

    private void setWindowProperties() {
        WindowAdapter closed = new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                listen = new WindowEvent(Frame.getWindows()[0], 201);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(listen);
                System.out.println("System closed by user");
                System.exit(0);
            }
        };
        this.addWindowListener(closed);
        setResizable(false);
        pack();
        setVisible(true);
        setLocation(50,50);// Center window
    }

    private class Engine extends JPanel implements Runnable {

        private final RayCasting visualization;

        public Engine(RayCasting visualization) {
            this.visualization = visualization;
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            setBackground(new Color(0, 0, 0));
            visualization.paint(graphics);
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

        private void sleep() {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }


    private class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent keyEvent)
        {
            if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE)
                startThread(engine);

            if (keyEvent.getKeyCode() == KeyEvent.VK_0)
                engine.visualization.createWalls();

            if (keyEvent.getKeyCode() == KeyEvent.VK_UP)
                engine.visualization.particle.expand();

            if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN)
                engine.visualization.particle.expand();
        }
    }
    private class MyMouseAdapter extends MouseMotionAdapter
    {

        @Override
        public void mouseMoved(MouseEvent e){
            engine.visualization.particle = new Particle(e.getX()-5,e.getY()-25);
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Frame::new);
    }

}