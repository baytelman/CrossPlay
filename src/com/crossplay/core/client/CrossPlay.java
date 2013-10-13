package com.crossplay.core.client;

import java.util.List;

import com.crossplay.core.client.board.BoardWidget;
import com.crossplay.core.shared.controller.BoardClientController;
import com.crossplay.core.shared.model.board.Board;
import com.crossplay.core.shared.model.board.Event;
import com.crossplay.core.shared.model.board.Tile;
import com.crossplay.core.shared.model.board.Token;
import com.crossplay.core.shared.model.character.GameCharacter;
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

	Panel boardContainer = null;
	/**
	 * This is the entry point method.
	 */
	
	int lastEventIndex = -1;
	BoardWidget board = null;
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

		PlayerCharacter character = new PlayerCharacter();
		character.setUniqueId(userId);

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
	public void action(Tile tile) {
		
		for (String tId: tile.getTokenIds()) {

			Token localToken = board.getBoard().getToken(tId);
			
			if (localToken == null)
				continue;
			
			Token temp = new Token(localToken.getUniqueId());
			
			int x = localToken.getX();
			int y = localToken.getY();
			
			boolean validCoordinates = false;
			while (!validCoordinates) {
				int r = (int)(Math.random() * 4);
				if (r == 0)
					x++;
				else if (r == 1)
					x--;
				else if (r == 2)
					y++;
				else 
					y--;
				validCoordinates = board.getBoard().validateCoordinatesInBoard(x,y);
			}
			
			temp.setCoordinates(x, y);
			greetingService.updateTokenStatus(temp, new AsyncCallback<Integer>() {

				@Override
				public void onSuccess(Integer result) {

					requestUpdate();
				}

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub

				}
			});
		}
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
				board.updateTileWidget(localToken);
			}
			
			CrossPlay.this.lastEventIndex = event.getIndex();
		}
	}
}
