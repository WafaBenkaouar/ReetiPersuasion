package moca.controler.persuasion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

public class PersuasionGUI extends JFrame {

	private final JPanel contentPane;
	Persuasion pers;
	private final double score[] = { 10, 36.6, 63.3, 90 };

	ShapeButton btnContraindre, btnVanter, btnPlaisanter, btnAdmier;
	ShapeButton[] butts = { btnContraindre, btnVanter, btnPlaisanter, btnAdmier };
	private final JLabel lblCoups;

	static String intro[] = { "Vous avez un problem", "Puis-je vous aider?" };

	static String AN[] = { "Je doute que vous le pensiez vraiment...",
					"C'est un peu excessif, vous n'trouvez pas?", "Assez, vous m'faites peur",
					"C'aitai sensai aitre un compliment?", "C'est vraiment ridicule",
					"N'essayez pas de me manipuler",
					"Ne perdez pas votre temps en flatterie avec moi" };
	static String AP[] = { "Je sais, c'est tr??s gentil de votre part" };
	static String CN[] = { "Ni maintenant, ni plus tard, ni jamais", "Vous ne me faite pas peur!",
					"Oh ... je vous en prie", "C'??tait sens?? ??tre un compliment",
					"Vous gaspillez votre salive", "Ah ... c'est comme ??a?" };
	static String CP[] = { "Comme vous voulez", "J'accepte, je n'ai pas le choix." };
	static String SVN[] = { "Oui, oui si vous le dite", "BlaBlaBla, n'importe quoi.",
					"Oh ... ne soyez pas baite", "Vous ??tes path??tique", "Tout ??a, c'est du vent " };
	static String SVP[] = { "sa alors, quel h??rohisme!", "Que d'aventure, comme je vous envie.",
					"Ma vie est vraiment terne a cot?? de la votre." };

	static String PN[] = { "Oh, j'ai compris", "Pas dr??le du tout.", "Oh ... ne soyez pas b??te",
					"Cessez de dire des b??tises",
					"Vous me faites perdre mon temps avec ces b??tises. " };
	static String PP[] = { "Bien sur, c'est mignon.", "Elle est bien bonne.",
					"C'est une bonne blague", "\\item=Laugh", "Pas mal, pas mal du tout!" };

	static String perdu[] = { "Je ne vous fais pas assez confiance pour vous en parler",
					"Vous avez la langue bien pendu, je ne vous dirai rien" };
	static String gagne[] = { "Tr??s impressionant",
					"On peut ??tre ami sur \\language=English facebook!" };

	/*
	 * public static void main(String[] args) { //PersuasionGUI gui = new
	 * PersuasionGUI(); //gui.setVisible(true); }
	 */

