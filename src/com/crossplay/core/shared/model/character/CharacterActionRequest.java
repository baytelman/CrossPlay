package com.crossplay.core.shared.model.character;

import java.io.Serializable;

public class CharacterActionRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String actionId;
	private String characterId;

	public CharacterActionRequest() {};
	
	public CharacterActionRequest(CharacterAction action, GameCharacter character) {
		this.setActionId(action.getUniqueId());
		this.setCharacterId(character.getUniqueId());
	}

	public String getCharacterId() {
		return characterId;
	}

	public void setCharacterId(String characterId) {
		this.characterId = characterId;
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
}
