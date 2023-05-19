package outils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * La classe Sequence permet de créer des séquences avec un en-tête et un type.
 */
public class Sequence {
    private String enTete;
    private String sequence;
    private TypeSeq typeSeq;
    private ArrayList<Sequence> listSeq = new ArrayList<Sequence>();

    
    /**
     * Construit une séquence avec un en-tête et un type.
     * @param enTete l'en-tête.
     * @param sequence la séquence.
     * @param typeSeq le type de la séquence.
     */
    public Sequence(String enTete, String sequence, TypeSeq typeSeq) {
        this.enTete = enTete;
        this.sequence = sequence;
        this.typeSeq = typeSeq;
    }

    /**
	 * retourne le nombre de séquence au format fasta 
	 * @param s correspond à la chaîne de caractère entrée par l'utilisateur
	 * @return nbSequences correspond au nombre de séquence contenu dans la chaîne entrée en paramètre
	 */
	public static int nbSequencesFormatFasta(String s) {
		int nbSequence=0;
		Pattern fastaPattern= Pattern.compile("^>.+\\n[ABCDEFGHIKLMNPQRSTVWXYZ-]+\\n?", Pattern.MULTILINE|Pattern.CASE_INSENSITIVE);
		Matcher fastaMatch = fastaPattern.matcher(s);
		while (fastaMatch.find()) {
			nbSequence++;
		}
		return nbSequence;
	}

    /**
	 * Construit une sequence query contenant toutes les séquences et fait appel au second constructeur pour créer 
	 * une liste de n Sequence où chaque indice correspond à une sequence de la query.
	 * 
	 * @param nbSeq correspond au nombre de séquence au format fasta
	 * @param s correspond à la séquence
	 * @param n correspond au nom de la séquence
	 * @param t correspond au type de la séquence (protéine ou adn)
	 */
	public Sequence(int nbSeq, String sequence, String enTete, TypeSeq typeSeq) {
		this.sequence= sequence;
		this.enTete = enTete;
		this.typeSeq = typeSeq;
		//Créer une liste de sequences à partir de toutes les sequences dans la query.
		for (int i=0; i<nbSeq; i++) {
			this.listSeq.add(new Sequence("Sequence_"+(i+1), "", typeSeq));
		}
	}

    /**
	 * modifie l'enTete de chaque séquences.
	 */
	public void setEnTeteAllSequence() {
		Pattern nomSeqPattern= Pattern.compile("^>.+\\n?", Pattern.MULTILINE);
		Matcher nomSeqMatch = nomSeqPattern.matcher(this.sequence); //pattern à tester dans la query
		for (int i=0; i<this.listSeq.size(); i++) {
			if (nomSeqMatch.find()) {
				//supprime les chevrons des enTetes des séquences.
				this.listSeq.get(i).setEnTete(nomSeqMatch.group().replace(">", ""));
			}
		}
	}

	/**
	 * modifie les séquences.
	 */
	public void setAllSequences() {
		Pattern seqPattern= Pattern.compile("^[ABCDEFGHIKLMNPQRSTVWXYZ-]+", Pattern.MULTILINE|Pattern.CASE_INSENSITIVE);
		Matcher seqMatch = seqPattern.matcher(this.sequence); //pattern à tester dans la query
		for (int i=0; i<this.listSeq.size(); i++) {
			if (seqMatch.find()) {
				this.listSeq.get(i).setSequence(seqMatch.group());
			}
		}
	}

    /**
     * Renvoie l'en-tête de la séquence.
     * @return l'en-tête de la séquence.
     */
    public String getEnTete() {
        return this.enTete;
    }

    /**
     * Modifie l'en-tête de la séquence.
     * @param enTete le nouveau en-tête de la séquence.
     */
    public void setEnTete(String enTete) {
        this.enTete = enTete;
    }

    /**
     * Retourne la séquence.
     * @return la chaîne de caractères correspondant à la séquence.
     */
    public String getSequence() {
        return this.sequence;
    }

    /**
     * Modifie la séquence.
     * @param sequence la nouvelle séquence.
     */
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    /**
     * Renvoie le type de la séquence.
     * @return le type de la séquence.
     */
    public TypeSeq getTypeSeq() {
        return this.typeSeq;
    }

    /**
     * Modifie le type de la séquence.
     * @param typeSeq le nouveau type de la séquence.
     */
    public void setTypeSeq(TypeSeq typeSeq) {
        this.typeSeq = typeSeq;
    }

    /**
     * Renvoie la séquence à l'indice donné.
     * @param indice l'indice de la séquence à renvoyer.
     * @return la séquence à l'indice donné.
    */
    public Sequence getSequenceAt(int indice) {
        return this.listSeq.get(indice);
    }

    /**
     * Retourne les infos pour chaque séquence de la liste.
     * @return les infos de toutes les séquences de la liste.
     */
    public String printAllSequence() {
        String infos = "";
        for (Sequence seq: this.listSeq){
            infos = infos + seq.toString() + "\n";
        }
        return infos;
    }

    @Override
    public String toString() {
        return "Sequence [enTete=" + this.enTete + ", sequence=" + this.sequence + ", typeSeq=" + this.typeSeq + "]";
    }
}