

public class Node
{

	public long id;
	public double lat;
	public double lon;
	public Way[] ways;
	public ArrayList<Way> tmp_ways;

	public Node(long id, double latitude, double longitude)
	{
		this.id = id;
		this.lat = latitude;
		this.lon = longitude;
		ways = null;
		tmp_ways = null;
	}

	public String toString()
	{
		return ("NODE n " + id + " :\n  nb ways : " + (ways != null ? ways.length : 0) + "\n  lat : " + lat + "\n  lon : " + lon);
	}

}