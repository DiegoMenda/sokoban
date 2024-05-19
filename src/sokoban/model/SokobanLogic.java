package model;

public class SokobanLogic {

	private GameWorld world;
	private Worker warehouseMan;
	private Level level;
	
	public SokobanLogic(GameWorld world) {
		this.world=world;
		this.level = world.getLevel();		
		this.warehouseMan = level.getWarehouseMan();
	
	}
	
	
	public void generalLogic(){
		
		
	}
		/*
		 * Si la posicion dentro del mapa es valida.
		 */
	 private boolean isValidPosition(int x, int y) {

		 return (x >= 0 && y >= 0 && 
				 x < world.getLevel().getRow()-1 && y < world.getLevel().getRow()-1 && 
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
			 
			 return true;
		 }
		 else if( level.getMobileEntities(x_to, y_to) instanceof Box ) {
			 
			 return isValidPosition(  x_to + (x_to-x_from), y_to + (y_to-y_from)  );
		 }
		 else return false;
	   }
	 
	
	private boolean checkGoal() {
		return false;
	}
	


	public void moveCharacter(int dx, int dy) {
		
		
		
		int charX = warehouseMan.getX();
		int charY = warehouseMan.getY();
        int newX = warehouseMan.getX() + dx;
        int newY = warehouseMan.getY() + dy;

//        if (canMoveCharacterTo(newX, newY, warehouseMan.getX(), warehouseMan.getY())) {
//            if (level.getMobileEntities(newX, newY) instanceof Box) {
//                moveBox(newX, newY, dx, dy);
//            }
//            
//            (warehouseMan.getX(), warehouseMan.getY(), world.getEntity(newX, newY) );
//            
//            warehouseMan.setX(newX);
//            warehouseMan.setY(newY);
//            level.setMobileEntities(newX, newY, warehouseMan);
//        }
		if(dx == 0 && dy == 0) {
					
					
		}
		else if (isValidPosition(newX, newY)) {
        	
        	if(level.getMobileEntities(newX,newY) != null && level.getMobileEntities(newX,newY) instanceof Box) { // hay una caja en medio
        		
        		if(isValidPosition( newX+dx, newY+dy)  && level.getMobileEntities(newX+dx, newY+dy) == null   ) { //en la posicion hay una caja que empujar
        			// mover la caja
        			MobileEntity box = level.getMobileEntities(newX, newY);
        			box.move(newX+dx, newY+dy);
        			level.setMobileEntities(newX+dx, newY+dy, box);
        			
 
        			// mover el personaje
        			warehouseMan.move(newX, newY);
        			level.setMobileEntities(newX, newY, warehouseMan);
        			level.setMobileEntities(charX, charY, null);
        			
        		}
        	}
        	else { // nueva posicion libre de cajas
        		// mover el personaje
    			warehouseMan.move(newX, newY);
    			level.setMobileEntities(newX, newY, warehouseMan);
    			level.setMobileEntities(charX, charY, null);
        	}
        	
        	
        }
        
        
    }
	
	private void moveBox(int x, int y, int dx, int dy) {
        int newBoxX = x + dx;
        int newBoxY = y + dy;

        if (isValidPosition(newBoxX, newBoxY)) {
        	MobileEntity origin = level.getMobileEntities(x, y);
        	MobileEntity destiny = level.getMobileEntities(newBoxX, newBoxY);
            level.setMobileEntities(newBoxX, newBoxY, destiny);
            level.setMobileEntities(x, y, destiny);
        }
    }
	
	private void nextLevel() {
		
		
	}
	
	
	
	
}