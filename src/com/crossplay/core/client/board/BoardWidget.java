package com.crossplay.core.client.board;

import java.util.List;

import com.crossplay.core.client.CrossPlay;
import com.crossplay.core.shared.model.board.Board;
import com.crossplay.core.shared.model.board.Localizable;
import com.crossplay.core.shared.model.board.Tile;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlexTable;

public class BoardWidget extends FlexTable {
	private Board board;

	public BoardWidget(final CrossPlay crossPlay, Board b)
	{
		super();
		this.board = b;

		int x = 0;
		int y = 0;

		for (List<Tile> list: this.board.getTiles()) {
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
			}
			y++;
		}

		Timer t = new Timer() {
			@Override
			public void run() {
				crossPlay.requestUpdate();
			}
		};
		t.scheduleRepeating(15000);

	}

	public void update(Tile tile) {
		Localizable local = this.board.updateLocaTile(tile);
		TileWidget widget = this.getTile(local.getX(), local.getY());
		widget.update();
	}

	private TileWidget getTile(int x, int y) {
		return (TileWidget)this.getWidget(x, y);
	}
}
