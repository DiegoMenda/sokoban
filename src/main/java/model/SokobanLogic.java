package model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SokobanLogic {

	private GameWorld world;
	private Worker warehouseMan;
	private Level level;
	private Stack<Move> history;
	private static final Logger logger = LoggerFactory.getLogger(SokobanLogic.class);
	public SokobanLogic(GameWorld world) {
		this.world=world;
		this.level = world.getLevel();		
		this.warehouseMan = level.getWarehouseMan();
		history = new Stack<>();

	}
	

	/*
	 * Si la posicion dentro del mapa es valida.
	 */
	public void undoMove() {
		if(history.isEmpty()) {
			logger.info("No move to undo");
			return;
		}
		
		Move lastMove = history.pop();
		logger.info("Undoing move: {}", lastMove);
		

		warehouseMan.move(lastMove.getOldX(),lastMove.getOldY());
		level.setMobileEntities(lastMove.getOldX(), lastMove.getOldY(), warehouseMan);
		level.setMobileEntities(lastMove.getNewX(), lastMove.getNewY(), null);
		
		if (lastMove.isBoxMove()) {
            MobileEntity box = level.getMobileEntities(lastMove.getNewBoxX(), lastMove.getNewBoxY());
            box.move(lastMove.getOldBoxX(), lastMove.getOldBoxY());
            level.setMobileEntities(lastMove.getOldBoxX(), lastMove.getOldBoxY(), box);
            level.setMobileEntities(lastMove.getNewBoxX(), lastMove.getNewBoxY(), null);

            if(level.getImmovableEntities(lastMove.getOldBoxX(), lastMove.getOldBoxY()) instanceof Goal)  {
            	((Box) box).setBoxOnGoalTexture();
				Goal gol = (Goal) level.getImmovableEntities(lastMove.getOldBoxX(), lastMove.getOldBoxY());
				gol.setGoalArchieved(true);
			}
            else if(level.getImmovableEntities(lastMove.getNewBoxX(), lastMove.getNewBoxY()) instanceof Goal)  {
            	((Box) box).setNormalBoxTexture();
				Goal gol = (Goal) level.getImmovableEntities(lastMove.getNewBoxX(), lastMove.getNewBoxY());
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
					history.push(new Move(charX,charY,newX,newY,box.getX(),box.getY(),newX+dx,newY+dy));
					if(level.getImmovableEntities(newX+dx, newY+dy) instanceof Goal) {
						  ((Goal) level.getImmovableEntities(newX + dx, newY + dy)).setGoalArchieved(true);
						    logger.info("We reached the goal.");
						    ((Box)box).setBoxOnGoalTexture();
					}
					if(level.getImmovableEntities(newX, newY) instanceof Goal)  {
						((Box)box).setNormalBoxTexture();
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
				history.push(new Move(charX,charY,newX,newY));
				logger.info("the warehouse man moves from ({}, {}) to ({}, {})", charX, charY, newX, newY);
				warehouseMan.move(newX, newY);
				level.setMobileEntities(newX, newY, warehouseMan);
				level.setMobileEntities(charX, charY, null);
			}

			world.addPuntuation();
		}
		else {
			logger.info("the warehouse man can not move from ({}, {}) to ({}, {})", charX, charY, newX, newY);
		}
		

	}

	//	private void moveBox(int x, int y, int dx, int dy) {
	//        int newBoxX = x + dx;
	//        int newBoxY = y + dy;
	//
	//        if (isValidPosition(newBoxX, newBoxY)) {
	//        	if(level.getImmovableEntities(newBoxX, newBoxY) instanceof Goal) {
	//        		logger.info("GOOOOOL");
	//        		level.getImmovableEntities(newBoxX, newBoxY).setImage(TexturePaths.generateImage(TexturePaths.TEXTURE_BOX2));
	//        	}
	//        	logger.info("GOOOOOL");
	//        	MobileEntity origin = level.getMobileEntities(x, y);
	//        	MobileEntity destiny = level.getMobileEntities(newBoxX, newBoxY);
	//            level.setMobileEntities(newBoxX, newBoxY, destiny);
	//            level.setMobileEntities(x, y, destiny);
	//        }
	//    }

	private void nextLevel() {


	}
	
	private static class Move{
		private final int oldX, oldY;
		private final int newX, newY;
		private final boolean boxMove;
		private final int oldBoxX, oldBoxY, newBoxX, newBoxY;
		
		public Move(int oldX, int oldY, int newX, int newY) {
			this.oldX = oldX;
			this.oldY = oldY;
			this.newX = newX;
			this.newY = newY;
			this.boxMove = false;
			this.oldBoxX = this.oldBoxY = this.newBoxX = this.newBoxY = -1;
		}
		
		
		public Move(int oldX, int oldY, int newX, int newY, int oldBoxX, int oldBoxY, int newBoxX, int newBoxY) {
			this.oldX = oldX;
			this.oldY = oldY;
			this.newX = newX;
			this.newY = newY;
			this.boxMove = true;
			this.oldBoxX = oldBoxX;
			this.oldBoxY = oldBoxY;
			this.newBoxX = newBoxX;
			this.newBoxY = newBoxY;
		}


		public int getOldX() {
			return oldX;
		}
		public int getOldY() {
			return oldY;
		}
		public int getNewX() {
			return newX;
		}
		public int getNewY() {
			return newY;
		}
		public boolean isBoxMove() {
			return boxMove;
		}
		public int getOldBoxX() {
			return oldBoxX;
		}
		public int getOldBoxY() {
			return oldBoxY;
		}
		public int getNewBoxX() {
			return newBoxX;
		}
		public int getNewBoxY() {
			return newBoxY;
		}


		@Override
		public String toString() {
			return "Move [oldX=" + oldX + ", oldY=" + oldY + ", newX=" + newX + ", newY=" + newY + "]";
		}
		
		
	}


}