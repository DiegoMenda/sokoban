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
	private int levelNumber;
	public GameWorld(String levelRoute) {
		levelFile = new File(levelRoute); 
		localPuntuation = new ArrayList<Integer>();
		setLevelNumber(0);
		loadLevel(levelFile);
		globalPuntuation=0;
	}
	
	public GameWorld() {
		
	}

	private void loadLevel(File file) {
		Level nivel = LevelLoader.loadLevel(file);
		if(nivel != null) {
			//REMOVE
			this.level = nivel;
			setLevelNumberMas();
			localPuntuation.add(getLevelNumber()-1, 0);
			if(getLevelNumber() > 1) {
				globalPuntuation += localPuntuation.get(getLevelNumber()-2);
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
		localPuntuation.set(getLevelNumber()-1, localPuntuation.get(getLevelNumber()-1)+1);
	}
	
	public void subPuntuation() {
		localPuntuation.set(getLevelNumber()-1, localPuntuation.get(getLevelNumber()-1)-1);
	}
	
	public Level getNextLevel() {
	    String currentLevelName = level.getLevelName();
	    int currentLevelNumber = getLevelNumber();
	    String nextLevelName = "./src/main/java/model/maps/level_" + (currentLevelNumber + 1) + ".txt";
	    File file = new File(nextLevelName);
	    loadLevel(file);
	    return level;
	}
	

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
    	setLevelNumber(other.getLevelNumber());
        this.levelFile = (File)other.levelFile;
        this.globalPuntuation = other.globalPuntuation;
        this.localPuntuation = new ArrayList<Integer>(other.localPuntuation); // Deep copy
        
        level.updateFrom(other.level);
        
    }
    
    public int getLevelNumber() {
    	
    	return levelNumber;
    }
    
   public void setLevelNumber(int numero) {
    	levelNumber = numero;
    }
    
    public void setLevelNumberMas() {
    	
    	 this.levelNumber++;
    }
}