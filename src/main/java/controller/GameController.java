package controller;

import model.GameWorld;
import model.SokobanLogic;
import view.GameFrame;
import view.EntitiesRenderer;

import javax.swing.*;

public class GameController {
    private GameWorld gameWorld;
    private SokobanLogic sokobanLogic;
    private GameFrame gameFrame;
    private EntitiesRenderer entitiesRenderer;
    private GameKeyListener keyListener;

    public GameController(String levelFilePath) {
    	
        gameWorld = new GameWorld(levelFilePath);
        sokobanLogic = new SokobanLogic(gameWorld);
        entitiesRenderer = new EntitiesRenderer(gameWorld.getLevel().getMobileEntities(), gameWorld.getLevel().getImmovableEntities());
        gameFrame = new GameFrame(entitiesRenderer);
        keyListener = new GameKeyListener(sokobanLogic);

        
        initController();
    }

    private void initController() {
        keyListener.setupKeyBindings(gameFrame.getRootPane());

        SwingUtilities.invokeLater(() -> {
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setVisible(true);
            gameFrame.requestFocusInWindow();
        });

        Timer gameTimer = new Timer(50, e -> updateGame()); /// ¿Le ponemos menos?
        gameTimer.start();
    }

    private void updateGame() {
        entitiesRenderer.repaint();
        gameFrame.setPuntuation(gameWorld.getPuntuation());
        gameFrame.setLevelName(gameWorld.getLevel().getLevelName());
    }

    public static void main(String[] args) {
        new GameController("./src/main/java/model/maps/test_level.txt");
    }
}