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

		// Instancie le controlleur avec le mod�le et la vue
		final ConverterController controller = new ConverterController(model, window);
		controller.initialize();
		window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
            	controller.updateModel();
            	model.saveConfiguration();
                window.dispose();
            }
		});
		
		//Met � jour la langue avec celle charg�e depuis le mod�le
		window.changeLanguage(model.getCurrentLocale());
		
		//Met � jour le type de conversion avec celui charg� depuis le mod�le
		window.changeConversionType(model.getCurrentConversion());
		
		//Met � jour la vue avec les derni�res donn�es de conversion
		window.update();
		
		//Customisation Fen�tre
		window.setTitle("Fahrenheit Converter");
		window.setResizable(false);
		window.setVisible(true);
	}

}
