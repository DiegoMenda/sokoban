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
            gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            gameFrame.setVisible(true);
            gameFrame.requestFocusInWindow();
            
        });
        
        Timer gameTimer = new Timer(50, e -> updateGame());
        gameTimer.start();
    }


    private void updateGame() {
        entitiesRenderer.repaint();
        gameFrame.setPuntuation(gameWorld.getPuntuation());
        gameFrame.setLevelName(gameWorld.getLevel().getLevelName());
        
        checkLevelCompletion();
    }
    
    private void checkLevelCompletion() {
        if (sokobanLogic.isLevelCompleted()) {
            entitiesRenderer.repaint();
            // Introduces a delay of 1 second (1000 ms) before loading the next level.
            Timer delayTimer = new Timer(1000, evt -> {
                // Cargar el siguiente nivel
                System.err.println(gameWorld.getLevel());
                gameWorld.loadNextLevel();
                // Update view
                entitiesRenderer.setMobileEntity(gameWorld.getLevel().getMobileEntities());
                entitiesRenderer.setImmovableEntity(gameWorld.getLevel().getImmovableEntities());
                ((Timer) evt.getSource()).stop(); // Detener el timer después de la ejecución
            });
            delayTimer.setRepeats(false);
            delayTimer.start();
        }
    }

    public static void main(String[] args) {
        new GameController("./src/main/java/model/maps/level_1.txt");
    }
    
    
}