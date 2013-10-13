package com.crossplay.core.shared.model.character;

public class MoveActionRequest extends CharacterActionRequest
{

	int y;
	int x;

	public MoveActionRequest() {};

	public MoveActionRequest(MoveAction moveAction, GameCharacter character, int x, int y) {
		super(moveAction, character);
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}