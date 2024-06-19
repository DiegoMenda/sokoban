import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Image;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.xml.bind.JAXBException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import model.GameWorld;
import model.GameWorldWithHistory;
import model.Goal;
import model.ImmovableEntity;
import model.Level;
import model.LevelLoader;
import model.LevelSaver;
import model.Move;
import model.Position;
import model.SokobanLogic;
import model.TexturePaths;
import model.Wall;
import model.Box;

class Test2 {

    private Level nivel;
    
    private File levelFile;
    private GameWorld mundo;
    private SokobanLogic logica;
    @BeforeEach
    void setUp() {
        
        levelFile = new File("./resources/maps/test_level.txt");
        nivel = LevelLoader.loadLevel(levelFile);
        mundo = new GameWorld("./resources/maps/test_level.txt");
        logica =new SokobanLogic(mundo);
    }

    @Test
    void readLevelName() {
        assertEquals("Nivel 1", nivel.getLevelName());
    }

    @Test
    void readLevelRows() {
        assertEquals(5, nivel.getRow());
    }

    @Test
    void readLevelCols() {
        assertEquals(7, nivel.getCol());
    }

    @Test
    void readWareHouseManPositionX() {
        assertEquals(1, nivel.getWarehouseMan().getX());
    }
    @Test
    void readBox() {
        assertEquals("#", nivel.getMobileEntities(2, 1).toString());
    }
    @Test
    void readAir() {
        assertEquals(".", nivel.getImmovableEntities(3, 1).toString());
    }
    @Test
    void readWall() {
        assertEquals("+", nivel.getImmovableEntities(0, 0).toString());
    }
    @Test
    void readGoal() {
        assertEquals("*", nivel.getImmovableEntities(4, 1).toString());
    }
    @Test
    void getImmovableEntities() {
    
        assertEquals("+", nivel.getImmovableEntities()[0][0].toString());
    }
    @Test
    void getMovableEntities() {
    
        assertEquals("#", nivel.getMobileEntities()[1][2].toString());
    }
    
    @Test
    void levelToString() {
        assertEquals("Level Name: Nivel 1\n"
        		+"Dimensions: 5x7\n"
        		+"WarehouseMan: W\n"
        		+"Immovable Entities:\n"
        		+"+ + + + + + + \n"
        		+ "+ . . . * . + \n"
        		+ "+ . . . . . + \n"
        		+ "+ . . . . . + \n"
        		+ "+ + + + + + + \n"
        		+"Mobile Entities:\n"
        		+"null null null null null null null \n"
        		+"null W # null null null null \n"
        		+"null null null null null null null \n"
        		+"null null null null null null null \n"
        		+"null null null null null null null \n", nivel.toString());
    }
    @Test
    void readWareHouseMan() {
        assertEquals("W", nivel.getMobileEntities(1, 1).toString());
    }
    @Test
    void readWareHouseManPositionY() {
        System.out.println(nivel.getWarehouseMan().getY());
        assertEquals(1, nivel.getWarehouseMan().getY());
    }
    @Test
    void canMoveFromTO() {
        logica.moveCharacter(1, 0);
        nivel =mundo.getLevel();
        assertEquals(1, nivel.getWarehouseMan().getY());
        assertEquals(2, nivel.getWarehouseMan().getX());
    }
    @Test
    void canMoveFromTO2() {
        logica.moveCharacter(1, 0);
        
        logica.moveCharacter(1, 0);
        //ImmovableEntity[][] ent = nivel.getImmovableEntities();
        Goal gol = (Goal) mundo.getLevel().getImmovableEntities(4, 1);
        assertTrue(gol.isGoalArchieved());
        logica.moveCharacter(1, 0);
        assertFalse(gol.isGoalArchieved());
        nivel =mundo.getLevel();
        assertEquals(1, nivel.getWarehouseMan().getY());
        assertEquals(4, nivel.getWarehouseMan().getX());
    }
    @Test
    void canMoveFromTO3() {
        logica.moveCharacter(0, 1);
        nivel =mundo.getLevel();
        assertEquals(2, nivel.getWarehouseMan().getY());
        assertEquals(1, nivel.getWarehouseMan().getX());
    }
    @Test
    void canMoveFromTO4() {
        logica.moveCharacter(0, 0);
        nivel =mundo.getLevel();
        assertEquals(1, nivel.getWarehouseMan().getY());
        assertEquals(1, nivel.getWarehouseMan().getX());
    }
    @Test
    void cantMoveFromTO() {
        logica.moveCharacter(-1, 0);
        nivel =mundo.getLevel();
        assertEquals(1, nivel.getWarehouseMan().getY());
        assertEquals(1, nivel.getWarehouseMan().getX());
    }
    @Test
    void cantMoveFromTO2() {
        logica.moveCharacter(0, -1);
        nivel =mundo.getLevel();
        assertEquals(1, nivel.getWarehouseMan().getY());
        assertEquals(1, nivel.getWarehouseMan().getX());
    }
    @Test
    void cantMoveFromTO3() {
        logica.moveCharacter(-5,0);
        nivel =mundo.getLevel();
        assertEquals(1, nivel.getWarehouseMan().getY());
        assertEquals(1, nivel.getWarehouseMan().getX());
    }
    
