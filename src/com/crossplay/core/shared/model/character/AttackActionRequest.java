package com.crossplay.core.shared.model.character;

import java.io.Serializable;

public class AttackActionRequest extends CharacterActionRequest implements Serializable {

	private String targetCharacterId;
	
	public AttackActionRequest() {}
	
	public AttackActionRequest(AttackAction attackAction, PlayerCharacter character,
			GameCharacter targetCharacter) {
		super(attackAction, character);
		this.setTargetCharacterId(targetCharacter.getUniqueId());
	}

	public String getTargetCharacterId() {
		return targetCharacterId;
	}

	public void setTargetCharacterId(String targetCharacterId) {
		this.targetCharacterId = targetCharacterId;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
