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
    private MobileEntity[][] mobileEntity;
    private ImmovableEntity[][] immovableEntity;
    public EntitiesRenderer(MobileEntity[][] mobileEntity, ImmovableEntity[][] immovableEntity) {
        this.mobileEntity = mobileEntity;
        this.immovableEntity = immovableEntity;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Entity[] entityY : immovableEntity) {
        	for(Entity entityX : entityY) {
            if (entityX != null) {
                entityX.draw(g);
            }
        	}
        }
        
        for (Entity[] entityY : mobileEntity) {
        	for(Entity entityX : entityY) {
            if (entityX != null) {
                entityX.draw(g);
            }
        	}
        }
    }
    
    // TODO: mirar si se puede hacer el sistema de forma más sofisticada.
    @Override
    public Dimension getPreferredSize() {
        int width = 
            mobileEntity[0].length * TILE_SIZE;

        int height = 
            mobileEntity.length * TILE_SIZE;
        
        return new Dimension(width, height);
    }
    
}