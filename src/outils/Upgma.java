package outils;

import java.io.PrintStream;
import java.util.ArrayList;

public class Upgma {
    private ArrayList<Node> listNoeudPreced;
    //indice des noeuds avec la distance minimale
    private int indiceNoeud1 = 0, indiceNoeud2 =0;
    private Node racine;

    public Upgma(ArrayList<Sequence> listSeq, Float[][] matriceD, ArrayList<Node> listNoeud){
        listNoeud = initialiseListNode(listSeq, listNoeud);
        // System.out.println("taille initiale liste noeud : "+listNoeud.size());
        matriceD = initialiseMatriceD(listNoeud, matriceD);
        Float min = 0.f;
        // affichageMatriceD(matriceD);
        while (listNoeud.size()>=2){
            if (listNoeud.size()>2){
                //copie la liste de noeud précédente pour le calcul de la nouvelle matrice de distances
                this.listNoeudPreced = new ArrayList<Node>(listNoeud);
                // affichageMatriceD(matriceD);
                min = minMatriceD(listNoeud, matriceD);

                //créer un noeud parent avec les deux noeuds les plus proches
                addCluster(this.indiceNoeud1, this.indiceNoeud2, listNoeud);
                //retire les deux noeuds regroupés
                removeNode(this.indiceNoeud1, this.indiceNoeud2-1, listNoeud);

                // System.out.println("distance minimale : "+min);
                // System.out.println("taille liste noeud apres clustering : "+listNoeud.size());
                //affichageArbre(listNoeud);
                // System.out.println("hauteur noeud avant cluster : "+listNoeud.get(listNoeud.size()-1).getHauteur());
                //hauteur noeud parent égale moyenne des distances des deux enfants
                listNoeud.get(listNoeud.size()-1).setHauteur(calculHauteur(min));
                // System.out.println("hauteur noeud apres cluster : "+listNoeud.get(listNoeud.size()-1).getHauteur());
                // System.out.println("");
                matriceD = reCalculMatriceD(listNoeud, matriceD);

            }
            else{
                //noeud racine parent des deux noeuds restants
                min = minMatriceD(listNoeud, matriceD);
                this.racine = new Node(listNoeud.get(0), listNoeud.get(1));
                listNoeud.add(this.racine);
                //retire les deux noeuds regroupés
                removeNode(this.indiceNoeud1, this.indiceNoeud2-1, listNoeud);

                // System.out.println("distance minimale : "+min);
                // System.out.println("taille liste noeud apres clustering : "+listNoeud.size());
                //affichageArbre(listNoeud);
                // System.out.println("hauteur noeud racine avant cluster : "+this.racine.getHauteur());
                //hauteur noeud parent égale moyenne des distances des deux enfants
                racine.setHauteur(calculHauteur(min));
                // System.out.println("hauteur noeud racine apres cluster : "+this.racine.getHauteur());
                // System.out.println("");
            }
        }
        printArbre(System.out);
    }

    
    /**
     * Calcul la hauteur du noeud.
     * @param hauteurEnfant1 la hauteur de l'enfant 1.
     * @param hauteurEnfant2 la hauteur de l'enfant 2.
     * @param distance la distance entre les deux enfants.
     * @return la hauteur calculée. 
     */
    public Float calculHauteur(Float distance){
        return 0.5f*(distance);
    }
    
    public String traversePreOrder() {
        if (this.racine == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("root (distance "+this.racine.getHauteur()+")");
    
        String pointerRight = "└──";
        String pointerLeft = (this.racine.getEnfant2() != null) ? "├──" : "└──";
    
        traverseNodes(sb, "", pointerLeft, this.racine.getEnfant1(), this.racine.getEnfant2() != null);
        traverseNodes(sb, "", pointerRight, this.racine.getEnfant2(), false);
    
        return sb.toString();
    }

    public void traverseNodes(StringBuilder sb, String padding, String pointer, Node node, boolean hasRightSibling) {
        if (node != null) {
            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);
            if (node.getObjSequence()!=null){
                sb.append(node.getObjSequence().getEnTete());
            }
            else if (node.getObjSequence()==null){
                //ajoute distance noeud
                sb.append("node (distance "+node.getHauteur()+")");
            }

            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightSibling) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }

