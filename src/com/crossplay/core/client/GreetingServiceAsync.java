package com.crossplay.core.client;

import java.util.List;

import com.crossplay.core.shared.model.board.Board;
import com.crossplay.core.shared.model.board.Event;
import com.crossplay.core.shared.model.board.Tile;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void currentGame(AsyncCallback<Board> callback);

	void updateTileStatus(Tile updatedTile, AsyncCallback<Integer> callback);

	void updatedTilesAfterEventAtIndex(int lastKnownEventIndex,
			AsyncCallback<List<Event>> callback);
}
