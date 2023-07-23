package outils;

import java.io.PrintStream;
import java.util.ArrayList;

public class AlgoPhenetique{
    //liste des noeuds précédents
    private static ArrayList<Node> listNoeudPreced;
    //indice des noeuds avec la distance minimale
    private static int indiceNoeud1 = 0, indiceNoeud2 = 0;
    //racine de l'arbre
    private static Node racine;
    private static Float[][] matriceD; //matrice de distances
    private static ArrayList<Node> listNoeud;

    /**
     * Affiche la matrice des distances.
     * @param matrice la matrice des distances à afficher.
     */
    public static void affichageMatriceD(Float[][] matrice){
        //affichage matrice de distance
        for (int i=0; i<matrice.length; i++){
            for (int j=0; j<matrice[i].length; j++){
                System.out.print(matrice[i][j] + "  ");
            }
            System.out.println("");
        }
    }

    /**
    * Compare deux à deux les séquences et initialise la matrice des distances.
    * @param listSeq la liste des séquences à comparer.
    * @param matriceD la matrice des distances à initialiser.
    * @return la matrice des distances initialiser.
    */
    public static void initialiseMatriceD() {
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
    }

    /**
     * Créer un noeud pour chaque séquence et le stocke dans la liste des noeuds.
     * @param listSeq la liste des séquences.
     * @param listNoeud la liste des noeuds vide.
     * @return la liste des noeuds remplie.
     */
    public static void initialiseListNode(ArrayList<Sequence> listSeq){
        for (Sequence s : listSeq){
            listNoeud.add(new Node(s));
        }
    }

