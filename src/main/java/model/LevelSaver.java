package model;




import java.io.File;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class LevelSaver {

	private static final Logger logger = LoggerFactory.getLogger(LevelSaver.class);
	private static JAXBContext context;

	private LevelSaver() {
		// Este constructor está vacío, ya que no necesitamos inicializar nada en esta clase
	}


    public static void saveToXML(GameWorldWithHistory gameWorldWithHistory, String filePath) throws JAXBException {
        if (context == null) {
            context = JAXBContext.newInstance(
                GameWorldWithHistory.class,
                GameWorld.class,
                Level.class,
                ImmovableEntity.class,
                MobileEntity.class,
                Wall.class,
                Air.class,
                Goal.class,
                Worker.class,
                Box.class,
                ImmovableEntity[][].class,
                MobileEntity[][].class
            );
        }

        logger.info("saving game in xml format on the following route {} ", filePath);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(gameWorldWithHistory, new File(filePath));
    }

	// Método para leer desde XML
    public static GameWorldWithHistory readFromXML(String filePath) throws JAXBException {
        logger.info("reading game in xml format from the following route {} ", filePath);
        if (context == null) {
            context = JAXBContext.newInstance(
                GameWorldWithHistory.class,
                GameWorld.class,
                Level.class,
                ImmovableEntity.class,
                MobileEntity.class,
                Wall.class,
                Air.class,
                Goal.class,
                Worker.class,
                Box.class,
                ImmovableEntity[][].class,
                MobileEntity[][].class
            );
        }

        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (GameWorldWithHistory) unmarshaller.unmarshal(new File(filePath));
    }


}
