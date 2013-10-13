package com.crossplay.core.shared.model.character;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.crossplay.core.shared.model.board.Token;

public abstract class GameCharacter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int maxHP;	
	private int movementRange;
	
	int currentHP;
	
	public List<CharacterAction> availableActions = new ArrayList<CharacterAction>();
	
	public transient Token currentGameToken;

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

	public int getMovementRange() {
		return movementRange;
	}

	public void setMovementRange(int movementRange) {
		this.movementRange = movementRange;
	}

	public CharacterAction getAction(String actionId) {
		for (CharacterAction action: availableActions)
			if (action.getUniqueId().equalsIgnoreCase(actionId))
				return action;
		return null;
	}
}
