import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Image;
import java.io.File;
import java.util.stream.Stream;

import javax.xml.bind.JAXBException;

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
import model.SokobanLogic;
import model.TexturePaths;
import model.Wall;

class Test2 {

    private Level nivel;
    private static LevelLoader cargador;
    private File levelFile;
    private GameWorld mundo;
    private SokobanLogic logica;
    @BeforeEach
    void setUp() {
        cargador = new LevelLoader();
        levelFile = new File("./src/main/java/model/maps/test_level.txt");
        nivel = cargador.loadLevel(levelFile);
        mundo = new GameWorld("./src/main/java/model/maps/test_level.txt");
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
        assertEquals("+++++++\n"
        		+ "+...*.+\n"
        		+ "+.....+\n"
        		+ "+.....+\n"
        		+ "+++++++\n", nivel.toString());
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
            new File("./src/main/java/model/maps/test_level_wrong_char.txt"),
            new File("./src/main/java/model/maps/test_level_2_warehouseMan.txt"),
            new File("./src/main/java/model/maps/test_level_no_boxes.txt"),
            new File("./src/main/java/model/maps/test_level_no_goals.txt"),
            new File("./src/main/java/model/maps/test_level_nboxes_!=_ngoals.txt"),
            new File("./src/main/java/model/maps/test_level_no_level.txt"),
            new File("./src/main/java/model/maps/test_level_no_whman.txt")
        );
    }
    @Test
    void createWithInvalidTexture() {
      Image im=TexturePaths.generateImage("hello");
        assertEquals(null, im);
    }

    @Test
   
    void undo() {
        logica.moveCharacter(0, -1);
        nivel =mundo.getLevel();
      nivel.undoMove();
        assertTrue(true);
        assertEquals(1, nivel.getWarehouseMan().getY());
        assertEquals(1, nivel.getWarehouseMan().getX());
    }
    
    @Test
    void saveLevel() throws JAXBException {
    	logica.moveCharacter(0, 1);
    	assertEquals(1, logica.getPuntuation());
    	GameWorldWithHistory mundohistoria = new GameWorldWithHistory(mundo, logica.getHistory());
    	
    	LevelSaver.saveToXML(mundohistoria,"./src/main/java/model/maps/test_level_save");
    	GameWorldWithHistory  leido = LevelSaver.readFromXML("./src/main/java/model/maps/test_level_save");
    	assertEquals("Nivel 1",leido.getGameWorld().getLevel().getLevelName());
    	
    }
    	
    
    
}
