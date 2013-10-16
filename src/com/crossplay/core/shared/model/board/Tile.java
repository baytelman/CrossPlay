package com.crossplay.core.shared.model.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.crossplay.core.shared.model.character.GameCharacter;

public class Tile extends Localizable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	transient Board board = null;
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

				if (board != null) {
					GameCharacter character= board.getCharacter(tokenId);
					if (character != null) {
						result += " [" + character.currentHP + "/" + character.maxHP + "]";
					}
				}
			}
			return result;
		}
		return "";
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

	public void setBoard(Board b) {
		this.board = b;
	}
}
