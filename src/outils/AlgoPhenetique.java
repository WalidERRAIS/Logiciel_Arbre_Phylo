package outils;

import java.io.PrintStream;
import java.util.ArrayList;

public class AlgoPhenetique{

    public static class NJ {

    }



    /**
     * La classe UPGMA construit un arbre phylogénétique avec l'algorithme UPGMA. 
     */
    public static class Upgma {
        //liste des noeuds précédents
        private static ArrayList<Node> listNoeudPreced;
        //indice des noeuds avec la distance minimale
        private static int indiceNoeud1 = 0, indiceNoeud2 = 0;
        //racine de l'arbre
        private static Node racine;

        /**
         * Construit un arbre à partir d'une liste de séquence.
         * Initialise la liste de noeuds, la matrice de distances et regroupe les noeuds les plus proches
         * jusqu'à se qu'il ne reste plus qu'un noeud dans la liste.
         * @param listSeq
         */
        public static void upgma(ArrayList<Sequence> listSeq){
            //initialise matrice distance et liste noeud
            Float[][] matriceD = new Float[listSeq.size()][listSeq.size()];
            ArrayList<Node> listNoeud = new ArrayList<Node>();
            listNoeud = initialiseListNode(listSeq, listNoeud);
            matriceD = initialiseMatriceD(listNoeud, matriceD);
            Float min = 0.f;
            while (listNoeud.size()>=2){
                if (listNoeud.size()>2){
                    //copie la liste de noeud précédente pour le calcul de la nouvelle matrice de distances
                    Upgma.listNoeudPreced = new ArrayList<Node>(listNoeud);
                    min = minMatriceD(listNoeud, matriceD);

                    //créer un noeud parent avec les deux noeuds les plus proches
                    addCluster(Upgma.indiceNoeud1, Upgma.indiceNoeud2, listNoeud);
                    //retire les deux noeuds regroupés
                    removeNode(Upgma.indiceNoeud1, Upgma.indiceNoeud2-1, listNoeud);

                    //hauteur noeud parent égale moyenne des distances des deux enfants
                    listNoeud.get(listNoeud.size()-1).setLongueurBranche(calculLongueurBranche(min));

                    matriceD = reCalculMatriceD(listNoeud, matriceD);

                }
                else{
                    //noeud racine parent des deux noeuds restants
                    min = minMatriceD(listNoeud, matriceD);
                    Upgma.racine = new Node(listNoeud.get(0), listNoeud.get(1));
                    listNoeud.add(Upgma.racine);
                    //retire les deux noeuds regroupés
                    removeNode(Upgma.indiceNoeud1, Upgma.indiceNoeud2-1, listNoeud);
                    //hauteur noeud parent égale moyenne des distances des deux enfants
                    racine.setLongueurBranche(calculLongueurBranche(min));
                }
            }
            printArbre(System.out);
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
            if (Upgma.racine == null) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            sb.append("root (Longueur branche "+Upgma.racine.getLongueurBranche()+")");
        
            String pointerRight = "└──";
            String pointerLeft = (Upgma.racine.getEnfant2() != null) ? "├──" : "└──";

        
            traverseNodes(sb, "", pointerLeft, Upgma.racine.getEnfant1(), Upgma.racine.getEnfant2() != null);
            traverseNodes(sb, "", pointerRight, Upgma.racine.getEnfant2(), false);
        
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
         * Créer un noeud pour chaque séquence et le stocke dans la liste des noeuds.
         * @param listSeq la liste des séquences.
         * @param listNoeud la liste des noeuds vide.
         * @return la liste des noeuds remplie.
         */
        public static ArrayList<Node> initialiseListNode(ArrayList<Sequence> listSeq, ArrayList<Node> listNoeud){
            for (Sequence s : listSeq){
                listNoeud.add(new Node(s));
            }
            return listNoeud;
        }

        /**
         * Renvoie la nouvelle matrice de distances après clustering.
         * @param listNoeud la nouvelle liste de noeuds.
         * @param matriceD la matrice de distances.
         * @return la nouvelle matrice calculée.
         */
        public static Float[][] reCalculMatriceD(ArrayList<Node> listNoeud, Float[][] matriceD) {
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
                        indiceI = Upgma.listNoeudPreced.indexOf(listNoeud.get(i));
                        // indiceK = indexOfEnTeteSeq(listNoeud.get(k).getObjSequence().getEnTete());
                        indiceK = Upgma.listNoeudPreced.indexOf(listNoeud.get(k));

                        //recupere la distance des noeuds dans la précédente matrice de distances.
                        matriceD[i][k] = oldMatriceD[indiceI][indiceK];
                    }
                    
                    //si seulement i à des enfants 
                    else if (listNoeud.get(i).getObjSequence()==null && listNoeud.get(k).getObjSequence()!=null){
                        indiceK = Upgma.listNoeudPreced.indexOf(listNoeud.get(k));
                        //seulement i à des enfants et il est le dernier cluster formé 
                        if (listNoeud.get(listNoeud.size()-1)==listNoeud.get(i)){
                            indiceIEnfant1 = Upgma.listNoeudPreced.indexOf(listNoeud.get(i).getEnfant1());
                            indiceIEnfant2 = Upgma.listNoeudPreced.indexOf(listNoeud.get(i).getEnfant2());
                            matriceD[i][k] = (oldMatriceD[indiceIEnfant1][indiceK]+oldMatriceD[indiceIEnfant2][indiceK])/2;

                        }
                        //seulement i à des enfants mais n'est pas le dernier cluster formé
                        else{
                            indiceI = Upgma.listNoeudPreced.indexOf(listNoeud.get(i));
                            matriceD[i][k] = (oldMatriceD[indiceI][indiceK]+oldMatriceD[indiceI][indiceK])/2;

                        }
                        
                    }

                    //si seulement k à des enfants
                    else if (listNoeud.get(k).getObjSequence()==null && listNoeud.get(i).getObjSequence()!=null){
                        indiceI = Upgma.listNoeudPreced.indexOf(listNoeud.get(i));
                        //seulement k à des enfants et il est le dernier cluster formé 
                        if (listNoeud.get(listNoeud.size()-1)==listNoeud.get(k)){
                            indiceKEnfant1 = Upgma.listNoeudPreced.indexOf(listNoeud.get(k).getEnfant1());
                            indiceKEnfant2 = Upgma.listNoeudPreced.indexOf(listNoeud.get(k).getEnfant2());
                            matriceD[i][k] = (oldMatriceD[indiceKEnfant1][indiceI]+oldMatriceD[indiceKEnfant2][indiceI])/2;

                        }
                        //seulement k à des enfants mais n'est pas le dernier cluster formé
                        else{
                            indiceK = Upgma.listNoeudPreced.indexOf(listNoeud.get(k));
                            matriceD[i][k] = (oldMatriceD[indiceK][indiceI]+oldMatriceD[indiceK][indiceI])/2;

                        }
                    }

                    //les deux noeuds i et k ont des enfants
                    else{
                        //les deux noeuds ont des enfants et i est le dernier cluster formé
                        if (listNoeud.get(listNoeud.size()-1)==listNoeud.get(i)){
                            indiceK = Upgma.listNoeudPreced.indexOf(listNoeud.get(k));
                            indiceIEnfant1 = Upgma.listNoeudPreced.indexOf(listNoeud.get(i).getEnfant1());
                            indiceIEnfant2 = Upgma.listNoeudPreced.indexOf(listNoeud.get(i).getEnfant2());
                            matriceD[i][k] = (oldMatriceD[indiceIEnfant1][indiceK]+oldMatriceD[indiceIEnfant2][indiceK])/2;

                        }
                        //les deux noeuds ont des enfants et k est le dernier cluster formé
                        else if (listNoeud.get(listNoeud.size()-1)==listNoeud.get(k)){
                            indiceI = Upgma.listNoeudPreced.indexOf(listNoeud.get(i));
                            indiceKEnfant1 = Upgma.listNoeudPreced.indexOf(listNoeud.get(k).getEnfant1());
                            indiceKEnfant2 = Upgma.listNoeudPreced.indexOf(listNoeud.get(k).getEnfant2());
                            matriceD[i][k] = (oldMatriceD[indiceKEnfant1][indiceI]+oldMatriceD[indiceKEnfant2][indiceI])/2;

                        }
                        //les deux noeuds ont des enfants et aucun des deux n'est le dernier cluster formé
                        else{
                            indiceI = Upgma.listNoeudPreced.indexOf(listNoeud.get(i));
                            indiceK = Upgma.listNoeudPreced.indexOf(listNoeud.get(k));
                            matriceD[i][k] = (oldMatriceD[indiceI][indiceK]+oldMatriceD[indiceI][indiceK])/2;

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
        public static Float[][] initialiseMatriceD(ArrayList<Node> listNoeud, Float[][] matriceD) {
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
        public static Float minMatriceD(ArrayList<Node> listNoeud, Float[][] matriceD){
            Float min = 999999999999.f;
            for (int i=0; i<matriceD.length; i++){
                for (int j=0; j<matriceD[i].length; j++){
                    if (matriceD[i][j]!=0.f && matriceD[i][j]<min){
                        min = matriceD[i][j];
                        //stocke les indices des noeuds avec distance minimale
                        Upgma.indiceNoeud1 = i;
                        Upgma.indiceNoeud2 = j;
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
        public static void addCluster(int indice1, int indice2, ArrayList<Node> listNoeud){
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
        public static void removeNode(int indice1, int indice2, ArrayList<Node> listNoeud){
            listNoeud.remove(indice1);
            listNoeud.remove(indice2);
        }
    }
}
