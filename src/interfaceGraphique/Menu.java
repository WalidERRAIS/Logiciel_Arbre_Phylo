package interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;

import outils.AlgoPhenetique;
import outils.Sequence;
import outils.TypeAlgoTree;
import outils.TypeSeq;
import outils.AlgoPhenetique.NJ;
import outils.AlgoPhenetique.Upgma;
import outils.MultipleAlignment;

/**
 * Classe pour la construction d'un Menu.
 */
public class Menu extends JFrame {
    //Attributs
	JPanel contentPane;
	private JInternalFrame internalFrame;
	private JInternalFrame internalFrameTree;
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
	private JLabel labelExtendPenalty;
	private JTextField extendPenalty;
	private JButton btnRunMultipleAlignment;
	private JPanel panelLabelStepOne;
	private JPanel panelLabelChoixTypeSequence;
	private JPanel panelChoixTypeSequence;
	private JPanel panelLabelSequenceFormat;
	private JPanel panelFichier;
	private JPanel panelLabelStepTwo;
	private JPanel panelGap;
	private JPanel panelFinal;
	private JPanel panelEntrezSequence;
	private JLabel labelStepOneTree;
	private JPanel panelLabelStepOneTree;
	private JLabel labelChoixAlgoTree;
	private JPanel panelLabelChoixAlgoTree;
	private JComboBox choixTypeAlgoTree;
	private JPanel panelChoixTypeAlgoTree;
	private JLabel labelSequenceFormatTree;
	private JPanel panelLabelSequenceFormatTree;
	private JButton btnRunTree;
	private JPanel readmePanel;
	

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
		contentPane = (JPanel) this.getContentPane();
		// getContentPane().setLayout(null);
		contentPane.setLayout(new BorderLayout());

		/*
		 * Ouvrir automatiquement la page d'accueil en appelant la méthode displayReadmePanel()
		 */
		readmePanel = displayReadmePanel();
		contentPane.add(readmePanel, BorderLayout.CENTER);

		/*
		 * Ajout de la barre d'outils au content pane
		 */
		contentPane.add(createToolBar(), BorderLayout.NORTH);
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
		toolBar.setOrientation(SwingConstants.HORIZONTAL);

		/*
		 * Bouton "Accueil"
		 */ 
		JButton btnAccueil = new JButton("ACCUEIL");
		toolBar.add(btnAccueil);
		btnAccueil.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (internalFrame != null) {
					internalFrame.dispose();
				}

				if (internalFrameTree != null) {
					internalFrameTree.dispose();
				}
		
				// Supprimez le JPanel contenant le texte explicatif s'il existe
				if (readmePanel != null) {
					contentPane.remove(readmePanel);
					readmePanel = null;
				}

