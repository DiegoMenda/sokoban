package view;

import javax.swing.JFrame;
import model.Entity;

public class GameFrame extends JFrame {
    private EntitiesRenderer renderer;

    public GameFrame(EntitiesRenderer renderer) {
        setTitle("Sokoban Game");
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