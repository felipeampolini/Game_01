package com.akamstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.akamstudios.main.Game;
import com.akamstudios.world.Camera;
import com.akamstudios.world.World;

public class Enemy extends Entity{

	private double speed = 1;
	
	private int maskx = 1,masky = 1, maskw = 14, maskh = 14;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public void tick() {
		if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())
				&& !isColliding((int)(x+speed), this.getY())) {
			x+=speed;
		}else if((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())
				&& !isColliding((int)(x-speed), this.getY())) {
			x-=speed;
		}
		
		if((int)y < Game.player.getY() && World.isFree(this.getX(),(int)(y+speed))
				&& !isColliding(this.getX(),(int)(y+speed))) {
			y+=speed;
		}else if((int)y > Game.player.getY() && World.isFree(this.getX(),(int)(y-speed))
				&& !isColliding(this.getX(),(int)(y-speed))) {
			y-=speed;
		}
	}
	
	public boolean isColliding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext + maskx, ynext + masky, maskw, maskh);
		
	
		for(int i=0; i< Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this)
				continue;
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx, e.getY() + masky, maskw, maskh);
			if(enemyCurrent.intersects(targetEnemy))
				return true;
		}
		
		return false;
	}
	
	public void render(Graphics g) {
		super.render(g);
		g.setColor(Color.blue);
		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);
	}
}
