package outils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.biojava.nbio.alignment.Alignments;
import org.biojava.nbio.core.alignment.matrices.SimpleSubstitutionMatrix;
import org.biojava.nbio.core.alignment.template.Profile;
import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.compound.AmbiguityDNACompoundSet;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompound;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompoundSet;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;
import org.biojava.nbio.alignment.SimpleGapPenalty;


/**
 * Classe pour effectuer l'alignement multiple.
 */
public class MultipleAlignment {
    /**
     * Méthode pour effectuer l'alignement multiple.
     * @param listSeq liste des séquences à aligner.
     * @param gapP penalité des gaps.
     * @param extendP penalité extension des gaps.
     * @return le résultat de l'alignement multiple.
     */
    public static StringBuilder multipleAlignment(ArrayList<Sequence> listSeq, int gapP, int extendP) {
        // Initialiser list sequences
        List<DNASequence> dnaSeqList = new ArrayList<>();
        List<ProteinSequence> proteinSeqList = new ArrayList<>();

        // Configuration des pénalités de gap
        int gapPenalty = gapP;
        int extendPenalty = extendP;
        SimpleGapPenalty gap = new SimpleGapPenalty(gapPenalty, extendPenalty);

        // Générer le contenu du fichier FASTA à partir du Profile
        StringBuilder fastaContent = new StringBuilder();

        for (Sequence sequence : listSeq) {
            if (sequence.getTypeSeq() == TypeSeq.ADN) {
                // Créez une nouvelle séquence avec l'en-tête et la séquence d'origine
                DNASequence dnaSequence;
                try {
                    dnaSequence = new DNASequence(sequence.getSequence(), AmbiguityDNACompoundSet.getDNACompoundSet());
                    dnaSequence.setOriginalHeader(sequence.getEnTete());
                    dnaSeqList.add(dnaSequence);
                } catch (CompoundNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
            }

            else if (sequence.getTypeSeq() == TypeSeq.PROTEINE) {
                // Créez une nouvelle séquence avec l'en-tête et la séquence d'origine
                ProteinSequence proteinSequence;
                try {
                    proteinSequence = new ProteinSequence(sequence.getSequence());
                    proteinSequence.setOriginalHeader(sequence.getEnTete());
                    proteinSeqList.add(proteinSequence);
                } catch (CompoundNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
            }

            else {
                System.err.println("Séquence invalide.");
            }
                
        }
        
        if (!dnaSeqList.isEmpty()){
            // Configuration du substitutif
            SimpleSubstitutionMatrix<NucleotideCompound> matrix=null;
            File fileInput = new File("src\\Matrice\\nuc-4_4.txt");
            try {
                matrix = new SimpleSubstitutionMatrix<NucleotideCompound>(AmbiguityDNACompoundSet.getDNACompoundSet(), fileInput);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Effectuer l'alignement
            Profile<DNASequence, NucleotideCompound> alignment = Alignments.getMultipleSequenceAlignment(dnaSeqList, matrix, gap);
            // System.out.println(alignment);

            // Générer le contenu du fichier FASTA à partir du Profile
            for (DNASequence seq : dnaSeqList) {
                String header = seq.getOriginalHeader();
                String alignedSequence = alignment.getAlignedSequence(seq).getSequenceAsString();
                fastaContent.append(">").append(header).append(alignedSequence).append("\n");
            }
        }
        else if (!proteinSeqList.isEmpty()){
            // Configuration du substitutif et des pénalités de gap
            SimpleSubstitutionMatrix<AminoAcidCompound> matrix=null;
            File fileInput = new File("src\\Matrice\\blosum62.txt");
            try {
                matrix = new SimpleSubstitutionMatrix<AminoAcidCompound>(AminoAcidCompoundSet.getAminoAcidCompoundSet(), fileInput);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        
            // Effectuer l'alignement avec Clustal Omega
            Profile<ProteinSequence, AminoAcidCompound> alignment = Alignments.getMultipleSequenceAlignment(proteinSeqList, matrix, gap);

            // Afficher l'alignement
            // System.out.println(alignment);

            // Générer le contenu du fichier FASTA à partir du Profile
            for (ProteinSequence seq : proteinSeqList) {
                String header = seq.getOriginalHeader();
                String alignedSequence = alignment.getAlignedSequence(seq).getSequenceAsString();
                fastaContent.append(">").append(header).append(alignedSequence).append("\n");
            }
        
        }
        return fastaContent;
    }
}
