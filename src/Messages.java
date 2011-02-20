import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * G�re les diverses donn�es sauvegard�es de l'application
 * @author Nicolas RIGNAULT
 *
 */
public class Messages {
	/**
	 * Nom des fichiers de configuration
	 */
	private static final String BUNDLE_NAME = "messages";

	/**
	 * R�cup�re les donn�es d'une langue pr�cise
	 */
	private static ResourceBundle RESOURCE_BUNDLE;
	
	public Messages(String locale1, String locale2) {
		Locale currentLocale = new Locale(locale1, locale2);
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, currentLocale);
	}

	/**
	 * Renvoie la cha�ne dans la langue donn�e
	 * @param key, la cl� dont on souhaite la traduction
	 * @return la traduction associ�e
	 */
	public String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	public boolean existKey(String key) {
		try {
			@SuppressWarnings("unused")
			String test = RESOURCE_BUNDLE.getString(key);
			return true;
		} catch (MissingResourceException e) {
			return false;
		}
	}
	
	public String getKey(String value) {
		try {
			for (String key : RESOURCE_BUNDLE.keySet()) {
				if (RESOURCE_BUNDLE.getString(key).equals(value))
					return key;
			}
			return null;
		} catch (MissingResourceException e) {
			return null;
		}
	}
}
