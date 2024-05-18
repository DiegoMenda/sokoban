package view;

import javax.swing.SwingUtilities;
import java.io.File;
import model.*;

public class RendererTest {
    public static void main(String[] args) {
        
    	GameWorld mundo = new GameWorld("./src/sokoban/model/maps/test_level.txt");
    	LevelLoader levelLoader = new LevelLoader();
        //File levelFile = new File("./src/sokoban/model/maps/test_level.txt");
        
    	SokobanLogic logica = new SokobanLogic(mundo);
        String xd = mundo.getLevel().toString();

        System.out.println(xd);
        
        
        EntitiesRenderer rend = new EntitiesRenderer(mundo.getLevel().getImmovableEntities());
        GameFrame gameFrame = new GameFrame(rend);
        SwingUtilities.invokeLater(() -> {
            
            gameFrame.setVisible(true);
          
            
        });
        
        int cont = 0;
        while(cont <= 1) {
        	try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	gameFrame.renderFrame();
        	logica.moveCharacter(0, -1);
        }
        
        
   
        
    }
}