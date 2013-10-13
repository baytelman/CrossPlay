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
	private List<String> tokenIds = new ArrayList<String>();
	
	public Tile() {
		this(0,0);
	}

	public Tile(int i, int j) {
		this.setCoordinates(i, j);
	}

	public String toString() {
		
		if (this.getTokenIds() != null && this.getTokenIds().size() > 0) {
			String result = "";
			for (String tokenId: this.getTokenIds()) {
				if (result.length() > 0)
					result += "+";
				result += tokenId;
			}
			return result;
		}
		return "";
		//return "[" + getStatus() + "]";
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void removeToken(Token token) {
		this.getTokenIds().remove(token.getUniqueId());
	}

	public void addToken(Token token) {
		this.getTokenIds().add(token.getUniqueId());
	}

	public List<String> getTokenIds() {
		return tokenIds;
	}

	public void updateWith(Tile remote) {
		this.setStatus(remote.getStatus());
	}
}
