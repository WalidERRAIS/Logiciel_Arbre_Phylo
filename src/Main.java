public class Main {
    public static void main(String[] args) throws Exception {
        // String ATU79210 = "ATGC";
        // String AAK64201 = "TTGC";
        // String CAB96345 = "ACCG";
        // String CBW48591 = "TACG";
        String[][] tabSeq = {
            {"A","T","G","C"},
            {"T","T","G","C"},
            {"A","C","C","G"},
            {"T","A","C","G"}
        };
        Float[][] matriceD = new Float[tabSeq.length][tabSeq.length];
        Float countDiff= 0.f;
        //i correspond à la première séquence de la comparaison
        for (int i=0; i<tabSeq.length-1; i++){
            int k = i+1;
            //k correspond à la deuxième séquence de la comparaison
            for (; k<tabSeq.length; k++){
                //j correspond à la colonne des éléments comparés
                for (int j=0; j<tabSeq[i].length; j++){
                    if (tabSeq[i][j]!=tabSeq[k][j])
                        countDiff++;
                    // System.out.println(tabSeq[i][j]);
                    // System.out.println(tabSeq[k][j]);
                    // System.out.println("");
                }
                matriceD[i][k] = countDiff;
                //réinitialise le compteur pour les prochaines séquences à comparer
                countDiff = 0.f;
            }
        }
        for (int i=0; i<matriceD.length; i++){
            for (int j=0; j<matriceD[i].length; j++){
                System.out.print(matriceD[i][j] + " ");
            }
            System.out.println("");
        }
    }
}
