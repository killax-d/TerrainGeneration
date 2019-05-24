package fr.killax.gui;

import javax.swing.JFrame;

public class MainWindow extends JFrame {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	public MainWindow() {
		setSize(WIDTH, HEIGHT);
		setTitle("Générateur de terrain V1.0-a");
		setLocationRelativeTo(null);
		setContentPane(new Canva());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		show();
	}
}
