import java.util.ArrayList;
import java.util.TreeMap;


public class Database {

	private TreeMap<Integer, ArrayList<Player>> allPlayers;

	public Database()
	{
		allPlayers = new TreeMap<Integer, ArrayList<Player>>();
		allPlayers.put(1, new ArrayList<Player>());
		allPlayers.put(2, new ArrayList<Player>());
		allPlayers.put(3, new ArrayList<Player>());
		allPlayers.put(4, new ArrayList<Player>());
		allPlayers.put(5, new ArrayList<Player>());
	}
	
	public TreeMap<Integer, ArrayList<Player>> getPlayers()
	{
		return allPlayers;
	}
}
