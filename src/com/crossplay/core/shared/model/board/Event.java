package com.crossplay.core.shared.model.board;

import java.io.Serializable;

public class Event implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tile tile = null;
	private int index = -1;
	public Event()
	{
		
	}
	public Event(Tile local) {
		this.setTile(local);
	}
	public void setIndex(int index) {
		this.index  = index;
	}
	public int getIndex() {
		return this.index;
	}
	public Tile getTile() {
		return tile;
	}
	public void setTile(Tile tile) {
		this.tile = tile;
	}

}
