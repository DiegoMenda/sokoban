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
	

	public void generalLogic(){
		
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
		
		if (lastMove.isBoxMove()) {
            MobileEntity box = level.getMobileEntities(lastMove.getNewBoxX(), lastMove.getNewBoxY());
            box.move(lastMove.getOldBoxX(), lastMove.getOldBoxY());
            level.setMobileEntities(lastMove.getOldBoxX(), lastMove.getOldBoxY(), box);
            level.setMobileEntities(lastMove.getOldBoxX(), lastMove.getOldBoxY(), null);

        }
		warehouseMan.move(lastMove.getOldX(),lastMove.getOldY());
		level.setMobileEntities(lastMove.getOldX(), lastMove.getOldY(), warehouseMan);
		level.setMobileEntities(lastMove.getNewX(), lastMove.getNewY(), null);
	}
	
	private boolean isValidPosition(int x, int y) {

		return (x >= 0 && y >= 0 && 
				x < world.getLevel().getCol()-1 && y < world.getLevel().getRow()-1 && 
				level.getImmovableEntities(x, y).getPenetrable() );
	}
	/*
	 * 
	 * Comprueba si la posicion esta ocupada. De estarlo, devuelve quien la ocupa, de lo contrario, devuelve null.
	 * 
	 */
	//	 private MobileEntity isPositionOccupied(int x, int y) {
	//		 
	//		 if(!isValidPosition(x,y)) {
	//             System.err.println("La posicion suministrada ("+x+", " + y +") se encuentra fuera de la zona de desplazamiento valida");
	//             return null;
	//		 }
	//		 
	//		 return level.getMobileEntities(x, y);
	//	 }



	private boolean canMoveCharacterTo(int x_to, int y_to, int x_from, int y_from) {
		//canMoveTo(x_to + (x_to-x_from), y_to + (y_to-y_from)) 

		if ( isValidPosition(x_to, y_to) ) {
			logger.info("canMoveCharacterTo: the character is able to move from ({}, {}) to ({}, {}) ", x_from, y_from, x_to, y_to);
			return true;
		}
		else if( level.getMobileEntities(x_to, y_to) instanceof Box ) {


			if(isValidPosition(  x_to + (x_to-x_from), y_to + (y_to-y_from)  )) {
				logger.info("canMoveCharacterTo: the character is able to move from ({}, {}) to ({}, {}) ", x_from, y_from, x_to, y_to);
				return true;
			}
			else {
				logger.info("canMoveCharacterTo: the character is NOT able to move from ({}, {}) to ({}, {}) ", x_from, y_from, x_to, y_to);
				return false;
			}
		}
		else {
			logger.info("canMoveCharacterTo: the character is NOT able to move from ({}, {}) to ({}, {}) ", x_from, y_from, x_to, y_to);
			return false;
		}
	}


	private boolean checkGoal() {
		return false;
	}



	public void moveCharacter(int dx, int dy) {



		int charX = warehouseMan.getX();
		int charY = warehouseMan.getY();
		int newX = warehouseMan.getX() + dx;
		int newY = warehouseMan.getY() + dy;

		if(dx == 0 && dy == 0) {


		}
		else if (isValidPosition(newX, newY)) {

			if(level.getMobileEntities(newX,newY) != null && level.getMobileEntities(newX,newY) instanceof Box) { // hay una caja en medio

				if(isValidPosition( newX+dx, newY+dy)  && level.getMobileEntities(newX+dx, newY+dy) == null   ) { //en la posicion hay una caja que empujar
					// mover la caja
					logger.info("the warehouse man moves the box from ({}, {}) to ({}, {})", newX, newY, newX+dx, newY+dy);
					MobileEntity box = level.getMobileEntities(newX, newY);
					history.push(new Move(charX,charY,newX,newY,box.getX(),box.getY(),newX+dx,newY+dy));
					if(level.getImmovableEntities(newX+dx, newY+dy) instanceof Goal) {
						Goal gol = (Goal) level.getImmovableEntities(newX+dx, newY+dy);
						gol.setGoalArchieved(true);
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
				history.push(new Move(charX,charY,newX,newY));
				logger.info("the warehouse man moves from ({}, {}) to ({}, {})", charX, charY, newX, newY);
				warehouseMan.move(newX, newY);
				level.setMobileEntities(newX, newY, warehouseMan);
				level.setMobileEntities(charX, charY, null);
			}


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