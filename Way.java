

import java.util.Arrays;

public class Way
{

	public static final int MOTORWAY = 1;
	public static final int MOTORWAY_LINK = 2;
	public static final int TRUNK = 3;
	public static final int TRUNK_LINK = 4;
	public static final int PRIMARY = 5;
	public static final int PRIMARY_LINK = 6;
	public static final int SECONDARY = 7;
	public static final int SECONDARY_LINK = 8;
	public static final int TERTIARY = 9;
	public static final int TERTIARY_LINK = 10;
	public static final int UNCLASSIFIED = 11;
	public static final int UNCLASSIFIED_LINK = 12;
	public static final int RESIDENTIAL = 13;
	public static final int RESIDENTIAL_LINK = 14;
	public static final int SERVICE = 15;
	public static final int SERVICE_LINK = 16;
	public static final int LIVING_STREET = 17;

	public Node start;
	public Node end;
	public double length;
	public Node[] nodes;
	public int speed_limit;
	public long id;
	public boolean oneway;
	public int type;
	public String name;
	public boolean accessGranted;

	public Way(long id, Node[] nodes, int speed_limit, int type, boolean oneway, boolean accessGranted)
	{
		if (nodes.length < 2)
			System.out.println("Way " + id + " has too few nodes !");
		this.id = id;
		this.speed_limit = speed_limit;
		this.start = nodes[0];
		this.end = nodes[nodes.length - 1];
		this.nodes = Arrays.copyOfRange(nodes, 1, nodes.length - 1);
		this.length = Operations.getLengthOfWay(this);
		this.type = type;
		this.oneway = oneway;
		this.accessGranted = accessGranted;
		this.name = null;
	}

	public static String getTypeName(int type)
	{
		if (type == MOTORWAY)
			return "motorway";
		else if (type == MOTORWAY_LINK)
			return "motorway_link";
		else if (type == TRUNK)
			return "trunk";
		else if (type == TRUNK_LINK)
			return "trunk_link";
		else if (type == PRIMARY)
			return "primary";
		else if (type == PRIMARY_LINK)
			return "primary_link";
		else if (type == SECONDARY)
			return "secondary";
		else if (type == SECONDARY_LINK)
			return "secondary_link";
		else if (type == TERTIARY)
			return "tertiary";
		else if (type == TERTIARY_LINK)
			return "tertiary_link";
		else if (type == UNCLASSIFIED)
			return "unclassified";
		else if (type == UNCLASSIFIED_LINK)
			return "unclassified_link";
		else if (type == RESIDENTIAL)
			return "residential";
		else if (type == RESIDENTIAL_LINK)
			return "residential_link";
		else if (type == SERVICE)
			return "service";
		else if (type == SERVICE_LINK)
			return "service_link";
		else if (type == LIVING_STREET)
			return "living_street";
		System.out.println("ERROR");
		return "ERROR";
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String toString()
	{
		return ("WAY N " + id + " :\n  speed_limit : " + speed_limit +
			"\n  oneway : " + oneway + "\n  type : " + getTypeName(type) +
			"\n  length : " + length + "km\n  name : " + name);
	}

}