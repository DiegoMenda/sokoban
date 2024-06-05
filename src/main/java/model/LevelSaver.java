package model;




import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class LevelSaver {

	private static final Logger logger = LoggerFactory.getLogger(LevelSaver.class);
	public static void main(String[] args) {
		 try {
	            GameWorld gameWorld = new GameWorld("./src/main/java/model/maps/test_level.txt");
	            GameWorldWithHistory gameWorldWithHistory = new GameWorldWithHistory(gameWorld, null);
	            //gameWorldWithHistory.getHistory().push(new Move(1, 2, 3, 4));

	            // Guardar en XML
	            saveToXML(gameWorldWithHistory, "./src/main/java/model/maps/test_level_save");

	            // Leer desde XML
	            GameWorldWithHistory loadedGameWorldWithHistory = readFromXML("./src/main/java/model/maps/test_level_save");
	            System.out.println("Loaded GameWorldWithHistory: " + loadedGameWorldWithHistory.getHistory());

	        } catch (JAXBException e) {
	            e.printStackTrace();
	        }

	}


	 public static void saveToXML(GameWorldWithHistory gameWorldWithHistory, String filePath) throws JAXBException {
	        JAXBContext context = JAXBContext.newInstance(GameWorldWithHistory.class);
	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        marshaller.marshal(gameWorldWithHistory, new File(filePath));
	    }

	    // Método para leer desde XML
	    public static GameWorldWithHistory readFromXML(String filePath) throws JAXBException {
	        JAXBContext context = JAXBContext.newInstance(GameWorldWithHistory.class);
	        Unmarshaller unmarshaller = context.createUnmarshaller();
	        return (GameWorldWithHistory) unmarshaller.unmarshal(new File(filePath));
	    }
}
