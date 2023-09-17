package outils;

import java.util.ArrayList;

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
    private Node parent;


    /**
     * Construit un noeud sans enfant. Correspond aux feuilles de l'arbre.
     * @param sequence la séquence.
     */
    public Node(Sequence sequence) {
        this.sequence = sequence;
        this.enfant1 = null;
        this.enfant2 = null;
        this.parent = null;
        this.longueurBranche = 0.0f;
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
        this.longueurBranche = 0.0f;
        //enregistrer le nouveau cluster comme parent des deux enfants
        n1.setParent(this);
        n2.setParent(this);
    }

    
    // public ArrayList<Node> getFeuilles() {
    //     ArrayList<Node> feuilles = new ArrayList<>();
    //     collecterFeuilles(this, feuilles);
    //     return feuilles;
    // }
    
    // private void collecterFeuilles(Node node, ArrayList<Node> feuilles) {
    //     if (node.getEnfant1() == null && node.getEnfant2() == null) {
    //         // Si le nœud courant n'a pas d'enfants, c'est une feuille
    //         feuilles.add(node);
    //     } else {
    //         // Sinon, poursuivre la recherche dans les enfants
    //         if (node.getEnfant1() != null) {
    //             collecterFeuilles(node.getEnfant1(), feuilles);
    //         }
    //         if (node.getEnfant2() != null) {
    //             collecterFeuilles(node.getEnfant2(), feuilles);
    //         }
    //     }
    // }
    
    //version 2
    // public float getDistanceToNode(Node autreNoeud) {
    //     // Vérifier si l'autre nœud est une feuille et si les deux nœuds ont le même parent
    //     if (autreNoeud.estFeuille() && this.getParent() == autreNoeud.getParent()) {
    //         // Si les deux nœuds sont des feuilles et ont le même parent, la distance est égale à leur longueur de branche commune
    //         return this.getLongueurBranche() + autreNoeud.getLongueurBranche();
    //     }
    
    //     // Sinon, appeler la méthode récursive pour trouver la distance entre les nœuds en passant la liste des parents visités
    //     ArrayList<Node> parentsVisited = new ArrayList<>();
    //     return findDistanceRecursive(this, autreNoeud, parentsVisited, 0);
    // }
    
    // private float findDistanceRecursive(Node currentNode, Node targetNode, ArrayList<Node> parentsVisited, float lengthToCurrent) {
    //     if (currentNode == targetNode) {
    //         // Cas de base : si le nœud courant est le nœud cible, retourner la longueur de branche du nœud courant
    //         return lengthToCurrent;
    //     }
    
    //     // Rechercher le chemin vers le nœud cible dans les enfants du nœud courant
    //     if (currentNode.getEnfant1() != null && currentNode.getEnfant1() != this && !parentsVisited.contains(currentNode.getEnfant1())) {
    //         parentsVisited.add(currentNode);
    //         float distanceToEnfant1 = findDistanceRecursive(currentNode.getEnfant1(), targetNode, parentsVisited, lengthToCurrent + currentNode.getEnfant1().getLongueurBranche());
    //         if (distanceToEnfant1 >= 0) {
    //             // Si le nœud cible est trouvé dans le sous-arbre de l'enfant1, retourner la distance
    //             return distanceToEnfant1;
    //         }
    //     }
    
    //     if (currentNode.getEnfant2() != null && currentNode.getEnfant2() != this && !parentsVisited.contains(currentNode.getEnfant2())) {
    //         parentsVisited.add(currentNode);
    //         float distanceToEnfant2 = findDistanceRecursive(currentNode.getEnfant2(), targetNode, parentsVisited, lengthToCurrent + currentNode.getEnfant2().getLongueurBranche());
    //         if (distanceToEnfant2 >= 0) {
    //             // Si le nœud cible est trouvé dans le sous-arbre de l'enfant2, retourner la distance
    //             return distanceToEnfant2;
    //         }
    //     }
    
    //     // Si le nœud cible n'a pas été trouvé dans les sous-arbres et que le nœud courant a un parent, remonter les parents
    //     if (currentNode.getParent() != null && !parentsVisited.contains(currentNode.getParent())) {
    //         parentsVisited.add(currentNode.getParent());
    //         float distanceToParent = findDistanceRecursive(currentNode.getParent(), targetNode, parentsVisited, lengthToCurrent + currentNode.getLongueurBranche());
    //         if (distanceToParent >= 0) {
    //             // Si le nœud cible est trouvé dans le sous-arbre d'un parent, retourner la distance
    //             return distanceToParent;
    //         }
    //     }
    
    //     // Si le nœud cible n'a pas été trouvé dans les sous-arbres, retourner -1
    //     return -1;
    // }
    
    // public boolean estFeuille() {
    //     return (this.getEnfant1() == null && this.getEnfant2() == null);
    // }
    

    /**
     * Renvoie le parent d'un noeud.
     * @return le parent du noeud
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Modifie le parent d'un noeud
     * @param parent nouveau parent du noeud
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Retourne la divergence nette du noeud par rapport aux autres
     * @return la divergence nette
     */
    public Float getdivergenceNette(){
        return this.divergenceNette;
    }

    /**
     * Modifie la divergence nette du noeud par rapport aux autres
     * @param divNette la nouvelle divergence nette
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

    /**
     * Affiche le contenu d'un noeud.
     */
    @Override
    public String toString() {
        return "Node : \nenfant1=" + this.enfant1.sequence.toString() + "\nenfant2=" + this.enfant2.sequence.toString();
    }
}