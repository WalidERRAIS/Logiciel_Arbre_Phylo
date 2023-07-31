import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import outils.AlgoPhenetique;
import outils.Sequence;
import outils.TypeSeq;
import outils.AlgoPhenetique.Upgma;
import outils.AlgoPhenetique.NJ;


import interfaceGraphique.Menu;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/*
 * To Do:
 * - connecter NJ au bouton reconstruction
 * - méthode poids moyen enracinement
 * - alignement clustal omega
 * - connecter alignement au bouton alignement
 * - ajouter sauvegarde fichier alignement
 * - ajouter sauvegarde resultat arbre
 * 
 * - remplacer par la suite mon calcul des distances (différences entre les séquences ) par la formule de la distance
 *  observée (S = M/L et D = 1 - S)
 * - d’appliquer une correction aux distances avec le modèle Jukes-Cantor
 * 
 * - ajouter recuperation sequences en ligne
 * 
 */
public class Main {
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        Menu menu = new Menu();
        menu.setVisible(true);
        //initialise sequences
        // Sequence seq1 = new Sequence("seq1", "ATGC", TypeSeq.ADN);
        // Sequence seq2 = new Sequence("seq2", "TTGC", TypeSeq.ADN);
        // Sequence seq3 = new Sequence("seq3", "ACCG", TypeSeq.ADN);
        // Sequence seq4 = new Sequence("seq4", "TACG", TypeSeq.ADN);
        // //initialise liste sequence
        // ArrayList<Sequence> listSeq = new ArrayList<Sequence>(List.of(seq1,seq2,seq3,seq4));
        
        // // test 2
        // Sequence seqA = new Sequence("seqA", "ATCGTGGTACTG", TypeSeq.ADN);
        // Sequence seqB = new Sequence("seqB", "CCGGAGAACTAG", TypeSeq.ADN);
        // Sequence seqC = new Sequence("seqC", "AACGTGCTACTG", TypeSeq.ADN);
        // Sequence seqD = new Sequence("seqD", "ATGGTGAAAGTG", TypeSeq.ADN);
        // Sequence seqE = new Sequence("seqE", "CCGGAAAACTTG", TypeSeq.ADN);
        // Sequence seqF = new Sequence("seqF", "TGGCCCTGTATC", TypeSeq.ADN);
        // //initialise liste sequence
        // ArrayList<Sequence> listSeq2 = new ArrayList<Sequence>(List.of(seqA,seqB,seqC,seqD, seqE, seqF));

        //test 2 bis
        // Sequence seqA = new Sequence("seqA", "ATGCTACGATGCTACGATGCTACGATG", TypeSeq.ADN);
        // Sequence seqB = new Sequence("seqB", "TCGTCGGTCACGACGATTGCTACGATC", TypeSeq.ADN);
        // Sequence seqC = new Sequence("seqC", "TCCACGGACGCGACAGTACGATCGATG", TypeSeq.ADN);
        // Sequence seqD = new Sequence("seqD", "CACACGGTCACGACGATAATAGACTCC", TypeSeq.ADN);
        // //initialise liste sequence
        // ArrayList<Sequence> listSeq2 = new ArrayList<Sequence>(List.of(seqA,seqB,seqC,seqD));

        // System.out.println("NJ results :");
        // String resNJ = NJ.nj(listSeq2);
        // System.out.println("");
        // System.out.println("UPGMA results :");
        // String resUpgma = Upgma.upgma(listSeq2);

