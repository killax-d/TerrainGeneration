package fr.killax.gui;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainWindow extends JFrame {
	
	static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	
	public static int WIDTH;
	public static int HEIGHT;
	
	public static boolean motionEnabled = false;
	
	private JFrame frame;
	
	public MainWindow() {
		frame = this;
		setSize(device.getDisplayMode().getWidth(), device.getDisplayMode().getHeight());
		WIDTH = getWidth();
		HEIGHT = getHeight();
		setTitle("Générateur de terrain V1.1-a");
		setUndecorated(true);
		setLocationRelativeTo(null);
		setContentPane(new Canva());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setUndecorated(true);
		addShorcut();
		addMotion();
		show();
	}
	
	private void addShorcut() {
		addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_F5) {
					setContentPane(new Canva());
					Canva canva = (Canva) getContentPane();
					canva.generate();
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

	private void addMotion() {
		motionEnabled = true;
		addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				Canva canva = (Canva) getContentPane();
				int id = canva.getCurrentBlocId();
	            if (e.getWheelRotation() < 0)
	            	canva.setBloc((++id >= Canva.available_blocs.length ? 0 : id));
	            else
	            	canva.setBloc((--id < 0 ? Canva.available_blocs.length-1 : id));
				revalidate();
				repaint();
			}
			
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				Canva canva = (Canva) getContentPane();
				if(e.getX() > WIDTH-48 && e.getY() < 32)
					canva.setCloseHover(true);
				else
				{
					canva.setCloseHover(false);
					int x = e.getX();
					x -= x % 16;
					
					int y = e.getY();
					y -= y % 16;

					canva.setCaseHover(new Point(x, y));
					String msg = String.format("CURSOR : [%d,%d] CORRECTED : [%d,%d] BLOC : [%s]", e.getX(), e.getY(), x, y, canva.getBlocAt(x, y));
					canva.setLabelInfo(msg);
				}
				revalidate();
				repaint();
				
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				Canva canva = (Canva) getContentPane();
				int x = e.getX();
				x -= x % 16;
				
				int y = e.getY();
				y -= y % 16;
				
				if (SwingUtilities.isLeftMouseButton(e))
					canva.setBlocAt(x, y, Canva.VOID);
				else
					canva.setBlocAt(x, y, canva.getCurrentBloc());
				
				canva.setCaseHover(new Point(x, y));
				String msg = String.format("CURSOR : [%d,%d] CORRECTED : [%d,%d] BLOC : [%s]", e.getX(), e.getY(), x, y, canva.getBlocAt(x, y));
				canva.setLabelInfo(msg);
				
				revalidate();
				repaint();
			}
		});

		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Canva canva = (Canva) getContentPane();
				if(canva.isCloseHovered())
					dispose();
				
				int x = e.getX();
				x -= x % 16;
				
				int y = e.getY();
				y -= y % 16;
				if (SwingUtilities.isLeftMouseButton(e))
					canva.setBlocAt(x, y, Canva.VOID);
				else
					canva.setBlocAt(x, y, canva.getCurrentBloc());
				
				canva.setCaseHover(new Point(x, y));
				String msg = String.format("CURSOR : [%d,%d] CORRECTED : [%d,%d] BLOC : [%s]", e.getX(), e.getY(), x, y, canva.getBlocAt(x, y));
				canva.setLabelInfo(msg);
				
				revalidate();
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
		});
	}
	
}
