package outils;

import java.util.ArrayList;

/**
 * La classe Sequence permet de créer des séquences avec un en-tête et un type.
 */
public class Sequence {
    private String enTete;
    private String sequence;
    private TypeSeq typeSeq;
    
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
    public static Sequence getSequenceAt(ArrayList<Sequence> listSeq, int indice) {
        return listSeq.get(indice);
    }

    /**
     * Retourne les infos pour chaque séquence de la liste.
     * @return les infos de toutes les séquences de la liste.
     */
    public static String printAllSequence(ArrayList<Sequence> listSeq) {
        String infos = "";
        for (Sequence seq: listSeq){
            infos = infos + seq.toString() + "\n";
        }
        return infos;
    }

    @Override
    public String toString() {
        return "Sequence [enTete=" + this.enTete + ", sequence=" + this.sequence + ", typeSeq=" + this.typeSeq + "]";
    }
}