    /**
     * Renvoie la distance minimale de la matrice des distances et stocke les indices des noeuds
     * avec la distance min.
     * @param listNoeud la liste des noeuds.
     * @param matriceD la matrice des distances.
     * @return la distance minimale dans la matrice.
     * @see addCluster
     * @see removeCluster
     */
    public static Float minMatrice(Float[][] matrice){
        Float min = 999999999999.f;
        for (int i=0; i<matrice.length; i++){
            for (int j=0; j<matrice[i].length; j++){
                if (matrice[i][j]!=0.f && matrice[i][j]<min){
                    min = matrice[i][j];
                    //stocke les indices des noeuds avec distance minimale
                    indiceNoeud1 = i;
                    indiceNoeud2 = j;
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
    public static void addCluster(){
        listNoeud.add(new Node(listNoeud.get(indiceNoeud1), listNoeud.get(indiceNoeud2)));
    }

    /**
     * Retire les deux noeuds regroupés de la liste de noeuds.
     * @param indice1 indice du premier noeud.
     * @param indice2 indice du second noeud.
     * @param listNoeud la liste des noeuds.
     * @see minMatriceD
     * @see addCluster
     */
    public static void removeNode(){
        listNoeud.remove(indiceNoeud1);
        listNoeud.remove(indiceNoeud2-1);
    }


    /**
     * Retourne une chaîne de caractère au format Newick de l'arbre en partant de la racine.
     * @param racine
     * @return l'arbre au format Newick
     */
    public static String Newick(Node racine) {
        if (racine.getEnfant1()!=null && racine.getEnfant2()!=null) {
            String output = "";
            output += "(" + Newick(racine.getEnfant1()) + "," + Newick(racine.getEnfant2()) + ")";
            return output.replace("\n", ""); //supprime les retours à la ligne
        } else {
            return racine.getObjSequence().getEnTete();
        }
    }

    // /**
    //  * Renvoie la nouvelle matrice de distances après clustering.
    //  * @param listNoeud la nouvelle liste de noeuds.
    //  * @param matriceD la matrice de distances.
    //  * @return la nouvelle matrice calculée.
    //  */
    // public static Float[][] reCalculMatriceD() {
    //     //copie la précédente matrice de distances.
    //     Float[][] oldMatriceD = new Float[matriceD.length][];
    //     for (int i = 0; i < matriceD.length; i++) {
    //         oldMatriceD[i] = matriceD[i].clone();
    //     }
    //     //initialise matrice suivante
    //     matriceD = new Float[listNoeud.size()][listNoeud.size()];

    //     //i correspond au premier noeud de la comparaison.
    //     for (int i=0; i<listNoeud.size(); i++){
    //         //indice entete noeud i.
    //         int indiceI = 0;
    //         //indice entete enfants noeud i.
    //         int indiceIEnfant1 = 0;
    //         int indiceIEnfant2 = 0;

    //         //k correspond au deuxième noeud de la comparaison.
    //         for (int k=0; k<listNoeud.size(); k++){
    //             //indice entete noeud k.
    //             int indiceK = 0;
    //             //indice entete enfants noeud k.
    //             int indiceKEnfant1 = 0;
    //             int indiceKEnfant2 = 0;

    //             //si i et k sont le même noeud
    //             if (listNoeud.get(i)==listNoeud.get(k))
    //                     matriceD[i][k] = 0.f;
                
    //             //si les deux noeuds sont des séquences.
    //             else if (listNoeud.get(i).getObjSequence()!=null && listNoeud.get(k).getObjSequence()!=null){
    //                 // indiceI = indexOfEnTeteSeq(listNoeud.get(i).getObjSequence().getEnTete());
    //                 indiceI = listNoeudPreced.indexOf(listNoeud.get(i));
    //                 // indiceK = indexOfEnTeteSeq(listNoeud.get(k).getObjSequence().getEnTete());
    //                 indiceK = listNoeudPreced.indexOf(listNoeud.get(k));

    //                 //recupere la distance des noeuds dans la précédente matrice de distances.
    //                 matriceD[i][k] = oldMatriceD[indiceI][indiceK];
    //             }
                
    //             //si seulement i à des enfants 
    //             else if (listNoeud.get(i).getObjSequence()==null && listNoeud.get(k).getObjSequence()!=null){
    //                 indiceK = listNoeudPreced.indexOf(listNoeud.get(k));
    //                 //seulement i à des enfants et il est le dernier cluster formé 
    //                 if (listNoeud.get(listNoeud.size()-1)==listNoeud.get(i)){
    //                     indiceIEnfant1 = listNoeudPreced.indexOf(listNoeud.get(i).getEnfant1());
    //                     indiceIEnfant2 = listNoeudPreced.indexOf(listNoeud.get(i).getEnfant2());
    //                     matriceD[i][k] = (oldMatriceD[indiceIEnfant1][indiceK]+oldMatriceD[indiceIEnfant2][indiceK])/2;

    //                 }
    //                 //seulement i à des enfants mais n'est pas le dernier cluster formé
    //                 else{
    //                     indiceI = listNoeudPreced.indexOf(listNoeud.get(i));
    //                     matriceD[i][k] = (oldMatriceD[indiceI][indiceK]+oldMatriceD[indiceI][indiceK])/2;

    //                 }
                    
    //             }

    //             //si seulement k à des enfants
    //             else if (listNoeud.get(k).getObjSequence()==null && listNoeud.get(i).getObjSequence()!=null){
    //                 indiceI = listNoeudPreced.indexOf(listNoeud.get(i));
    //                 //seulement k à des enfants et il est le dernier cluster formé 
    //                 if (listNoeud.get(listNoeud.size()-1)==listNoeud.get(k)){
    //                     indiceKEnfant1 = listNoeudPreced.indexOf(listNoeud.get(k).getEnfant1());
    //                     indiceKEnfant2 = listNoeudPreced.indexOf(listNoeud.get(k).getEnfant2());
    //                     matriceD[i][k] = (oldMatriceD[indiceKEnfant1][indiceI]+oldMatriceD[indiceKEnfant2][indiceI])/2;

    //                 }
    //                 //seulement k à des enfants mais n'est pas le dernier cluster formé
    //                 else{
    //                     indiceK = listNoeudPreced.indexOf(listNoeud.get(k));
    //                     matriceD[i][k] = (oldMatriceD[indiceK][indiceI]+oldMatriceD[indiceK][indiceI])/2;

    //                 }
    //             }

    //             //les deux noeuds i et k ont des enfants
    //             else{
    //                 //les deux noeuds ont des enfants et i est le dernier cluster formé
    //                 if (listNoeud.get(listNoeud.size()-1)==listNoeud.get(i)){
    //                     indiceK = listNoeudPreced.indexOf(listNoeud.get(k));
    //                     indiceIEnfant1 = listNoeudPreced.indexOf(listNoeud.get(i).getEnfant1());
    //                     indiceIEnfant2 = listNoeudPreced.indexOf(listNoeud.get(i).getEnfant2());
    //                     matriceD[i][k] = (oldMatriceD[indiceIEnfant1][indiceK]+oldMatriceD[indiceIEnfant2][indiceK])/2;

    //                 }
    //                 //les deux noeuds ont des enfants et k est le dernier cluster formé
    //                 else if (listNoeud.get(listNoeud.size()-1)==listNoeud.get(k)){
    //                     indiceI = listNoeudPreced.indexOf(listNoeud.get(i));
    //                     indiceKEnfant1 = listNoeudPreced.indexOf(listNoeud.get(k).getEnfant1());
    //                     indiceKEnfant2 = listNoeudPreced.indexOf(listNoeud.get(k).getEnfant2());
    //                     matriceD[i][k] = (oldMatriceD[indiceKEnfant1][indiceI]+oldMatriceD[indiceKEnfant2][indiceI])/2;

    //                 }
    //                 //les deux noeuds ont des enfants et aucun des deux n'est le dernier cluster formé
    //                 else{
    //                     indiceI = listNoeudPreced.indexOf(listNoeud.get(i));
    //                     indiceK = listNoeudPreced.indexOf(listNoeud.get(k));
    //                     matriceD[i][k] = (oldMatriceD[indiceI][indiceK]+oldMatriceD[indiceI][indiceK])/2;

    //                 }

    //             }
    //         }
    //     }

    //     return matriceD;
    // }

    //-----------------------------Neighbor Joining-----------------------------------------
    public static class NJ {
        private static Float[][] matriceModifie; //matrice de distances modifie

        public static void nj(ArrayList<Sequence> listSeq){
            //initialise matrice distance et liste noeud
            matriceD = new Float[listSeq.size()][listSeq.size()];
            listNoeud = new ArrayList<Node>();
            initialiseListNode(listSeq);
            initialiseMatriceD();
            // calcul divergence nette des noeuds.
            calculDivergenceNette();
            
            System.out.println("matrice distance");
            affichageMatriceD(matriceD);
            System.out.println("divergence nette : ");
            for (Node n : listNoeud){
                System.out.println(n.getdivergenceNette());
            }
            //calcul distance modifie
            calculMatriceModifie();
            System.out.println("matrice distance modifie");
            affichageMatriceD(matriceModifie);

            //choisir distance modifie minimale 
            //on ne stocke pas la valeur min dans une variable car on ne l'a reutilise pas
            minMatrice(matriceModifie);
            //stocke valeur matrice distances des deux noeuds enfants pour le calcul de la branche
            Float distEnfants = matriceD[indiceNoeud1][indiceNoeud2];
            System.out.println("distance enfants : ");
            System.out.println(distEnfants);
            //créer un noeud parent avec les deux noeuds ayant la plus petite distance modifie
            addCluster();
            //retire les deux noeuds regroupés
            removeNode();
            //calcul longueur branche noeud parent avec ses enfants
            System.out.println(listNoeud.get(listNoeud.size()-1).toString());
            calculLongueurBrancheNJ(distEnfants,
            listNoeud.get(listNoeud.size()-1).getEnfant1().getdivergenceNette(),
            listNoeud.get(listNoeud.size()-1).getEnfant2().getdivergenceNette());
            System.out.println("distance node : "+listNoeud.get(listNoeud.size()-1).getLongueurBranche());
            System.out.println("distance enfant 1 : "+listNoeud.get(listNoeud.size()-1).getEnfant1().getLongueurBranche());
            System.out.println("distance enfant 2 : "+listNoeud.get(listNoeud.size()-1).getEnfant2().getLongueurBranche());
            //calcul nouvelle matrice distance noeud parent et les autres noeuds
            


        }

        public static void calculLongueurBrancheNJ(Float distEnfants, Float divEnfant1, Float divEnfant2){
            Float dEnfant1 = ((distEnfants / 2) + (divEnfant1-divEnfant2)) /(2*(listNoeud.size()-2));
            Float dEnfant2 = distEnfants - dEnfant1;
            listNoeud.get(listNoeud.size()-1).getEnfant1().setLongueurBranche(dEnfant1);
            listNoeud.get(listNoeud.size()-1).getEnfant2().setLongueurBranche(dEnfant2);
        }

        public static void calculMatriceModifie(){
            //initialise matrice distances modifie
            matriceModifie = new Float[listNoeud.size()][listNoeud.size()];
            //i correspond au premier noeud de la comparaison.
            for (int i=0; i<listNoeud.size(); i++){
                //k correspond au deuxième noeud de la comparaison.
                for (int k=0; k<listNoeud.size(); k++){
                    //si i et k sont le même noeud
                    if (listNoeud.get(i)==listNoeud.get(k))
                            matriceModifie[i][k] = 0.f;
                    //si les deux noeuds sont des séquences.
                    else {
                        //calcul distance modifie M(ij) = d(ij) - [r(i) + r(j)]/(N-2)
                        matriceModifie[i][k] = matriceD[i][k]-(listNoeud.get(i).getdivergenceNette() + listNoeud.get(k).getdivergenceNette())/(listNoeud.size()-2);
                    }
                }
            }
        }


        /**
         * Calcul la divergence nette de chaque noeud avec tous les autres. 
         * @param listNoeud la liste des noeuds.
         * @param matriceD la matrice de distances.
         */
        public static void calculDivergenceNette(){
           //initialise compteur divergence nette
            Float countDivNette = 0.f;
            for (int i=0; i<listNoeud.size(); i++){
                for (int j=0; j<matriceD[i].length; j++){
                    countDivNette += matriceD[i][j]; 
                }
                //on enregistre la divergence nette de la séquence i
                listNoeud.get(i).setdivergenceNette(countDivNette);
                //réinitialise le compteur de la divergence nette pour les prochaines séquences. 
                countDivNette = 0.f;                
            }
        }
    }

    //-----------------------------UPGMA-----------------------------------------

    /**
     * La classe UPGMA construit un arbre phylogénétique avec l'algorithme UPGMA. 
     */
    public static class Upgma {
        /**
         * méthode upgma reconstruit un arbre à partir d'une liste de séquence.
         * Initialise la liste de noeuds, la matrice de distances et regroupe les noeuds les plus proches
         * jusqu'à se qu'il ne reste plus qu'un noeud dans la liste.
         * @param listSeq
         */
        public static void upgma(ArrayList<Sequence> listSeq){
            //initialise matrice distance et liste noeud
            matriceD = new Float[listSeq.size()][listSeq.size()];
            listNoeud = new ArrayList<Node>();
            initialiseListNode(listSeq);
            initialiseMatriceD();
            Float min = 0.f;
            while (listNoeud.size()>=2){
                if (listNoeud.size()>2){
                    //copie la liste de noeud précédente pour le calcul de la nouvelle matrice de distances
                    listNoeudPreced = new ArrayList<Node>(listNoeud);
                    min = minMatrice(matriceD);

                    //créer un noeud parent avec les deux noeuds les plus proches
                    addCluster();
                    //retire les deux noeuds regroupés
                    removeNode();

                    //hauteur noeud parent égale moyenne des distances des deux enfants
                    listNoeud.get(listNoeud.size()-1).setLongueurBranche(calculLongueurBranche(min));

                    Upgma.reCalculMatriceD();

                }
                else{
                    //noeud racine parent des deux noeuds restants
                    min = minMatrice(matriceD);
                    racine = new Node(listNoeud.get(0), listNoeud.get(1));
                    listNoeud.add(racine);
                    //retire les deux noeuds regroupés
                    removeNode();
                    //hauteur noeud parent égale moyenne des distances des deux enfants
                    racine.setLongueurBranche(calculLongueurBranche(min));
                }
            }
            printArbre(System.out);
            System.out.print(Newick(racine));
        }

        /**
         * Calcul la longueur de la branche du noeud en faisant la moyenne des distances des deux enfants.
         * @param distance la distance entre les deux enfants.
         * @return la longueur de la branche du noeud. 
         */
        public static Float calculLongueurBranche(Float distance){
            return 0.5f*distance;
        }

        /**
         * Méthode récursive adaptée du site https://www.baeldung.com/java-print-binary-tree-diagram.
         * Dessine l'arbre en partant de la racine.
         * @return la chaîne de caractères représentant l'arbre phylogénétique.
         */
        public static String traversePreOrder() {
            if (racine == null) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            sb.append("root (Longueur branche "+racine.getLongueurBranche()+")");
        
            String pointerRight = "└──";
            String pointerLeft = (racine.getEnfant2() != null) ? "├──" : "└──";

        
            traverseNodes(sb, "", pointerLeft, racine.getEnfant1(), racine.getEnfant2() != null);
            traverseNodes(sb, "", pointerRight, racine.getEnfant2(), false);
        
            return sb.toString();
        }

        /**
         * Méthode récursive adaptée du site https://www.baeldung.com/java-print-binary-tree-diagram.
         * Dessine les sous-arbres.
         * @param sb la chaîne de caractère.
         * @param padding le pas.
         * @param pointer forme des branches.
         * @param node le noeud de l'arbre.
         * @param hasRightSibling si le noeud possède un noeud frère.
         */
        public static void traverseNodes(StringBuilder sb, String padding, String pointer, Node node, boolean hasRightSibling) {
            if (node != null) {
                sb.append("\n");
                sb.append(padding);
                sb.append(pointer);
                if (node.getObjSequence()!=null){
                    sb.append(node.getObjSequence().getEnTete());
                }
                else if (node.getObjSequence()==null){
                    //ajoute longueur branche du noeud
                    sb.append("node(Longueur branche "+node.getLongueurBranche()+")");
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

        /**
         * Affiche l'arbre.
         */
        public static void printArbre(PrintStream os) {
            os.println(traversePreOrder()+"\n");
        }

        /**
         * Renvoie la nouvelle matrice de distances après clustering.
         * @param listNoeud la nouvelle liste de noeuds.
         * @param matriceD la matrice de distances.
         * @return la nouvelle matrice calculée.
         */
        public static void reCalculMatriceD() {
            //copie la précédente matrice de distances.
            Float[][] oldMatriceD = new Float[matriceD.length][];
            for (int i = 0; i < matriceD.length; i++) {
                oldMatriceD[i] = matriceD[i].clone();
            }
            //initialise matrice suivante
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

                    //si i et k sont le même noeud
                    if (listNoeud.get(i)==listNoeud.get(k))
                            matriceD[i][k] = 0.f;
                    
                    //si les deux noeuds sont des séquences.
                    else if (listNoeud.get(i).getObjSequence()!=null && listNoeud.get(k).getObjSequence()!=null){
                        // indiceI = indexOfEnTeteSeq(listNoeud.get(i).getObjSequence().getEnTete());
                        indiceI = listNoeudPreced.indexOf(listNoeud.get(i));
                        // indiceK = indexOfEnTeteSeq(listNoeud.get(k).getObjSequence().getEnTete());
                        indiceK = listNoeudPreced.indexOf(listNoeud.get(k));

                        //recupere la distance des noeuds dans la précédente matrice de distances.
                        matriceD[i][k] = oldMatriceD[indiceI][indiceK];
                    }
                    
                    //si seulement i à des enfants 
                    else if (listNoeud.get(i).getObjSequence()==null && listNoeud.get(k).getObjSequence()!=null){
                        indiceK = listNoeudPreced.indexOf(listNoeud.get(k));
                        //seulement i à des enfants et il est le dernier cluster formé 
                        if (listNoeud.get(listNoeud.size()-1)==listNoeud.get(i)){
                            indiceIEnfant1 = listNoeudPreced.indexOf(listNoeud.get(i).getEnfant1());
                            indiceIEnfant2 = listNoeudPreced.indexOf(listNoeud.get(i).getEnfant2());
                            matriceD[i][k] = (oldMatriceD[indiceIEnfant1][indiceK]+oldMatriceD[indiceIEnfant2][indiceK])/2;

                        }
                        //seulement i à des enfants mais n'est pas le dernier cluster formé
                        else{
                            indiceI = listNoeudPreced.indexOf(listNoeud.get(i));
                            matriceD[i][k] = (oldMatriceD[indiceI][indiceK]+oldMatriceD[indiceI][indiceK])/2;

                        }
                        
                    }

                    //si seulement k à des enfants
                    else if (listNoeud.get(k).getObjSequence()==null && listNoeud.get(i).getObjSequence()!=null){
                        indiceI = listNoeudPreced.indexOf(listNoeud.get(i));
                        //seulement k à des enfants et il est le dernier cluster formé 
                        if (listNoeud.get(listNoeud.size()-1)==listNoeud.get(k)){
                            indiceKEnfant1 = listNoeudPreced.indexOf(listNoeud.get(k).getEnfant1());
                            indiceKEnfant2 = listNoeudPreced.indexOf(listNoeud.get(k).getEnfant2());
                            matriceD[i][k] = (oldMatriceD[indiceKEnfant1][indiceI]+oldMatriceD[indiceKEnfant2][indiceI])/2;

                        }
                        //seulement k à des enfants mais n'est pas le dernier cluster formé
                        else{
                            indiceK = listNoeudPreced.indexOf(listNoeud.get(k));
                            matriceD[i][k] = (oldMatriceD[indiceK][indiceI]+oldMatriceD[indiceK][indiceI])/2;

                        }
                    }

                    //les deux noeuds i et k ont des enfants
                    else{
                        //les deux noeuds ont des enfants et i est le dernier cluster formé
                        if (listNoeud.get(listNoeud.size()-1)==listNoeud.get(i)){
                            indiceK = listNoeudPreced.indexOf(listNoeud.get(k));
                            indiceIEnfant1 = listNoeudPreced.indexOf(listNoeud.get(i).getEnfant1());
                            indiceIEnfant2 = listNoeudPreced.indexOf(listNoeud.get(i).getEnfant2());
                            matriceD[i][k] = (oldMatriceD[indiceIEnfant1][indiceK]+oldMatriceD[indiceIEnfant2][indiceK])/2;

                        }
                        //les deux noeuds ont des enfants et k est le dernier cluster formé
                        else if (listNoeud.get(listNoeud.size()-1)==listNoeud.get(k)){
                            indiceI = listNoeudPreced.indexOf(listNoeud.get(i));
                            indiceKEnfant1 = listNoeudPreced.indexOf(listNoeud.get(k).getEnfant1());
                            indiceKEnfant2 = listNoeudPreced.indexOf(listNoeud.get(k).getEnfant2());
                            matriceD[i][k] = (oldMatriceD[indiceKEnfant1][indiceI]+oldMatriceD[indiceKEnfant2][indiceI])/2;

                        }
                        //les deux noeuds ont des enfants et aucun des deux n'est le dernier cluster formé
                        else{
                            indiceI = listNoeudPreced.indexOf(listNoeud.get(i));
                            indiceK = listNoeudPreced.indexOf(listNoeud.get(k));
                            matriceD[i][k] = (oldMatriceD[indiceI][indiceK]+oldMatriceD[indiceI][indiceK])/2;

                        }

                    }
                }
            }
        }
    }
}
