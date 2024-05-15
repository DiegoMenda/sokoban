package model;

import java.io.File;
import java.util.ArrayList;

public class GameWorld {
	
	File levelFile;
	private int puntuation;
	private ArrayList local_puntuation;
	private Level lvl;
	
	
	public GameWorld(String levelRoute) {
		levelFile = new File(levelRoute+1); 
	}

	private Level loadLevel(File file) {
		return LevelLoader.loadLevel(file);
	}
		
	
	public boolean eventMoveEntity() {
		return false;
	}
	
	

}