				// Affichez à nouveau le readmePanel en appelant la nouvelle méthode
				readmePanel = displayReadmePanel();
				contentPane.add(readmePanel, BorderLayout.CENTER);
				// contentPane.revalidate();
				// contentPane.repaint();
			}
		});

		/*
		 * Bouton Align
		 */
		JButton btnAlign = new JButton("ALIGN");
		toolBar.add(btnAlign);
		btnAlign.addActionListener(new ActionListener() {
			/*
			 * Au clic si aucune fenetre n'est ouverte ouvre la fenetre Alignement
			 * sinon ferme la fenetre Phylogeny
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					// Supprimez le JPanel contenant le texte explicatif s'il existe
					if (readmePanel != null) {
						contentPane.remove(readmePanel);
						readmePanel = null;
					}

					if ((internalFrame==null || internalFrame.isClosed()) && internalFrameTree==null){
						contentPane.add(createInternalFrameAlignement());
					}
					else if (internalFrameTree!=null){
						internalFrameTree.dispose();
						contentPane.add(createInternalFrameAlignement());
					}
					else if (internalFrame.isVisible())
						throw new IllegalArgumentException();
				}
				catch (IllegalArgumentException e1) {
					JOptionPane.showMessageDialog(contentPane,"Erreur! Fenêtre déjà ouverte!","Alert",JOptionPane.WARNING_MESSAGE);     
				}
			}
			
		});

		/*
		 * Bouton Phylogeny
		 */
		JButton btnPhylogeny = new JButton("PHYLOGENY");
		toolBar.add(btnPhylogeny);
		btnPhylogeny.addActionListener(new ActionListener() {
			/*
			 * Au clic si aucune fenetre n'est ouverte ouvre la fenetre Phylogeny
			 * sinon ferme la fenetre Alignement
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					// Supprimez le JPanel contenant le texte explicatif s'il existe
					if (readmePanel != null) {
						contentPane.remove(readmePanel);
						readmePanel = null;
					}

					if ((internalFrameTree==null || internalFrameTree.isClosed()) && internalFrame==null){
						contentPane.add(createInternalFrameTree());
					}
					else if (internalFrame!=null){
						internalFrame.dispose();
						contentPane.add(createInternalFrameTree());
					}
					else if (internalFrameTree.isVisible())
						throw new IllegalArgumentException();
				}
				catch (IllegalArgumentException e1) {
					JOptionPane.showMessageDialog(contentPane,"Erreur! Fenêtre déjà ouverte!","Alert",JOptionPane.WARNING_MESSAGE);     
				}
			}
		});

		return toolBar;
	}

	/**
	 * Affiche le contenu du fichier Accueil > ancien readme
	 * @return le panel r
	 */
	private JPanel displayReadmePanel() {
		// Lecture du contenu du fichier "Accueil.html"
		StringBuilder readmeContent = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src\\Accueil.html"), "UTF-8"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				readmeContent.append(line).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		// Convertir le contenu Markdown en HTML
		Parser parser = Parser.builder().build();
		Node document = parser.parse(readmeContent.toString());
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		String htmlContent = renderer.render(document);
	
		// Créez un JEditorPane pour afficher le contenu HTML
		JEditorPane readmeEditorPane = new JEditorPane("text/html", htmlContent);
		readmeEditorPane.setEditable(false);
	
		// Ajoutez le JEditorPane au JScrollPane pour permettre le défilement
		JScrollPane scrollPane = new JScrollPane(readmeEditorPane);
	
		// Créez un JPanel pour contenir le JScrollPane et ajustez les marges si nécessaire
		JPanel readmePanel = new JPanel(new BorderLayout());
		readmePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		readmePanel.add(scrollPane, BorderLayout.CENTER);
	
		return readmePanel;
	}
	

	/**
	 * Construction d'un internal frame pour la reconstruction d'arbre.
	 * @return internalFrameTree retourne le frame interne de reconstruction d'arbre.
	 */
	private JInternalFrame createInternalFrameTree(){
		internalFrameTree = new JInternalFrame("PHYLOGENY", false, true);
		internalFrameTree.getContentPane().setLayout(new GridLayout(9,1));
		internalFrameTree.setVisible(true);

		/*
		 * Cadre step 1 pour la reconstruction d'arbres
		 */
		createLabelStepOneTree();

		/*
		 * label choix type sequence
		 */
		createLabelChoixTypeSequence(internalFrameTree);

		/*
		 * Choix type sequence
		 */
		createBoxChoixTypeSequence(internalFrameTree);

		/*
		 * label choix algo reconstruction arbres phylogénétiques
		 */
		createLabelChoixAlgoTree();

		/*
		 * choix type algo reconctruction arbres phylo
		 */
		createBoxChoixAlgoTree();

		/*
		 * cadre instruction format alignement pour la reconctruction
		 */
		createLabelSequenceFormatTree();

		/*
		 * Cadre entrez sequences au format fasta avec barre pour scroll
		 */
		createTextAreaEntrezSeqScrollPane(internalFrameTree);

		/*
		 * popup menu copier coller couper
		 */
		createPopupMenuEntrezSeq();		

		/*
		 * cadre choix d'un fichier contenant les sequences à aligner
		 */
		createLabelChoixFichier();

		/*
		 * affiche aucun fichier choisi par default et modifie avec le nom du fichier choisi
		 */
		createLabelfichierChoisi();

		/*
		 * bouton choix d'un fichier de sequences extension fasta
		 */
		createBtnChoisirFichier(internalFrameTree);

		/*
		 * bouton qui lance la reconstruction d'arbre phylogénétique
		 */
		createBtnRunTree();

		return internalFrameTree;
	}
	



	/**
	 * Affiche un cadre avec l'instruction de l'étape 1
	 */
	private void createLabelStepOneTree() {
		labelStepOneTree = new JLabel("STEP 1 - Entrez les sequences alignées pour la reconstruction d'arbres phylogénétiques");
		labelStepOneTree.setForeground(new Color(255, 0, 0));
		labelStepOneTree.setFont(new Font("SansSerif", Font.PLAIN, 16));
		//ajoute le jlabel au panel et ajoute le panel au frame interne
		panelLabelStepOneTree = new JPanel();
		panelLabelStepOneTree.add(labelStepOneTree);
		internalFrameTree.getContentPane().add(panelLabelStepOneTree);
	}

	/**
	 * Affiche un cadre avec l'instruction choix du type de séquences
	 */
	private void createLabelChoixAlgoTree() {
		labelChoixAlgoTree = new JLabel("Sélectionner l'algorithme de reconstruction");
		labelChoixAlgoTree.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panelLabelChoixAlgoTree = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelLabelChoixAlgoTree.add(labelChoixAlgoTree);
		internalFrameTree.getContentPane().add(panelLabelChoixAlgoTree);
	}

	/**
	 * Affiche une liste pour choisir le type d'algos de reconstruction phylo
	 */
	private void createBoxChoixAlgoTree() {
		TypeAlgoTree[] typeAlgoTree = new TypeAlgoTree[]{TypeAlgoTree.UPGMA, TypeAlgoTree.Neighbor_Joining};
		choixTypeAlgoTree = new JComboBox(typeAlgoTree);
		choixTypeAlgoTree.setPreferredSize(new Dimension(750, 50));
		panelChoixTypeAlgoTree = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelChoixTypeAlgoTree.add(choixTypeAlgoTree);
		internalFrameTree.getContentPane().add(panelChoixTypeAlgoTree);
		choixTypeAlgoTree.addActionListener(new ActionListener() {
			/**
			 * Récupère l'item selectionné par l'utilisateur.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				choixTypeAlgoTree.setSelectedItem(choixTypeAlgoTree.getSelectedItem());
			}
		});
		
	}

	/**
	 * Affiche un cadre avec l'instruction du format de l'alignement à mettre pour la reconctruction
	 */
	private void createLabelSequenceFormatTree() {
		labelSequenceFormatTree = new JLabel("Entrez les séquences alignées au format fasta");
		labelSequenceFormatTree.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panelLabelSequenceFormatTree = new JPanel();
		panelLabelSequenceFormatTree.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelLabelSequenceFormatTree.add(labelSequenceFormatTree);
		internalFrameTree.getContentPane().add(panelLabelSequenceFormatTree);
	}


	/**
	 * Créer un bouton qui au clic lance la reconstruction d'arbre phylogénétique
	 */
	private void createBtnRunTree() {
		btnRunTree = new JButton("Construct Tree");
		btnRunTree.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panelFinal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelFinal.add(btnRunTree);
		internalFrameTree.getContentPane().add(panelFinal);
		btnRunTree.addActionListener(new ActionListener() {
			/**
			 * Au clic sur le bouton lance la reconstruction d'arbre des séquences entrées 
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					String seq = null;
					//si aucun fichier choisi prend sequence cadre entrer sequence
					if (sequenceFichier==null)
						seq= entrezSequence.getText();
					//si cadre entrer sequence null prend fichier choisi
					else if (entrezSequence.getText()==null) 
						seq= sequenceFichier.toString();
					//si les deux sont pleins prend fichier choisi
					else if ((sequenceFichier!=null)&&(entrezSequence.getText()!=null))
						seq= sequenceFichier.toString();
					//si les deux sont null lance une exception
					else
						throw new IllegalArgumentException();
					//verifie si au moins 2 séquences au format fasta
					int nbSeq= Sequence.nbSequencesFormatFasta(seq);
					if (nbSeq>=2) {
						Sequence query = new Sequence(nbSeq, seq, "SequenceQuery"
							,(TypeSeq) choixTypeSequence.getSelectedItem());
						query.setEnTeteAllSequence();
						query.setAllSequences();
						//recupere la liste des séquences avec les en-têtes et les séquences modifiées.
						ArrayList<Sequence> listSeq = query.getListSequence();
						// for (Sequence s: listSeq){
						// 	print(s.toString());
						// }
						//reconstruit l'arbre selon l'algo choisi
						if (choixTypeAlgoTree.getSelectedItem() == TypeAlgoTree.UPGMA){
							//recupere le format newick de l'arbre
							String resUpgma = Upgma.upgma(listSeq);
							// System.out.println(resUpgma);
							AlgoPhenetique.drawTreeFromNewick(resUpgma);
						}
						else if (choixTypeAlgoTree.getSelectedItem() == TypeAlgoTree.Neighbor_Joining){
							String resNJ = NJ.nj(listSeq);
							// System.out.println(resNJ);
							AlgoPhenetique.drawTreeFromNewick(resNJ);
						}
					}	
					else
						throw new IllegalArgumentException();
				}
				catch (IllegalArgumentException e1) {
					JOptionPane.showMessageDialog(entrezSequence,"Erreur! Entrez au moins deux séquences au format fasta!","Alert",JOptionPane.WARNING_MESSAGE);     
				}
			}
		});
	}

	/**
	 * Construction d'un internal frame pour l'alignement
	 * @return internalFrame retourne le frame interne d'Alignement
	 */
	private JInternalFrame createInternalFrameAlignement() {
		internalFrame = new JInternalFrame("ALIGNEMENT Multiple", false, true);
		internalFrame.getContentPane().setLayout(new GridLayout(9,1));
		internalFrame.setVisible(true);

		/*
		 * Cadre step 1
		 */
		createLabelStepOne();

		/*
		 * label choix type sequence
		 */
		createLabelChoixTypeSequence(internalFrame);

		/*
		 * Choix type de sequence
		 */
		createBoxChoixTypeSequence(internalFrame);

		/*
		 * Cadre indication format de sequence
		 */
		createLabelSequenceFormat();

		/*
		 * Cadre entrez sequences au format fasta avec barre pour scroll
		 */
		createTextAreaEntrezSeqScrollPane(internalFrame);

		/*
		 * popup menu copier coller couper
		 */
		createPopupMenuEntrezSeq();		

		/*
		 * cadre choix d'un fichier contenant les sequences à aligner
		 */
		createLabelChoixFichier();

		/*
		 * affiche aucun fichier choisi par default et modifie avec le nom du fichier choisi
		 */
		createLabelfichierChoisi();

		/*
		 * bouton choix d'un fichier de sequences extension fasta
		 */
		createBtnChoisirFichier(internalFrame);

		/*
		 * Cadre step 2
		 */
		createLabelStepTwo();

		/*
		 * label gap penalty
		 */
		createLabelGapPenalty();

		/*
		 * saisi gap penalty
		 */
		saisiGapPenalty();

		/*
		 * label penality extension gap
		 */
		createLabelExtendGapPenalty();

		/*
		 * saisi penality extension gap
		 */
		saisiExtendGapPenalty();

		/*
		 * bouton lance alignement multiple
		 */
		createBtnRunMultipleAlignment();

		return internalFrame;
	}

	/**
	 * Affiche un cadre avec l'instruction de l'étape 1
	 */
	private void createLabelStepOne() {
		labelStepOne = new JLabel("STEP 1 - Entrez vos sequences à aligner");
		labelStepOne.setForeground(new Color(255, 0, 0));
		labelStepOne.setFont(new Font("SansSerif", Font.PLAIN, 16));
		//ajoute le jlabel au panel et ajoute le panel au frame interne
		panelLabelStepOne = new JPanel();
		panelLabelStepOne.add(labelStepOne);
		internalFrame.getContentPane().add(panelLabelStepOne);
	}

	/**
	 * Affiche un cadre avec l'instruction choix du type de séquences
	 */
	private void createLabelChoixTypeSequence(JInternalFrame actualFrame) {
		labelChoixTypeSequence = new JLabel("Sélectionner le type des séquences");
		labelChoixTypeSequence.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panelLabelChoixTypeSequence = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelLabelChoixTypeSequence.add(labelChoixTypeSequence);
		actualFrame.getContentPane().add(panelLabelChoixTypeSequence);
	}

	/**
	 * Affiche une liste pour choisir le type de séquences
	 */
	private void createBoxChoixTypeSequence(JInternalFrame actualFrame) {
		TypeSeq[] typeSequence = new TypeSeq[]{TypeSeq.ADN,TypeSeq.PROTEINE};
		choixTypeSequence = new JComboBox(typeSequence);
		choixTypeSequence.setPreferredSize(new Dimension(750, 50));
		panelChoixTypeSequence = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelChoixTypeSequence.add(choixTypeSequence);
		actualFrame.getContentPane().add(panelChoixTypeSequence);
		choixTypeSequence.addActionListener(new ActionListener() {
			/**
			 * Récupère l'item selectionné par l'utilisateur.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				choixTypeSequence.setSelectedItem(choixTypeSequence.getSelectedItem());
			}
		});
		
	}

	/**
	 * Affiche un cadre avec l'instruction du format de séquences à entrer
	 */
	private void createLabelSequenceFormat() {
		labelSequenceFormat = new JLabel("Entrez les séquences au Format Fasta");
		labelSequenceFormat.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panelLabelSequenceFormat = new JPanel();
		panelLabelSequenceFormat.setLayout(new FlowLayout(FlowLayout.LEFT));
		// labelSequenceFormat.setHorizontalAlignment(JLabel.LEFT);
		panelLabelSequenceFormat.add(labelSequenceFormat);
		internalFrame.getContentPane().add(panelLabelSequenceFormat);
	}

	/**
	 * Affiche un champs pour y rentrer les séquences
	 */
	private void createTextAreaEntrezSeqScrollPane(JInternalFrame actualFrame) {
		//le scroll pane permet de visualiser des composants plus grands en mettant des barres de defilement
		scrollPane = new JScrollPane();
		panelEntrezSequence = new JPanel(new BorderLayout());
		panelEntrezSequence.add(scrollPane);
		entrezSequence = new JTextArea();
		scrollPane.setViewportView(entrezSequence);
		entrezSequence.setLineWrap(true);
		entrezSequence.setEditable(true);
		actualFrame.getContentPane().add(panelEntrezSequence, BorderLayout.CENTER);
		entrezSequence.getDocument().addDocumentListener(new DocumentListener() {
			/**
			 * Récupère les changements dans le champs saisi séquences.
			 */
			@Override
			public void insertUpdate(DocumentEvent e) {
				SwingUtilities.invokeLater(() -> {
                    entrezSequence.setText(entrezSequence.getText());
                });
			}

			@Override
			public void removeUpdate(DocumentEvent e) {}

			@Override
			public void changedUpdate(DocumentEvent e) {}
		});
	}

	/**
	 * Affiche un menu popup au clic droit
	 * de la souris dans le champs de saisi des séquences
	 * permettant de copier, coller ou couper
	 */
	private void createPopupMenuEntrezSeq() {
		//popup menu
		popupMenuEntrezSeq = new JPopupMenu();
		addPopup(entrezSequence, popupMenuEntrezSeq);

		//item copier
		itemCopier = new JMenuItem("Copier");
		itemCopier.addActionListener(new ActionListener() {
			/**
			 * au clic permet de copier l'élément sélectionné
			 */
			public void actionPerformed(ActionEvent e) {
				entrezSequence.copy();
			}
		});
		itemCopier.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		popupMenuEntrezSeq.add(itemCopier);

		//item coller
		itemColler = new JMenuItem("Coller");
		itemColler.addActionListener(new ActionListener() {
			/**
			 * au clic permet de coller l'élément sélectionné
			 */
			public void actionPerformed(ActionEvent e) {
				entrezSequence.paste();
			}
		});
		itemColler.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		popupMenuEntrezSeq.add(itemColler);

		//item couper
		itemCouper = new JMenuItem("Couper");
		itemCouper.addActionListener(new ActionListener() {
			/**
			 * au clic permet de coller l'élément sélectionné
			 */
			public void actionPerformed(ActionEvent e) {
				entrezSequence.cut();
			}
		});
		itemCouper.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		popupMenuEntrezSeq.add(itemCouper);
	}

	/**
	 * Ajoute un menu popup dans un composant
	 * @param component composant dans lequel on ajoute le menu
	 * @param popup menu à ajouter
	 */
	private static void addPopup(JTextArea component, final JPopupMenu popup) {
		/**
		 * Affiche menu popup et ses items lors d'un clic droit dans le composant
		 */
		component.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		});
	}

	/**
	 * Affiche un cadre avec l'instruction de choix d'un fichier contenant les séquences
	 */
	private void createLabelChoixFichier() {
		labelChoixFichier = new JLabel("Ou donner le fichier contenant les séquences");
		labelChoixFichier.setFont(new Font("SansSerif", Font.PLAIN, 14));
		//ajoute panel dans lequel on ajoutera le bouton fichier et label fichier choisi
		panelFichier = new JPanel();
		panelFichier.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 0));
		// labelStepOne.setHorizontalAlignment(JLabel.CENTER);
		panelFichier.add(labelChoixFichier);
		// internalFrame.getContentPane().add(panelFichier);
	}

	/**
	 * Affiche un cadre avec l'instruction "Aucun fichier choisi" 
	 * ou le nom du fichier choisi renvoyé par la méthode choixFichier
	 * @see choixFichier
	 */
	private void createLabelfichierChoisi() {
		labelFichierChoisi = new JLabel("Aucun fichier choisi");
		labelFichierChoisi.setFont(new Font("SansSerif", Font.PLAIN, 14));
		// labelFichierChoisi.setBounds(506, 290, 273, 23);
		panelFichier.add(labelFichierChoisi);
		// internalFrame.getContentPane().add(panelFichier);
	}

	/**
	 * Affiche un bouton "Choisir un fichier" 
	 * qui au clic appelle la méthode choixFichier
	 * @see choixFichier
	 */
	private void createBtnChoisirFichier(JInternalFrame actualFrame) {
		btnChoisirFichier = new JButton("Choisir un fichier");
		btnChoisirFichier.addActionListener(new ActionListener() {
			/**
			 * Clic sur le boutton "Choisir un fichier"
			 * appelle la méthode choixFichier
			 * @see choixFichier 
			 */
			public void actionPerformed(ActionEvent e) {
				choixFichier();
			}
		});
		btnChoisirFichier.setFont(new Font("SansSerif", Font.PLAIN, 14));
		// btnChoisirFichier.setBounds(330, 286, 135, 28);
		panelFichier.add(btnChoisirFichier);
		actualFrame.getContentPane().add(panelFichier);
	}

	/**
	 * Ouvre une fenetre de dialogue pour le choix
	 * d'un fichier avec l'extension .fasta
	 * @return une chaîne de caractere correspondant à la sequence query
	 */
	private void choixFichier() {
		String line;
		BufferedReader br;
		File f;
		String filePath;
		//instancie sequenceFichier lorsque l'utilisateur choisi un fichier
		sequenceFichier= new StringBuilder();
		JFileChooser select = new JFileChooser();
		// Filtre les fichiers pour n'afficher que les fichiers avec l'extension .fasta
		select.addChoosableFileFilter(new FiltreExtensionFichier());
		select.setAcceptAllFileFilterUsed(false);
		//affiche "ouvrir" dans la fenetre de dialogue
		int res = select.showDialog(internalFrame, "Ouvrir");
		if (res==JFileChooser.APPROVE_OPTION) {
			f = select.getSelectedFile();
			filePath= f.getPath();
			labelFichierChoisi.setText(f.getName());
			try {
				br= new BufferedReader(new FileReader(filePath));
				while ( (line= br.readLine()) !=null) {
					sequenceFichier.append(line);
					sequenceFichier.append("\n");
				}
				br.close();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(internalFrame,"Erreur survenu lors de l'ouverture du fichier","Alert",JOptionPane.WARNING_MESSAGE);     
			}
		}
	}

	/**
	 * Affiche un cadre avec l'instruction de l'étape 2
	 */
	private void createLabelStepTwo() {
		labelStepTwo = new JLabel("STEP 2 - Entrez vos paramètres");
		labelStepTwo.setForeground(new Color(255, 0, 0));
		labelStepTwo.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panelLabelStepTwo = new JPanel();
		// panelLabelStepTwo.setLayout(new BorderLayout());
		// labelStepOne.setHorizontalAlignment(JLabel.CENTER);
		panelLabelStepTwo.add(labelStepTwo);
		internalFrame.getContentPane().add(panelLabelStepTwo);
	}

	/**
	 * Affiche un cadre avec l'instruction d'entrer la pénalité de gap
	 */
	private void createLabelGapPenalty() {
		labelGapPenalty = new JLabel("Gap Penalty : ");
		labelGapPenalty.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panelGap = new JPanel();
		panelGap.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 0));
		panelGap.add(labelGapPenalty);
	}
	

	/**
	 * Affiche un champs pour y entrer la valeur de pénalité de gap
	 */
	private void saisiGapPenalty() {
		gapPenalty = new JTextField();
		gapPenalty.setBounds(120, 375, 48, 28);
		panelGap.add(gapPenalty);
		gapPenalty.setColumns(10);
		// internalFrame.getContentPane().add(panelGap);
	}

	/**
	 * Affiche un cadre avec l'instruction d'entrer la pénalité d'extension de gap
	 */
	private void createLabelExtendGapPenalty() {
		labelExtendPenalty = new JLabel("Extend Gap Penalty : ");
		labelExtendPenalty.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panelGap.add(labelExtendPenalty);
	}
	

	/**
	 * Affiche un champs pour y entrer la valeur de pénalité d'extension de gap
	 */
	private void saisiExtendGapPenalty() {
		extendPenalty = new JTextField();
		extendPenalty.setBounds(120, 375, 48, 28);
		panelGap.add(extendPenalty);
		extendPenalty.setColumns(10);
		internalFrame.getContentPane().add(panelGap);
	}

	/**
	 * Affiche un bouton qui au clic lance l'alignement multiple
	 */
	private void createBtnRunMultipleAlignment() {
		btnRunMultipleAlignment = new JButton("Lancez Alignement Multiple");
		btnRunMultipleAlignment.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panelFinal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelFinal.add(btnRunMultipleAlignment);
		internalFrame.getContentPane().add(panelFinal);
		btnRunMultipleAlignment.addActionListener(new ActionListener() {
			/**
			 * Au clic sur le bouton lance l'alignement multiple des séquences entrées 
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					String seq = null;
					//si aucun fichier choisi prend sequence cadre entrer sequence
					if (sequenceFichier==null)
						seq= entrezSequence.getText();
					//si cadre entrer sequence null prend fichier choisi
					else if (entrezSequence.getText().isEmpty()) 
						seq= sequenceFichier.toString();
					//si les deux sont pleins prend fichier choisi
					else if ((sequenceFichier!=null)&&(entrezSequence.getText()!=null))
						seq= sequenceFichier.toString();
					
					//verifie si au moins 2 séquences au format fasta
					int nbSeq= Sequence.nbSequencesFormatFasta(seq);
					if (nbSeq>=2) {
						Sequence query = new Sequence(nbSeq, seq, "SequenceQuery",(TypeSeq) choixTypeSequence.getSelectedItem());
						query.setEnTeteAllSequence();
						query.setAllSequences();
						//recupere la liste des séquences avec les en-têtes et les séquences modifiées.
						ArrayList<Sequence> listSeq = query.getListSequence();
						// for (Sequence s: listSeq){
						// 	print(s.toString());
						// }
						// print(query.printAllSequence());
						//recupere valeurs parametres alignement multiple
						int valueGapPenalty = Integer.parseInt(gapPenalty.getText());
						int valueExtendPenalty = Integer.parseInt(extendPenalty.getText());
						
						//generer resultat alignement multiple dans fichier fasta 
						StringBuilder fastaContent = MultipleAlignment.multipleAlignment(listSeq, valueGapPenalty, valueExtendPenalty);
						// File outputFile = new File("\\Desktop\\output.fasta");
						// FileWriter writer = new FileWriter(outputFile);
						// writer.write(fastaContent.toString());
						// writer.close();

						// System.out.println("Alignement enregistré avec succès dans " + outputFile.getAbsolutePath());
						displayAlignmentResult(fastaContent.toString());
					}
					//si les deux sont null ou si moins de 2 sequences fasta
					else
						throw new IllegalArgumentException();
				}
				catch (IllegalArgumentException e1) {
					JOptionPane.showMessageDialog(entrezSequence,"Erreur! Entrez au moins deux séquences au format fasta ou saisissez des valeurs pour les paramètres!","Alert",JOptionPane.WARNING_MESSAGE);     
				}
				// catch (FileNotFoundException e2) {
				// 	System.err.println("Erreur : Fichier Matrice introuvable.");
				// 	e2.printStackTrace();
				// }
 				// catch (IOException e3) {
				// 	e3.printStackTrace();
				// }
			}

			// private void print(String printAllSequence) {
			// 	System.out.println(printAllSequence);
			// }
		});
		
	}

	/**
	 * Affiche le résultat de l'alignement dans une fenetre de dialogue.
	 * @param fastaContent résultat de l'alignement au format fasta
	 */
	private void displayAlignmentResult(String fastaContent) {
        JDialog dialog = new JDialog(this, "Résultat de l'alignement multiple");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());


        // Créez barre de menu avec l'item "Save As"
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveAsItem = new JMenuItem("Save As...");

        // ActionListener pour la sauvegarde
        saveAsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // sauvegarde le fichier fasta
				saveAlignmentResult(fastaContent);
            }
        });

        fileMenu.add(saveAsItem);
        menuBar.add(fileMenu);
        dialog.setJMenuBar(menuBar);

        // Affiche le contenu au format fasta dans la fenêtre
        JTextArea textArea = new JTextArea(fastaContent);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        dialog.add(scrollPane, BorderLayout.CENTER);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
		dialog.setResizable(true);
    }


	/**
	 * Sauvegarde le résultat de l'alignement dans un fichier fasta.
	 * @param fastaContent
	 */
	private void saveAlignmentResult(String fastaContent) {
		JFileChooser fileChooser = new JFileChooser();
		//filtrer les fichiers .fasta
		fileChooser.setFileFilter(new FiltreExtensionFichier());
	
		int option = fileChooser.showSaveDialog(this);
		if (option == JFileChooser.APPROVE_OPTION) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(appendFastaExtension(fileChooser.getSelectedFile())))) {
				writer.write(fastaContent);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Ajoute l'extension .fasta
	 * @param file fichier résultat de l'alignement.
	 * @return le fichier résultat de l'alignement.
	 */
	private File appendFastaExtension(File file) {
		//vérifie si le nom de fichier contient déjà l'extension .fasta
		String fileName = file.getName();
		if (!fileName.toLowerCase().endsWith(".fasta")) {
			// Ajoutez l'extension .fasta si elle n'est pas déjà présente
			return new File(file.getParentFile(), fileName + ".fasta");
		}
		return file;
	}
}