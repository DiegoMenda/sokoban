package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class GameWorld {
	
	private File levelFile;
	private int puntuation;
	private ArrayList<Integer> localPuntuation;
	private Level level;
	
	public GameWorld(String levelRoute) {
		levelFile = new File(levelRoute); 
		loadLevel(levelFile);
		puntuation=0;
	}
	
	public GameWorld() {
	}

	private void loadLevel(File file) {
		this.level = LevelLoader.loadLevel(file);
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
		return puntuation;
	}

	public void setPuntuation(int puntuation) {
		this.puntuation = puntuation;
	}

	@XmlElement
	public List<Integer> getLocalPuntuation() {
	    return localPuntuation;
	}

	public void setLocalPuntuation(List<Integer> localPuntuation) {
	    this.localPuntuation = (ArrayList<Integer>) localPuntuation;
	}

	public void addPuntuation() {
		puntuation++;
	}
	
	public void subPuntuation() {
		puntuation--;
	}
}
