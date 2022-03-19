package com.akamstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.akamstudios.graficos.Spritesheet;
import com.akamstudios.main.Game;
import com.akamstudios.world.Camera;
import com.akamstudios.world.World;

public class Player extends Entity {

	public boolean right, up, down, left;
	public int right_dir = 0, left_dir = 1;
	public int dir = right_dir;
	public double speed = 1.4;
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage playerDamage;
	
	private boolean arma = false;
	
	public int ammo = 0;
	
	public boolean isDamaged = false;
	private int damageFrames = 0;
	
	public boolean tiro = false, mouseTiro = false;
	
	public boolean imortal = false;
	public boolean municaoInfinita = false;
	
	public double life = 100, maxLife = 100;
	public int mx, my;
	
	//variaveis para fake jump
	public boolean jump = false;	
	public boolean isJumping = false;	
	//public int z = 0;
	public int jumpFrames = 20, jumpCur = 0;
	public int jumpSpeed = 2;
	public boolean jumpUp = false, jumpDown = false;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		playerDamage = Game.spritesheet.getSprite(0, 16, 16, 16);
		
		for(int i=0; i<4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 0, 16, 16);
		}
		
		for(int i=0; i<4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, 16, 16);
		}
	}

	public void tick() {
		
		if(jump) {
			if(!isJumping) {
				jump = false;
				isJumping = true;
				jumpUp = true;
			}			
		}
		
		if(isJumping) {
			if(jumpUp) {
				jumpCur+=jumpSpeed;
			}else if(jumpDown) {
				jumpCur-=jumpSpeed;
				if(jumpCur <= 0) {
					isJumping = false;
					jumpDown = false;
					jumpUp = false;
				}
			}
			z = jumpCur;
			if(jumpCur >= jumpFrames) {
				jumpUp = false;
				jumpDown = true;
			}
		}
		
		moved = false;
		if(right && World.isFree((int)(x+speed), this.getY(), this.getZ())) {
			moved = true;
			dir = right_dir;
			x+=speed;
		}else if (left && World.isFree((int)(x-speed), this.getY(), this.getZ())) {
			moved = true;
			dir = left_dir;
			x-=speed;
		}
		
		if(up && World.isFree(this.getX(), (int)(y-speed), this.getZ())) {
			moved = true;
			y-=speed;
		}else if(down && World.isFree(this.getX(), (int)(y+speed), this.getZ())) {
			moved = true;
			y+=speed;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
		}
		
		checkCollisionLifePack();
		checkCollisionAmmo();
		checkCollisionGun();
		
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames == 8) {
				this.damageFrames = 0;
				isDamaged = false;
			}
		}
		
		if(tiro) {
			tiro = false;
			
			if(municaoInfinita) {
				//Criar bala e atirar
				int dx = 0;
				int px = 0;
				int py = 7;
				if(dir == right_dir) {
					px = 7;
					dx = 1;
				}else {
					px = 7;
					dx = -1;
				}
				
				Tiro tiro = new Tiro(this.getX()+px, this.getY()+py, 3, 3, null, dx , 0);
				Game.tiros.add(tiro);
			}else if(arma && ammo > 0) {
				ammo--;
				//Criar bala e atirar
				int dx = 0;
				int px = 0;
				int py = 7;
				if(dir == right_dir) {
					px = 7;
					dx = 1;
				}else {
					px = 7;
					dx = -1;
				}
				
				Tiro tiro = new Tiro(this.getX()+px, this.getY()+py, 3, 3, null, dx , 0);
				Game.tiros.add(tiro);
			}
		
		}
		
		if(mouseTiro) {
						
			mouseTiro = false;
			
			if(municaoInfinita) {
				
				//Criar bala e atirar
				double angle = 0;
	
				int px = 7, py = 7;
				
				if(dir == right_dir) {
					px = 7;
					angle = Math.atan2(my - (this.getY()+py - Camera.y), mx - (this.getX()+px - Camera.x));
				}else {
					px = 7;
					angle = Math.atan2(my - (this.getY()+py - Camera.y), mx - (this.getX()+px - Camera.x));
				}
				
				double dx = Math.cos(angle);
				double dy = Math.sin(angle);
				
				Tiro tiro = new Tiro(this.getX()+px, this.getY()+py, 3, 3, null, dx , dy);
				Game.tiros.add(tiro);
				
			}else if(arma && ammo > 0) {
				ammo--;
				//Criar bala e atirar
				double angle = 0;
	
				int px = 7, py = 7;
				
				if(dir == right_dir) {
					px = 7;
					angle = Math.atan2(my - (this.getY()+py - Camera.y), mx - (this.getX()+px - Camera.x));
				}else {
					px = 7;
					angle = Math.atan2(my - (this.getY()+py - Camera.y), mx - (this.getX()+px - Camera.x));
				}
				
				double dx = Math.cos(angle);
				double dy = Math.sin(angle);
				
				Tiro tiro = new Tiro(this.getX()+px, this.getY()+py, 3, 3, null, dx , dy);
				Game.tiros.add(tiro);
			}
		}
		
		if(life <= 0) {
			//GAMEOVER
			life = 0;
			Game.gameState = "GAME_OVER";
		}
		
		updateCamera();

	}
	
	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*16 - Game.HEIGHT);
	}
	
	public void checkCollisionGun() {
		for(int i = 0; i< Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Weapon) {
				if(Entity.isColidding(this, atual)) {
					arma = true;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void checkCollisionAmmo() {
		for(int i = 0; i< Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Ammo) {
				if(Entity.isColidding(this, atual)) {
					ammo+=4;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void checkCollisionLifePack() {
		for(int i = 0; i< Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Lifepack) {
				if(Entity.isColidding(this, atual)) {
					life+=10;
					if(life >= 100)
						life = 100;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void checkItems() {
		for(int i = 0; i< Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Lifepack) {
				life+=8;
				if(life >= 100)
					life = 100;
				Game.entities.remove(i);
				return;
			}
		}
	}
		
	public void render(Graphics g) {
		if(!isDamaged) {
			if(dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
				if(arma) {
					g.drawImage(Entity.GUN_RIGHT, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					//draw arma direita
				}
			}else if(dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
				if(arma) {
					g.drawImage(Entity.GUN_LEFT, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					//draw arma esquerda
				}
			}
		}else {
			g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
			
			//Verificar posicao para mostrar aarma em branco quando tomar dano
			if(dir == right_dir) {
				if(arma) {
					g.drawImage(Entity.GUN_RIGHT_WHITE, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
				}
			}else if(dir == left_dir) {
				if(arma) {
					g.drawImage(Entity.GUN_LEFT_WHITE, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
				}
			}
			
		}
		if(isJumping) {
			g.setColor(Color.black);
			g.fillOval(this.getX() - Camera.x + 1, this.getY() - Camera.y + 11, 14, 5);
		}
	}
	
}
