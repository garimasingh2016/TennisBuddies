import java.util.ArrayList;
import java.util.TreeMap;

public class Matcher {
	
	//FIELDS
	private Player currentPlayer;
	private Database myDatabase;

	//CONSTRUCTOR
	public Matcher()
	{
		myDatabase = new Database();
	}
	
	//METHODS
	
	public Player getCurrentPlayer()
	{
		return currentPlayer;
	}
	
	public void setCurrentPlayer(Player p)
	{
		currentPlayer = p;
	}
	
	public TreeMap<Integer, ArrayList<Player>> getAllPlayers()
	{
		return myDatabase.getPlayers();
	}
	
	public Player findPlayer(String name)
	{
		for (int i : this.getAllPlayers().keySet())
		{
			ArrayList<Player> currentArr = this.getAllPlayers().get(i);
			for (Player p : currentArr)
			{
				if (p.getName().equals(name))
				{
					return p;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * @param p the player to be matched
	 * @return an array of all players of the same skill level that are available at the same time as Player p
	 */
	public ArrayList<Player> matchPlayer(Player p)
	{
		int targetLevel = p.getSkillLevel();
		return this.findSameDays(p, targetLevel);
	}
	
	/**
	 * @param p the player to be matched
	 * @param level the skill level of Player p
	 * @return an array of all players of the same skill level that are available at the same time as Player p
	 */
	private ArrayList<Player> findSameDays(Player p, int level)
	{
		ArrayList<Player> matches = new ArrayList<Player>();
		
		for (Player x : this.getAllPlayers().get(level))
		{
			if (p.hasMatchingDays(x) && x != p)
			{
				matches.add(x);
			}
		}
		
		return matches;
	}
}
