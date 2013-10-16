package com.crossplay.core.server;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.crossplay.core.client.GreetingService;
import com.crossplay.core.shared.controller.BoardClientController;
import com.crossplay.core.shared.model.board.Board;
import com.crossplay.core.shared.model.board.Event;
import com.crossplay.core.shared.model.board.Token;
import com.crossplay.core.shared.model.character.CharacterAction;
import com.crossplay.core.shared.model.character.CharacterActionRequest;
import com.crossplay.core.shared.model.character.PlayerCharacter;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
GreetingService {

	final static String kSessionPlayerId = "kSessionPlayerId";
	
	private HttpSession getSession() {
		// Get the current request and then return its session
		return this.getThreadLocalRequest().getSession();
	}
	
	Board _currentGame = null;
	
	@Override
	public Board currentGame() {
		if (_currentGame != null)
			return _currentGame;
		
		_currentGame = BoardClientController.getInstance().createEmptyBoard(12,12);
		
		return _currentGame;
	}

	@Override
	public List<Event> updatedTilesAfterEventAtIndex(int lastKnownEventIndex) {
		return _currentGame.getEventsAfterIndex(lastKnownEventIndex);
	}

	@Override
	public Board joinCurrentGame(PlayerCharacter c) {
		currentGame();
		
		if (!_currentGame.characters.containsKey(c.getUniqueId())) {
			signUpPlayer(c);
		}
		
		return _currentGame;
	}

	private void signUpPlayer(PlayerCharacter c) {
		
		c.currentHP = c.maxHP = 10;
		
		_currentGame.characters.put(c.getUniqueId(), c);
		_currentGame.addCharacter(c);
		getSession().setAttribute(kSessionPlayerId, c.getUniqueId());

		Token t = new Token(c.getUniqueId());
		BoardClientController.getInstance().updateLocalToken(_currentGame, t);
		
		Event e = new Event(c);
		e.setToken(t);
		_currentGame.addEvent(e);
	}

	@Override
	public void requestAction(CharacterActionRequest request) {
		CharacterAction action = _currentGame.getActionWithRequest(_currentGame, request);
		if (action.validateActionRequest(_currentGame, request)) {
			Event e = action.executeActionRequest(_currentGame, request);
			if (e != null)
				_currentGame.addEvent(e);
		}
	}
}
