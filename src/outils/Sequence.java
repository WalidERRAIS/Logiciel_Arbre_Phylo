package outils;

import java.util.ArrayList;

public class Sequence {
    private String enTete;
    private String sequence;
    private TypeSeq typeSeq;
    
    public Sequence(String enTete, String sequence, TypeSeq typeSeq) {
        this.enTete = enTete;
        this.sequence = sequence;
        this.typeSeq = typeSeq;
    }

    public String getEnTete() {
        return this.enTete;
    }

    public void setEnTete(String enTete) {
        this.enTete = enTete;
    }

    public String getSequence() {
        return this.sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public TypeSeq getTypeSeq() {
        return this.typeSeq;
    }

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