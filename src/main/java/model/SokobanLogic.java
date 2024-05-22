package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SokobanLogic {

	private GameWorld world;
	private Worker warehouseMan;
	private Level level;
	private static final Logger logger = LoggerFactory.getLogger(SokobanLogic.class);
	public SokobanLogic(GameWorld world) {
		this.world=world;
		this.level = world.getLevel();		
		this.warehouseMan = level.getWarehouseMan();

	}


	/*
	 * Si la posicion dentro del mapa es valida.
	 */
	private boolean isValidPosition(int x, int y) {

		return (x >= 0 && y >= 0 && 
				x < world.getLevel().getCol()-1 && y < world.getLevel().getRow()-1 && 
				level.getImmovableEntities(x, y).getPenetrable() );
	}



	public void moveCharacter(int dx, int dy) {



		int charX = warehouseMan.getX();
		int charY = warehouseMan.getY();
		int newX = warehouseMan.getX() + dx;
		int newY = warehouseMan.getY() + dy;

	


		
		if (isValidPosition(newX, newY) && (dx != 0 || dy != 0)) {

			if(level.getMobileEntities(newX,newY) instanceof Box) { // hay una caja en medio

				if(isValidPosition( newX+dx, newY+dy)  && level.getMobileEntities(newX+dx, newY+dy) == null   ) { //en la posicion hay una caja que empujar
					// mover la caja
					logger.info("the warehouse man moves the box from ({}, {}) to ({}, {})", newX, newY, newX+dx, newY+dy);
					MobileEntity box = level.getMobileEntities(newX, newY);
					if(level.getImmovableEntities(newX+dx, newY+dy) instanceof Goal) {
						  ((Goal) level.getImmovableEntities(newX + dx, newY + dy)).setGoalArchieved(true);
						    logger.info("GOOOOOL");
						    box.setImage(TexturePaths.generateImage(TexturePaths.TEXTURE_BOX2));

					}
					if(level.getImmovableEntities(newX, newY) instanceof Goal)  {
						box.setImage(TexturePaths.generateImage(TexturePaths.TEXTURE_BOX));
						Goal gol = (Goal) level.getImmovableEntities(newX, newY);
						gol.setGoalArchieved(false);
					}
					box.move(newX+dx, newY+dy);
					level.setMobileEntities(newX+dx, newY+dy, box);

					logger.info("the warehouse man moves from ({}, {}) to ({}, {})", charX, charY, newX, newY);
					// mover el personaje
					warehouseMan.move(newX, newY);
					level.setMobileEntities(newX, newY, warehouseMan);
					level.setMobileEntities(charX, charY, null);

				}
			}
			else { // nueva posicion libre de cajas
				// mover el personaje
				logger.info("the warehouse man moves from ({}, {}) to ({}, {})", charX, charY, newX, newY);
				warehouseMan.move(newX, newY);
				level.setMobileEntities(newX, newY, warehouseMan);
				level.setMobileEntities(charX, charY, null);
			}

		
		}


	}

	




}