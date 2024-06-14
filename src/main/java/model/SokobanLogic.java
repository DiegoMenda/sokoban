package model;


import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SokobanLogic {

	private GameWorld world;
	//private Worker warehouseMan;
	//private Level level;  --> world.getLevel()
	private List<Move> history;
	private static final Logger logger = LoggerFactory.getLogger(SokobanLogic.class);
	public SokobanLogic(GameWorld world) {
		this.world=world;
		//this.level = world.getLevel();		
		//this.warehouseMan = world.getLevel().getWarehouseMan();
		setHistory(new LinkedList<>());
	}
	

	/*
	 * Si la posicion dentro del mapa es valida.
	 */
	public void undoMove() {
		if(getHistory().isEmpty()) {
			logger.info("No move to undo");
			return;
		}
		
		Move lastMove = getHistory().remove(getHistory().size()-1);
		
		logger.info("Undoing move: {}", lastMove);
		
		world.getLevel().getWarehouseMan().move(lastMove.getOldX(),lastMove.getOldY());
		world.getLevel().setMobileEntities(lastMove.getOldX(), lastMove.getOldY(), world.getLevel().getWarehouseMan());
		world.getLevel().setMobileEntities(lastMove.getNewX(), lastMove.getNewY(), null);
		
		if (lastMove.isBoxMove()) {
            MobileEntity box = world.getLevel().getMobileEntities(lastMove.getNewBoxX(), lastMove.getNewBoxY());
            box.move(lastMove.getOldBoxX(), lastMove.getOldBoxY());
            world.getLevel().setMobileEntities(lastMove.getOldBoxX(), lastMove.getOldBoxY(), box);
            world.getLevel().setMobileEntities(lastMove.getNewBoxX(), lastMove.getNewBoxY(), null);

            if(world.getLevel().getImmovableEntities(lastMove.getOldBoxX(), lastMove.getOldBoxY()) instanceof Goal)  {
            	((Box) box).setBoxOnGoalTexture();
				Goal gol = (Goal) world.getLevel().getImmovableEntities(lastMove.getOldBoxX(), lastMove.getOldBoxY());
				gol.setGoalArchieved(true);
			}
            else if(world.getLevel().getImmovableEntities(lastMove.getNewBoxX(), lastMove.getNewBoxY()) instanceof Goal)  {
            	((Box) box).setNormalBoxTexture();
				Goal gol = (Goal) world.getLevel().getImmovableEntities(lastMove.getNewBoxX(), lastMove.getNewBoxY());
				gol.setGoalArchieved(false);
			}
            else {
            	((Box) box).setNormalBoxTexture();
            }
           
            
        }
		world.subPuntuation();
	}
	
	private boolean isValidPosition(int x, int y) {

		return (x >= 0 && y >= 0 && 
				x < world.getLevel().getCol()-1 && y < world.getLevel().getRow()-1 && 
				world.getLevel().getImmovableEntities(x, y).getPenetrable() );
	}



	public void moveCharacter(int dx, int dy) {


		
		int charX = world.getLevel().getWarehouseMan().getX();
		int charY = world.getLevel().getWarehouseMan().getY();
		int newX = world.getLevel().getWarehouseMan().getX() + dx;
		int newY = world.getLevel().getWarehouseMan().getY() + dy;

		if (isValidPosition(newX, newY) && (dx != 0 || dy != 0)) {

			if(world.getLevel().getMobileEntities(newX,newY) instanceof Box) { // hay una caja en medio

				if(isValidPosition( newX+dx, newY+dy)  && world.getLevel().getMobileEntities(newX+dx, newY+dy) == null   ) { //en la posicion hay una caja que empujar
					// mover la caja
					logger.info("the warehouse man moves the box from ({}, {}) to ({}, {})", newX, newY, newX+dx, newY+dy);
					MobileEntity box = world.getLevel().getMobileEntities(newX, newY);
					Position oldPosition = new Position(charX, charY);
					Position newPosition = new Position(newX, newY);
					Position oldBoxPosition = new Position(box.getX(), box.getY());
					Position newBoxPosition = new Position(newX + dx, newY + dy);

					getHistory().add(new Move(oldPosition, newPosition, oldBoxPosition, newBoxPosition));
					if(world.getLevel().getImmovableEntities(newX+dx, newY+dy) instanceof Goal) {
						  ((Goal) world.getLevel().getImmovableEntities(newX + dx, newY + dy)).setGoalArchieved(true);
						    logger.info("We reached the goal.");
						    ((Box)box).setBoxOnGoalTexture();
					}
					if(world.getLevel().getImmovableEntities(newX, newY) instanceof Goal)  {
						((Box)box).setNormalBoxTexture();
						Goal gol = (Goal) world.getLevel().getImmovableEntities(newX, newY);
						gol.setGoalArchieved(false);
					}
					box.move(newX+dx, newY+dy);
					world.getLevel().setMobileEntities(newX+dx, newY+dy, box);

					logger.info("the warehouse man moves from ({}, {}) to ({}, {})", charX, charY, newX, newY);
					// mover el personaje
					world.getLevel().getWarehouseMan().move(newX, newY);
					world.getLevel().setMobileEntities(newX, newY, world.getLevel().getWarehouseMan());
					world.getLevel().setMobileEntities(charX, charY, null);
					world.addPuntuation();
					logger.info("la puntuacion nueva es {}", world.getPuntuation());
				}
			}
			else { // nueva posicion libre de cajas
				// mover el personaje
				Position oldPosition = new Position(charX, charY);
				Position newPosition = new Position(newX, newY);

				getHistory().add(new Move(oldPosition, newPosition));

				logger.info("the warehouse man moves from ({}, {}) to ({}, {})", charX, charY, newX, newY);
				world.getLevel().getWarehouseMan().move(newX, newY);
				world.getLevel().setMobileEntities(newX, newY, world.getLevel().getWarehouseMan());
				world.getLevel().setMobileEntities(charX, charY, null);
				world.addPuntuation();
				logger.info("la puntuacion nueva es {}", world.getPuntuation());
			}

			
		}
		else {
			logger.info("the warehouse man can not move from ({}, {}) to ({}, {})", charX, charY, newX, newY);
		}
		

	}
	
	
	
	private void nextLevel() {
		world.loadNextLevel();
	}
	
	
	//GETTER DE LA PILA (BORRAR SI A ISAM NO LE PARECE BIEN
	public List<Move>  getHistory() {
	    return history;
	}
	
	
	
	
	public boolean isLevelCompleted() {
	    for (int y = 0; y < world.getLevel().getRow(); y++) {
	        for (int x = 0; x < world.getLevel().getCol(); x++) {
	            if (world.getLevel().getImmovableEntities(x, y) instanceof Goal) {
	                Goal goal = (Goal) world.getLevel().getImmovableEntities(x, y);
	                if (!goal.isGoalArchieved()) {
	                    return false;
	                }
	            }
	        }
	    }
	    return true;
	}
		

	
public int getPuntuation() {
	return world.getPuntuation();
}


public void setHistory(List<Move> history) {
	this.history = history;
}

public void clearHistory() {
	history = new LinkedList<>();
}





}