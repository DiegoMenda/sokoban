package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class GameWorld {
	
	private File levelFile;
	private int globalPuntuation;
	private ArrayList<Integer> localPuntuation;
	private Level level;
	
	public GameWorld(String levelRoute) {
		levelFile = new File(levelRoute); 
		localPuntuation = new ArrayList<Integer>();
		loadLevel(levelFile);
		globalPuntuation=0;
		//levelNumber = 0;
	}
	
	public GameWorld() {
		
	}

	private void loadLevel(File file) {
		Level nivel = LevelLoader.loadLevel(file);
		if(nivel != null) {
			//88
			this.level = nivel;
			LevelNumber.levelNumber++;
			localPuntuation.add(LevelNumber.levelNumber-1, 0);
			if(LevelNumber.levelNumber > 1) {
				globalPuntuation += localPuntuation.get(LevelNumber.levelNumber-2);
			}
			
		} else {
			
			System.out.println("\n\n\nGAME OVER BRO\n\n\n");
		}
	}
	@XmlElement
	public Level getLevel() {
		return this.level;
	}
	public void setLevel(Level level) {
		this.level=level;
	}
	@XmlElement
	public File getLevelFile() {
		return levelFile;
	}


	public void setLevelFile(File levelFile) {
		this.levelFile = levelFile;
	}

	@XmlElement
	public int getPuntuation() {
		return globalPuntuation;
	}

	public void setPuntuation(int puntuation) {
		this.globalPuntuation = puntuation;
	}

	@XmlElement
	public List<Integer> getLocalPuntuation() {
	    return localPuntuation;
	}

	public void setLocalPuntuation(List<Integer> localPuntuation) {
	    this.localPuntuation = (ArrayList<Integer>) localPuntuation;
	}

	public void addPuntuation() {
		localPuntuation.set(LevelNumber.levelNumber-1, localPuntuation.get(LevelNumber.levelNumber-1)+1);
	}
	
	public void subPuntuation() {
		localPuntuation.set(LevelNumber.levelNumber-1, localPuntuation.get(LevelNumber.levelNumber-1)-1);
	}
	
	public Level getNextLevel() {
	    String currentLevelName = level.getLevelName();
	    int currentLevelNumber = LevelNumber.levelNumber;
	    String nextLevelName = "./src/main/java/model/maps/level_" + (currentLevelNumber + 1) + ".txt";
	    File file = new File(nextLevelName);
	    loadLevel(file);
	    return level;
	}
	
//	public boolean isGameOver() {
//	    String currentLevelName = level.getLevelName();
//	    int currentLevelNumber = Integer.parseInt(currentLevelName.substring(6));
//	    return getNextLevel() == null;
//	}

	
	
	// devuelve false si hemos terminado
	public boolean loadNextLevel() {
	    Level nextLevel = getNextLevel();
	    if (nextLevel != null) {
	        level = nextLevel;
	        return false;
	    }
	    return true;
	}

    public void updateFrom(GameWorld other) {
        this.levelFile = (File)other.levelFile;
        this.globalPuntuation = other.globalPuntuation;
        this.localPuntuation = new ArrayList<Integer>(other.localPuntuation); // Deep copy
        
        level.updateFrom(other.level);
        
    }
}