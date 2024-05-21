package model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TexturePaths {
	public static String def = "./src/sokoban/model/textures/box.png";
    public static final String TEXTURE_WALL = "./src/sokoban/model/textures/wall.png";
    public static final String TEXTURE_AIR = "./src/sokoban/model/textures/air.png";
    public static final String TEXTURE_WORKER = "./src/sokoban/model/textures/worker.png";
    public static final String TEXTURE_GOAL = "./src/sokoban/model/textures/goal.png";
    public static final String TEXTURE_BOX = "./src/sokoban/model/textures/box.png";
    public static final String TEXTURE_BOX2 = "./src/sokoban/model/textures/box-modified.png";

    
    
    
    
    public static Image generateImage(String path) {
    	
    	try {
			return ImageIO.read(new File(path));
			
		} catch (IOException e) {
			//e.printStackTrace();
			System.err.println("Not loaderd --> " + path  + " Sys dir actually is --> " + System.getProperty("user.dir"));
			return null;
		}
    	
    }
    
}