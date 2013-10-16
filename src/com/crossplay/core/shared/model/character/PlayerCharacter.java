package com.crossplay.core.shared.model.character;

import java.io.Serializable;

public class PlayerCharacter extends GameCharacter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MoveAction getCurrentMoveAction() {
		for (CharacterAction action: this.availableActions)
			if (action instanceof MoveAction)
				return (MoveAction)action;
		return null;
	}

	public AttackAction getCurrentAttackAction() {
		for (CharacterAction action: this.availableActions)
			if (action instanceof AttackAction)
				return (AttackAction)action;
		return null;
	}

}
