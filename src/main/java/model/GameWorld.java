package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class GameWorld {
	
	private File levelFile;
	private int globalPuntuation;
	private ArrayList<Integer> localPuntuation;
	private Level level;
	private int levelNumber;
	private static final Logger logger = LoggerFactory.getLogger(GameWorld.class);
	public GameWorld(String levelRoute) {
		levelFile = new File(levelRoute); 
		localPuntuation = new ArrayList<>(1);
		levelNumber=0;
		loadLevelFromFile(levelFile);
		globalPuntuation=0;
	}
	
	
	public GameWorld() {}


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
		localPuntuation.set(getLevelNumber()-1, localPuntuation.get(getLevelNumber()-1)+1);
	}
	
	public void subPuntuation() {
		localPuntuation.set(getLevelNumber()-1, localPuntuation.get(getLevelNumber()-1)-1);
	}
	
	private Level loadLevelFromFile(File file) {
		Level nivel = LevelLoader.loadLevel(file);
		if(nivel != null) {
			
			this.level = nivel;
			incLevelNumber();
			localPuntuation.add(getLevelNumber()-1, 0);
			if(getLevelNumber() > 1) {
				globalPuntuation += localPuntuation.get(getLevelNumber()-2);
			}
			
		} else {
			localPuntuation.add(getLevelNumber(), 0);
			logger.info("There is an error loading the level {}", file.getAbsolutePath());
		}
		return nivel;
	}
	

	// returns false if the level is not correct.
	public boolean loadNextLevel() {
		int currentLevelNumber = getLevelNumber();
		Level nextLevel = null;
		String nextLevelName = "./src/main/java/model/maps/level_" + (currentLevelNumber + 1) + ".txt";
		File file = new File(nextLevelName);
		if(file.exists()) {
			nextLevel = loadLevelFromFile(file);
		}
		else {
			logger.error("Tried to load the next level from {}, but the file does not exist.", file.getAbsolutePath());
			return false; // we reached the end.
		}
	
	    if (nextLevel != null) { // correct level
	        level = nextLevel;
	        return true;
	    } else { //incorrect
	    	incLevelNumber();
	    	return loadNextLevel();
	    	}
	}

    public void updateFrom(GameWorld other) {
    	setLevelNumber(other.getLevelNumber());
        this.levelFile = other.levelFile;
        this.globalPuntuation = other.globalPuntuation;
        this.localPuntuation = new ArrayList<>(other.localPuntuation); // Deep copy
        
        level.updateFrom(other.level);
        
    }
    
    public int getLevelNumber() {
    	return levelNumber;
    }
    
   public void setLevelNumber(int numero) {
    	levelNumber = numero;
    }
    
    public void incLevelNumber() {
    	
    	 this.levelNumber++;
    }
    
	public int getActualLevelPuntuation() {
		return localPuntuation.get(levelNumber-1);
	}
    
}