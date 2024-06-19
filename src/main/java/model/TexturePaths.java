package model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TexturePaths {
	private static final Logger logger = LoggerFactory.getLogger(TexturePaths.class);
    public static final String TEXTURE_WALL = "./resources/textures/wall.png";
    public static final String TEXTURE_AIR = "./resources/textures/air.png";
    public static final String TEXTURE_WORKER = "./resources/textures/worker.png";
    public static final String TEXTURE_GOAL = "./resources/textures/goal.png";
    public static final String TEXTURE_BOX = "./resources/textures/box.png";
    public static final String TEXTURE_BOX2 = "./resources/textures/box-modified.png";

    
    
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