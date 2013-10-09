package com.crossplay.core.shared.model.board;

import java.io.Serializable;

public class Localizable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;

	public Localizable() {
		super();
	}

	public String coordinates() {
		return "[" + getX() + ", " + getY() + "]";
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}


}