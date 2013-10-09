package com.crossplay.core.shared.model.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int width;
	int height;
	
	private List<List<Tile>> tiles;
	HashMap<String, Token> tokens = new HashMap<String, Token>();
	
	transient List<Event> events = new ArrayList<Event>();
	
	public Board()
	{
		this(0,0);
	}

	public Board(int w, int h) {
		this.width = w;
		this.height = h;
		
		this.setTiles(null);
	}

	public String toString() {
		String result = "";
		
		for (List<Tile> list: this.getTiles()) {
			if (result.length() > 0) {
				result += "\n";
			}
			for (Localizable tile: list) {
				result += tile.toString();
			}
		}
		
		return result;
	}

	public List<List<Tile>> getTiles() {
		return tiles;
	}

	public void setTiles(List<List<Tile>> tiles) {
		this.tiles = tiles;
	}

	public int addEvent(Event e) {
		e.setIndex(this.events.size());
		this.events.add(e);
		return e.getIndex();
	}
	
	public List<Event> getEventsAfterIndex(int lastKnownEventIndex) {
		if (lastKnownEventIndex >= this.events.size() - 1)
			return new ArrayList<Event>();
		
		return  new ArrayList<Event>(this.events.subList(lastKnownEventIndex + 1, this.events.size()));
	}

	public Tile getTile(int x, int y) {
		return getTiles().get(y).get(x);
	}

	public Token getToken(String uniqueId) {
		return this.tokens.get(uniqueId);
	}

	public Token addToken(Token remote) {
		this.tokens.put(remote.getUniqueId(), remote);
		return remote;
	}
}
