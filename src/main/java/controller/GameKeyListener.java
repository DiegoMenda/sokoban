package controller;


import javax.swing.*;
import java.awt.event.ActionEvent;

public class GameKeyListener {

    
    GameController gameController;
    public GameKeyListener(GameController gc) {
    	gameController = gc;
    }

    public void setupKeyBindings(JComponent component) {
        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW); // para que no se pulse si estamos fuera de la ventana
        ActionMap actionMap = component.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("W"), "moveUp");
        inputMap.put(KeyStroke.getKeyStroke("S"), "moveDown");
        inputMap.put(KeyStroke.getKeyStroke("A"), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke("D"), "moveRight");
        inputMap.put(KeyStroke.getKeyStroke("Z"), "undoMove");

        //REMOVE
        inputMap.put(KeyStroke.getKeyStroke("J"), "guarda");
        inputMap.put(KeyStroke.getKeyStroke("K"), "carga");
        
        // move up
        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	gameController.sokobanLogic.moveCharacter(0, -1);
            }
        });
        // move down
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	gameController.sokobanLogic.moveCharacter(0, 1);
            }
        });
        // move left
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	gameController.sokobanLogic.moveCharacter(-1, 0);
            }
        });
        // move right
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	gameController.sokobanLogic.moveCharacter(1, 0);
            }
        });
        // undo
        actionMap.put("undoMove", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	gameController.sokobanLogic.undoMove();
            }
        });
        //
        
        
        
        
        
        
        //REMOVE
        
        actionMap.put("carga", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	gameController.loadLevel();
            	gameController.updateGame();
            	gameController.getGameFrame().centerRenderer();

            }
        });
        
        actionMap.put("guarda", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	gameController.saveLevel();
            	gameController.updateGame();
            }
        });
        
        
        
        
        
    }
}