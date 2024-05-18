package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LevelLoader {
	

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
        Level level = null;
        Worker warehouseMan = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String levelName = reader.readLine();
            String[] dimensions = reader.readLine().split(" ");
            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);

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
                            break;
                        case '.':
                            inamovible[i][j] = new Air(i,j, TexturePaths.generateImage(TexturePaths.TEXTURE_AIR));
                            movible[i][j] = null;
                            break;
                        case '*':
                            inamovible[i][j] = new Goal(i,j, TexturePaths.generateImage(TexturePaths.TEXTURE_GOAL));
                            movible[i][j] = null;
                            break;
                        case '#':
                            //inamovible[i][j] = new Box(i,j,  TexturePaths.generateImage(TexturePaths.TEXTURE_BOX));
                        	inamovible[i][j] = new Air(i,j, TexturePaths.generateImage(TexturePaths.TEXTURE_AIR));
                        	movible[i][j] = new Box(i,j,  TexturePaths.generateImage(TexturePaths.TEXTURE_BOX));
                        	
                            break;
                        case 'W':
                        	inamovible[i][j] = new Air(i,j, TexturePaths.generateImage(TexturePaths.TEXTURE_AIR));
                        	warehouseMan = new Worker(i,j, TexturePaths.generateImage(TexturePaths.TEXTURE_WORKER));
                        	movible[i][j] = warehouseMan;
                            break;
                    }
                }
            }

            level = new Level(rows, cols,levelName, inamovible, movible);
            level.setWarehouseMan(warehouseMan);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return level;
    }
}

