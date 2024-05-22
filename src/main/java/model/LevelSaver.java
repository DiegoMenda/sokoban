package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LevelSaver {

	private static final Logger logger = LoggerFactory.getLogger(LevelSaver.class);
	public static void main(String[] args) {
		//System.out.println(System.getProperty("user.dir"));


		//		
		//File levelFile = new File(System.getProperty("user.dir")+"/src/sokoban/model/maps/test_level.txt"); 
		File levelFile = new File("./src/main/java/model/maps/test_level.txt"); 
		//		       
		Level level = LevelLoader.loadLevel(levelFile);
		String xd = level.toString();
		//	        
		System.out.println(xd);

		LevelSaver.saveLevel(level, "./src/main/java/model/maps/test_level_save.txt");

	}



	public static boolean saveLevel(Level level, String dir) {
		logger.info("saving level {} in {}", level.getLevelName(), dir);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(dir)))) {
			// Write the level name
			writer.write(level.getLevelName());
			writer.newLine();

			// Write the dimensions
			writer.write(level.getRow() + " " + level.getCol());
			writer.newLine();

			// Write the grid of entities
			for (int y = 0; y < level.getRow(); y++) {
				for (int x = 0; x < level.getCol(); x++) {
					if(level.getMobileEntities(x,y) != null) {
						writer.write(level.getMobileEntities(x,y).toString());
					}else {
						writer.write(level.getImmovableEntities(x,y).toString());
					}
				}
				writer.newLine();
			}
			return true;
		} catch (IOException e) {
			logger.error("error writing while saving the level {} in {}", level.getLevelName(), dir);
			e.printStackTrace();
			return false;
		}

	}

}
