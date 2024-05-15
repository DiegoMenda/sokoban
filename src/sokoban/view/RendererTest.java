package view;

import javax.swing.SwingUtilities;
import java.io.File;
import model.*;

public class RendererTest {
    public static void main(String[] args) {
        LevelLoader levelLoader = new LevelLoader();
        File levelFile = new File("./src/sokoban/model/maps/test_level.txt");

        Level level = levelLoader.loadLevel(levelFile);
        String xd = level.toString();

        System.out.println(xd);
        Renderer rend = new Renderer(level.pos);
        SwingUtilities.invokeLater(() -> {
            GameFrame gameFrame = new GameFrame(rend);
            gameFrame.setVisible(true);
        });
    }
}