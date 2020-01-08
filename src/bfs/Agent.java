package bfs;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Intelligent agent that can navigate a maze and find its exit using Breadth-First Search (BFS)
 * @author Mariella
 *
 */
public class Agent 
{
	
	/**
	 * Breadth-first search (BFS) undertaken by our maze-navigating Agent
	 * Assumes that a solution exists to the BFS
	 * @param maze - Maze (state) with current location of agent
	 * @param goal - Maze (state) with the location we wish to reach
	 * @return The "path" = an ordered list of moves to reach the goal maze (state)  
	 */
	public LinkedList<Vertex> bfs(Maze currentMaze, Maze goalMaze)
	{
		//Make an empty queue named frontier which includes vertices to visit; note that LinkedList implements the Queue interface
		LinkedList<Vertex> frontier = new LinkedList<Vertex>();
		//Create an empty set of vertices that have been visited
		HashSet<Vertex> visited = new HashSet<Vertex>(); 
		
		//Enqueue start location
		frontier.add(currentMaze.getAgentLocation());
		
		while(!frontier.isEmpty()) // while there remain vertices to explore
		{
			//Dequeue 'parent', i.e., remove head of queue
			Vertex current = frontier.remove();
			//Update current maze to maze associated with current vertex location for agent
		    currentMaze = currentMaze.neighbor(current);
			//If this vertex is the goal, we are done
			if (current.equals(goalMaze.getAgentLocation()))
			{
				return getPath(current);
			
			}   
			//Else if we have already visited this vertex, move on to the next in frontier
			else if (visited.contains(current))
				continue;
			//Else add current vertex to visited set and enqueue its children to frontier
			else
			{
				visited.add(current);
				//For each undiscovered child of parent, enqueue child onto frontier
				for (Vertex move : currentMaze.moves())
				{
					move.setParent(current);
					frontier.add(move);
				}
			}
		}

		
		return new LinkedList<Vertex>(); //essentially returning null - ArrayList is empty
	}
	
	//HELPER METHODS
	
	/**
	 * Reconstructs the path to the current vertex by adding its ancestors (parent, parent of parent, parent of parent of parent...)
	 * @param current - vertex from which we wish to reverse construct path
	 * @return The reconstructed path to the current vertex
	 */
	private static LinkedList<Vertex> getPath(Vertex current)
	{ 
		LinkedList<Vertex> path = new LinkedList<Vertex>();
		path.add(current);
		while (current.getParent() != null)
		{
			path.add(0, current.getParent());
			current = current.getParent();
		}
		return path;
	}
	

}
