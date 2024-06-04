package controller;

import model.GameWorld;
import model.SokobanLogic;
import view.GameFrame;
import view.EntitiesRenderer;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameController {
    private GameWorld gameWorld;
    private SokobanLogic sokobanLogic;
    private GameFrame gameFrame;
    private EntitiesRenderer entitiesRenderer;

    public GameController(String levelFilePath) {
        gameWorld = new GameWorld(levelFilePath);
        sokobanLogic = new SokobanLogic(gameWorld);
        entitiesRenderer = new EntitiesRenderer(gameWorld.getLevel().getMobileEntities(), gameWorld.getLevel().getImmovableEntities());
        gameFrame = new GameFrame(entitiesRenderer);

        initController();
    }

    private void initController() {
        gameFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // No action on key release
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // No action on key typed
            }
        });

        SwingUtilities.invokeLater(() -> {
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setVisible(true);
            gameFrame.requestFocusInWindow();
        });

        Timer gameTimer = new Timer(200, e -> updateGame());
        gameTimer.start();
    }

    private void handleKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                sokobanLogic.moveCharacter(0, -1);
                break;
            case KeyEvent.VK_S:
                sokobanLogic.moveCharacter(0, 1);
                break;
            case KeyEvent.VK_A:
                sokobanLogic.moveCharacter(-1, 0);
                break;
            case KeyEvent.VK_D:
                sokobanLogic.moveCharacter(1, 0);
                break;
            case KeyEvent.VK_Z:
                sokobanLogic.undoMove();
                break;
            default:
                break;
        }
        updateGame();
    }

    private void updateGame() {
        gameFrame.renderFrame();
        gameFrame.setPuntuation(gameWorld.getPuntuation());
        gameFrame.setLevelName(gameWorld.getLevel().getLevelName());
    }

    public static void main(String[] args) {
        new GameController("./src/main/java/model/maps/test_level.txt");
    }
}