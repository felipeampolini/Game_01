package com.akamstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.akamstudios.main.Game;

public class Entity {

	public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(7*16, 0, 16, 16);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(8*16, 0, 16, 16);
	public static BufferedImage AMMO_EN = Game.spritesheet.getSprite(7*16, 16, 16, 16);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(8*16, 16, 16, 16);

	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	private BufferedImage sprite;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {//Constructor
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}

	public int getX() {
		return (int)this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return (int)this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX(), this.getY(), null);
	}
	
}
