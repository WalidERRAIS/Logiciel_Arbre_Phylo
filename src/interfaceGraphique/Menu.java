package interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.*;


public class Menu extends JFrame {
    //Attributs
	private JInternalFrame internalFrame;
	private JLabel labelStepOne;
	private JLabel labelChoixTypeSequence;
	private JComboBox choixTypeSequence;
	private JLabel labelSequenceFormat;
	private JScrollPane scrollPane;
	private JTextArea entrezSequence;
	private JPopupMenu popupMenuEntrezSeq;
	private JMenuItem itemCopier;
	private JMenuItem itemColler;
	private JMenuItem itemCouper;
	private JLabel labelChoixFichier;
	private JLabel labelFichierChoisi;
	private JButton btnChoisirFichier;
	private StringBuilder sequenceFichier = null;
	private JLabel labelStepTwo;
	private JLabel labelGapPenalty;
	private JTextField gapPenalty;
	private JButton btnRunMultipleAlignment;

    /**
	 * Construction du Menu principal 
	 */
	public Menu() {
		/*
		 * Page principale
		 */
		super("Menu Principal");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //tue le processus à la fermeture de la fenêtre
		this.setSize(899, 600); //change la taille de la fenêtre
		this.setLocationRelativeTo(null); //centre la fenêtre
		this.setResizable(true);
		
		/*
		 * Creation et configuration content Pane
		 */
		JPanel contentPane = (JPanel) this.getContentPane();
		// getContentPane().setLayout(null);
		contentPane.setLayout(new BorderLayout());

		/*
		 * Ajout de la barre d'outils au content pane
		 */
		contentPane.add(createToolBar(), BorderLayout.WEST);

		// /*
		//  * Ajout du frame interne Alignement
		//  */
		// contentPane.add(createInternalFrameAlignement());

	}

	/**
	 * Construction de la barre d'outils
	 * @return toolBar retourne la barre d'outils
	 */
	private JToolBar createToolBar() {
		/*
		 * Configuration barre d'outils
		 */
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 0, 90, 563);
		toolBar.setOrientation(SwingConstants.VERTICAL);
		toolBar.
		/*
		 * Bouton Align
		 */
		JButton btnAlign = new JButton("ALIGN");
		toolBar.add(btnAlign);

		/*
		 * Bouton Phylogeny
		 */
		JButton btnPhylogeny = new JButton("PHYLOGENY");
		toolBar.add(btnPhylogeny);

		return toolBar;
	}

	/**
	 * Construction d'un internal frame pour l'alignement
	 * @return internalFrame retourne le frame interne d'Alignement
	 */
	private JInternalFrame createInternalFrameAlignement() {
		internalFrame = new JInternalFrame("ALIGNEMENT Multiple");
		internalFrame.setBounds(79, 0, 806, 563);
		internalFrame.getContentPane().setLayout(null);
		internalFrame.setVisible(true);

		/*
		 * Cadre step 1
		 */
		createLabelStepOne();

		/*
		 * label choix type sequence
		 */
		createLabelChoixTypeSequence();

		// /*
		//  * Choix type de sequence
		//  */
		// createBoxChoixTypeSequence();

		// /*
		//  * Cadre indication format de sequence
		//  */
		// createLabelSequenceFormat();

		// /*
		//  * Cadre entrez sequences au format fasta avec barre pour scroll
		//  */
		// createTextAreaEntrezSeqScrollPane();

		// /*
		//  * popup menu copier coller couper
		//  */
		// createPopupMenuEntrezSeq();		

		// /*
		//  * cadre choix d'un fichier contenant les sequences à aligner
		//  */
		// createLabelChoixFichier();

		// /*
		//  * affiche aucun fichier choisi par default et modifie avec le nom du fichier choisi
		//  */
		// createLabelfichierChoisi();

		// /*
		//  * bouton choix d'un fichier de sequences extension fasta
		//  */
		// createBtnChoisirFichier();

		// /*
		//  * Cadre step 2
		//  */
		// createLabelStepTwo();

		// /*
		//  * label gap penalty
		//  */
		// createLabelGapPenalty();

		// /*
		//  * saisi gap penalty
		//  */
		// saisiGapPenalty();

		// /*
		//  * bouton lance alignement multiple
		//  */
		// createBtnRunMultipleAlignment();

		return internalFrame;
	}

	/**
	 * Affiche un cadre avec l'instruction de l'étape 1
	 */
	private void createLabelStepOne() {
		labelStepOne = new JLabel("STEP 1 - Entrez vos sequences à aligner");
		labelStepOne.setForeground(new Color(255, 0, 0));
		labelStepOne.setFont(new Font("SansSerif", Font.PLAIN, 16));
		labelStepOne.setBounds(6, 19, 314, 31);
		internalFrame.getContentPane().add(labelStepOne);
	}

	/**
	 * Affiche un cadre avec l'instruction choix du type de séquences
	 */
	private void createLabelChoixTypeSequence() {
		labelChoixTypeSequence = new JLabel("Sélectionner le type des séquences");
		labelChoixTypeSequence.setFont(new Font("SansSerif", Font.PLAIN, 14));
		labelChoixTypeSequence.setBounds(6, 62, 248, 31);
		internalFrame.getContentPane().add(labelChoixTypeSequence);
	}
}
