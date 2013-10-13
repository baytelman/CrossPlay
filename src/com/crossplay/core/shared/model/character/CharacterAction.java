package com.crossplay.core.shared.model.character;

import java.io.Serializable;

import com.crossplay.core.shared.model.board.Board;
import com.crossplay.core.shared.model.board.Event;

public abstract class CharacterAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String uniqueId;
	
	transient GameCharacter character;
	
	abstract boolean validateActionRequest(Board b, CharacterActionRequest request);
	public abstract Event executeActionRequest(Board b, CharacterActionRequest request);

	public CharacterAction() {};
	
	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	public CharacterAction(String uId) {
		this.setUniqueId(uId);
	}
	public abstract String getActionAllowedText();
	public abstract String getActionForbiddenText();
	
}