    @Test
    void testConstructorThrowsException() throws Exception {
        Constructor<TexturePaths> constructor = TexturePaths.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        assertThrows(InvocationTargetException.class, () -> {
            constructor.newInstance();
        });
    }
    @Test
    void loadNextLevel() {
    	mundo = new GameWorld("./resources/maps/level_1.txt");
    	Level level2 = LevelLoader.loadLevel(  new File("./resources/maps/level_2.txt")  );
    	mundo.loadNextLevel();
    	assertEquals(level2.toString(),mundo.getLevel().toString());
    }
    @Test
    void getLevelPuntuation() {
    	
    	assertEquals(0, mundo.getActualLevelPuntuation());
    	logica.moveCharacter(0, 1);
    	assertEquals(1, mundo.getActualLevelPuntuation());
    }
    @Test
    void updateFrom() {
    	GameWorld mundo2 = new GameWorld("./resources/maps/level_2.txt");
    	mundo.updateFrom(mundo2);
    	assertEquals("Nivel 2", mundo.getLevel().getLevelName());
    	
    }

    @Test 
    void isLevelCompleted() {
    	logica.moveCharacter(1, 0);
    	logica.moveCharacter(1, 0);
    	assertTrue(logica.isLevelCompleted());
    }
//    @Test
//    void cantMoveFromTO3() {
//        logica.moveCharacter(1, 1);
//        nivel =mundo.getLevel();
//        assertEquals(1, nivel.getWarehouseMan().getY());
//        assertEquals(1, nivel.getWarehouseMan().getX());
//    }
    @ParameterizedTest
    @MethodSource("provideInvalidLevelFiles")
    void testInvalidLevels(File levelFile) {
        Level nivel = LevelLoader.loadLevel(levelFile);
        assertEquals(null, nivel);
    }

    private static Stream<File> provideInvalidLevelFiles() {
        return Stream.of(
            new File("./resources/maps/test_level_wrong_char.txt"),
            new File("./resources/maps/test_level_2_warehouseMan.txt"),
            new File("./resources/maps/test_level_no_boxes.txt"),
            new File("./resources/maps/test_level_no_goals.txt"),
            new File("./resources/maps/test_level_nboxes_!=_ngoals.txt"),
            new File("./resources/maps/test_level_no_level.txt"),
            new File("./resources/maps/test_level_no_whman.txt")
        );
    }
    @Test
    void createWithInvalidTexture() {
      Image im=TexturePaths.generateImage("hello");
        assertEquals(null, im);
    }

