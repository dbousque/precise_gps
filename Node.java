

import java.util.ArrayList;

public class Node implements Comparable<Node>
{

	public long id;
	public double lat;
	public double lon;
	public boolean is_intersect;
	public Way[] ways;
	public ArrayList<Way> tmp_ways;

	public Node(long id, double latitude, double longitude)
	{
		this.id = id;
		this.lat = latitude;
		this.lon = longitude;
		ways = null;
		tmp_ways = new ArrayList();
	}

	public void addNewWay(Way new_way)
	{
		tmp_ways.add(new_way);
	}

	public void validateWays()
	{
		int		i;

		i = 0;
		ways = new Way[tmp_ways.size()];
		for (Way way : tmp_ways)
		{
			ways[i] = way;
			i++;
		}
		tmp_ways = null;
	}

	public int compareTo(Node otherNode)
	{
		System.out.println("ID : " + id + ", otherID : " + otherNode.id);
		if (id > otherNode.id)
			return (1);
		if (id == otherNode.id)
			return (0);
		return (-1);
	}

	public String toString()
	{
		return ("NODE n " + id + " :\n  nb ways : " + (ways != null ? ways.length : 0) + "\n  lat : " + lat + "\n  lon : " + lon);
	}

}