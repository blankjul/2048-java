package de.julz.game.ai.archiv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import de.julz.game.ai.AbstractPlayer;
import de.julz.game.model.Action;
import de.julz.game.model.Board;
import de.julz.game.model.GameState;

@SuppressWarnings("restriction")
public class NoHardCodePlayer extends AbstractPlayer {

	private String code;

	public NoHardCodePlayer() {

		try {
			code = "";
			for (String file : new String[] { "tile.js", "grid.js", "game.js", "ai.js" }) {
				code += new String(Files.readAllBytes(Paths.get("src", "main", "java", "de", "julz", "game", "ai",
						"nohardcodeintelligence", file)));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public static void main(String[] args) throws Exception {

		NoHardCodePlayer p = new NoHardCodePlayer();
		
		Board b = new Board(new int[][] { { 3, 3, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } });
		p.next(new GameState(b), null);


	}

	@Override
	public Action next(GameState state, Set<Action> actions) {

		String script = new String(code);
		Action a = Action.NIL;

		try {
			// create a script engine manager
			ScriptEngineManager factory = new ScriptEngineManager();
			// create a JavaScript engine
			ScriptEngine engine = factory.getEngineByName("JavaScript");
			
			script += "\n";
			script += String.format("state = game.grid.fromJson(\"%s\");\n", Board.toJSON(state.getBoard()));
			script += "game.grid.cells = state;\n";
			script += "move = getBestMove(game.grid,3,false).move;\n";

			engine.eval(script);
			
			double actionAsInt =  Double.valueOf(engine.get("move").toString());
			
			if (actionAsInt == 0) {
				a = Action.LEFT;
			} else if (actionAsInt == 1) {
				a = Action.DOWN;
			} else if (actionAsInt == 2) {
				a = Action.RIGHT;
			} else if (actionAsInt == 3) {
				a = Action.UP;
			}
			
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		
		return a;
	}

}