package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LevelLoader {
	
	private static final Logger logger = LoggerFactory.getLogger(LevelLoader.class);
	public static void main(String[] args) {
		//System.out.println(System.getProperty("user.dir"));
		
		
//		
//	        LevelLoader levelLoader = new LevelLoader();
//	        //File levelFile = new File(System.getProperty("user.dir")+"/src/sokoban/model/maps/test_level.txt"); 
//	        File levelFile = new File("./src/sokoban/model/maps/test_level.txt"); 
//		       
//	        Level level = levelLoader.loadLevel(levelFile);
//	        String xd = level.toString();
//	        
//	        System.out.println(xd);
//	        
//	        
	        
	    }
	
	
	
    public static Level loadLevel(File file) {
    	logger.info("reading file {} to generate level", file.getName());
        Level level = null;
        Worker warehouseMan = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String levelName = reader.readLine();
            String[] dimensions = reader.readLine().split(" ");
            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);
            logger.info("loading a level that has {} rows", rows);
            logger.info("loading a level that has {} columns", cols);
            ImmovableEntity[][] inamovible = new ImmovableEntity[rows][cols];
            MobileEntity[][] movible = new MobileEntity[rows][cols];

            for (int i = 0; i < rows; i++) {
                String line = reader.readLine();
                for (int j = 0; j < cols; j++) {
                    char c = line.charAt(j);
                    switch (c) {
                        case '+':
                            inamovible[i][j] = new Wall(i,j, TexturePaths.generateImage(TexturePaths.TEXTURE_WALL));
                            movible[i][j] = null;
                            logger.info("a wall has been loaded");
                            break;
                        case '.':
                            inamovible[i][j] = new Air(i,j, TexturePaths.generateImage(TexturePaths.TEXTURE_AIR));
                            movible[i][j] = null;
                            logger.info("an air");
                            break;
                        case '*':
                            inamovible[i][j] = new Goal(i,j, TexturePaths.generateImage(TexturePaths.TEXTURE_GOAL));
                            movible[i][j] = null;
                            logger.info("a goal has been loaded");
                            break;
                        case '#':
                            //inamovible[i][j] = new Box(i,j,  TexturePaths.generateImage(TexturePaths.TEXTURE_BOX));
                        	inamovible[i][j] = new Air(i,j, TexturePaths.generateImage(TexturePaths.TEXTURE_AIR));
                        	movible[i][j] = new Box(i,j,  TexturePaths.generateImage(TexturePaths.TEXTURE_BOX));
                        	logger.info("a box has been loaded");
                            break;
                        case 'W':
                        	inamovible[i][j] = new Air(i,j, TexturePaths.generateImage(TexturePaths.TEXTURE_AIR));
                        	warehouseMan = new Worker(i,j, TexturePaths.generateImage(TexturePaths.TEXTURE_WORKER));
                        	movible[i][j] = warehouseMan;
                        	logger.info("the warehouse man has been loaded");
                            break;
                    }
                }
            }

            level = new Level(rows, cols,levelName, inamovible, movible);
            level.setWarehouseMan(warehouseMan);
        } catch (IOException e) {
        	logger.error("error reading from file {}", file.getName());
            e.printStackTrace();
        }

        return level;
    }
}

