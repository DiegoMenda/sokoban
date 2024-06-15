package model;



import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SokobanLogic {

	private GameWorld world;
	
	
	private List<Move> history;
	private static final Logger logger = LoggerFactory.getLogger(SokobanLogic.class);
	public SokobanLogic(GameWorld world) {
		this.world=world;
		
		history= new LinkedList<>();
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
	    int newX = charX + dx;
	    int newY = charY + dy;

	    if (!isValidPosition(newX, newY) || (dx == 0 && dy == 0)) {
	        logger.info("the warehouse man cannot move from ({}, {}) to ({}, {})", charX, charY, newX, newY);
	        return;
	    }

	    if (world.getLevel().getMobileEntities(newX, newY) instanceof Box) {
	        moveBoxAndCharacter(charX, charY, newX, newY, dx, dy);
	    } else {
	        moveCharacterOnly(charX, charY, newX, newY);
	    }
	}

	private void moveBoxAndCharacter(int charX, int charY, int newX, int newY, int dx, int dy) {
	    if (canMoveBox(newX, newY, dx, dy)) {
	        MobileEntity box = world.getLevel().getMobileEntities(newX, newY);
	        Position oldPosition = new Position(charX, charY);
	        Position newPosition = new Position(newX, newY);
	        Position oldBoxPosition = new Position(box.getX(), box.getY());
	        Position newBoxPosition = new Position(newX + dx, newY + dy);

	        getHistory().add(new Move(oldPosition, newPosition, oldBoxPosition, newBoxPosition));
	        updateBoxState(box, newX + dx, newY + dy);
	        moveEntity(box, newX + dx, newY + dy);
	        moveCharacterToNewPosition(charX, charY, newX, newY);
	    }
	}

	private boolean canMoveBox(int newX, int newY, int dx, int dy) {
	    return isValidPosition(newX + dx, newY + dy) && world.getLevel().getMobileEntities(newX + dx, newY + dy) == null;
	}

	private void updateBoxState(MobileEntity box, int boxNewX, int boxNewY) {
	    if (world.getLevel().getImmovableEntities(boxNewX, boxNewY) instanceof Goal) {
	        ((Goal) world.getLevel().getImmovableEntities(boxNewX, boxNewY)).setGoalArchieved(true);
	        logger.info("We reached the goal.");
	        ((Box) box).setBoxOnGoalTexture();
	    }
	    if (world.getLevel().getImmovableEntities(box.getX(), box.getY()) instanceof Goal) {
	        ((Box) box).setNormalBoxTexture();
	        ((Goal) world.getLevel().getImmovableEntities(box.getX(), box.getY())).setGoalArchieved(false);
	    }
	}

	private void moveCharacterOnly(int charX, int charY, int newX, int newY) {
	    Position oldPosition = new Position(charX, charY);
	    Position newPosition = new Position(newX, newY);
	    getHistory().add(new Move(oldPosition, newPosition));

	    moveCharacterToNewPosition(charX, charY, newX, newY);
	}

	private void moveCharacterToNewPosition(int charX, int charY, int newX, int newY) {
	    logger.info("the warehouse man moves from ({}, {}) to ({}, {})", charX, charY, newX, newY);
	    moveEntity(world.getLevel().getWarehouseMan(), newX, newY);
	    world.getLevel().setMobileEntities(newX, newY, world.getLevel().getWarehouseMan());
	    world.getLevel().setMobileEntities(charX, charY, null);
	    world.addPuntuation();
	    logger.info("la puntuacion nueva es {}", world.getPuntuation());
	}

	private void moveEntity(MobileEntity entity, int x, int y) {
	    entity.move(x, y);
	    world.getLevel().setMobileEntities(x, y, entity);
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