            String paddingForBoth = paddingBuilder.toString();
            String pointerRight = "└──";
            String pointerLeft = (node.getEnfant2() != null) ? "├──" : "└──";

            traverseNodes(sb, paddingForBoth, pointerLeft, node.getEnfant1(), node.getEnfant2() != null);
            traverseNodes(sb, paddingForBoth, pointerRight, node.getEnfant2(), false);
        }
    }

    public void printArbre(PrintStream os) {
        os.print(traversePreOrder());
    }

    public void affichageArbre(ArrayList<Node> listNoeud){
        // String cross = "├─";
        // String cornerBot = "└─ ";
        // String cornerTop = "┌─ ";
        // String vertical = "│";
        // String space = " ";

        for (Node n : listNoeud){
            if (n.getEnfant1()!=null && n.getEnfant2()!=null){
                System.out.println(n.getEnfant1().getObjSequence().getEnTete());
                System.out.println(n.getEnfant2().getObjSequence().getEnTete());

            }
            else{
                System.out.println(n.getObjSequence().getEnTete());
            }
        }
    }

    /**
     * Créer un noeud pour chaque séquence et le stocke dans la liste des noeuds.
     * @param listSeq la liste des séquences.
     * @param listNoeud la liste des noeuds vide.
     * @return la liste des noeuds remplie.
     */
    public ArrayList<Node> initialiseListNode(ArrayList<Sequence> listSeq, ArrayList<Node> listNoeud){
        for (Sequence s : listSeq){
            listNoeud.add(new Node(s));
        }
        return listNoeud;
    }

    /**
     * Renvoie l'indice du noeud dans la précédente liste de noeuds avec l'entete donné.
     * @param entete l'entete de la séquence à chercher.
     * @return l'indice du noeud.
     */
    public int indexOfEnTeteSeq(String entete){
        int index = -1;
        for (int i=0; i<this.listNoeudPreced.size(); i++){
            //si le noeud est une séquence, on regarde si c'est le même entete.
            if (this.listNoeudPreced.get(i).getObjSequence()!=null){
                if (this.listNoeudPreced.get(i).getObjSequence().getEnTete()==entete){
                    index = i;
                    break;
                }
            }
            //si le noeud possède des enfants, on regarde quel enfant à le même entete.
            else{
                if (this.listNoeudPreced.get(i).getEnfant1().getObjSequence().getEnTete()==entete || this.listNoeudPreced.get(i).getEnfant2().getObjSequence().getEnTete()==entete){
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    /**
     * Renvoie la nouvelle matrice de distances après clustering.
     * @param listNoeud la nouvelle liste de noeuds.
     * @param matriceD la matrice de distances.
     * @return la nouvelle matrice calculée.
     */
    public Float[][] reCalculMatriceD(ArrayList<Node> listNoeud, Float[][] matriceD) {
        Float[][] oldMatriceD = new Float[matriceD.length][];
        for (int i = 0; i < matriceD.length; i++) {
            oldMatriceD[i] = matriceD[i].clone();
        }
        matriceD = new Float[listNoeud.size()][listNoeud.size()];

        //i correspond au premier noeud de la comparaison.
        for (int i=0; i<listNoeud.size(); i++){
            //indice entete noeud i.
            int indiceI = 0;
            //indice entete enfants noeud i.
            int indiceIEnfant1 = 0;
            int indiceIEnfant2 = 0;

            //k correspond au deuxième noeud de la comparaison.
            for (int k=0; k<listNoeud.size(); k++){
                //indice entete noeud k.
                int indiceK = 0;
                //indice entete enfants noeud k.
                int indiceKEnfant1 = 0;
                int indiceKEnfant2 = 0;

                if (listNoeud.get(i)==listNoeud.get(k))
                        matriceD[i][k] = 0.f;

                //si le noeud est une séquence.
                else if (listNoeud.get(i).getObjSequence()!=null && listNoeud.get(k).getObjSequence()!=null){
                    indiceI = indexOfEnTeteSeq(listNoeud.get(i).getObjSequence().getEnTete());
                    indiceK = indexOfEnTeteSeq(listNoeud.get(k).getObjSequence().getEnTete());
                    //recupere la distance des noeuds dans la précédente matrice de distances.
                    matriceD[i][k] = oldMatriceD[indiceI][indiceK];
                }
                //si le noeud i possède des enfants.
                else if (listNoeud.get(i).getObjSequence()==null && listNoeud.get(k).getObjSequence()!=null){
                    indiceIEnfant1 = indexOfEnTeteSeq(listNoeud.get(i).getEnfant1().getObjSequence().getEnTete());
                    indiceIEnfant2 = indexOfEnTeteSeq(listNoeud.get(i).getEnfant2().getObjSequence().getEnTete());
                    indiceK = indexOfEnTeteSeq(listNoeud.get(k).getObjSequence().getEnTete());
                    //on calcule la moyenne des distances entre k et les enfants de i.
                    matriceD[i][k] = (oldMatriceD[indiceIEnfant1][indiceK]+oldMatriceD[indiceIEnfant2][indiceK])/2;
                }
                //si le noeud k possède des enfants.
                else if (listNoeud.get(k).getObjSequence()==null && listNoeud.get(i).getObjSequence()!=null){
                    indiceKEnfant1 = indexOfEnTeteSeq(listNoeud.get(k).getEnfant1().getObjSequence().getEnTete());
                    indiceKEnfant2 = indexOfEnTeteSeq(listNoeud.get(k).getEnfant2().getObjSequence().getEnTete());
                    indiceI = indexOfEnTeteSeq(listNoeud.get(i).getObjSequence().getEnTete());
                    //on calcule la moyenne des distances entre i et les enfants de k.
                    matriceD[i][k] = (oldMatriceD[indiceI][indiceKEnfant1]+oldMatriceD[indiceI][indiceKEnfant2])/2;
                }
                //si i et k possèdent des enfants.
                else{
                    //on calcule la moyenne des distances entre les enfants du dernier cluster formé et le second noeud qui possède des enfants.
                    //trouver si le dernier cluster formé est le noeud i ou k, 
                    //on compare l'entete de l'enfant 1 du dernier cluster avec l'entete de l'enfant 1 de i et k.

                    //si entete des enfants du dernier cluster égale entete des enfants de i alors i est le dernier cluster formé.
                    //donc on calcule la moyenne des distances entre les enfants de i et le cluster k.
                    if(listNoeud.get(listNoeud.size()-1).getEnfant1().getObjSequence().getEnTete()==listNoeud.get(i).getEnfant1().getObjSequence().getEnTete()
                    && listNoeud.get(listNoeud.size()-1).getEnfant2().getObjSequence().getEnTete()==listNoeud.get(i).getEnfant2().getObjSequence().getEnTete()){
                        indiceIEnfant1 = indexOfEnTeteSeq(listNoeud.get(i).getEnfant1().getObjSequence().getEnTete());
                        indiceIEnfant2 = indexOfEnTeteSeq(listNoeud.get(i).getEnfant2().getObjSequence().getEnTete());
                        //besoin de l'indice que pour un des deux enfants car les deux enfants du cluster k sont au même indice
                        indiceKEnfant1 = indexOfEnTeteSeq(listNoeud.get(k).getEnfant1().getObjSequence().getEnTete());
                        matriceD[i][k] = (oldMatriceD[indiceIEnfant1][indiceKEnfant1]+oldMatriceD[indiceIEnfant2][indiceKEnfant1])/2;
                    }
                    //si entete des enfants du dernier cluster égale entete des enfants de k alors k est le dernier cluster formé.
                    //donc on calcule la moyenne des distances entre les enfants de k et le cluster i.
                    else if (listNoeud.get(listNoeud.size()-1).getEnfant1().getObjSequence().getEnTete()==listNoeud.get(k).getEnfant1().getObjSequence().getEnTete()
                    && listNoeud.get(listNoeud.size()-1).getEnfant2().getObjSequence().getEnTete()==listNoeud.get(k).getEnfant2().getObjSequence().getEnTete()){
                        indiceKEnfant1 = indexOfEnTeteSeq(listNoeud.get(k).getEnfant1().getObjSequence().getEnTete());
                        indiceKEnfant2 = indexOfEnTeteSeq(listNoeud.get(k).getEnfant2().getObjSequence().getEnTete());
                        //besoin de l'indice que pour un des deux enfants car les deux enfants du cluster i sont au même indice
                        indiceIEnfant1 = indexOfEnTeteSeq(listNoeud.get(i).getEnfant1().getObjSequence().getEnTete());
                        matriceD[i][k] = (oldMatriceD[indiceIEnfant1][indiceKEnfant1]+oldMatriceD[indiceIEnfant1][indiceKEnfant1])/2;
                    }
                }
            }
        }

        return matriceD;
    }


    /**
    * Compare deux à deux les séquences et initialise la matrice des distances.
    * @param listSeq la liste des séquences à comparer.
    * @param matriceD la matrice des distances à initialiser.
    * @return la matrice des distances initialiser.
    */
    public Float[][] initialiseMatriceD(ArrayList<Node> listNoeud, Float[][] matriceD) {
        //Initialisation compteur de distance
        Float countDiff = 0.f;
        //i correspond à la première séquence de la comparaison
        for (int i=0; i<listNoeud.size(); i++){
            //k correspond à la deuxième séquence de la comparaison
            for (int k=0; k<listNoeud.size(); k++){
                //j correspond à la position des éléments comparés
                for (int j=0; j<listNoeud.get(i).getObjSequence().getSequence().length(); j++){
                    if (listNoeud.get(i).getObjSequence().getSequence().charAt(j)!=listNoeud.get(k).getObjSequence().getSequence().charAt(j))
                        countDiff++;
                }
                //affecte la distance entre les séquences i et k
                matriceD[i][k] = countDiff;                
                //réinitialise le compteur pour les prochaines séquences à comparer
                countDiff = 0.f;
            }
        }
        return matriceD;
    }

    /**
     * Affiche la matrice des distances.
     * @param matriceD la matrice des distances à afficher.
     */
    public static void affichageMatriceD(Float[][] matriceD){
        //affichage matrice de distance
        for (int i=0; i<matriceD.length; i++){
            for (int j=0; j<matriceD[i].length; j++){
                System.out.print(matriceD[i][j] + "  ");
            }
            System.out.println("");
        }
    }
    
    /**
     * Renvoie la distance minimale de la matrice des distances.
     * @param listNoeud la liste des noeuds.
     * @param matriceD la matrice des distances.
     * @return la distance minimale dans la matrice.
     * @see addCluster
     * @see removeCluster
     */
    public Float minMatriceD(ArrayList<Node> listNoeud, Float[][] matriceD){
        Float min = 999999999999.f;
        for (int i=0; i<matriceD.length; i++){
            for (int j=0; j<matriceD[i].length; j++){
                if (matriceD[i][j]!=0.f && matriceD[i][j]<min){
                    min = matriceD[i][j];
                    //stocke les indices des noeuds avec distance minimale
                    this.indiceNoeud1 = i;
                    this.indiceNoeud2 = j;
                }
            }
        }
        return min;
    }

    /**
     * Regroupe les deux noeuds les plus proches de la liste dans un nouveau noeud.
     * @param indice1 indice du premier noeud.
     * @param indice2 indice du second noeud.
     * @param listNoeud la liste des noeuds.
     * @see minMatriceD
     * @see removeNode
     */
    public void addCluster(int indice1, int indice2, ArrayList<Node> listNoeud){
        listNoeud.add(new Node(listNoeud.get(indice1), listNoeud.get(indice2)));
    }

    /**
     * Retire les deux noeuds regroupés.
     * @param indice1 indice du premier noeud.
     * @param indice2 indice du second noeud.
     * @param listNoeud la liste des noeuds.
     * @see minMatriceD
     * @see addCluster
     */
    public void removeNode(int indice1, int indice2, ArrayList<Node> listNoeud){
        listNoeud.remove(indice1);
        listNoeud.remove(indice2);
    }
}
