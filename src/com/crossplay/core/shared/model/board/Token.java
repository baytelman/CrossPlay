package com.crossplay.core.shared.model.board;

import java.io.Serializable;

public class Token extends Localizable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String uniqueId;
	
	public Token() {
		this("Unknown");
	}
	
	public Token(String string) {
		this.uniqueId = string;
	}
	
}
