import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;

/**
 * Vue du convertisseur
 * @author Nicolas RIGNAULT
 *
 */
public class ConverterView extends JFrame {

	/**
	 * ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Barre de menu
	 */
	private JMenuBar menuBar = new JMenuBar();
	
	/**
	 * Menu de sélection de langue
	 */
	private JMenu langMenu;
	
	/**
	 * Menu de sélection de conversion
	 */
	private JMenu convertToMenu;
		
	/**
	 * Titre pour l'item °C
	 */
	private JLabel celsiusLabel;
	
	/**
	 * Boite de texte pour les °C
	 */
	private JTextField celsiusField;
	
	/**
	 * Titre pour l'item °F
	 */
	private JLabel fahrenheitLabel;
	
	/**
	 * Boite de texte pour les °F
	 */
	private JTextField fahrenheitField;
	
	/**
	 * Bouton pour effectuer la conversion
	 */
	private JButton convertButton;
	
	/**
	 * Modèle contenant les données
	 */
	private final ConverterModel model;
	
	/**
	 * Langues disponibles
	 */
	private Map<String, JRadioButtonMenuItem> languages;
	
	/**
	 * Type de conversion possible
	 */
	private Map<Character, JRadioButtonMenuItem> conversionTypes;
	
	/**
	 * Données sauvegardées (configuration et langues)
	 */
	private Messages messages;
	