	public PersuasionGUI(final Persuasion pers) {

		this.pers = pers;
		this.pers.setRandomPreferences();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 498);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);

		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout());

		final JProgressBar progressBar = new JProgressBar();
		progressBar.setForeground(Color.CYAN);
		panel.add(progressBar, BorderLayout.NORTH);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));

		btnContraindre = new ShapeButton("Contraindre", 190, 180, 180, 90, 180, score[0]);

		btnContraindre.setFc(Color.YELLOW);
		btnContraindre.setAc(Color.RED);
		btnContraindre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pers.getEmoValue(pers.getPreferences().get(btnContraindre.tex)) > 0.0)
					sayRdm(CP);
				else
					sayRdm(CN);
				progressBar.setValue((int) (progressBar.getValue() + (pers.getEmoValue(pers
								.getPreferences().get(btnContraindre.tex))
								* (int) btnContraindre.getScore() / 2)));
				pers.nb_coups++;
				lblCoups.setText("" + pers.nb_coups + "/4");
				shuffleScore();
				setScore();

			}
		});

		btnVanter = new ShapeButton("Se Vanter", 0, 180, 270, 90, 180, score[1]);
		btnVanter.setText("");

		btnVanter.setFc(Color.YELLOW);
		btnVanter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pers.getEmoValue(pers.getPreferences().get(btnVanter.tex)) > 0.0)
					sayRdm(SVP);
				else
					sayRdm(SVN);
				progressBar.setValue((int) (progressBar.getValue() + (pers.getEmoValue(pers
								.getPreferences().get(btnVanter.tex)) * (int) btnVanter.getScore() / 2)));

				pers.nb_coups++;
				lblCoups.setText("" + pers.nb_coups + "/4");
				shuffleScore();
				setScore();
			}
		});
		btnVanter.setAc(Color.RED);

		btnAdmier = new ShapeButton("Admirer", 190, 0, 90, 90, 180, score[2]);
		btnAdmier.setFc(Color.YELLOW);
		btnAdmier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pers.getEmoValue(pers.getPreferences().get(btnAdmier.tex)) > 0.0)
					sayRdm(AP);
				else
					sayRdm(AN);

				progressBar.setValue((int) (progressBar.getValue() + (pers.getEmoValue(pers
								.getPreferences().get(btnAdmier.tex)) * (int) btnAdmier.getScore() / 2)));
				pers.nb_coups++;
				lblCoups.setText("" + pers.nb_coups + "/4");
				shuffleScore();
				setScore();
			}
		});
		btnAdmier.setAc(Color.RED);

		btnPlaisanter = new ShapeButton("Plaisanter", 0, 0, 0, 90, 180, score[3]);
		btnPlaisanter.setFc(Color.YELLOW);
		btnPlaisanter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pers.getEmoValue(pers.getPreferences().get(btnPlaisanter.tex)) > 0.0)
					sayRdm(PP);
				else
					sayRdm(PN);
				progressBar.setValue((int) (progressBar.getValue() + (pers.getEmoValue(pers
								.getPreferences().get(btnPlaisanter.tex))
								* (int) btnPlaisanter.getScore() / 2)));
				pers.nb_coups++;
				lblCoups.setText("" + pers.nb_coups + "/4");
				shuffleScore();
				setScore();
			}
		});
		btnPlaisanter.setAc(Color.RED);

		JPanel panel_2 = new JPanel();

		JButton btnRotate = new JButton("Rotate");
		btnRotate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				shuffleScore();
				setScore();
			}
		});

		btnContraindre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				pers.play("Contraindre");

			}
		});
		btnContraindre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {

				pers.play("Neutral");
			}
		});
		btnVanter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {

				pers.play("Se Vanter");

			}
		});
		btnVanter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {

				pers.play("Neutral");
			}
		});
		btnAdmier.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {

				pers.play("Admirer");

			}
		});
		btnAdmier.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {

				pers.play("Neutral");
			}
		});
		btnPlaisanter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {

				pers.play("Plaisanter");

			}
		});
		btnPlaisanter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				pers.play("Neutral");
			}
		});

		panel_1.add(btnContraindre);
		panel_1.add(btnVanter);
		panel_1.add(btnAdmier);
		panel_1.add(btnPlaisanter);
		panel.add(panel_2, BorderLayout.SOUTH);
		panel_2.add(btnRotate);
		lblCoups = new JLabel("0/4");
		panel_2.add(lblCoups);

	}

	public void sayRdm(String[] messageList) {
		Random rnd = new Random();
		pers.say(messageList[rnd.nextInt(messageList.length)]);
	}

	/*
	 * public PersuasionGUI() {
	 * 
	 * setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); setBounds(100, 100, 450,
	 * 498); contentPane = new JPanel(); contentPane.setBorder(new
	 * EmptyBorder(5, 5, 5, 5)); setContentPane(contentPane);
	 * contentPane.setLayout(new BorderLayout());
	 * 
	 * JPanel panel = new JPanel(); panel.setBackground(Color.BLACK);
	 * 
	 * contentPane.add(panel, BorderLayout.CENTER); panel.setLayout(new
	 * BorderLayout());
	 * 
	 * final JProgressBar progressBar = new JProgressBar();
	 * progressBar.setForeground(Color.CYAN); panel.add(progressBar,
	 * BorderLayout.NORTH);
	 * 
	 * JPanel panel_1 = new JPanel(); panel_1.setBackground(Color.DARK_GRAY);
	 * panel.add(panel_1, BorderLayout.CENTER); panel_1.setLayout(new
	 * GridLayout(0, 2, 0, 0));
	 * 
	 * btnContraindre = new ShapeButton("Contraindre", 190, 180, 180, 90, 180,
	 * score[0]);
	 * 
	 * btnContraindre.setFc(Color.YELLOW); btnContraindre.setAc(Color.RED);
	 * btnContraindre.addActionListener(new ActionListener() {
	 * 
	 * @Override public void actionPerformed(ActionEvent e) {
	 * progressBar.setValue(progressBar.getValue() + (int)
	 * btnContraindre.getScore()); } });
	 * 
	 * btnVanter = new ShapeButton("Se Vanter", 0, 180, 270, 90, 180, score[1]);
	 * btnVanter.setText("");
	 * 
	 * btnVanter.setFc(Color.YELLOW); btnVanter.addActionListener(new
	 * ActionListener() {
	 * 
	 * @Override public void actionPerformed(ActionEvent e) {
	 * progressBar.setValue(progressBar.getValue() + (int)
	 * btnVanter.getScore()); } }); btnVanter.setAc(Color.RED);
	 * 
	 * btnAdmier = new ShapeButton("Admirer", 190, 0, 90, 90, 180, score[2]);
	 * btnAdmier.setFc(Color.YELLOW); btnAdmier.addActionListener(new
	 * ActionListener() {
	 * 
	 * @Override public void actionPerformed(ActionEvent e) { } });
	 * btnAdmier.setAc(Color.RED);
	 * 
	 * btnPlaisanter = new ShapeButton("Plaisanter", 0, 0, 0, 90, 180,
	 * score[3]); btnPlaisanter.setFc(Color.YELLOW);
	 * btnPlaisanter.addActionListener(new ActionListener() {
	 * 
	 * @Override public void actionPerformed(ActionEvent e) { } });
	 * btnPlaisanter.setAc(Color.RED);
	 * 
	 * JPanel panel_2 = new JPanel();
	 * 
	 * JButton btnRotate = new JButton("Rotate");
	 * btnRotate.addActionListener(new ActionListener() {
	 * 
	 * @Override public void actionPerformed(ActionEvent e) { shuffleScore();
	 * setScore(); } }); panel_1.add(btnContraindre); panel_1.add(btnVanter);
	 * panel_1.add(btnAdmier); panel_1.add(btnPlaisanter); panel.add(panel_2,
	 * BorderLayout.SOUTH); panel_2.add(btnRotate);
	 * 
	 * }
	 */
	public void setScore() {

		btnContraindre.setScore(score[0]);

		btnVanter.setScore(score[1]);

		btnAdmier.setScore(score[2]);

		btnPlaisanter.setScore(score[3]);

	}

	public void shuffleScore() {
		Random rnd = new Random();
		for (int i = score.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			double a = score[index];
			score[index] = score[i];
			score[i] = a;
		}
	}

}
