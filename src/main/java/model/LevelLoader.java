package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LevelLoader {
	
	private static final Logger logger = LoggerFactory.getLogger(LevelLoader.class);
//	public static void main(String[] args) {
//		//System.out.println(System.getProperty("user.dir"));
//		
//		
//		
//	        LevelLoader levelLoader = new LevelLoader();
//	        //File levelFile = new File(System.getProperty("user.dir")+"/src/sokoban/model/maps/test_level.txt"); 
//	        File levelFile = new File("./src/main/java/model/maps/test_level.txt"); 
//		       
//	        Level level = levelLoader.loadLevel(levelFile);
//	        String xd = level.toString();
//	        
//	        System.out.println(xd);
//	        
//	        
//	        
//	    }
	
	
	
    public static Level loadLevel(File file) {
    	logger.info("reading file {} to generate level", file.getName());
        Level level = null;
        Worker warehouseMan = null;
        int nWareHouseMan=0;
        int nboxes = 0;
        int ngoals = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String levelName = reader.readLine();
            String[] dimensions = reader.readLine().split(" ");
            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);
            logger.info("loading a level that has {} rows", rows);
            logger.info("loading a level that has {} columns", cols);
            ImmovableEntity[][] inamovible = new ImmovableEntity[rows][cols];
            MobileEntity[][] movible = new MobileEntity[rows][cols];

            for (int y = 0; y < rows; y++) {
                String line = reader.readLine();
                for (int x = 0; x < cols; x++) {
                    char c = line.charAt(x);
                    switch (c) {
                        case '+':
                            inamovible[y][x] = new Wall(x,y, TexturePaths.generateImage(TexturePaths.TEXTURE_WALL));
                            movible[y][x]  = null;
                            logger.info("a wall has been loaded ({}, {})", x, y);
                            break;
                        case '.':
                            inamovible[y][x]  = new Air(x,y, TexturePaths.generateImage(TexturePaths.TEXTURE_AIR));
                            movible[y][x]  = null;
                            logger.info("an air");
                            break;
                        case '*':
                            inamovible[y][x]  = new Goal(x,y, TexturePaths.generateImage(TexturePaths.TEXTURE_GOAL));
                            movible[y][x] = null;
                            logger.info("a goal has been loaded({}, {})", x, y);
                            ngoals++;
                            break;
                        case '#':
                            //inamovible[i][j] = new Box(i,j,  TexturePaths.generateImage(TexturePaths.TEXTURE_BOX));
                        	inamovible[y][x]  = new Air(x,y, TexturePaths.generateImage(TexturePaths.TEXTURE_AIR));
                        	movible[y][x]  = new Box(x,y,  TexturePaths.generateImage(TexturePaths.TEXTURE_BOX));
                        	nboxes++;
                        	logger.info("a box has been loaded({}, {})", x, y);
                            break;
                        case 'W':
                        	inamovible[y][x]  = new Air(x,y, TexturePaths.generateImage(TexturePaths.TEXTURE_AIR));
                        	warehouseMan = new Worker(x,y, TexturePaths.generateImage(TexturePaths.TEXTURE_WORKER));
                        	movible[y][x]  = warehouseMan;
                        	if(nWareHouseMan==0)
                        		nWareHouseMan++;
                        	else {
                        		logger.error("The level {} is wrong, there is more than one warehouse man", levelName );
                        		return null;
                        	}
                        	logger.info("the warehouse man has been loaded({}, {})", x, y);
                            break;
                         default:
                        	 logger.error("the character {} readed is not valid", c);
                        	 return null;
                    }
                }
            }
            if(nboxes== 0 ) {
            	logger.error("The level {} is wrong, there should be at least one box", levelName);
            	return null;
            }
            if(ngoals == 0 ) {
            	logger.error("The level {} is wrong, there should be at least one goal", levelName);
            	return null;
            }
            if(nboxes!=ngoals) {
            	logger.error("The level {} is wrong, the number of boxes does not match the number of goals", levelName);
            	return null;
            	
            }
            level = new Level(cols,rows, levelName, inamovible, movible);
            level.setWarehouseMan(warehouseMan);
        } catch (IOException e) {
        	logger.error("error reading from file {}", file.getName());
            e.printStackTrace();
        }

        return level;
    }
}

