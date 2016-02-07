

import java.util.ArrayList;

public class Node
{

	public long id;
	public double lat;
	public double lon;
	public boolean is_intersect;
	public Way[] ways;
	public boolean[] isAtStart;
	public ArrayList<Way> tmp_ways;

	public Node(long id, double latitude, double longitude)
	{
		this.id = id;
		this.lat = latitude;
		this.lon = longitude;
		this.is_intersect = false;
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
		isAtStart = new boolean[tmp_ways.size()];
		for (Way way : tmp_ways)
		{
			ways[i] = way;
			if (way.start == this)
				isAtStart[i] = true;
			else if (way.end == this)
				isAtStart[i] = false;
			else
				System.out.println("BIG ERROR, node neither at end nor at the beginning of way.");
			i++;
		}
		tmp_ways = null;
	}

	public String toString()
	{
		return ("NODE n " + id + " :\n  is_intersect : " + is_intersect + "\n  nb ways : " + (ways != null ? ways.length : 0) + "\n  lat : " + lat + "\n  lon : " + lon);
	}

}