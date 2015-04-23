package de.julz.game.model.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.google.gson.Gson;

import de.julz.game.model.Action;
import de.julz.game.model.Board;

@SuppressWarnings("restriction")
public class Eval {

	public static Board move(Board b, Action a) {

		try {
			// create a script engine manager
			ScriptEngineManager factory = new ScriptEngineManager();
			// create a JavaScript engine
			ScriptEngine engine = factory.getEngineByName("JavaScript");

			// evaluate JavaScript code from String
			String code = "";

			code += new String(Files.readAllBytes(Paths.get("src", "test", "java", "de", "julz", "game", "model",
					"util", "tile.js")));

			code += new String(Files.readAllBytes(Paths.get("src", "test", "java", "de", "julz", "game", "model",
					"util", "grid.js")));
			code += new String(Files.readAllBytes(Paths.get("src", "test", "java", "de", "julz", "game", "model",
					"util", "game.js")));

			
			int[][] input = b.getArray();
			for (int i = 0; i < input.length; i++) {
				for (int j = 0; j < input[0].length; j++) {
					if (input[i][j] > 0) input[i][j] = (int) Math.pow(2, input[i][j]);
				}
			}
			
			
			
			String json = new Gson().toJson(input);

			code += String.format("state = game.grid.fromJson(\"%s\");\n", json);
			code += "game.grid.cells = state;\n";

			if (a == Action.LEFT) {
				code += "game.move(0);\n";
			} else if (a == Action.DOWN) {
				code += "game.move(1);\n";
			} else if (a == Action.RIGHT) {
				code += "game.move(2);\n";
			} else if (a == Action.UP) {
				code += "game.move(3);\n";
			}

			code += "var result = game.grid.toJson();\n";

			engine.eval(code);

			String jsonResult = (String) engine.get("result");
			int[][] result = new Gson().fromJson(jsonResult, int[][].class);
			
			for (int i = 0; i < input.length; i++) {
				for (int j = 0; j < input[0].length; j++) {
					if (result[i][j] > 0) result[i][j] = (int) (Math.log(result[i][j]) / Math.log(2));
				}
			}

			return new Board(result);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {

		Board b = new Board(new int[][] { { 3, 3 , 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } });

		System.out.println(b);
		Board result = move(b, Action.RIGHT);
		System.out.println(result);

	}

}