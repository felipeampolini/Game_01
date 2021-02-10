package com.akamstudios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.akamstudios.entities.*;

import com.akamstudios.main.Game;

public class World {
	
	private Tile[] tiles;
	public static int WIDTH, HEIGHT;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for(int xx =0; xx< map.getWidth(); xx++) {
				for(int yy =0; yy< map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy*map.getWidth())];
					
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);	
					
					switch(pixelAtual) {
						case 0xFF000000:
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
							break;
							
						case 0xFFFFFFFF:
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_WALL);
							break;
							
						case 0xFF0026FF:
							//player
							Game.player.setX(xx*16);
							Game.player.setY(yy*16);
							break;
						
						case 0xFFFF0000:
							//enemy
							Game.entities.add(new Enemy(xx*16, yy*16, 16, 16, Entity.ENEMY_EN));
							break;
							
						case 0xFFFF6A00:
							//weapon
							Game.entities.add(new Weapon(xx*16, yy*16, 16, 16, Entity.WEAPON_EN));
							break;
							
						case 0xFFFF7F7F:
							//Life pack
							Game.entities.add(new Lifepack(xx*16, yy*16, 16, 16, Entity.LIFEPACK_EN));
							break;
							
						case 0xFFFFD800:
							//ammo
							Game.entities.add(new Ammo(xx*16, yy*16, 16, 16, Entity.AMMO_EN));
							break;
							
						default:
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);										
					}
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g) {
		for(int xx =0; xx< WIDTH; xx++) {
			for(int yy =0; yy< HEIGHT; yy++) {
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
	
}
