//jython utilise python 2.7 et biopython 1.76 dernière version qui supporte python 2.7
import java.util.ArrayList;
import java.util.List;
// import org.python.util.PythonInterpreter;

import outils.Node;
import outils.Sequence;
import outils.TypeSeq;
import outils.Upgma;


public class Main {
    public static void main(String[] args) throws Exception {
        //initialise sequences
        Sequence seq1 = new Sequence("seq1", "ATGC", TypeSeq.ADN);
        Sequence seq2 = new Sequence("seq2", "TTGC", TypeSeq.ADN);
        Sequence seq3 = new Sequence("seq3", "ACCG", TypeSeq.ADN);
        Sequence seq4 = new Sequence("seq4", "TACG", TypeSeq.ADN);
        //initialise liste sequence
        ArrayList<Sequence> listSeq = new ArrayList<Sequence>(List.of(seq1,seq2,seq3,seq4));
        //initialise matrice distance et liste noeud
        Float[][] matriceD = new Float[listSeq.size()][listSeq.size()];
        ArrayList<Node> listNoeud = new ArrayList<Node>();
        
        //test 2
        Sequence seqA = new Sequence("seqA", "ATCGTGGTACTG", TypeSeq.ADN);
        Sequence seqB = new Sequence("seqB", "CCGGAGAACTAG", TypeSeq.ADN);
        Sequence seqC = new Sequence("seqC", "AACGTGCTACTG", TypeSeq.ADN);
        Sequence seqD = new Sequence("seqD", "ATGGTGAAAGTG", TypeSeq.ADN);
        Sequence seqE = new Sequence("seqE", "CCGGAAAACTTG", TypeSeq.ADN);
        Sequence seqF = new Sequence("seqF", "TGGCCCTGTATC", TypeSeq.ADN);

        //initialise liste sequence
        ArrayList<Sequence> listSeq2 = new ArrayList<Sequence>(List.of(seqA,seqB,seqC,seqD, seqE, seqF));
        //initialise matrice distance et liste noeud
        Float[][] matriceD2 = new Float[listSeq2.size()][listSeq2.size()];
        ArrayList<Node> listNoeud2 = new ArrayList<Node>();

        Upgma resultUpgma = new Upgma(listSeq2, matriceD2, listNoeud2);

        // PythonInterpreter pyInterp = new PythonInterpreter()
        // pyInterp.execfile("C:\\Users\\walid\\Programmation Java\\Licence pro Bioinfo\\UASB01 Projet tuteure\\Logiciel_Arbre_Phylo\\src\\Python_scripts\\Script.py");
        
    }

}