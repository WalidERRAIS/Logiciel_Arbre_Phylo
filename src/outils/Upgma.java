package outils;

import java.util.ArrayList;

public class Upgma {
    public Upgma(ArrayList<Sequence> listSeq, Float[][] matriceD, ArrayList<Node> listNoeud){
        listNoeud = initialiseListNode(listSeq, listNoeud);
        matriceD = initialiseMatriceD(listSeq, matriceD);
        affichageMatriceD(matriceD);
        Float min = minMatriceD(listNoeud, matriceD);
        System.out.println(min);
        //affichageArbre(listNoeud);
        System.out.println("distance avant cluster : "+listNoeud.get(listNoeud.size()-1).getHauteur());
        //hauteur nouveu noeud égale moyenne des distances des deux enfants
        listNoeud.get(listNoeud.size()-1).setHauteur(calculHauteur(listNoeud.get(listNoeud.size()-1).getEnfant1().getHauteur(),
        listNoeud.get(listNoeud.size()-1).getEnfant2().getHauteur(), min));
        System.out.println("distance apres cluster : "+listNoeud.get(listNoeud.size()-1).getHauteur());

    }

    public Float calculHauteur(Float hauteurEnfant1, Float hauteurEnfant2, Float distance){
        return 0.5f*(hauteurEnfant1 + hauteurEnfant2+ distance);
    }
    

    public void affichageArbre(ArrayList<Node> listNoeud){
        String cross = "├─";
        String cornerBot = "└─ ";
        String cornerTop = "┌─ ";
        String vertical = "│";
        String space = " ";

        for (Node n : listNoeud){
            if (n.getEnfant1()!=null && n.getEnfant2()!=null){
                System.out.println(cornerTop+n.getEnfant1().getObjSequence().getEnTete());
                System.out.println(cornerBot+n.getEnfant2().getObjSequence().getEnTete());

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
    * Compare deux à deux les séquences et initialise la matrice des distances.
    * @param listSeq la liste des séquences à comparer.
    * @param matriceD la matrice des distances à initialiser.
    * @return la matrice des distances initialiser.
    */
    public Float[][] initialiseMatriceD(ArrayList<Sequence> listSeq, Float[][] matriceD) {
        //Initialisation compteur de distance
        Float countDiff = 0.f;
        //i correspond à la première séquence de la comparaison
        for (int i=0; i<listSeq.size()-1; i++){
            int k = i+1;
            //k correspond à la deuxième séquence de la comparaison
            for (; k<listSeq.size(); k++){
                //j correspond à la position des éléments comparés
                for (int j=0; j<listSeq.get(i).getSequence().length(); j++){
                    if (listSeq.get(i).getSequence().charAt(j)!=listSeq.get(k).getSequence().charAt(j))
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
                System.out.print(matriceD[i][j] + " ");
            }
            System.out.println("");
        }
    }
    
    /**
     * Renvoie la distance minimale de la matrice de distances.
     * @param listNoeud la liste des noeuds.
     * @param matriceD la matrice des distances.
     * @return la distance minimale dans la matrice.
     * @see addCluster
     * @see removeCluster
     */
    public Float minMatriceD(ArrayList<Node> listNoeud, Float[][] matriceD){
        Float min = 999999999999.f;
        //indice sequences avec distance minimale
        int k=0, l=0;
        for (int i=0; i<matriceD.length; i++){
            for (int j=0; j<matriceD[i].length; j++){
                if (matriceD[i][j]!=null && matriceD[i][j]<min){
                    min = matriceD[i][j];
                    k = i;
                    l = j;
                }
            }
        }
        //créer un noeud parent avec les deux noeuds les plus proches
        addCluster(k, l, listNoeud);
        //retire les deux noeuds regroupés
        removeNode(k, l-1, listNoeud);
        
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
