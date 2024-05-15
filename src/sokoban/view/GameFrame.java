package view;

import javax.swing.JFrame;
import model.Entity;

public class GameFrame extends JFrame {
    private Renderer renderer;

    public GameFrame(Renderer renderer) {
        setTitle("Entity Test");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.renderer = renderer;
        add(renderer);
    }
  
    public void renderFrame() {
    	renderer.repaint();
    }
}