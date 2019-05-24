package fr.killax.gui;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import fr.killax.assets.Assets;

public class Canva extends Container {

	private final int SPRITE_WIDTH = 16;
	private final int SPRITE_HEIGHT = 16;
	private final int DIRT = 3;
	private int WIDTH;
	private int HEIGHT;
	
	private int a;
	private int b;
	
	private ArrayList<Point> points;
	
	public Canva() {
		points = new ArrayList<Point>();
		Random rand = new Random();
		a = rand.nextInt(10);
		b = rand.nextInt(10);
		WIDTH = MainWindow.WIDTH/SPRITE_WIDTH;
		HEIGHT = MainWindow.HEIGHT/SPRITE_HEIGHT;
		for (int x = 0; x < WIDTH; x++) {
			points.add(new Point(x ,a*f(x)+b));
		}
	}
	
	private int f(int x) {
		return (int) (x*Math.sin(Math.sqrt(x)));
	}
	
	public void paint(Graphics g) {
		for (int i = 0; i < points.size(); i++) {
			int x = (int) points.get(i).getX()*SPRITE_WIDTH;
			int y = (int) points.get(i).getY()+MainWindow.HEIGHT/2;
			y -= y % 16;
			
			g.drawImage(Assets.getImage("grass.png"), x, y, SPRITE_WIDTH, SPRITE_HEIGHT, null);
			for (int j = 1; j <= DIRT; j++) {
				g.drawImage(Assets.getImage("dirt.png"), x, y+j*SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT, null);
			}
			int k = DIRT+1;
			while (y+k*SPRITE_HEIGHT < MainWindow.HEIGHT) {
				g.drawImage(Assets.getImage("stone.png"), x, y+k*SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT, null);
				k++;
			}
		}
	}
}
