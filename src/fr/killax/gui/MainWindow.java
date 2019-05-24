package fr.killax.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class MainWindow extends JFrame {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	private JFrame frame;
	
	public MainWindow() {
		frame = this;
		setSize(WIDTH, HEIGHT);
		setTitle("Générateur de terrain V1.1-a");
		setLocationRelativeTo(null);
		setContentPane(new Canva());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addShorcut();
		show();
	}
	
	private void addShorcut() {
		addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_F5) {
					setContentPane(new Canva());
					revalidate();
					repaint();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
		});
	}

}
