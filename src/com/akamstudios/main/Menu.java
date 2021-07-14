package com.akamstudios.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu {

	public String[] options = {"novo jogo", "carregar jogo", "sair"};
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	
	public boolean up, down, enter;
	
	public boolean pause = false;
	
	public void tick() {
		if(up) {
			up = false;
			currentOption --;
			if(currentOption < 0)
				currentOption = maxOption;
		}
		if(down) {
			down = false;
			currentOption ++;
			if(currentOption > maxOption)
				currentOption = 0;
		}
		if(enter) {
			enter = false;
			switch(options[currentOption]) {
				case "novo jogo":
				case "continuar":
					Game.gameState = "NORMAL";
					pause = false;
					break;
				
				case "sair":
					System.exit(1);
					break;
			}
		}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 0, 0, 100));
		//g.setColor(Color.black);
		g2.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
		g.setColor(Color.BLUE);
		g.setFont(new Font("arial", Font.BOLD, 36));
		g.drawString(">Skel<", (Game.WIDTH * Game.SCALE)/2 - 55, (Game.HEIGHT * Game.SCALE)/2 - 180);
		
		//Opcoes de menu
		g.setColor(Color.white);
		
		switch(options[currentOption]) {
			case "novo jogo":
				g.setFont(new Font("arial", Font.BOLD, 24));
				
				g.drawString(">", (Game.WIDTH * Game.SCALE)/2 - 100, (Game.HEIGHT * Game.SCALE)/2 - 120);
				if(!pause)
					g.drawString("Novo jogo", (Game.WIDTH * Game.SCALE)/2 - 55, (Game.HEIGHT * Game.SCALE)/2 - 120);
				else
					g.drawString("Continuar", (Game.WIDTH * Game.SCALE)/2 - 55, (Game.HEIGHT * Game.SCALE)/2 - 120);
				
				g.setFont(new Font("arial", Font.PLAIN, 24));
				
				g.drawString("Carregar jogo", (Game.WIDTH * Game.SCALE)/2 - 70, (Game.HEIGHT * Game.SCALE)/2 - 80);
				g.drawString("Sair", (Game.WIDTH * Game.SCALE)/2 - 15, (Game.HEIGHT * Game.SCALE)/2 - 40);
				break;
				
			case "carregar jogo":
				g.setFont(new Font("arial", Font.PLAIN, 24));
				
				if(!pause)
					g.drawString("Novo jogo", (Game.WIDTH * Game.SCALE)/2 - 55, (Game.HEIGHT * Game.SCALE)/2 - 120);
				else
					g.drawString("Continuar", (Game.WIDTH * Game.SCALE)/2 - 55, (Game.HEIGHT * Game.SCALE)/2 - 120);
					
				g.setFont(new Font("arial", Font.BOLD, 24));
				
				g.drawString(">", (Game.WIDTH * Game.SCALE)/2 - 100, (Game.HEIGHT * Game.SCALE)/2 - 80);
				g.drawString("Carregar jogo", (Game.WIDTH * Game.SCALE)/2 - 70, (Game.HEIGHT * Game.SCALE)/2 - 80);
				
				g.setFont(new Font("arial", Font.PLAIN, 24));
				g.drawString("Sair", (Game.WIDTH * Game.SCALE)/2 - 15, (Game.HEIGHT * Game.SCALE)/2 - 40);
				break;
				
			case "sair":
				g.setFont(new Font("arial", Font.PLAIN, 24));
				
				if(!pause)
					g.drawString("Novo jogo", (Game.WIDTH * Game.SCALE)/2 - 55, (Game.HEIGHT * Game.SCALE)/2 - 120);
				else
					g.drawString("Continuar", (Game.WIDTH * Game.SCALE)/2 - 55, (Game.HEIGHT * Game.SCALE)/2 - 120);
					
				g.drawString("Carregar jogo", (Game.WIDTH * Game.SCALE)/2 - 70, (Game.HEIGHT * Game.SCALE)/2 - 80);
				
				g.setFont(new Font("arial", Font.BOLD, 24));
				
				g.drawString(">", (Game.WIDTH * Game.SCALE)/2 - 100, (Game.HEIGHT * Game.SCALE)/2 - 40);
				g.drawString("Sair", (Game.WIDTH * Game.SCALE)/2 - 15, (Game.HEIGHT * Game.SCALE)/2 - 40);
				break;
		}	
	}
}
