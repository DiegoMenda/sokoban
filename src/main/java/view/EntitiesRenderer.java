package view;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;
import model.*;

public class EntitiesRenderer extends JPanel {

	private int TILE_SIZE = 36;
	GameWorld world;
    public EntitiesRenderer(GameWorld world) {

    	this.world = world;
    }
    


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawEntities(g, world.getLevel().getImmovableEntities());
        drawEntities(g, world.getLevel().getMobileEntities());
    }

    private void drawEntities(Graphics g, Entity[][] entities) {
        for (Entity[] entityRow : entities) {
            for (Entity entity : entityRow) {
                if (entity != null) {
                    entity.draw(g);
                }
            }
        }
    }
    
    // TODO: mirar si se puede hacer el sistema de forma más sofisticada.
    @Override
    public Dimension getPreferredSize() {
        int width = 
        		world.getLevel().getMobileEntities()[0].length * TILE_SIZE;

        int height = 
        		world.getLevel().getMobileEntities().length * TILE_SIZE;
        
        return new Dimension(width, height);
    }
    
}