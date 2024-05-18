package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LevelSaver {


	public static void main(String[] args) {
		//System.out.println(System.getProperty("user.dir"));


		//		
		//	        //File levelFile = new File(System.getProperty("user.dir")+"/src/sokoban/model/maps/test_level.txt"); 
			        File levelFile = new File("./src/sokoban/model/maps/test_level.txt"); 
		//		       
			        Level level = LevelLoader.loadLevel(levelFile);
			        String xd = level.toString();
		//	        
			        System.out.println(xd);
  
			        LevelSaver.saveLevel(level, "./src/sokoban/model/maps/test_level_save.txt");
			        
 

	}



	public static boolean saveLevel(Level level, String dir) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(dir)))) {
			// Write the level name
			writer.write(level.getLevelName());
			writer.newLine();

			// Write the dimensions
			writer.write(level.getRow() + " " + level.getRow());
			writer.newLine();

			// Write the grid of entities
			for (int i = 0; i < level.getRow(); i++) {
				for (int j = 0; j < level.getRow(); j++) {
					writer.write(level.getImmovableEntities()[i][j].toString());
				}
				writer.newLine();
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

}
