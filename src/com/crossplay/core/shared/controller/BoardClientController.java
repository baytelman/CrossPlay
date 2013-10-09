package com.crossplay.core.shared.controller;

import java.util.ArrayList;
import java.util.List;

import com.crossplay.core.shared.model.board.Board;
import com.crossplay.core.shared.model.board.Tile;
import com.crossplay.core.shared.model.board.Token;

public class BoardClientController {
	
	private static BoardClientController instance = null;
	 
    private BoardClientController() {   
    	
    }

    public static synchronized BoardClientController getInstance() {
            if (instance == null) {
                    instance = new BoardClientController();
            }
            return instance;
    }
    
	public Board createEmptyBoard(int w, int h) {
		
		Board board = new Board(w, h);
		List<List<Tile>> tiles = new ArrayList<List<Tile>>();
		
		for (int y = 0; y < h; y++) {
			ArrayList<Tile> row = new ArrayList<Tile>();
			for (int x = 0; x < w; x++) {
				row.add(new Tile(x, y));
			}
			tiles.add(row);
		}
		board.setTiles(tiles);
		
		return board;
	}
	
	public Tile updateLocalTile(Board b, Tile remote) {
		Tile local = b.getTile(remote.getX(), remote.getY());
		local.setStatus(remote.getStatus());
		return local;
	}

	public Token updateLocalToken(Board b, Token remote) {

		Token local = b.getToken(remote.getUniqueId());
		if ( local == null ) {
			local = b.addToken(remote);
		}
		Tile originalTile = b.getTile(local.getX(), local.getY());
		local.setPrevCoordinates(originalTile.getX(), originalTile.getY());
		local.setCoordinates(remote.getX(), remote.getY());

		originalTile.removeToken(remote);

		Tile targetTile = b.getTile(local.getX(), local.getY());
		targetTile.addToken(remote);
		return local;
	}
}

