package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LevelLoader {
	
	private static final Logger logger = LoggerFactory.getLogger(LevelLoader.class);


	
	
	public static Level loadLevel(File file) {
	    logger.info("reading file {} to generate level", file.getName());
	    Level level = null;
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
	        LevelContext context = new LevelContext(inamovible, movible, null, 0);

	        for (int y = 0; y < rows; y++) {
	            String line = reader.readLine();
	            for (int x = 0; x < cols; x++) {
	                char c = line.charAt(x);
	                Result result = processChar(c, x, y, context, levelName);
	                if (result.isInvalid) {
	                    return null;
	                }
	                context.warehouseMan = result.warehouseMan;
	                context.nWareHouseMan = result.nWareHouseMan;
	                if (c == '*') ngoals++;
	                if (c == '#') nboxes++;
	            }
	        }
	        if (!validateLevel(levelName, context.nWareHouseMan, nboxes, ngoals)) {
	            return null;
	        }
	        level = new Level(cols, rows, levelName, inamovible, movible);
	        level.setWarehouseMan(context.warehouseMan);
	    } catch (IOException | NullPointerException e) {
	        logger.error("error reading from file {}", file.getName());
	        return null;
	    }

	    return level;
	}

	private static class Result {
	    Worker warehouseMan;
	    int nWareHouseMan;
	    boolean isInvalid;

	    Result(Worker warehouseMan, int nWareHouseMan, boolean isInvalid) {
	        this.warehouseMan = warehouseMan;
	        this.nWareHouseMan = nWareHouseMan;
	        this.isInvalid = isInvalid;
	    }
	}

	private static class LevelContext {
	    ImmovableEntity[][] inamovible;
	    MobileEntity[][] movible;
	    Worker warehouseMan;
	    int nWareHouseMan;

	    LevelContext(ImmovableEntity[][] inamovible, MobileEntity[][] movible, Worker warehouseMan, int nWareHouseMan) {
	        this.inamovible = inamovible;
	        this.movible = movible;
	        this.warehouseMan = warehouseMan;
	        this.nWareHouseMan = nWareHouseMan;
	    }
	}

	private static Result processChar(char c, int x, int y, LevelContext context, String levelName) {
	    switch (c) {
	        case '+':
	            context.inamovible[y][x] = new Wall(x, y, TexturePaths.generateImage(TexturePaths.TEXTURE_WALL));
	            context.movible[y][x] = null;
	            logger.info("a wall has been loaded ({}, {})", x, y);
	            break;
	        case '.':
	            context.inamovible[y][x] = new Air(x, y, TexturePaths.generateImage(TexturePaths.TEXTURE_AIR));
	            context.movible[y][x] = null;
	            logger.info("an air");
	            break;
	        case '*':
	            context.inamovible[y][x] = new Goal(x, y, TexturePaths.generateImage(TexturePaths.TEXTURE_GOAL));
	            context.movible[y][x] = null;
	            logger.info("a goal has been loaded({}, {})", x, y);
	            break;
	        case '#':
	            context.inamovible[y][x] = new Air(x, y, TexturePaths.generateImage(TexturePaths.TEXTURE_AIR));
	            context.movible[y][x] = new Box(x, y, TexturePaths.generateImage(TexturePaths.TEXTURE_BOX), TexturePaths.generateImage(TexturePaths.TEXTURE_BOX2));
	            logger.info("a box has been loaded({}, {})", x, y);
	            break;
	        case 'W':
	            context.inamovible[y][x] = new Air(x, y, TexturePaths.generateImage(TexturePaths.TEXTURE_AIR));
	            context.warehouseMan = new Worker(x, y, TexturePaths.generateImage(TexturePaths.TEXTURE_WORKER));
	            context.movible[y][x] = context.warehouseMan;
	            if (context.nWareHouseMan == 0)
	                context.nWareHouseMan++;
	            else {
	                logger.error("The level {} is wrong, there is more than one warehouse man", levelName);
	                return new Result(null, context.nWareHouseMan, true);
	            }
	            logger.info("the warehouse man has been loaded({}, {})", x, y);
	            break;
	        default:
	            logger.error("the character {} readed is not valid", c);
	            return new Result(null, context.nWareHouseMan, true);
	    }
	    return new Result(context.warehouseMan, context.nWareHouseMan, false);
	}

	private static boolean validateLevel(String levelName, int nWareHouseMan, int nboxes, int ngoals) 
	{
	    if (nWareHouseMan != 1) {
	        logger.error("The level {} is wrong, there should be at least one warehouse man", levelName);
	        return false;
	    }
	    if (nboxes == 0) {
	        logger.error("The level {} is wrong, there should be at least one box", levelName);
	        return false;
	    }
	    if (ngoals == 0) {
	        logger.error("The level {} is wrong, there should be at least one goal", levelName);
	        return false;
	    }
	    if (nboxes != ngoals) {
	        logger.error("The level {} is wrong, the number of boxes does not match the number of goals", levelName);
	        return false;
	    }
	    return true;
	}


}

