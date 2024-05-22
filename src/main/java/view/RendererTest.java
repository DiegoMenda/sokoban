package view;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import model.*;

public class RendererTest {
    private static int xDirection = 0;
    private static int yDirection = 0;

    public static void main(String[] args) {
        GameWorld mundo = new GameWorld("./src/main/java/model/maps/test_level.txt");
        LevelLoader levelLoader = new LevelLoader();
        SokobanLogic logica = new SokobanLogic(mundo);
        String xd = mundo.getLevel().toString();

        System.out.println(xd);

        EntitiesRenderer rend = new EntitiesRenderer(mundo.getLevel().getMobileEntities(), mundo.getLevel().getImmovableEntities());
        GameFrame gameFrame = new GameFrame(rend);

        gameFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        xDirection = 0;
                        yDirection = -1;
                        break;
                    case KeyEvent.VK_DOWN:
                        xDirection = 0;
                        yDirection = 1;
                        break;
                    case KeyEvent.VK_LEFT:
                        xDirection = -1;
                        yDirection = 0;
                        break;
                    case KeyEvent.VK_RIGHT:
                        xDirection = 1;
                        yDirection = 0;
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //Stop
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_RIGHT:
                        xDirection = 0;
                        yDirection = 0;
                        break;
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // ¿Que es esto?
            }
        });

        SwingUtilities.invokeLater(() -> {
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setVisible(true);
            gameFrame.requestFocusInWindow();
        });

        while (true) {
            try {
                Thread.sleep(120);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gameFrame.renderFrame();
            logica.moveCharacter(xDirection, yDirection);
        }
        
        
    }
}