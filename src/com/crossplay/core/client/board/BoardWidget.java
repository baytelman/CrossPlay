package com.crossplay.core.client.board;

import java.util.List;

import com.crossplay.core.client.CrossPlay;
import com.crossplay.core.shared.model.board.Board;
import com.crossplay.core.shared.model.board.Tile;
import com.crossplay.core.shared.model.board.Token;
import com.crossplay.core.shared.model.character.CharacterAction;
import com.crossplay.core.shared.model.character.GameCharacter;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlexTable;

public class BoardWidget extends FlexTable {
	private Board board;

	public BoardWidget(final CrossPlay crossPlay, Board b)
	{
		super();
		this.setBoard(b);

		int x = 0;
		int y = 0;

		for (List<Tile> list: this.getBoard().getTiles()) {
			x = 0;
			for (final Tile tile: list) {
				TileWidget widget = new TileWidget(tile);
				this.setWidget(x++, y, widget);

				widget.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						crossPlay.action(tile);
					}
				});
				widget.addMouseOverHandler(new MouseOverHandler() {

					@Override
					public void onMouseOver(MouseOverEvent event) {
crossPlay.suggestAction(tile);
					}

				});
			}
			y++;
		}

		Timer t = new Timer() {
			@Override
			public void run() {
				crossPlay.requestUpdate();
			}
		};
		t.scheduleRepeating(5000);

	}

	public void previewActionAllowedInTileWidget(Tile actionTile, CharacterAction action) {
		TileWidget widget = this.getTile(actionTile.getX(), actionTile.getY());
		widget.setText(action.getActionAllowedText());
	}

	public void previewActionForbiddenInTileWidget(Tile actionTile, CharacterAction action) {
		TileWidget widget = this.getTile(actionTile.getX(), actionTile.getY());
		widget.setText(action.getActionForbiddenText());
	}

	public void updateTileWidget(Tile updatedLocalTile) {
		TileWidget widget = this.getTile(updatedLocalTile.getX(), updatedLocalTile.getY());
		widget.update();
	}

	public void updateTileWidget(Token updatedLocalToken) {
		TileWidget oldWidget = this.getTile(updatedLocalToken.getPrevX(), updatedLocalToken.getPrevY());
		TileWidget newWidget = this.getTile(updatedLocalToken.getX(), updatedLocalToken.getY());
		oldWidget.update();
		newWidget.update();
	}

	private TileWidget getTile(int x, int y) {
		return (TileWidget)this.getWidget(x, y);
	}

	public Board getBoard() {
		return board;
	}

	private void setBoard(Board board) {
		this.board = board;
	}

	public void updateCharacterWidget(GameCharacter localCharacter) {
		// TODO Auto-generated method stub

	}
}
