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
	private static JAXBContext context;
	



	 public static void saveToXML(GameWorldWithHistory gameWorldWithHistory, String filePath) throws JAXBException {
	        if (context == null) {
	            context = JAXBContext.newInstance(GameWorldWithHistory.class);
	        }

	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        marshaller.marshal(gameWorldWithHistory, new File(filePath));
	    }

	    // Método para leer desde XML
	 public static GameWorldWithHistory readFromXML(String filePath) throws JAXBException {
		    JAXBContext context = getJAXBContext();
		    Unmarshaller unmarshaller = context.createUnmarshaller();
		    return (GameWorldWithHistory) unmarshaller.unmarshal(new File(filePath));
		}

		private static JAXBContext getJAXBContext() throws JAXBException {
		    if (context == null) {
		        context = JAXBContext.newInstance(GameWorldWithHistory.class);
		    }
		    return context;
		}
}
