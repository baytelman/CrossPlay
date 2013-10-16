package com.crossplay.core.shared.model.character;

import java.io.Serializable;

import com.crossplay.core.shared.model.board.Board;
import com.crossplay.core.shared.model.board.Event;
import com.crossplay.core.shared.model.board.Token;

public abstract class AttackAction extends CharacterAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AttackAction() {
	}
	public AttackAction(String string) {
		super(string);
	}
	public abstract int getAttackRange();
	public abstract int getDamage();
	
	@Override
	public boolean validateActionRequest(Board b, CharacterActionRequest request) {
		if (! (request instanceof AttackActionRequest))
			return false;

		AttackActionRequest attackRequest = (AttackActionRequest)request;

		Token t = b.getToken(request.getCharacterId());
		Token target = b.getToken(attackRequest.getTargetCharacterId());

		if (t == null || target == null)
			return false;

		int distance = Math.abs(t.getX() - target.getX()) + Math.abs(t.getY() - target.getY());

		return this.getAttackRange() >= distance;
	}

	@Override
	public Event executeActionRequest(Board b, CharacterActionRequest request) {

		if (!this.validateActionRequest(b, request))
			return null;
		
		AttackActionRequest attackRequest = (AttackActionRequest)request;
		GameCharacter character = b.getCharacter(attackRequest.getCharacterId());
		GameCharacter targetcharacter = b.getCharacter(attackRequest.getTargetCharacterId());

		targetcharacter.currentHP -= character.performAttack(this);

		Event e = new Event(targetcharacter);
		return e;
	}

	@Override
	public String getActionAllowedText() {
		return "Attack";
	}

	@Override
	public String getActionForbiddenText() {
		return "x";
	}

}
