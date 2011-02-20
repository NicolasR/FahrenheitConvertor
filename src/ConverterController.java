import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

/**
 * Controlleur du convertisseur
 * @author Nicolas RIGNAULT
 *
 */
public class ConverterController implements ActionListener, KeyListener{
	
	/**
	 * Modèle associé au convertisseur
	 */
	private final ConverterModel model;
	
	/**
	 * Vue associée au convertisseur
	 */
	private final ConverterView view;
	
	/**
	 * Expression régulière pour vérifier les données entrées
	 */
	private Pattern regexp = Pattern.compile("^((0[1-9]*)|(-?[1-9][0-9]*))(\\.[0-9]+)?");
	
	/**
	 * Constructeur
	 * @param model, le modèle du convertisseur
	 * @param view, la vue du convertisseur
	 */
	public ConverterController(ConverterModel model, ConverterView view)
	{
		this.model = model;
		this.view = view;
	}
	
	/**
	 * Initialise le controlleur
	 */
	public void  initialize()
	{
		//view.getConfigButton().addActionListener(this);
		view.getConvertButton().addActionListener(this);
		view.getConvertButton().addKeyListener(this);
		view.getCelsiusField().addKeyListener(this);
		view.getFahrenheitField().addKeyListener(this);
	}

	/**
	 * Prépare la conversion (vérifications diverses)
	 */
	private void prepareConversion()
	{
		switch(model.getCurrentConversion())
		{
			case 'F':
				if (!validateValue(view.getCelsiusValue()))
				{
					view.getFahrenheitField().setText(view.getMessage("ValueError"));
					return;
				}
				Double celsiusValue = Double.parseDouble(view.getCelsiusValue());
				convertToFarhenheit(celsiusValue);
				break;
			
			case 'C':
				if (!validateValue(view.getFahrenheitValue()))
				{
					view.getCelsiusField().setText(view.getMessage("ValueError"));
					return;
				}
				Double fahrenheitValue = Double.parseDouble(view.getFahrenheitValue());
				convertToCelsius(fahrenheitValue);
				break;
			
			default:
				System.out.println("Erreur: type de conversion non existant");
		}
		
		return;
	}
	
	/**
	 * Surveille les actions sur le bouton de conversion
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
				
		if (arg0.getSource() == view.getConvertButton())
		{
			prepareConversion();
		}
	}
	
	/**
	 * Effectue la conversion de °C vers °F
	 * @param celsius, la valeur à convertir
	 */
	public void convertToFarhenheit(double celsius)
	{
//		if (!validateValue(view.getCelsiusValue()))
//			return;
		
		model.setFahrenheit((1.8*celsius) + 32.0);
		model.setCelsius(Double.parseDouble(view.getCelsiusValue()));
		view.update();
	}
	
	/**
	 * Effectue la converion de °F vers °C
	 * @param farhenheit, la valeur à convertir
	 */
	public void convertToCelsius(double farhenheit)
	{
//		if (!validateValue(view.getFahrenheitValue()))
//			return;
		model.setCelsius((farhenheit-32.0)/(1.8));
		model.setFahrenheit(Double.parseDouble(view.getFahrenheitValue()));
		view.update();
	}
	
	/**
	 * Met à jour le modèle avec les données de la vue
	 */
	public void updateModel()
	{
		if (validateValue(view.getCelsiusValue()) || view.getCelsiusValue().length() == 0)
			try {
				Double celsiusValue = Double.parseDouble(view.getCelsiusValue());
				model.setCelsius(celsiusValue);
			} catch (NumberFormatException e) {
				model.setEmptyCelsius();
			}
		
		if (validateValue(view.getFahrenheitValue()) || view.getFahrenheitValue().length() == 0)
			try {
				Double fahrenheitValue = Double.parseDouble(view.getFahrenheitValue());
				model.setFahrenheit(fahrenheitValue);
			} catch (NumberFormatException e) {
				model.setEmptyFahrenheit();
			}
	}
	
	/**
	 * Vérifie qu'une valeur est valide
	 * @param number, la chaîne à vérifier
	 * @return un booleen
	 */
	private boolean validateValue(String number)
	{
		return regexp.matcher(number).matches();
	}

	/**
	 * Action effectuée lorsque l'on appuie sur les touches du clavier
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			prepareConversion();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

}
