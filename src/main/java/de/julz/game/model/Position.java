package de.julz.game.model;

/**
 * A Position class which holds the X as well as the Y value.
 */
public class Position {

	private int x;
	private int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int X() {
		return x;
	}

	public int Y() {
		return y;
	}
	
	
	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Position))return false;
	    Position pos = (Position)other;
	    if (this.x == pos.X() && this.y == pos.Y()) return true;
	    return false;
	}
	
	@Override
    public int hashCode()
    {
        int hash = 17;
        hash = ((hash + x) << 5) - (hash + x);
        hash = ((hash + y) << 5) - (hash + y);
        return hash;
    }

}
