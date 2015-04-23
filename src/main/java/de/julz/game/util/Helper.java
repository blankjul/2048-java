package de.julz.game.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * This class provides some useful functions which are be used by several
 * different classes.
 *
 */
public class Helper {

	/** object to get random values */
	private static Random r = new Random();





	/**
	 * Generates a String.
	 * 
	 * @param coll
	 * @return
	 */
	public static <T> String toString(Collection<T> coll) {
		StringBuffer sb = new StringBuffer();
		if (coll == null)
			return "";
		sb.append("[");
		for (T entry : coll) {
			sb.append(entry.toString());
			sb.append(",");
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Return a random entry from a list.
	 */
	public static <T> T getRandomEntry(List<T> list) {
		if (list.isEmpty())
			return null;
		else if (list.size() == 1) {
			return list.get(0);
		} else {
			int index = r.nextInt(list.size());
			return list.get(index);
		}
	}

	/**
	 * Return a random entry from a set.
	 * 
	 * @param set
	 * @return
	 */
	public static <T> T getRandomEntry(Set<T> set) {
		if (set.isEmpty())
			return null;
		List<T> asList = new ArrayList<T>(set);
		Collections.shuffle(asList);
		return asList.get(0);
	}

	

	/**
	 * print parameters from one executed game, contains all parameters which
	 * can be extracted from the game
	 */
	public static void printParameter(String[] par) {
		String print = "";
		String err = "";
		String start = "[PARAMETER_START,";
		String end = "PARAMETER_ENDE]";

		print += start;

		for (int i = 0; i < par.length; i++) {
			if (par[i].contains(",")) {
				err = "[FATAL_CSV_ERROR]";
			}
			print += par[i] + ",";
		}

		print += end;
		print += err;

		System.out.println(print);
	}

	/**
	 * Concatenates two Arrays.
	 * 
	 * @param A
	 * @param B
	 * @return
	 */
	public static <T> T[] concatenate(T[] A, T[] B) {
		int aLen = A.length;
		int bLen = B.length;

		@SuppressWarnings("unchecked")
		T[] C = (T[]) Array.newInstance(A.getClass().getComponentType(), aLen
				+ bLen);
		System.arraycopy(A, 0, C, 0, aLen);
		System.arraycopy(B, 0, C, aLen, bLen);

		return C;
	}
}