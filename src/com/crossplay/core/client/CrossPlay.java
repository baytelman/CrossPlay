
package com.crossplay.core.client;

import java.util.List;

import com.crossplay.core.client.board.BoardWidget;
import com.crossplay.core.shared.controller.BoardClientController;
import com.crossplay.core.shared.model.board.Board;
import com.crossplay.core.shared.model.board.Event;
import com.crossplay.core.shared.model.board.Tile;
import com.crossplay.core.shared.model.board.Token;
import com.crossplay.core.shared.model.character.GameCharacter;
import com.crossplay.core.shared.model.character.MoveAction;
import com.crossplay.core.shared.model.character.MoveActionRequest;
import com.crossplay.core.shared.model.character.PlayerCharacter;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CrossPlay implements EntryPoint {
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	public static PlayerCharacter character = null;
	public static BoardWidget board = null;
	
	Panel boardContainer = null;
	/**
	 * This is the entry point method.
	 */

	int lastEventIndex = -1;
	Label errorLabel = null;

	public void onModuleLoad() {
		Button updateButton = new Button("Update");
		Button sendButton = new Button("Request Board");
		final TextBox userIdTextBox = new TextBox();
		userIdTextBox.setText("username");

		errorLabel = new Label();

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("requestGameContainer").add(userIdTextBox);
		RootPanel.get("requestGameContainer").add(sendButton);
		RootPanel.get("requestUpdateContainer").add(updateButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		boardContainer = RootPanel.get("boardContainer");

		sendButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String uId = userIdTextBox.getText();
				joinCurrentGame(uId);
			}
		});

		updateButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				requestUpdate();
			}
		});
	}
	public void requestUpdate() {
		errorLabel.setText("Requesting update...");
		greetingService.updatedTilesAfterEventAtIndex(this.lastEventIndex, new AsyncCallback<List<Event>>() {
			@Override
			public void onSuccess(List<Event> result) {
				errorLabel.setText("(Updated)");
				if (result != null) {
					processEvents(result);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}
	private void joinCurrentGame(String userId) {

		character = new PlayerCharacter();
		character.setMovementRange(3);
		character.setUniqueId(userId);
		character.availableActions.add(new MoveAction("move"));
		
		errorLabel.setText("Requesting game...");
		greetingService.joinCurrentGame(character, new AsyncCallback<Board>() {

			@Override
			public void onFailure(Throwable caught) {
				boardContainer.clear();
				boardContainer.add(new Label(caught.toString()));
			}

			@Override
			public void onSuccess(Board result) {
				boardContainer.clear();
				errorLabel.setText("(Joined game)");
				board = new BoardWidget(CrossPlay.this, result);
				boardContainer.add(board);
			}
		});
	}
	

	public void suggestAction(Tile tile) {
		int x = tile.getX();
		int y = tile.getY();

		MoveAction m = (MoveAction)character.availableActions.get(0);
		MoveActionRequest request = new MoveActionRequest(m, character, x, y);

		if (m.validateActionRequest(board.getBoard(), request)) {
			board.previewActionAllowedInTileWidget(tile, m);
		} else {
			board.previewActionForbiddenInTileWidget(tile, m);
		}
	}
	
	public void action(Tile tile) {
		int x = tile.getX();
		int y = tile.getY();

		MoveAction m = (MoveAction)character.availableActions.get(0);
		MoveActionRequest request = new MoveActionRequest(m, character, x, y);

		greetingService.requestAction(request, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				requestUpdate();
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	private void processEvents(List<Event> result) {
		for (Event event: result) {
			if (event.getTile() != null) {
				Tile localTile = BoardClientController.getInstance().updateLocalTile(board.getBoard(), event.getTile());
				board.updateTileWidget(localTile);
			}

			if (event.getCharacter() != null) {
				GameCharacter localCharacter = BoardClientController.getInstance().updateLocalCharacter(board.getBoard(), event.getCharacter());
				board.updateCharacterWidget(localCharacter);
			}

			if (event.getToken() != null) {
				Token localToken = BoardClientController.getInstance().updateLocalToken(board.getBoard(), event.getToken());
				
				if (character != null && character.getUniqueId().equalsIgnoreCase(localToken.getUniqueId())) {
					character.currentGameToken = localToken;
				}
				
				board.updateTileWidget(localToken);
			}

			CrossPlay.this.lastEventIndex = event.getIndex();
		}
	}
}
