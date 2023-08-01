package outils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.biojava.nbio.alignment.Alignments;
import org.biojava.nbio.core.alignment.matrices.SimpleSubstitutionMatrix;
import org.biojava.nbio.core.alignment.template.AlignedSequence;
import org.biojava.nbio.core.alignment.template.Profile;
import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.compound.AmbiguityDNACompoundSet;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompound;
import org.biojava.nbio.core.sequence.compound.DNACompoundSet;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;
import org.biojava.nbio.core.sequence.io.FastaWriterHelper;
import org.biojava.nbio.alignment.SimpleGapPenalty;

import org.biojava.nbio.phylo.ForesterWrapper;


public class MultipleAlignment {

    public static Profile<DNASequence, NucleotideCompound> multipleAlignmentAdn(ArrayList<Sequence> listSeq) throws FileNotFoundException{
        List<DNASequence> dnaSeqList = new ArrayList<>();
        for (Sequence sequence : listSeq) {
            if (sequence.getTypeSeq() == TypeSeq.ADN) {
                try {
                    // Créez une nouvelle séquence avec l'en-tête et la séquence d'origine
                    DNASequence dnaSequence = new DNASequence(sequence.getSequence(), AmbiguityDNACompoundSet.getDNACompoundSet());
                    dnaSequence.setOriginalHeader(sequence.getEnTete());
                    dnaSeqList.add(dnaSequence);
                } catch (CompoundNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                System.err.println("Séquence invalide. Attendue : ADN, Type reçu : " + sequence.getTypeSeq());
            }
        }
        // Configuration du substitutif et des pénalités de gap
        SimpleSubstitutionMatrix<NucleotideCompound> matrix=null;
        File fileInput = new File("src\\Matrice\\MatriceJukesCantor.txt");
        matrix = new SimpleSubstitutionMatrix<NucleotideCompound>(AmbiguityDNACompoundSet.getDNACompoundSet(), fileInput);
        
        int gapPenalty = -10;
        int extendPenalty = -1;
        SimpleGapPenalty gap = new SimpleGapPenalty(gapPenalty, extendPenalty);

        // Effectuer l'alignement avec Clustal Omega
        Profile<DNASequence, NucleotideCompound> alignment = Alignments.getMultipleSequenceAlignment(dnaSeqList, matrix, gap);
        System.out.println(alignment);

        // Générer le contenu du fichier FASTA à partir du Profile
        StringBuilder fastaContent = new StringBuilder();
        for (DNASequence seq : dnaSeqList) {
            String header = seq.getOriginalHeader();
            String alignedSequence = alignment.getAlignedSequence(seq).getSequenceAsString();
            fastaContent.append(">").append(header).append(alignedSequence).append("\n");
        }

        File outputFile = new File("C:\\Users\\pietr\\Desktop\\output.fasta");
        try {
            FileWriter writer = new FileWriter(outputFile);
            writer.write(fastaContent.toString());
            writer.close();

            System.out.println("Alignement enregistré avec succès dans " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return alignment;
    }


    // public static void multipleAlignmentProteine(List<Sequence> sequences) {
    //     List<ProteinSequence> proteinSeqList = new ArrayList<>();
    //     for (Sequence sequence : sequences) {
    //         if (sequence.getType() == SequenceType.Protein) {
    //             proteinSeqList.add(new ProteinSequence(sequence.getSequence()));
    //         } else {
    //             System.err.println("Séquence invalide. Attendue : Protéine, Type reçu : " + sequence.getType());
    //         }
    //     }

    //     // Configuration du substitutif et des pénalités de gap
    //     SimpleSubstitutionMatrix<ProteinSequence, AminoAcidCompound> matrix = new SimpleSubstitutionMatrix<>();
    //     int gapPenalty = -10;
    //     int extendPenalty = -1;
    //     SimpleGapPenalty gap = new SimpleGapPenalty(gapPenalty, extendPenalty);

    //     // Effectuer l'alignement avec Clustal Omega
    //     Alignment<ProteinSequence, AminoAcidCompound> alignment = Alignments.getMultipleSequenceAlignment(proteinSeqList, matrix, gap);

    //     // Afficher l'alignement
    //     System.out.println(alignment);
    // }
}
