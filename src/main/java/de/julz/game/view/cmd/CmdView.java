package de.julz.game.view.cmd;

import de.julz.game.model.Board;
import de.julz.game.view.ui.KeyInput;

public class CmdView {


	public CmdView(Board board) {
		new KeyInput();

	}


	public void update(Board board) {
		System.out.println(board);
	}
	

}