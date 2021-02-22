package com.akamstudios.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.akamstudios.entities.Player;

public class UI {

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(4, 4, 50, 8);
		g.setColor(Color.green);
		g.fillRect(4, 4, (int)((Player.life/Player.maxLife)*50), 8);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 8));
		g.drawString((int)Player.life+"/"+(int)Player.maxLife, 5, 11);
	}
	
}
