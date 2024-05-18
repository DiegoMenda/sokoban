package model;

import java.io.File;
import java.util.ArrayList;

public class GameWorld {
	
	File levelFile;
	private int puntuation;
	private ArrayList localPuntuation;
	private Level level;
	
	
	public GameWorld(String levelRoute) {
		levelFile = new File(levelRoute); 
		loadLevel(levelFile);
	}

	private void loadLevel(File file) {
		this.level = LevelLoader.loadLevel(file);
	}
		
	
	public boolean eventMoveEntity() {
		return false;
	}
	
	public Level getLevel() {
		
		return this.level;
	}
	

	
	

}
