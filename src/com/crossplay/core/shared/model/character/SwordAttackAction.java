package com.crossplay.core.shared.model.character;

import java.io.Serializable;


public class SwordAttackAction extends AttackAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SwordAttackAction() {}
	
	public SwordAttackAction(String string) {
		super(string);
	}

	@Override
	public
	int getAttackRange() {
		return 1;
	}

	@Override
	public
	int getDamage() {
		return 2;
	}

}
