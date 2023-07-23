package outils;

/**
 * La classe Node permet de créer des noeuds. Un noeud est soit un objet Sequence
 * soit un noeud possédant deux noeuds enfants.
 * @see Sequence
 */
public class Node{
    private Sequence sequence;
    private Node enfant1;
    private Node enfant2;
    private Float longueurBranche;
    private Float divergenceNette; //divergence d'une séquence par rapport aux autres.


    /**
     * Construit un noeud sans enfant. Correspond aux feuilles de l'arbre.
     * @param sequence la séquence.
     */
    public Node(Sequence sequence) {
        this.sequence = sequence;
        this.enfant1 = null;
        this.enfant2 = null;
    }

    /**
     * Construit un noeud possédant deux noeuds enfants.
     * @param n1 noeud 1.
     * @param n2 noeud 2.
     */
    public Node(Node n1, Node n2){
        this.enfant1 = n1;
        this.enfant2 = n2;
        this.sequence = null;
    }

    /**
     * Retourne la divergence nette de la séquence par rapport aux autres
     * @return la divergence nette
     */
    public Float getdivergenceNette(){
        return this.divergenceNette;
    }

    /**
     * Modifie la divergence nette de la séquence par rapport aux autres
     */
    public void setdivergenceNette(Float divNette){
        this.divergenceNette = divNette;
    }

    /**
     * Renvoie l'objet séquence d'un noeud.
     * @return l'objet Sequence.
     */
    public Sequence getObjSequence() {
        return sequence;
    }

    /**
     * Modifie l'objet séquence d'un noeud.
     * @param sequence le nouvel objet Sequence.
     */
    public void setObjSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    /**
     * Renvoie l'enfant 1 du noeud.
     * @return noeud enfant 1.
     */
    public Node getEnfant1() {
        return this.enfant1;
    }

    /**
     * Modifie l'enfant 1 du noeud.
     * @param enfant1 le nouvel enfant 1.
     */
    public void setEnfant1(Node enfant1) {
        this.enfant1 = enfant1;
    }

    /**
     * Renvoie l'enfant 2 du noeud.
     * @return noeud enfant 2.
     */
    public Node getEnfant2() {
        return this.enfant2;
    }

    /**
     * Modifie l'enfant 2 du noeud.
     * @param enfant2 le nouvel enfant 2.
     */
    public void setEnfant2(Node enfant2) {
        this.enfant2 = enfant2;
    }

    /**
     * Renvoie la longueur de la branche.
     * @return longueur de la branche.
     */
    public Float getLongueurBranche() {
        return this.longueurBranche;
    }
    
    /**
     * Modifie la longueur de la branche.
     * @param longueurBranche la nouvelle longueur.
     */
    public void setLongueurBranche(Float longueurBranche) {
        this.longueurBranche = longueurBranche;
    }

    @Override
    public String toString() {
        return "Node : \nenfant1=" + enfant1.sequence.toString() + "\nenfant2=" + enfant2.sequence.toString();
    }
}