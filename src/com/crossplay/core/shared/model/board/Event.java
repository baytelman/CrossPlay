package com.crossplay.core.shared.model.board;

import java.io.Serializable;

import com.crossplay.core.shared.model.character.GameCharacter;

public class Event implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tile tile = null;
	private Token token = null;
	private GameCharacter character = null;
	private int index = -1;
	public Event()
	{
		
	}
	public Event(Tile local) {
		this.setTile(local);
	}
	public Event(Token local) {
		this.setToken(local);
	}
	public Event(GameCharacter c) {
		this.setCharacter(c);
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
	public Token getToken() {
		return token;
	}
	public void setToken(Token token) {
		this.token = token;
	}
	public GameCharacter getCharacter() {
		return character;
	}
	public void setCharacter(GameCharacter character) {
		this.character = character;
	}

}
