package com.crossplay.core.client.board;

import com.crossplay.core.shared.model.board.Tile;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.user.client.ui.Button;

public class TileWidget extends Button {

	private Tile tile;

	public TileWidget(Tile tile) {
		super("");
		this.tile = tile;
		this.setWidth("60px");
		this.setHeight("40px");
		this.update();

		this.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				TileWidget.this.update();
			}
		});
	}

	void update() {
		TileWidget.this.setText(TileWidget.this.tile.toString());
	}

}
