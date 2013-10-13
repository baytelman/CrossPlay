package com.crossplay.core.shared.model.character;

import java.io.Serializable;

import com.crossplay.core.shared.model.board.Board;
import com.crossplay.core.shared.model.board.Event;
import com.crossplay.core.shared.model.board.Tile;
import com.crossplay.core.shared.model.board.Token;

public class MoveAction extends CharacterAction implements Serializable {

	public MoveAction() {}

	public MoveAction(String uId) {
		super(uId);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean validateActionRequest(Board b, CharacterActionRequest request) {
		if (! (request instanceof MoveActionRequest))
			return false;

		MoveActionRequest moveRequest = (MoveActionRequest)request;

		Token t = b.getToken(request.getCharacterId());

		if (t == null)
			return false;

		GameCharacter character = b.getCharacter(request.getCharacterId());

		int distance = Math.abs(t.getX() - moveRequest.x) + Math.abs(t.getY() - moveRequest.y);

		return character.getMovementRange() >= distance;
	}

	MoveActionRequest requestMovement(int x, int y) {
		MoveActionRequest request = new MoveActionRequest(this, this.character, x, y);
		return request;
	}

	@Override
	public Event executeActionRequest(Board b, CharacterActionRequest request) {

		if (!this.validateActionRequest(b, request))
			return null;
		
		MoveActionRequest moveRequest = (MoveActionRequest)request;

		Token local = b.getToken(request.getCharacterId());
		Tile originalTile = b.getTile(local.getX(), local.getY());

		local.setPrevCoordinates(originalTile.getX(), originalTile.getY());
		local.setCoordinates(moveRequest.x, moveRequest.y);

		originalTile.removeToken(local);

		Tile targetTile = b.getTile(local.getX(), local.getY());
		targetTile.addToken(local);

		Event e = new Event(local);

		return e;
	}

	@Override
	public String getActionAllowedText() {
		return "Move";
	}

	@Override
	public String getActionForbiddenText() {
		// TODO Auto-generated method stub
		return "x";
	}

}
