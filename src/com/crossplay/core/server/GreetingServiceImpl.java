package com.crossplay.core.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.crossplay.core.client.GreetingService;
import com.crossplay.core.shared.controller.BoardClientController;
import com.crossplay.core.shared.model.board.Board;
import com.crossplay.core.shared.model.board.Event;
import com.crossplay.core.shared.model.board.Tile;
import com.crossplay.core.shared.model.board.Token;
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
	HashMap<String, PlayerCharacter> _players = new HashMap<>();;
	
	@Override
	public Board currentGame() {
		if (_currentGame != null)
			return _currentGame;
		
		_currentGame = BoardClientController.getInstance().createEmptyBoard(12,12);
		
		return _currentGame;
	}

	@Override
	public int updateTileStatus(Tile updatedTile) {
		Tile local = BoardClientController.getInstance().updateLocalTile(_currentGame, updatedTile);
		int eventIndex = _currentGame.addEvent(new Event(local));
		
		return eventIndex;
	}

	@Override
	public List<Event> updatedTilesAfterEventAtIndex(int lastKnownEventIndex) {
		if (_currentGame != null)
			return _currentGame.getEventsAfterIndex(lastKnownEventIndex);
		return new ArrayList<Event>();
	}

	@Override
	public int updateTokenStatus(Token updatedToken) {
		Token local = BoardClientController.getInstance().updateLocalToken(_currentGame, updatedToken);
		int eventIndex = _currentGame.addEvent(new Event(local));
		
		return eventIndex;
	}

	@Override
	public Board joinCurrentGame(PlayerCharacter c) {
		currentGame();
		
		if (!_players.containsKey(c.getUniqueId())) {
			signUpPlayer(c);
		}
		
		return _currentGame;
	}

	private void signUpPlayer(PlayerCharacter c) {
		_players.put(c.getUniqueId(), c);
		getSession().setAttribute(kSessionPlayerId, c.getUniqueId());

		Token t = new Token(c.getUniqueId());
		BoardClientController.getInstance().updateLocalToken(_currentGame, t);
		
		Event e = new Event(c);
		e.setToken(t);
		_currentGame.addEvent(e);
	}
}
