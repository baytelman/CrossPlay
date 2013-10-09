package com.crossplay.core.client;

import java.util.List;

import com.crossplay.core.shared.model.board.Board;
import com.crossplay.core.shared.model.board.Event;
import com.crossplay.core.shared.model.board.Tile;
import com.crossplay.core.shared.model.board.Token;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	Board currentGame();
	int updateTileStatus(Tile updatedTile);
	List<Event> updatedTilesAfterEventAtIndex(int lastKnownEventIndex);
	int updateTokenStatus(Token updatedToken);
}