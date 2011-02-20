import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration du programme (données et langues)
 * @author Nicolas RIGNAULT
 *
 */
public class Configuration extends Properties {

	/**
	 * ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identifiant de type configuration de données
	 */
	public static final int configuration = 1;
	
	/**
	 * Identifiant de type langage
	 */
	public static final int languages = 2;
	
	/**
	 * Type (données ou langues)
	 */
	private int type;
	
	/**
	 * Constructeur qui charge le fichier de configuration du type associé
	 * @param type, le type de configuration
	 */
	public Configuration(int type) {
		super();
		this.type = type;
		try {
			FileInputStream in;
			if (type == Configuration.configuration)
				in = new FileInputStream("converter.configuration");
			else
				in = new FileInputStream("languages.configuration");
			this.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Pas de fichier de configuration trouvé !");
		} catch (IOException e) {
			System.out.println("Erreur lors de l'ouverture du fichier de configuration");
		}
	}
	
	/**
	 * Sauvegarde le fichier de configuration
	 */
	public void save() {
		try {
			FileOutputStream out;
			if (type == Configuration.configuration)
				out = new FileOutputStream("converter.configuration");
			else
				out = new FileOutputStream("languages.configuration");
			this.store(out, "--- Info ---");
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Pas de fichier de configuration trouvé !");
		} catch (IOException e) {
			System.out.println("Erreur lors de l'ouverture du fichier de configuration");
		}
	}

}
