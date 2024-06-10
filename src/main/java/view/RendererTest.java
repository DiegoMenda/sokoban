package view;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import model.*;

public class RendererTest {
    private static int xDirection = 0;
    private static int yDirection = 0;
    private static boolean unDo = false;
    private static boolean moveCooldown = false;

    public static void main(String[] args) {
        String[] mapas = new String[9];
        mapas[0] = "/src/main/java/model/maps/test_level.txt";
        mapas[1] = "/src/main/java/model/maps/test_level_no_boxes.txt";
        mapas[2] = "/src/main/java/model/maps/test_level_no_goals.txt";
        mapas[3] = "/src/main/java/model/maps/test_level_no_whman.txt";
        mapas[4] = "/src/main/java/model/maps/test_level_2_warehouseMan.txt";
        mapas[5] = "/src/main/java/model/maps/test_level_nboxes_!=_ngoals.txt";

        GameWorld mundo = new GameWorld("./src/main/java/model/maps/test_level.txt");

        SokobanLogic logica = new SokobanLogic(mundo);
        String xd = mundo.getLevel().toString();
       

        

        EntitiesRenderer rend = new EntitiesRenderer(mundo.getLevel().getMobileEntities(), mundo.getLevel().getImmovableEntities());
        GameFrame gameFrame = new GameFrame(rend);
        gameFrame.setLevelName(mundo.getLevel().getLevelName());

        gameFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!moveCooldown) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_W:
                            xDirection = 0;
                            yDirection = -1;
                            break;
                        case KeyEvent.VK_S:
                            xDirection = 0;
                            yDirection = 1;
                            break;
                        case KeyEvent.VK_A:
                            xDirection = -1;
                            yDirection = 0;
                            break;
                        case KeyEvent.VK_D:
                            xDirection = 1;
                            yDirection = 0;
                            break;
                        case KeyEvent.VK_Z:
                            unDo = true;
                            break;
                    }
                    moveCooldown = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_D:
                        xDirection = 0;
                        yDirection = 0;
                        moveCooldown = false;
                        break;
                    case KeyEvent.VK_Z:
                        unDo = false;
                        break;
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // No se requiere implementación
            }
        });

        SwingUtilities.invokeLater(() -> {
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setVisible(true);
            gameFrame.requestFocusInWindow();
        });

        Timer gameTimer = new Timer(100, e -> {
            gameFrame.renderFrame();
            if (!unDo) {
                if (xDirection != 0 || yDirection != 0) {
                    logica.moveCharacter(xDirection, yDirection);
                }
            } else {
                logica.undoMove();
                unDo = false;
            }
            gameFrame.setPuntuation(mundo.getPuntuation());
            moveCooldown = false; // Reset cooldown after each timer tick
        });

        gameTimer.start();

    }
}