    @Test
    void undo() {
        logica.moveCharacter(0, 1);
        assertFalse(logica.getHistory().isEmpty());
        nivel =mundo.getLevel();
        logica.undoMove();
        assertEquals(1, nivel.getWarehouseMan().getY());
        assertEquals(1, nivel.getWarehouseMan().getX());
    }
    @Test
    void undoNoHistory() {
        
        assertTrue(logica.getHistory().isEmpty());
        nivel =mundo.getLevel();
        logica.undoMove();
        assertEquals(1, nivel.getWarehouseMan().getY());
        assertEquals(1, nivel.getWarehouseMan().getX());
    }
    @Test
    void undoBoxMovement() {
   
        logica.moveCharacter(1, 0);
        
        nivel =mundo.getLevel();
        logica.undoMove();
       
        assertEquals(1, nivel.getWarehouseMan().getY());
        assertEquals(1, nivel.getWarehouseMan().getX());
    }
    @Test 
    void undoBoxGoal(){
    	logica.moveCharacter(1, 0);
    	logica.moveCharacter(1, 0);
    	logica.moveCharacter(1, 0);
    	logica.undoMove();
    	assertTrue(logica.isLevelCompleted());
    	logica.undoMove();
    	assertFalse(logica.isLevelCompleted());
    }
    @Test
    void logicHistory() {
    	logica.moveCharacter(0, 1);
    	assertNotNull(logica.getHistory());
    	logica.setHistory(null);
    	assertNull(logica.getHistory());
    }
    @Test
    void logicClearHistory() {
    	logica.moveCharacter(0, 1);
    	assertFalse(logica.getHistory().isEmpty());
    	logica.clearHistory();
    	assertTrue(logica.getHistory().isEmpty());
    }

    @BeforeEach
    @AfterEach
    void resetContext() {
        try {
            // Accedemos al campo `context` privado de `LevelSaver` para restablecerlo a null
            Field contextField = LevelSaver.class.getDeclaredField("context");
            contextField.setAccessible(true);
            contextField.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error resetting context field", e);
        }
    }

    @Test
    void saveLevel() throws JAXBException {
        // Movemos al personaje y verificamos la puntuación
        logica.moveCharacter(0, 1);
        assertEquals(1, mundo.getLocalPuntuation().get(0));
        
        // Creamos el objeto `GameWorldWithHistory`
        GameWorldWithHistory mundohistoria = new GameWorldWithHistory(mundo, logica.getHistory());
        
        // Guardamos en XML
        LevelSaver.saveToXML(mundohistoria, "./resources/maps/test_level_save.xml");
        
        // Leemos desde XML
        GameWorldWithHistory leido = LevelSaver.readFromXML("./resources/maps/test_level_save.xml");
        
        // Verificamos que los datos leídos son correctos
        assertEquals("Nivel 1", leido.getGameWorld().getLevel().getLevelName());
    }
    @Test void onlyreadLevel() throws JAXBException{
    	GameWorldWithHistory  leido = LevelSaver.readFromXML("./resources/maps/test_level_save");
    	leido = LevelSaver.readFromXML("./resources/maps/test_level_save");
    	assertEquals("Nivel 1",leido.getGameWorld().getLevel().getLevelName());
    	 leido = LevelSaver.readFromXML("./resources/maps/test_level_save");
    	assertEquals("Nivel 1",leido.getGameWorld().getLevel().getLevelName());
    }

    
    @Test 
    void changeLocalPuntuation() {
    	ArrayList<Integer> lista = new ArrayList<Integer>();
    	lista.add(77);
    	mundo.setLocalPuntuation(lista);
    	assertEquals(77, mundo.getLocalPuntuation().get(0));
    }
   
    @Test void moveEmptyCOnstructor() {
    	Move movimiento= new Move();
    	assertEquals(-1, movimiento.getNewX());
    	assertEquals(-1, movimiento.getNewY());
    	assertEquals(-1, movimiento.getOldX());
    	assertEquals(-1, movimiento.getOldY());
    	assertEquals(-1, movimiento.getOldBoxX());
    	assertEquals(-1, movimiento.getOldBoxY());
    	assertEquals(-1, movimiento.getNewBoxX());
    	assertEquals(-1, movimiento.getNewBoxY());

    }
    @Test
    void testMoveGetters() {
        
        Position oldPosition = new Position(1, 2);
        Position newPosition = new Position(3, 4);
        Position oldBoxPosition = new Position(5, 6);
        Position newBoxPosition = new Position(7, 8);

        
        Move move = new Move(oldPosition, newPosition, oldBoxPosition, newBoxPosition);

       
        assertEquals(1, move.getOldX());
        assertEquals(2, move.getOldY());
        assertEquals(3, move.getNewX());
        assertEquals(4, move.getNewY());
        assertEquals(5, move.getOldBoxX());
        assertEquals(6, move.getOldBoxY());
        assertEquals(7, move.getNewBoxX());
        assertEquals(8, move.getNewBoxY());
        assertEquals(true, move.isBoxMove());
    }









    
}
