package view;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import model.*;

public class EntitiesRenderer extends JPanel {

	private int titleSize = 36;
	private transient GameWorld world;
    public EntitiesRenderer(GameWorld world) {

    	this.world = world;
    }
    

    @Override
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
    
    
    @Override
    public Dimension getPreferredSize() {
        int width = 
        		world.getLevel().getMobileEntities()[0].length * titleSize;

        int height = 
        		world.getLevel().getMobileEntities().length * titleSize;
        
        return new Dimension(width, height);
    }
    
}