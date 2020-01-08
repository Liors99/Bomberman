package game;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import bombs.Bomb;
import bombs.Powerup;
import players.Human;
import players.Player;
import tiles.Tile;

/**
 * 
 * Class that handles the map components of loading, rendering and printing the map
 *
 */
public class Map
{	
	private Game game;
	private int numCols, numRows; // height and width in tiles (number of columns, number of rows)
	private int[][] tiles; // Stores tile IDs; text-based map
	// Destructible block spawning
	private final int NUM_BLOCKS = 120;
	private int[][] baseMap;
	private ArrayList<Powerup> powerUps;
	private ArrayList<Player> players; // Could this be combined with players in GameState?
	
	/**
	 * A constructor for the map object
	 * @param game - the game object
	 * @param illegalSpawnLocs - arraylist containing all the illegal spawn locations for blocks
	 */
	public Map(Game game, ArrayList<int[]> illegalSpawnLocs)
	{
		numRows = game.getNumTilesY();
		numCols = game.getNumTilesX();
		
		baseMap= new int[numCols][numRows];
		
		for(int i=0;i<numCols;i++) {
			for (int j=0;j<numRows;j++) {
				if(i==0 || i==numRows-1 || j==0 || j==numCols-1) {
					baseMap[i][j]=1;
				}
				else {
					if(j%2==0 && i%2==0) {
						baseMap[i][j]=1;
					}
					else {
						baseMap[i][j]=0;
					}
				}
			}
		}
		this.game = game;
		powerUps = new ArrayList<Powerup>();
		players = new ArrayList<Player>();
		loadMap();
		spawnDestructibleBlocks(illegalSpawnLocs);
	}
	
	
	/**
	 * A method that renders the map
	 * @param g - the graphics component
	 */
	public void render(Graphics g)
	{
		for(int row = 0; row < numRows; row++)
		{
			for(int col = 0; col < numCols; col++)
			{
				getTile(col,row).render(g,col*this.game.getTileSize(),row*this.game.getTileSize());
			}
		}
		for (int i = 0; i < powerUps.size(); i ++)
		{
			powerUps.get(i).render(g);
		}
	}
	
	/**
	 * Loads the base map, only indestructible blocks
	 */
	private void loadMap()
	{
		
		tiles = new int [numCols][numRows];
		for(int row = 0 ; row < numRows; row++)
		{
			for(int col = 0; col < numCols; col++)
			{
				tiles[col][row] = baseMap[col][row];
			}
		}
	}
	
	/**
	 * Loads only the destructible blocks to the map, only the base map is being loaded here
	 * @param illegalSpawnLocs
	 */
	private void spawnDestructibleBlocks(ArrayList<int[]> illegalSpawnLocs) {
		Random randomGenerator = new Random();
		int count = 0;
		while (count < NUM_BLOCKS)
		{
			int[] pair = new int[]{randomGenerator.nextInt(numCols), randomGenerator.nextInt(numRows)};
			// if this pair {row,col} is associated with a grass tile (== 0) and it is not an illegal spawn location
			if ((tiles[pair[0]][pair[1]] == 0)) // it is a grass tile
			{
				boolean isLegal = true;
				for (int[] loc : illegalSpawnLocs) // now go through and make sure it is not an illegal spawn location
				{
					if (loc != null) // if this is a tile on our grid 
					{
						if ((loc[0] == pair[0]) && (loc[1] == pair[1]))
						{
							isLegal = false;
							break;
						}
					}
				}	
				if (isLegal)
				{
					tiles[pair[0]][pair[1]] = 2; // change it to a destructible block/brick
					count++;	
				}
			}
		}
	}
	
	/**
	 * A method that prints the state of the map to the console
	 * @param all_players - an arraylist containing all the player objects at a given state
	 */
	public void printMap(ArrayList<Player> all_players) {
		String[][] map_toPrint=new String[numCols][numRows];
		
		for(int i=0;i<numCols;i++) {
			for(int j=0;j<numRows;j++) {
				map_toPrint[i][j]=Integer.toString(tiles[i][j]);
			}
		}

		for(int i=0;i<all_players.size();i++) {
			int p_col=all_players.get(i).getCol();
			int p_row=all_players.get(i).getRow();
			
			map_toPrint[p_col][p_row]="P";
			
			for(Bomb b: all_players.get(i).getBombs()) {
				System.out.println("Bomb had been placed");
				if(b.getActive()) {
					map_toPrint[b.getCol()][b.getRow()]="B";
				}
			}

		}
		
		for(int i=0;i<numRows;i++) {
			for(int j=0;j<numCols;j++) {
				System.out.print(" "+ map_toPrint[j][i]);
			}
			System.out.println();
		}

		System.out.println();
		for(int i=0;i<numCols;i++)
			System.out.print(" *");
		System.out.println();
		
	}	
	
	// GETTERS & SETTERS
	
	public Tile getTile(int col, int row)
	{
		if (col < 0 || row < 0 || col >= numCols|| row >= numRows)
			return Tile.grassTile; 
		// Check if the tile ID we have in our tiles array is legal; 
		// if it is, return the associated Tile object (e.g., grass, brick, ...)
		// if it isn't, return a grass tile as the default
		Tile t = Tile.tiles[tiles[col][row]];
		if(t == null)
			return Tile.grassTile;
		return t;
	}
	
	public int[][] getTiles()
	{
		return this.tiles;
	}
	
	
	public void setTile(int col, int row, int id)
	{
		tiles[col][row] = id;
	}
	
	
	public ArrayList<Powerup> getPowerUps(){
		return this.powerUps;
	}
	
	
	public void addPlayertoMap(Player p)
	{
		this.players.add(p);
	}
	
	
	public ArrayList<Player> getPlayers()
	{
		return this.players;
	}
	
	
	


}