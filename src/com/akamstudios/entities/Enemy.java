package com.akamstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.akamstudios.main.Game;
import com.akamstudios.main.Sound;
import com.akamstudios.world.Camera;
import com.akamstudios.world.World;

public class Enemy extends Entity{

	private double speed = 0.8;
	
	private int maskx = 1,masky = 1, maskw = 14, maskh = 14;
	
	private int frames = 0, maxFrames = 10, index = 0, maxIndex = 1;
	
	private BufferedImage[] sprites;

	private int life = 3;
	
	private boolean isDamaged = false;
	private int damageFrames = 10, damageCurrent = 0;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[3]; // 3 imagens de animação
//		sprites[0] = Game.spritesheet.getSprite(112, 16, 16, 16);
		sprites[0] = Game.spritesheet.getSprite(128, 16, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(144, 16, 16, 16);		
	}
	
	public void tick() {
		
		if(!isCollidingWithPlayer()) {
		
			if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY(), this.getZ())
					&& !isColliding((int)(x+speed), this.getY())) {
				x+=speed;
			}else if((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY(), this.getZ())
					&& !isColliding((int)(x-speed), this.getY())) {
				x-=speed;
			}
			
			if((int)y < Game.player.getY() && World.isFree(this.getX(),(int)(y+speed), this.getZ())
					&& !isColliding(this.getX(),(int)(y+speed))) {
				y+=speed;
			}else if((int)y > Game.player.getY() && World.isFree(this.getX(),(int)(y-speed), this.getZ())
					&& !isColliding(this.getX(),(int)(y-speed))) {
				y-=speed;
			}
			
		}else{
			//perto do player
			
			if(Game.rand.nextInt(100) < 10) {
				if(!Game.player.imortal) {
					if(Game.som)
						Sound.hurtEffect.play();
					Game.player.life-=Game.rand.nextInt(3);
					Game.player.isDamaged = true;
				}
			}
			
			
		}
		
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex)
				index = 0;
		}
		
		collidingTiro();
		
		if(life <= 0) {
			destroySelf();
			return;
		}
		
		if(isDamaged) {
			damageCurrent++;
			if(damageCurrent == damageFrames) {
				damageCurrent = 0;
				isDamaged = false;
			}
		}
		
	}
	
	public void destroySelf() {
		Game.enemies.remove(this);
		Game.entities.remove(this);
	}
	
	public void collidingTiro(){		
		for(int i = 0; i< Game.tiros.size(); i++) {			
			Entity e = Game.tiros.get(i);
			if(e instanceof Tiro) {				
				if(Entity.isColidding(this, e)) {
					isDamaged = true;
					life--;
					Game.tiros.remove(i);
					return;
				}				
			}
		}
	}
	
	public boolean isCollidingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);
				
		return enemyCurrent.intersects(player);
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
		if(!isDamaged)
			g.drawImage(sprites[index], this.getX()- Camera.x, this.getY() - Camera.y, null);
		else
			g.drawImage(Entity.ENEMY_FEEDBACK, this.getX()- Camera.x, this.getY() - Camera.y, null);
		
//		g.setColor(Color.blue);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);
	}
}
