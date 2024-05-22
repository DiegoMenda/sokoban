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
    private LevelLoader cargador;
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
        System.out.println(nivel.getWarehouseMan().getX());
        System.out.println(nivel.getWarehouseMan().getY());
        nivel =mundo.getLevel();
        assertEquals(1, nivel.getWarehouseMan().getY());
        assertEquals(2, nivel.getWarehouseMan().getX());
    }
    @Test
    void cantMoveFromTO() {
        logica.moveCharacter(-1, 0);
        System.out.println(nivel.getWarehouseMan().getX());
        System.out.println(nivel.getWarehouseMan().getY());
        nivel =mundo.getLevel();
        assertEquals(1, nivel.getWarehouseMan().getY());
        assertEquals(1, nivel.getWarehouseMan().getX());
    }
    @Test
    void invalidCharLevel() {
        mundo = new GameWorld("./src/main/java/model/maps/test_level_wrong_char.txt");
        assertEquals(null, mundo.getLevel());
    }
    @Test
    void moreThanOneWhareHouseManInALevel() {
        mundo = new GameWorld("./src/main/java/model/maps/test_level_2_warehouseMan.txt");
        assertEquals(null, mundo.getLevel());
    }
    @Test
    void noBoxesInALevel() {
        mundo = new GameWorld("./src/main/java/model/maps/test_level_no_boxes.txt");
        assertEquals(null, mundo.getLevel());
    }
    @Test
    void noGoalsInALevel() {
        mundo = new GameWorld("./src/main/java/model/maps/test_level_no_goals.txt");
        assertEquals(null, mundo.getLevel());
    }
    @Test
    void differentNUmberOfGoalsAndBoxes() {
        mundo = new GameWorld("./src/main/java/model/maps/test_level_nboxes_!=_ngoals.txt");
        assertEquals(null, mundo.getLevel());
    }
}