package view;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;
import model.*;

public class EntitiesRenderer extends JPanel {


    private Entity[][] entities;
    public EntitiesRenderer(Entity[][] entities) {
        this.entities = entities;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Entity[] entity1 : entities) {
        	for(Entity entity2 : entity1) {
            if (entity2 != null) {
                entity2.draw(g);
            }
        	}
        }
    }
}