        // //test 3
        // Sequence HIV_1_group_N = new Sequence("HIV_1_group_N", "-------------ATGGGAACAGAGCTTAAAGCCCTGTGTTAAATTAA-CCCCA----TTATGTGTAA-----CTATGCTTTGTAACAATAGCAATGGG-------AATAGTGCAGGGAATAGTACT------------ACCAATAGGA---C-AGAGGATCTAGAAGACAGACAAAT---------------------GAA-----AAATTGCTCATTCAATATAACCACTGAGATAAGAGATAGAAAGAAGCAAGTTTACTCTCTGTTTTATGTAGAAGATGTAGTGCCAATCAAAGATGGGACTGACAATAATACATATAG-GCTAATAAATTGTAATACCACAGCTGTGACACAAGCTTGTCCTAAGAC", TypeSeq.ADN);
        // Sequence HIV_1_group_O = new Sequence("HIV_1_group_O", "---GGTAGAACAAATGCAGGAAGACATTATTAGCTTATGGGAACAGAG-CTTCAAACCTTGTGTGCAA-----ATGACTTTTCTGTGTGTACAAATGA--------ATTGTCACATTGTAGAAGAA-------------ACCAACAGCT---C-A------TCAGCAGAGAAACCTTT---------------------ACA-----A---TGTGAGTTTAATGTAACCACTGTTGTCAAGGACAAAAAAGAGAAAAAACAGGCTCTATTTTATAGATCAGATTTAATGAAATT---AGATGAAACAAACGAAACAATGTATAC-ATTAATTAATTGTAACTCCACAACCATTAAGCAAGCCTGTCCAAAGAT", TypeSeq.ADN);
        // Sequence HIV_1_subtype_A = new Sequence("HIV_1_subtype_A", "------AGAGCAGATGCATACAGATATAATCAGTCTATGGGATCAGAG-CCTAAAGCCATGTGTAGAA-----TTAACCCCTCTCTGCGTTACTTTAGATTGTCTAAACGCCACCCTCAATGCCACC------------GCCCCCAATG---T-CACCAATGACATGGAAGGAGAAAT---------------------GAA-----AAACTGCTCTTACAATATAACCACAGAATTAAAGGATAAGAAACAGCAAGTGTATTCACTTTTTTATAAGCTTGATGTAGTACAAATTAATGAAAAGAATAAAACAAACAAGTATAG-ATTAATAAATTGTAATACCTCAGCCATTAC------------------", TypeSeq.ADN);
        // Sequence HIV_1_subtype_B = new Sequence("HIV_1_subtype_B", "------AGAGCAGATGCAGGAGGATGTAATCAGTTTATGGGATCAAAG-TCTAAAGCCATGTGTAAAA-----TTAACTCCTCTCTGCGTTACTTTACATTGTACCAATGTCTCTTTAAATAGTACCAATGCAAATTATACCAGTGACT---CTAAAGGAATCGGAAATATAACAGAT---------------------GAAGCAAGAAACTGTTCTTTTAATATAACCACAGAATTAATAGATAAGAAGCAGAAAGTTTATGCACTTTTTTATAAGCTTGATATAGTACAAAT---GGGAGATAATAATAGTAGTGAGTATAG-ATTAATAAATTGTAA---------------------------------", TypeSeq.ADN);
        // Sequence HIV_1_subtype_C = new Sequence("HIV_1_subtype_C", "------GGATCAGATGCATGAGGATATAATCAGTTTATGGGATGAAAG-CCTAAAGCCATGTGTAACG-----TTAACCCCACTCTGTGTCACTTTATATTGTAGTAATGTTAATAATACCAGTAGTAATGTTAATAGTACCAGTAGTAATGTTAATAGTACCAGTAATGGTAATGATACCACCCAGGAGGGCATAGAAGAGATAAAAAATTGCTCTTTCAATGCAACCACAGAAATAAAAGATAAGCACCGGAAAGAGCATGCACTTTTTTATAGACTAGACATAGTGCCAATTAGTAATAACACCTATAG-------------------------------------------------------------", TypeSeq.ADN);
        // Sequence HIV_1_subtype_D = new Sequence("HIV_1_subtype_D", "------GGAGCAGATGCATGAGGATATCATCAGTTTATGGGATCAAAG-CCTAAAACCATGTGTAAAA-----TTAACCCCACTCTGTGTCACTTTAAACTGCACTGATGCAAGGAGGAATGAGACT------------AGGAATAATA---CAGGAATGGAAAACAATGATCAAATA---------------------GAAATGAAAAACTGCTCTTTCAATATAACCACAAAATTAATAGATAAGAAGAAGCAAGTACATGCACTTTTTTATAGACTTGATGTGGTACAAATAGATAATGAGACTAGTAATAGCAACTATAGCAACTATAGATTAATAAATTGCAA-------------------------", TypeSeq.ADN);
        // Sequence HIV_2_subtype_A = new Sequence("HIV_2_subtype_A", "---TGTCAAGTTAACACCCTTATGTGTAGTAATGAACTGTAGCAAACTCAGTAACACTGCAAATACAACAGCTGCACCCACAGCAAACGCAACAACCACAT-CCGCAAACACAACAACCACAACAACAAAAAATGT--ATGGATAAATGAGACTTCTCCATGCATGCGCGCAGACAACTGCTCAGGATTAGGGGGGGAAGAGATGGTCACTTGTCACTTCAATATGACAGGGTTAGAGAGAGATAAGAAAAAGGCGTATAATGAAACATGGTACTCAAAGGATGTAGTATGTGA---ATATGAAAACCA----------------------------------------------------------------", TypeSeq.ADN);
        // Sequence HIV_2_subtype_B = new Sequence("HIV_2_subtype_B", "ATGTGTTAAATTAACCCCACTATGTGTAACAATGAATTGTAGTAACCT---TAACA---------------------CCACAGCCAACACCACCACAGC---CAACAACATCACAGCCTCTAAGAACATAAGTA------TGCTCAATGGAAATGACTCATGTATCCGAGATGACAACTGCACAGGTATAGAATTTGAGAACATGGTAACATGTCGATTCAATATGACAGGATTAAAAGTAGATGAACCGAAAACGTATAATGACACCTGGTATGCAGAAGATGTACAGTGTAAT--GGGAGCAGATGCCATATAAAAACCTGTAACACATCAAT--------------------------------------", TypeSeq.ADN);
        // Sequence SIV_chpz = new Sequence("SIV_chpz", "------GTCACAAATGCAAGAAGATATCATTAGCCTATGGGAGCAAAG-TCTAAAGCCATGTGTAAAG-----TTAACCCCTTTATGTGTAACTCTAAATTGCTCTACTGCTATTTTTAGAGCAAACAGGAC------TAGCACCAACACAACTACACCTAGCACCATCGCAACTAGTCCTACAACTGATACAATTTATGAAATGAAAAATTGCTCTTTTAATGTAACAACAGAATTAAGGGATAAAAAGAAACAGGTATATTCTTTATTTTATGTAGATGATGTAGTGAAACTTAATGATGGAGATTCAACAAATAG-------------------------------------------------------", TypeSeq.ADN);
        // Sequence SIV_mac = new Sequence("SIV_mac", "TTGTGTAAAATTATCCCCATTATGCATTACTATGAGATGCAATAAA---AGTGAGA---CAGATAGATGGGGATTGACAAAATCAATAACAACAACAGCAT-CAACAACATCAACGACAGCATCAGCAAGAGTAGA--CATGGTCAATGAGACTAGTTCTTGTATAGCCCAGGATAATTGCGCAGGCTTGGAACAAGAGCAAATGATAAGCTGTAAATTCAACATGACAGGGTTAAATAGAGACAAGAAAAAAGAGTACAATGAAACCTGGTACTCTGCAGATTTGGTATGTGA---ACAAGGGAATAACAC-------------------------------------------------------------", TypeSeq.ADN);

        // //initialise liste sequence
        // ArrayList<Sequence> listSeq3 = new ArrayList<Sequence>(List.of(HIV_1_group_N, HIV_1_group_O, HIV_1_subtype_A, HIV_1_subtype_B, HIV_1_subtype_C, HIV_1_subtype_D, HIV_2_subtype_A, HIV_2_subtype_B, SIV_chpz, SIV_mac));
        // System.out.println("NJ results :");
        // String resNJ3 = NJ.nj(listSeq3);
        // System.out.println("");
        // System.out.println("UPGMA results :");
        // String resUpgma3 = Upgma.upgma(listSeq3);

        
        // try {
        //     ProcessBuilder builder = new ProcessBuilder("python",
        //      "src\\interfaceGraphique\\DrawTree.py",
        //       resNJ3, resUpgma3);
        //     Process process = builder.start();

        //     BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        //     BufferedReader readers = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        //     String lines=null;
        //     while ((lines=reader.readLine())!=null){
        //         System.out.println(lines);
        //     }
        //     while ((lines=readers.readLine())!=null){
        //         System.out.println("error lines"+lines);
        //     }
        // }
        // catch (Exception e){
        //     e.printStackTrace();
        // }
    }
}