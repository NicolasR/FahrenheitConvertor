
/**
 * Mod�le associ� au convertisseur
 * @author Nicolas RIGNAULT
 *
 */
public class ConverterModel {
	
	/**
	 * Derni�re valeur en �C
	 */
	private double celsius;
	
	/**
	 * Derni�re valeur en �F
	 */
	private double fahrenheit;
	
	/**
	 * Indique si la zone de texte �C est remplie
	 */
	private boolean isCelsiusEmpty;
	
	/**
	 * Indique si la one de texte �F est remplie
	 */
	private boolean isFahrenheitEmpty;
	
	/**
	 * Configuration correspondante aux donn�es de l'application
	 */
	private Configuration configuration;
	
	/**
	 * Configuration correspondante aux langues de l'application
	 */
	private Configuration languages;
	
	/**
	 * Langue courante
	 */
	private String currentLocale;
	
	/**
	 * Type de conversion
	 */
	private char currentConversion;
	
	/**
	 * Constructeur
	 */
	public ConverterModel()
	{
		celsius = 0.0;
		isCelsiusEmpty = true;
		fahrenheit = 0.0;
		isFahrenheitEmpty = true;
		configuration = new Configuration(Configuration.configuration);
		if (configuration.getProperty("language") == null)
		{
			configuration.setProperty("language", "fr_FR");
			currentLocale = "fr_FR";
		}
		else
			currentLocale = configuration.getProperty("language");
		languages = new Configuration(Configuration.languages);
		loadLanguages();
		
		if (configuration.getProperty("conversionType") == null)
		{
			configuration.setProperty("conversionType", "F");
		}
		else
			currentConversion = configuration.getProperty("conversionType").toCharArray()[0];
		initialize();
	}
	
	/**
	 * Charge les langues disponibles
	 */
	public void loadLanguages()
	{
		if (languages.size() == 0 || !languages.keySet().contains("Fran�ais"))
			languages.setProperty("French", "fr_FR");
			
	}
	
	/**
	 * Initialise le mod�le
	 */
	public void initialize() {
		String celsiusValue = configuration.getProperty("celsiusValue");
		String fahrenheitValue = configuration.getProperty("fahrenheitValue");
		if (celsiusValue != null)
		{
			try {
				celsius = Double.parseDouble(celsiusValue);
				isCelsiusEmpty = false;
			} catch (NumberFormatException e) {
				//On garde la valeur par d�faut
			}
		}
		
		if (fahrenheitValue != null)
		{
			try {
				fahrenheit = Double.parseDouble(fahrenheitValue);
				isFahrenheitEmpty = false;
			} catch (NumberFormatException e) {
				//On garde la valeur par d�faut
			}
		}
	}
	
	/**
	 * Indique si la zone de texte �C est remplie
	 * @return un booleen
	 */
	public boolean isEmptyCelsius()
	{
		return isCelsiusEmpty;
	}
	
	/**
	 * D�fini que la zone de texte �C est remplie
	 */
	public void setEmptyCelsius()
	{
		isCelsiusEmpty = true;
	}
	
	/**
	 * Indique si la zone de texte �F est remplie
	 * @return un booleen
	 */
	public boolean isEmptyFahrenheit()
	{
		return isFahrenheitEmpty;
	}
	
	/**
	 * D�fini que la zone de texte �F est remplie
	 */
	public void setEmptyFahrenheit()
	{
		isFahrenheitEmpty = true;
	}
	
	/**
	 * Renvoie la derni�re valeur �C stock�e
	 * @return un nombre
	 */
	public double getCelsius()
	{
		return celsius;
	}
	
	/**
	 * D�fini la nouvelle valeur �C � stocker
	 * @param value, la nouvelle valeur
	 */
	public void setCelsius(double value)
	{
		celsius = value;
		isCelsiusEmpty = false;
	}
	
	/**
	 * Renvoie la derni�re valeur �F stock�e
	 * @return un nombre
	 */
	public double getFahrenheit()
	{
		return fahrenheit;
	}
	
	/**
	 * D�fini la nouvelle valeur �F � stocker
	 * @param value, la nouvelle valeur
	 */
	public void setFahrenheit(double value)
	{
		fahrenheit = value;
		isFahrenheitEmpty = false;
	}
	
	/**
	 * Renvoie les langages disponibles
	 * @return la configuration associ�e
	 */
	public Configuration getLanguages()
	{
		return languages;
	}
	
	/**
	 * Renvoie les donn�es de l'application
	 * @return la configuration associ�e
	 */
	public Configuration getConfiguration()
	{
		return configuration;
	}
	
	/**
	 * Renvoie la langue courante de l'application
	 * @return la langue courante
	 */
	public String getCurrentLocale()
	{
		return currentLocale;
	}
	
	/**
	 * D�fini la nouvelle langue de l'application
	 * @param locale, la nouvelle langue
	 */
	public void setCurrentLocale(String locale)
	{
		currentLocale = locale;
	}
	
	/**
	 * Renvoi le type de conversion actuel
	 * @return le type de conversion actuel
	 */
	public char getCurrentConversion()
	{
		return currentConversion;
	}
	
	/**
	 * D�fini le type de conversion actuel
	 * @param conversion, le nouveau type de conversion
	 */
	public void setCurrentConversion(char conversion)
	{
		currentConversion = conversion;
	}
	
	/**
	 * Sauvegarde les donn�es de l'application
	 */
	public void saveConfiguration()
	{
		String celsiusValue = "", fahrenheitValue = "";
		if (!isCelsiusEmpty)
			celsiusValue = Double.toString(celsius);
		if (!isFahrenheitEmpty)
			fahrenheitValue = Double.toString(fahrenheit);
		configuration.setProperty("celsiusValue", celsiusValue);
		configuration.setProperty("fahrenheitValue", fahrenheitValue);
		configuration.setProperty("language", currentLocale);
		configuration.setProperty("conversionType", Character.toString(currentConversion));
		configuration.save();
	}
	
//	/**
//	 * Charge les donn�es de l'application
//	 */
//	public void loadConfiguration()
//	{
//		try {
//			Double celsiusValue = Double.parseDouble(configuration.getProperty("celsiusValue"));
//			if (celsiusValue != null)
//				celsius = celsiusValue;
//		} catch (NumberFormatException e) {
//			isCelsiusEmpty = true;
//		}
//		
//		try {
//			Double fahrenheitValue = Double.parseDouble(configuration.getProperty("fahrenheitValue"));
//			if (fahrenheitValue != null)
//				fahrenheit = fahrenheitValue;
//		} catch (NumberFormatException e) {
//			isFahrenheitEmpty = true;
//		}
//		
//		String locale = configuration.getProperty("language");
//		if (locale != null)
//			currentLocale = locale;
//		
//		String conversion = configuration.getProperty("conversionType");
//		if(conversion != null)
//			currentConversion = conversion.toCharArray()[0];
//	}
}
