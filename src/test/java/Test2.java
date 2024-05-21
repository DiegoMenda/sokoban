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
        assertEquals(5, nivel.getRow());
    }

    @Test
    void readWareHouseManPositionX() {
        assertEquals(1, nivel.getWarehouseMan().getX());
    }

    @Test
    void readWareHouseManPositionY() {
    	System.out.println(nivel.getWarehouseMan().getY());
        assertEquals(3, nivel.getWarehouseMan().getY());
    }
    @Test
    void canMoveFromTO() {
    	logica.moveCharacter(1, 0);
    	System.out.println(nivel.getWarehouseMan().getX());
    	System.out.println(nivel.getWarehouseMan().getY());
    	nivel =mundo.getLevel();
        assertEquals(3, nivel.getWarehouseMan().getY());
        assertEquals(2, nivel.getWarehouseMan().getX());
    }
    
}
