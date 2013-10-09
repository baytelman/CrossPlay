package com.crossplay.core.shared.model.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tile extends Localizable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int status = 0;
	transient List<String> tokens = new ArrayList<String>();
	
	public Tile() {
		this(0,0);
	}

	public Tile(int i, int j) {
		this.setCoordinates(i, j);
	}

	public String toString() {
		return "[" + getStatus() + "]";
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void removeToken(Token token) {
		this.tokens.remove(token.uniqueId);
	}

	public void addToken(Token token) {
		this.tokens.add(token.uniqueId);
	}
}
