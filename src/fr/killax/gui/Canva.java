package fr.killax.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JLabel;

import fr.killax.assets.Assets;

public class Canva extends Container {

	public static final String GRASS = "grass.png";
	public static final String DIRT = "dirt.png";
	public static final String STONE = "stone.png";
	public static final String WOOD = "wood.png";
	public static final String LEAVES = "leaves.png";
	public static final String VOID = "void";
	
	public static final String[] available_blocs = new String[] {GRASS, DIRT, STONE, WOOD, LEAVES};
	
	private final int SPRITE_WIDTH = 16;
	private final int SPRITE_HEIGHT = 16;
	private final int DIRT_COUNT = 3;
	private int WIDTH;
	private int HEIGHT;
	
	private JLabel label;
	private boolean close_hover;
	private Point case_hover;
	private String current_bloc;
	private int current_bloc_id;
	
	private int a;
	private int b;
	
	private Random rand;
	
	private ArrayList<Point> points;
	private HashMap<Point, String> blocs;
	
	public Canva() {
		points = new ArrayList<Point>();
		blocs = new HashMap<Point, String>();
		label = new JLabel("");
		label.setLocation(10, MainWindow.HEIGHT - label.getHeight() -10);
		rand = new Random();
		a = rand.nextInt(10);
		b = rand.nextInt(10);
		WIDTH = MainWindow.WIDTH/SPRITE_WIDTH;
		HEIGHT = MainWindow.HEIGHT/SPRITE_HEIGHT;
		for (int x = 0; x < WIDTH; x++) {
			points.add(new Point(x ,a*f(x)+b));
		}
		current_bloc = GRASS;
		current_bloc_id = 0;
		generate();
		add(label);
		close_hover = false;
	}
	
	public void setCloseHover(boolean hover) {
		close_hover = hover;
	}

	public void setCaseHover(Point point) {
		case_hover = point;
	}
	
	public void setBloc(int id) {
		current_bloc = available_blocs[id];
		current_bloc_id = id;
	}
	
	public int getCurrentBlocId() {
		return current_bloc_id;
	}

	public String getCurrentBloc() {
		return current_bloc;
	}
	
	public boolean isCloseHovered() {
		return close_hover;
	}
	
	public void generate() {
		generateTerrain();
		generateTrees();
		generateFlowers();
	}
	
	public void setLabelInfo(String content) {
		label.setText(content);
	}
	
	private int f(int x) {
		return (int) (x*Math.sin(Math.sqrt(x)));
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		drawMap(g);
		g.setColor(Color.WHITE);
		g.drawString(label.getText(), label.getX(), label.getY());
		g.drawImage((close_hover ? Assets.getImage("close_hover.jpg") : Assets.getImage("close.jpg")), MainWindow.WIDTH-48, 0, 48, 32, null);
		if (case_hover != null && current_bloc != VOID) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5F));
			g2d.drawImage(Assets.getImage(current_bloc), (int) case_hover.getX(), (int) case_hover.getY(), SPRITE_WIDTH, SPRITE_HEIGHT, null);
		}
	}
	
	private void drawMap(Graphics g){
		for (Point point : blocs.keySet()) {
			String bloc = blocs.get(point);
			if (bloc != "void" && bloc != null)
				g.drawImage(Assets.getImage(blocs.get(point)), (int) point.getX(), (int) point.getY(), SPRITE_WIDTH, SPRITE_HEIGHT, null);
		}
	}
	
	public String getBlocAt(int x, int y) {
		for (Point point : blocs.keySet()) {
			if ((int) point.getX() == x && (int) point.getY() == y)
				return blocs.get(point);
		}
		return "void";
	}
	
	public void setBlocAt(int x, int y, String bloc) {
		Point oldPoint = null;
		for (Point point : blocs.keySet()) {
			if ((int) point.getX() == x && (int) point.getY() == y)
				oldPoint = point;
		}
		if(oldPoint != null)
			blocs.remove(oldPoint);
		if(bloc != VOID)
			blocs.put(new Point(x, y), bloc);
	}
	
	private void generateTerrain() {
		for (int i = 0; i < points.size(); i++) {
			int x = (int) points.get(i).getX()*SPRITE_WIDTH;
			int y = (int) points.get(i).getY()+MainWindow.HEIGHT/2;
			y -= y % 16;

			blocs.put(new Point(x, y), GRASS);
			for (int j = 1; j <= DIRT_COUNT; j++) {
				blocs.put(new Point(x, y+j*SPRITE_HEIGHT), DIRT);
			}
			int k = DIRT_COUNT+1;
			while (y+k*SPRITE_HEIGHT < MainWindow.HEIGHT) {
				blocs.put(new Point(x, y+k*SPRITE_HEIGHT), STONE);
				k++;
			}
		}
	}
	
	private void generateTrees() {
		int treeSpace = 2;
		for (int i = 0; i < points.size(); i++) {
			if(treeSpace == 0) {
				int x = (int) points.get(i).getX()*SPRITE_WIDTH;
				int y = (int) points.get(i).getY()+MainWindow.HEIGHT/2-16;
				y -= y % 16;

				int treeHeight = rand.nextInt(2) + 2;
				for (int j = 0; j < treeHeight; j++) {
					int newY = y-j*SPRITE_HEIGHT;
					blocs.put(new Point(x, newY), WOOD);
				}
				if(treeHeight != 0) {
					for(int h = 0; h < 2; h++) {
						for(int s = -2; s <= 2; s++) {
							int newX = x+s*SPRITE_WIDTH;
							int newY = y-(treeHeight+h)*SPRITE_HEIGHT;
							if (newX >= 0 && newX < MainWindow.WIDTH && getBlocAt(newX, newY) == VOID) {
								blocs.put(new Point(newX, newY), LEAVES);
							}
						}
					}
					for(int s = -1; s <= 1; s++) {
						int newX = x+s*SPRITE_WIDTH;
						int newY = y-(treeHeight+2)*SPRITE_HEIGHT;
						if (newX >= 0 && newX < MainWindow.WIDTH && getBlocAt(newX, newY) == VOID) {
							blocs.put(new Point(newX, newY), LEAVES);
						}
					}
				}
				
				treeSpace = rand.nextInt(6)+6;
			}
			treeSpace--;
		}
		
	}
	
	private void generateFlowers() {
		for (int i = 0; i < points.size(); i++) {
			int x = (int) points.get(i).getX()*SPRITE_WIDTH;
			int y = (int) points.get(i).getY()+MainWindow.HEIGHT/2-16;
			y -= y % 16;
			int flowerId = rand.nextInt(10);
			if(getBlocAt(x, y) == "void")
				blocs.put(new Point(x, y), String.format("flower_%d.png", (flowerId > 4 ? 4 : flowerId)));
		}
	}
	
}
