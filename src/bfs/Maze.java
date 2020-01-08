package bfs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Text-based representation of a maze puzzle
 * @author Mariella
 *
 */
public class Maze 
{
	Vertex agentLocation;
	int[][] grid;
	 
	//CONSTRUCTOR
	/**
	 * Constructor for maze - takes 2D array as grid input
	 * @param grid - 2D int array; grid[] = column, grid[][] = row
	 * @param location - the starting location of the Agent
	 */
	public Maze(int[][] grid, Vertex location)
	{
		this.agentLocation = location;
		this.grid = grid;
	}
	
	//PUBLIC METHODS
	
	/**
	 * Prints the maze, marking the current agent location with the asterisk character '*'
	 */
	public void display()
	{
	    for (int col = 0; col < grid.length; col++) // grid.length gives the number of rows in the grid
	    {
	    	    for (int row = 0; row < grid[col].length; row++)
	    	    {
	    	    	    if ((row == agentLocation.getRow()) && (col == agentLocation.getCol()))
	    	    	        System.out.print('*');
	    	    	    else
	    	    	    	    System.out.print(this.grid[col][row]);
	    	    }
	    	    System.out.println(); // start new line for next row
	    }
	    System.out.println(); // print blank line to separate from next maze display in console
	}
	
	
	/**
	 * Determines the possible moves given current location
	 * @return An ArrayList of possible moves, defined by their col,row coordinates (Location object)
	 */
	public ArrayList<Vertex> moves()
	{
		ArrayList<Vertex> possibleMoves = new ArrayList<Vertex>();
		Vertex[] agentCrossSection = new Vertex[]
				{new Vertex(this.agentLocation.getCol() - 1, this.agentLocation.getRow()),
				 new Vertex(this.agentLocation.getCol() + 1, this.agentLocation.getRow()),
				 new Vertex(this.agentLocation.getCol(), this.agentLocation.getRow() - 1),
				 new Vertex(this.agentLocation.getCol(), this.agentLocation.getRow() + 1)};
        for (Vertex vertex : agentCrossSection)
        {
        	    int tileID = this.grid[vertex.getCol()][vertex.getRow()];
        	    if (tileID == 0 || tileID == 2) //if there is an open space in the grid at this vertex
        	    {
    	    	        // Add this vertex to list of possible moves
    	    	        possibleMoves.add(vertex);
        	    }

        }
		return possibleMoves;
	}
	
	
	/** 
	 * Return another maze instance with the input move made
	 * @param move - move to make, defined as a Location to which to move the agent
	 * @return A new instance of Maze, with the input move executed
	 */
	public Maze neighbor(Vertex move)
	{
		return new Maze(this.grid, move);
	}
	
	
	//HELPER METHODS
	public boolean foundSafeTile(Vertex vertex)
	{
		//check if there is a "safe space" to which you can return after planting bomb
	    Vertex current = this.agentLocation;
	    boolean foundSafeTile = false;
	    //while a parent vertex exists and we haven't yet found a "safe tile" from our bomb blast
	    while ((current.getParent() != null) && !foundSafeTile) 
	    {
	    	    int vertSeparation = Math.abs(current.getParent().getCol() - vertex.getCol());
	    	    int horzSeparation = Math.abs(current.getParent().getRow() - vertex.getRow());
	    	    boolean notInCrossSection = ((current.getParent().getCol() != vertex.getCol()) || 
	    	    		(current.getParent().getRow() != vertex.getRow()));
	    	    if ( vertSeparation >= 2 || horzSeparation >= 2 || notInCrossSection)
	    	    {

	    	    	    foundSafeTile = true;
	    	    }
	    	    current = current.getParent();
	    }
	    
	    return foundSafeTile;
	}
	
     
     //GETTERS 
     
     public Vertex getAgentLocation() {
 		return agentLocation;
 	}
     
}
