package com.akamstudios.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.akamstudios.world.World;

public class Menu {

	public String[] options = {"novo jogo", "carregar jogo", "sair"};
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	
	public boolean up, down, enter;
	
	public static boolean pause = false;
	public static boolean saveExists = false;
	public static boolean saveGame = false;
	
	public void tick() {
		File file = new File("save.txt");
		saveExists = (file.exists());
		
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
					file = new File("save.txt");
					file.delete();
				case "continuar":
					Game.gameState = "NORMAL";
					pause = false;
					break;
					
				case "carregar jogo":
					file = new File("save.txt");
					if(file.exists()) {
						String saver = loadGame(10);
						applySave(saver);
					}
					break;
				
				case "sair":
					System.exit(1);
					break;
			}
		}
	}
	
	public static void applySave(String str) {
		String[] spl = str.split("/");
		for(int i=0; i<spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			switch(spl2[0]) {
				case "level":
					World.restartGame("level"+spl2[1]+".png");
					Game.gameState = "NORMAL";
					pause = false;
					break;
				case "vida":
					Game.player.life = Integer.parseInt(spl2[1]);
					break;
			}
		}
	}
	
	public static String loadGame(int encode) {
		String line = "";
		File file = new File("save.txt");
		if(file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
				try {
					while((singleLine = reader.readLine()) != null) {
						String[] trans = singleLine.split(":");
						char[] val = trans[1].toCharArray();
						trans[1] = "";
						for(int i=0; i<val.length; i++) {
							val[i]-=encode;
							trans[1]+=val[i];
						}
						line+=trans[0];
						line+=":";
						line+=trans[1];
						line+="/";
					}
				}catch(IOException e) {}
			}catch(FileNotFoundException e) {}
		}
		
		return line;
	}
	
	public static void saveGame(String[] val1, int[] val2, int encode) {
		
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("save.txt"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		for(int i=0; i<val1.length; i++) {
			String current = val1[i];
			current += ":";
			char[] value = Integer.toString(val2[i]).toCharArray();
			
			for(int n=0; n<value.length; n++) {
				value[n]+=encode;
				current+=value[n];
			}

			try {
				write.write(current);
				if(i < val1.length - 1)
					write.newLine();
			}catch(IOException e) {}
			
		}
		try {
			write.flush();
			write.close();
		}catch(IOException e) {}
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
