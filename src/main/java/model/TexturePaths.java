package model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TexturePaths {
	private static final Logger logger = LoggerFactory.getLogger(TexturePaths.class);
    public static final String TEXTURE_WALL = "./src/main/java/model/textures/wall.png";
    public static final String TEXTURE_AIR = "./src/main/java/model/textures/air.png";
    public static final String TEXTURE_WORKER = "./src/main/java/model/textures/worker.png";
    public static final String TEXTURE_GOAL = "./src/main/java/model/textures/goal.png";
    public static final String TEXTURE_BOX = "./src/main/java/model/textures/box.png";
    public static final String TEXTURE_BOX2 = "./src/main/java/model/textures/box-modified.png";

    
    
    private TexturePaths() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    
    public static Image generateImage(String path) {
    	
    	try {
    		logger.info("image generated correctly from this path {}", path);
			return ImageIO.read(new File(path));
			
		} catch (IOException e) {
			
			logger.error("can not generate image correctly from this path {}", path);
			return null;
		}
    	
    }
    
}