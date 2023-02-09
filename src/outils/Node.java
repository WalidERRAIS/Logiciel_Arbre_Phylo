package outils;

public class Node{
    private Sequence sequence;
    private Node enfant1;
    private Node enfant2;
    private Float hauteur;

    public Node(Sequence sequence) {
        this.sequence = sequence;
        this.enfant1 = null;
        this.enfant2 = null;
        this.hauteur = 0.f;
    }

    public Node(Node n1, Node n2){
        this.enfant1 = n1;
        this.enfant2 = n2;
    }
    
    public Sequence getObjSequence() {
        return sequence;
    }

    public void setObjSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public Node getEnfant1() {
        return this.enfant1;
    }

    public void setEnfant1(Node enfant1) {
        this.enfant1 = enfant1;
    }

    public Node getEnfant2() {
        return this.enfant2;
    }

    public void setEnfant2(Node enfant2) {
        this.enfant2 = enfant2;
    }

    public Float getHauteur() {
        return this.hauteur;
    }
    
    public void setHauteur(Float hauteur) {
        this.hauteur = hauteur;
    }

    

    @Override
    public String toString() {
        return "Node : \nenfant1=" + enfant1.sequence.getEnTete() + "\nenfant2=" + enfant2.sequence.getEnTete();
    }

    
    

}