	/**
	 * Constructeur de la vue
	 * @param height, la hauteur de la fenêtre
	 * @param width, la largeur de la fenêtre
	 * @param model, le modèle associé au convertisseur
	 */
	public ConverterView(int height, int width, final ConverterModel model)
	{
		this.setSize(height, width);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.model = model;
		
		messages = new Messages("fr", "FR");
		celsiusLabel = new JLabel(messages.getString("CelsiusLabel"));
		celsiusField = new JTextField();
		fahrenheitLabel = new JLabel(messages.getString("FahrenheitLabel"));
		fahrenheitField = new JTextField();
		convertButton = new JButton(messages.getString("ConvertButton"));
		conversionTypes = new HashMap<Character, JRadioButtonMenuItem>();
		
		langMenu = new JMenu(messages.getString("LanguageMenu"));
		convertToMenu = new JMenu(messages.getString("ConvertMenu"));
		
		//Initialisation menu de conversion
		ButtonGroup convertGroup = new ButtonGroup();
		JRadioButtonMenuItem radioF = new JRadioButtonMenuItem("°F");
		radioF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				changeConversionType('F');
			}
		});
		convertGroup.add(radioF);
		convertToMenu.add(radioF);
		conversionTypes.put('F',radioF);
		JRadioButtonMenuItem radioC = new JRadioButtonMenuItem("°C");
		radioC.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				changeConversionType('C');
			}
		});
		convertGroup.add(radioC);
		convertToMenu.add(radioC);
		conversionTypes.put('C',radioC);
		menuBar.add(langMenu);
		menuBar.add(convertToMenu);
		
		//Initialisation menu des langues
		languages = new HashMap<String, JRadioButtonMenuItem>();
		ButtonGroup langGroup = new ButtonGroup();
		for (Object lang : model.getLanguages().keySet()) {
			final String language = (String)lang;
			JRadioButtonMenuItem element = 
				new JRadioButtonMenuItem(messages.getString(language));
			element.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					changeLanguage(model.getLanguages().getProperty(language));
					
				}
			});
			if (language.equals("French"))
				element.setSelected(true);
			languages.put(language, element);
			langGroup.add(element);
			langMenu.add(element);
		}
		this.setJMenuBar(menuBar);
		
		initialize();
	}
	
	/**
	 * Changer la langue du programme
	 * @param locale, la nouvelle langue à appliquer
	 */
	public void changeLanguage(String locale)
	{
		if (locale.length() != 5)
			return;
		String locale1 = locale.substring(0, 2);
		String locale2 = locale.substring(3, 5);
		
		//On récupère la clé du messageur d'erreur (s'il y en a un)
		String key1 = null, key2 = null;
		if (messages.getKey(celsiusField.getText()) != null)		
		{
			key1 = messages.getKey(celsiusField.getText());
		}
		
		if (messages.getKey(fahrenheitField.getText()) != null)
		{
			key2 = messages.getKey(fahrenheitField.getText());
		}
		
		messages = new Messages(locale1, locale2);
		convertButton.setText(messages.getString("ConvertButton"));
		celsiusLabel.setText(messages.getString("CelsiusLabel"));
		fahrenheitLabel.setText(messages.getString("FahrenheitLabel"));
		langMenu.setText(messages.getString("LanguageMenu"));
		convertToMenu.setText(messages.getString("ConvertMenu"));
		for (String lang : languages.keySet()) {
			languages.get(lang).setText(messages.getString(lang));
			if (model.getLanguages().getProperty(lang).equals(locale))
				languages.get(lang).setSelected(true);
		}
		model.setCurrentLocale(locale);
		langMenu.setMnemonic(messages.getString("LanguageMenu").toCharArray()[0]);
		convertToMenu.setMnemonic(messages.getString("ConvertMenu").toCharArray()[0]);
		
		//Traduction message d'erreur
		if (key1 != null)
			celsiusField.setText(messages.getString(key1));
		
		if (key2 != null)
			fahrenheitField.setText(messages.getString(key2));

	}
	
	/**
	 * Change le type de conversion
	 * @param conversion, la nouvelle conversion à appliquer
	 */
	public void changeConversionType(char conversion)
	{
		for (char conversionType : conversionTypes.keySet()) {
			if (conversionType == conversion)
			{
				model.setCurrentConversion(conversion);
				conversionTypes.get(conversionType).setSelected(true);
				return;
			}
		}
	}
	
	/**
	 * Initialisation de la vue
	 */
	public void initialize()
	{
		//Messages.initializeMessages("fr", "FR");
		GridLayout mainLayout = new GridLayout(3,2);
		mainLayout.setHgap(10);
		mainLayout.setVgap(50);
		this.setLayout(mainLayout);
		//this.getContentPane().add(celciusValue, BorderLayout.CENTER);
		//JPanel container = new JPanel();
		//container.setBackground(Color.BLACK);
		//container.setSize(100, 100);
		this.getContentPane().add(celsiusLabel); //$NON-NLS-1$
		this.getContentPane().add(celsiusField);
		this.getContentPane().add(fahrenheitLabel); //$NON-NLS-1$
		this.getContentPane().add(fahrenheitField);
		this.getContentPane().add(convertButton);
	}

	/**
	 * Récupération du bouton de conversion
	 * @return le bouton
	 */
	public JButton getConvertButton() {
		return convertButton;
	}
	
	/**
	 * Renvoie la zone de saisie de texte °C
	 * @return JTextField
	 */
	public JTextField getCelsiusField() {
		return celsiusField;
	}
	
	/**
	 * Renvoie la zone de saisie de texte °F
	 * @return JTextField
	 */
	public JTextField getFahrenheitField() {
		return fahrenheitField;
	}
	
	/**
	 * Renvoie la valeur en °C
	 * @return la chaîne associée
	 */
	public String getCelsiusValue()
	{
		return celsiusField.getText();
	}
	
	/**
	 * Défini la valeur en °C
	 * @param value, la nouvelle valeur
	 */
	public void setCelsiusValue(String value)
	{
		celsiusField.setText(value);
	}
	
	/**
	 * Renvoie la valeur en °F
	 * @return la chaîne associée
	 */
	public String getFahrenheitValue()
	{
		return fahrenheitField.getText();
	}
	
	/**
	 * Défini la valeur en °F
	 * @param value, la nouvelle valeur
	 */
	public void setFahrenheitValue(double value)
	{
		fahrenheitField.setText(Double.toString(value));
	}
	
	/**
	 * Indique si la boite de texte °C a été remplie avec une valeur valide
	 * @return un booleen
	 */
	public boolean isCelsiusEmpty()
	{
		return celsiusField.getText().length() == 0;
	}

	/**
	 * Indique si la boite de texte °F a été remplie avec une valeur valide
	 * @return un booleen
	 */
	public boolean isFarhenHeitEmpty()
	{
		return fahrenheitField.getText() == null;
	}
	
	/**
	 * Met à jour la vue avec les nouvelles données du modèle
	 */
	public void update() {
		if (!model.isEmptyCelsius())
			celsiusField.setText(Double.toString(model.getCelsius()));
		
		if (!model.isEmptyFahrenheit())
			fahrenheitField.setText(Double.toString(model.getFahrenheit()));
	}

	/**
	 * Renvoie la traduction de l'expression donné en paramètre
	 * @param msg, l'expression à traduire
	 * @return l'expression traduite
	 */
	public String getMessage(String msg)
	{
		return messages.getString(msg);
	}
}
