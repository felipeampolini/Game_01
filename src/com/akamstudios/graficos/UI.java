package com.akamstudios.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.akamstudios.main.Game;

public class UI {

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(4, 4, 50, 8);
		g.setColor(Color.green);		
		g.fillRect(4, 4, (int)((Game.player.life/Game.player.maxLife)*50), 8);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 8));
		
		if(Game.player.imortal)
			g.drawString("GodMode", 5, 11);
		else
			g.drawString((int)Game.player.life+"/"+(int)Game.player.maxLife, 5, 11);
		
	}
	
}
