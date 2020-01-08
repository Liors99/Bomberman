package bfs;
import java.util.HashSet;
import java.util.Objects;

/**
 * Location in a grid in terms of row and column
 * @author Mariella
 *
 */
public class Vertex 
{
	private int row;
	private int col;
	private Vertex parent; // by default, vertex does not have a parent
	
	
	public Vertex(int col, int row)
	{
		this.col = col;
		this.row = row;
	}
	
	@Override
	public String toString()
	{
		String result = "(" + this.col + "," + this.row + ")";
		return result;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(row, col);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	

	//GETTERS AND SETTERS
	
	public int getRow() {return this.row;}

	public int getCol() {return this.col;}
    
    public void setParent(Vertex parent) {this.parent = parent;}
    
    public Vertex getParent() {return this.parent; }
}
