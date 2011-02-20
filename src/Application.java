import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Application {

	/**
	 * Main de l'application
	 * @param args les arguments de l'application
	 */
	public static void main(String[] args) {
		// Instancie le model et la vue
		final ConverterModel model = new ConverterModel();
		final ConverterView window = new ConverterView(320, 240, model);

		// Instancie le controlleur avec le modèle et la vue
		final ConverterController controller = new ConverterController(model, window);
		controller.initialize();
		window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
            	controller.updateModel();
            	model.saveConfiguration();
                window.dispose();
            }
		});
		
		//Met à jour la langue avec celle chargée depuis le modèle
		window.changeLanguage(model.getCurrentLocale());
		
		//Met à jour le type de conversion avec celui chargé depuis le modèle
		window.changeConversionType(model.getCurrentConversion());
		
		//Met à jour la vue avec les dernières données de conversion
		window.update();
		
		//Customisation Fenêtre
		window.setTitle("Fahrenheit Converter");
		window.setResizable(false);
		window.setVisible(true);
	}

}
