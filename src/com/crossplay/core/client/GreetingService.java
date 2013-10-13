package com.crossplay.core.client;

import java.util.List;

import com.crossplay.core.shared.model.board.Board;
import com.crossplay.core.shared.model.board.Event;
import com.crossplay.core.shared.model.character.CharacterActionRequest;
import com.crossplay.core.shared.model.character.PlayerCharacter;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	Board currentGame();
	Board joinCurrentGame(PlayerCharacter c);
	
	List<Event> updatedTilesAfterEventAtIndex(int lastKnownEventIndex);
	
	void requestAction(CharacterActionRequest request);
}