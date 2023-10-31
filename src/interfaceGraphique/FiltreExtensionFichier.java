package interfaceGraphique;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Filtre les fichiers pour n'afficher que les fichiers avec l'extension .fasta
 * @author walid
 * @see choixFichier
 */
public class FiltreExtensionFichier extends FileFilter {
	/**
	 * Retourne vrai si le paramètre f est
	 * un dossier ou un fichier avec l'extension .fasta
	 * @param f correspond à un fichier ou dossier
	 */
	@Override
	public boolean accept(File f) {
		if (f.isDirectory() || f.getName().endsWith(".fasta"))
			return true;
		return false;
	}	
	
	/**
	 * Affiche la description des fichiers acceptés
	 * dans la liste déroulante type de fichier
	 */
	@Override
	public String getDescription() {
		return "Format Fasta (*.fasta)";
	}
}

