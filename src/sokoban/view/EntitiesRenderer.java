package view;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;
import model.*;

public class EntitiesRenderer extends JPanel {


    private MobileEntity[][] mobileEntity;
    private ImmovableEntity[][] immovableEntity;
    public EntitiesRenderer(MobileEntity[][] mobileEntity, ImmovableEntity[][] immovableEntity) {
        this.mobileEntity = mobileEntity;
        this.immovableEntity = immovableEntity;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Entity[] entityX : immovableEntity) {
        	for(Entity entityY : entityX) {
            if (entityY != null) {
                entityY.draw(g);
            }
        	}
        }
        
        for (Entity[] entityX : mobileEntity) {
        	for(Entity entityY : entityX) {
            if (entityY != null) {
                entityY.draw(g);
            }
        	}
        }
    }
}