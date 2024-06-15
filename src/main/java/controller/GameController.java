package controller;

import model.Air;
import model.Box;
import model.Entity;
import model.GameWorld;
import model.GameWorldWithHistory;
import model.Goal;
import model.LevelLoader;
import model.LevelSaver;
import model.SokobanLogic;
import model.Wall;
import model.Worker;
import view.GameFrame;
import view.EntitiesRenderer;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameController {
    private GameWorld gameWorld;
    SokobanLogic sokobanLogic;
    private GameFrame gameFrame;
    private EntitiesRenderer entitiesRenderer;
    private GameKeyListener keyListener;
	private static final Logger logger = LoggerFactory.getLogger(SokobanLogic.class);


    public GameController(String levelFilePath) {
    	
        gameWorld = new GameWorld(levelFilePath);
        sokobanLogic = new SokobanLogic(gameWorld);
        entitiesRenderer = new EntitiesRenderer(gameWorld);
        setGameFrame(new GameFrame(entitiesRenderer));
        keyListener = new GameKeyListener(this);

        
        initController();
    }

    private void initController() {
        keyListener.setupKeyBindings(getGameFrame().getRootPane());
        SwingUtilities.invokeLater(() -> {
            getGameFrame().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            getGameFrame().setVisible(true);
            getGameFrame().requestFocusInWindow();

            // Asigna las acciones a los botones aquí
            getGameFrame().setStartButtonAction(e -> startNewGame());
            //getGameFrame().setLoadButtonAction(e -> loadLevel());
            getGameFrame().setRestartLevelButtonAction(e -> restartCurrentLevel());
            getGameFrame().setSaveGameButtonAction(e -> saveLevel());
            getGameFrame().setOpenSaveGameButtonAction(e -> loadLevel());
        });

        Timer gameTimer = new Timer(50, e -> updateGame());
        gameTimer.start();
    }


    private void restartCurrentLevel() {
    	int numero = gameWorld.getLevelNumber();
    	gameWorld.setLevel(LevelLoader.loadLevel(new File("./src/main/java/model/maps/level_"+numero+".txt")));
    	gameWorld.setLevelNumber(numero);
    	gameWorld.getLocalPuntuation().set(numero-1, 0);
    	this.gameWorld.updateFrom(gameWorld);
   	 
    	sokobanLogic.clearHistory();
    	updateGame();
    	
		
	}

	private void startNewGame() {
    	gameWorld.setLevel(LevelLoader.loadLevel(new File("./src/main/java/model/maps/level_1.txt")));
    	gameWorld.setLevelNumber(1);
    	gameWorld.setPuntuation(0);
    	List<Integer> lista = new ArrayList<>();
    	lista.add(0);
    	gameWorld.setLocalPuntuation(lista);
    	this.gameWorld.updateFrom(gameWorld);
    	 
    	 sokobanLogic.clearHistory();
    	 updateGame();
    }


	public void updateGame() {
        entitiesRenderer.repaint();
        getGameFrame().setPuntuation(gameWorld.getPuntuation());
        getGameFrame().setLevelName(gameWorld.getLevel().getLevelName());
        checkLevelCompletion();
    }
    
    private void checkLevelCompletion() {
        if (sokobanLogic.isLevelCompleted()) {
            entitiesRenderer.repaint();
            // Introduces a delay of 1 second (1000 ms) before loading the next level.
            //Timer delayTimer = new Timer(1000, evt -> {
                // Cargar el siguiente nivel
                System.err.println(gameWorld.getLevel());
                gameWorld.loadNextLevel();
                sokobanLogic.clearHistory();
                // Update view
                //entitiesRenderer.setMobileEntity(gameWorld.getLevel().getMobileEntities());
                //entitiesRenderer.setImmovableEntity(gameWorld.getLevel().getImmovableEntities());
                getGameFrame().centerRenderer();
                //((Timer) evt.getSource()).stop(); // Detener el timer después de la ejecución
            //});
            //delayTimer.setRepeats(false);
            //delayTimer.start();
        }
    }
    
    
    public void saveLevel() {
        // Crea un JFileChooser para guardar el archivo XML
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar archivo XML de nivel");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos XML", "xml");
        fileChooser.addChoosableFileFilter(filter);

        // Establece el directorio por defecto
        File defaultDirectory = new File("./src/main/java/model/maps/");
        fileChooser.setCurrentDirectory(defaultDirectory);

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            // Asegúrate de que el archivo tenga la extensión .xml
            if (!fileToSave.getAbsolutePath().endsWith(".xml")) {
                fileToSave = new File(fileToSave + ".xml");
            }
            try {
                // Guarda el archivo seleccionado
                LevelSaver.saveToXML(new GameWorldWithHistory(gameWorld, sokobanLogic.getHistory()), fileToSave.getAbsolutePath());
                logger.info("Level saved successfully.");
            } catch (JAXBException e) {
                e.printStackTrace();
                logger.error("Error saving level to XML", e);
            }
        }
    }
	
	public void loadLevel() {
	    // Crea un JFileChooser para seleccionar el archivo XML
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setDialogTitle("Seleccionar archivo XML de nivel guardado");
	    fileChooser.setAcceptAllFileFilterUsed(false);
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos XML", "xml");
	    fileChooser.addChoosableFileFilter(filter);

	    // Establece el directorio por defecto
	    File defaultDirectory = new File("./src/main/java/model/maps/");
	    fileChooser.setCurrentDirectory(defaultDirectory);

	    int userSelection = fileChooser.showOpenDialog(null);

	    if (userSelection == JFileChooser.APPROVE_OPTION) {
	        File fileToLoad = fileChooser.getSelectedFile();
	        try {
	            // Lee el archivo seleccionado
	            GameWorldWithHistory newLevelLoaded = LevelSaver.readFromXML(fileToLoad.getAbsolutePath());
	            if (newLevelLoaded != null) {
	                System.err.println(newLevelLoaded.getGameWorld().getLevel());
	                System.err.println(newLevelLoaded.getGameWorld().getLevel().getImmovableEntities(0, 0).getX());
	                this.gameWorld.updateFrom((GameWorld) newLevelLoaded.getGameWorld());

	                cleanUpArray(newLevelLoaded.getGameWorld().getLevel().getMobileEntities());

	                System.err.println(gameWorld.getLevel());
	                System.out.println(newLevelLoaded.getHistory().toString());
	                sokobanLogic.setHistory(newLevelLoaded.getHistory()); // Actualiza el historial también
	                logger.info("Level loaded successfully.");
	            } else {
	                logger.error("Failed to load level.");
	            }
	        } catch (JAXBException e) {
	            e.printStackTrace();
	            logger.error("Error loading level from XML", e);
	        }
	    }
	}
	

	
    private <T extends Entity> void cleanUpArray(T[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] != null && !(array[i][j] instanceof Wall) && 
                    !(array[i][j] instanceof Air) && !(array[i][j] instanceof Goal) && 
                    !(array[i][j] instanceof Worker) && !(array[i][j] instanceof Box)) {
                    array[i][j] = null;
                }
            }
        }
    }
	
    
    

    public static void main(String[] args) {
        new GameController("./src/main/java/model/maps/level_1.txt");
    }

	public GameFrame getGameFrame() {
		return gameFrame;
	}

	public void setGameFrame(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}
    
    
}