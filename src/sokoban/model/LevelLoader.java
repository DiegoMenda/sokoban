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
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String levelName = reader.readLine();
            String[] dimensions = reader.readLine().split(" ");
            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);

            Entity[][] pos = new Entity[rows][cols];

            for (int i = 0; i < rows; i++) {
                String line = reader.readLine();
                for (int j = 0; j < cols; j++) {
                    char c = line.charAt(j);
                    switch (c) {
                        case '+':
                            pos[i][j] = new Wall(i,j, TexturePaths.generateImage(TexturePaths.TEXTURE_WALL));
                            break;
                        case '.':
                            pos[i][j] = new Air(i,j, TexturePaths.generateImage(TexturePaths.TEXTURE_AIR));
                            break;
                        case '*':
                            pos[i][j] = new Goal(i,j, TexturePaths.generateImage(TexturePaths.TEXTURE_GOAL));
                            break;
                        case '#':
                            pos[i][j] = new Box(i,j,  TexturePaths.generateImage(TexturePaths.TEXTURE_BOX));
                            break;
                        case 'W':
                            pos[i][j] = new Worker(i,j, TexturePaths.generateImage(TexturePaths.TEXTURE_WORKER));
                            break;
                    }
                }
            }

            level = new Level(rows, cols,levelName, pos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return level;
    }
}

