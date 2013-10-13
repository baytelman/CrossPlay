package com.crossplay.core.shared.model.character;

import java.io.Serializable;
import java.util.List;

import com.crossplay.core.shared.model.board.Token;

public class GameCharacter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int maxHP;	
	int movementRange;
	
	int currentHP;
	
	List<CharacterAction> availableActions;
	
	transient Token currentGameToken;

	private String uniqueId;

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public void updateWith(GameCharacter remote) {
		this.currentHP = remote.currentHP;
	}
}
