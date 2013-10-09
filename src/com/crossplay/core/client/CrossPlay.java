package com.crossplay.core.client;

import java.util.List;

import com.crossplay.core.client.board.BoardWidget;
import com.crossplay.core.shared.model.board.Board;
import com.crossplay.core.shared.model.board.Event;
import com.crossplay.core.shared.model.board.Tile;
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
@SuppressWarnings("unused")
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
		
		errorLabel = new Label();

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("requestGameContainer").add(sendButton);
		RootPanel.get("requestUpdateContainer").add(updateButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		boardContainer = RootPanel.get("boardContainer");

		sendButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				requestJoinGame();
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
					for (Event event: result) {
						if (event.getTile() != null) {
							board.update(event.getTile());
						}
						CrossPlay.this.lastEventIndex = event.getIndex();
					}
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}
	private void requestJoinGame() {

		errorLabel.setText("Requesting game...");
		greetingService.currentGame(new AsyncCallback<Board>() {

			@Override
			public void onFailure(Throwable caught) {
				boardContainer.clear();
				boardContainer.add(new Label(caught.toString()));
			}

			@Override
			public void onSuccess(Board result) {
				boardContainer.clear();
				errorLabel.setText("(Joined game");
				board = new BoardWidget(CrossPlay.this, result);
				boardContainer.add(board);
			}
		});
	}
	public void action(Tile tile) {
		tile.setStatus(tile.getStatus() + 1);
		greetingService.updateTileStatus(tile, new AsyncCallback<Integer>() {
			
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
