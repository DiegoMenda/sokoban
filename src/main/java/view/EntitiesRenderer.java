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
    
    public void setMobileEntity(MobileEntity[][] mobileEntity) {
        this.mobileEntity = mobileEntity;


    }

    public void setImmovableEntity(ImmovableEntity[][] immovableEntity) {
        this.immovableEntity = immovableEntity;
    }
    

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawEntities(g, immovableEntity);
        drawEntities(g, mobileEntity);
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
            mobileEntity[0].length * TILE_SIZE;

        int height = 
            mobileEntity.length * TILE_SIZE;
        
        return new Dimension(width, height);
    }
    
}