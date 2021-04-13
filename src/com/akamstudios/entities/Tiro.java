package com.akamstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.akamstudios.main.Game;
import com.akamstudios.world.Camera;

public class Tiro extends Entity{
	
	private int dx;
	private int dy;
	private double spd = 4;

	private int life = 100, curlife = 0;
	
	public Tiro(int x, int y, int width, int height, BufferedImage sprite, int dx, int dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
	}
	
	public void tick() {
		x+=dx*spd;
		y+=dy*spd;
		curlife++;
		if(curlife == life) {
			Game.tiros.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 3, 3);
	}
	
}
