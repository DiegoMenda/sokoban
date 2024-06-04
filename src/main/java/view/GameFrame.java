package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameFrame extends JFrame {
    private EntitiesRenderer renderer;
    private int puntuation = 0;
    private String levelName = "";
    
    public GameFrame(EntitiesRenderer renderer) {
        setTitle("Sokoban Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        this.renderer = renderer;

        renderer.setPreferredSize(new Dimension(400, 300)); 

        add(renderer);

        OverlayPanel overlayPanel = new OverlayPanel();
        overlayPanel.setBounds(0, 0, getWidth(), getHeight());
        add(overlayPanel);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                centerRenderer();
            }
        });

        centerRenderer();
    }

    private void centerRenderer() {
        int frameWidth = getWidth();
        int frameHeight = getHeight();
        int rendererWidth = renderer.getPreferredSize().width;
        System.out.println("W: "+rendererWidth);
        int rendererHeight = renderer.getPreferredSize().height;
        System.out.println("H: "+rendererHeight);
        int x = (frameWidth - rendererWidth) / 2;
        int y = (frameHeight - rendererHeight) / 2;

        renderer.setBounds(x, y, rendererWidth, rendererHeight);
    }

    public void setPuntuation(int puntuation) {
        this.puntuation = puntuation;
        repaint(); 
    }
    
    public void setLevelName(String levelName) {
        this.levelName = levelName;
        repaint(); 
    }

    public void renderFrame() {
        renderer.repaint();
        repaint();
    }

    private class OverlayPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.setColor(Color.BLACK); 
            g.drawString("Puntuacion: " + puntuation, 5, 30);
            g.drawString("Nombre de nivel: " + levelName , 5, 55);
        }
    }
}