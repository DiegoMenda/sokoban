import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.GameWorld;
import model.Level;
import model.LevelLoader;
import model.SokobanLogic;

public class Test2 {

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
        logica.moveCharacter(1, 0);
        nivel =mundo.getLevel();
        assertEquals(1, nivel.getWarehouseMan().getY());
        assertEquals(4, nivel.getWarehouseMan().getX());
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
//    @Test
//    void cantMoveFromTO3() {
//        logica.moveCharacter(1, 1);
//        nivel =mundo.getLevel();
//        assertEquals(1, nivel.getWarehouseMan().getY());
//        assertEquals(1, nivel.getWarehouseMan().getX());
//    }
    @Test
    void invalidCharLevel() {
        levelFile = new File("./src/main/java/model/maps/test_level_wrong_char.txt");
        nivel = LevelLoader.loadLevel(levelFile);
        assertEquals(null, nivel);
    }
    @Test
    void moreThanOneWhareHouseManInALevel() {
        
        levelFile = new File("./src/main/java/model/maps/test_level_2_warehouseMan.txt");
        nivel = LevelLoader.loadLevel(levelFile);
        assertEquals(null, nivel);
     
    }
    @Test
    void noBoxesInALevel() {
        
        levelFile = new File("./src/main/java/model/maps/test_level_no_boxes.txt");
        nivel = LevelLoader.loadLevel(levelFile);
        assertEquals(null, nivel);
    }
    @Test
    void noGoalsInALevel() {
        
        levelFile = new File("./src/main/java/model/maps/test_level_no_goals.txt");
        nivel = LevelLoader.loadLevel(levelFile);
        assertEquals(null, nivel);
    }
    @Test
    void differentNUmberOfGoalsAndBoxes() {
        
        levelFile = new File("./src/main/java/model/maps/test_level_nboxes_!=_ngoals.txt");
        nivel = LevelLoader.loadLevel(levelFile);
        assertEquals(null, nivel);
    }
}