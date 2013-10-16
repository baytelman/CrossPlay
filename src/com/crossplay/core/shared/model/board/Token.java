package com.crossplay.core.shared.model.board;

import java.io.Serializable;

import com.crossplay.core.shared.model.character.GameCharacter;

public class Token extends Localizable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String uniqueId;

	transient private int prevX;
	transient private int prevY;
	
	transient GameCharacter character;
	
	public Token() {
		this("Unknown");
	}
	
	public Token(String string) {
		this.setUniqueId(string);
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public int getPrevX() {
		return prevX;
	}

	public int getPrevY() {
		return prevY;
	}
	
	public void setPrevCoordinates(int x, int y) {
		this.prevX = x;
		this.prevY = y;
	}
}
