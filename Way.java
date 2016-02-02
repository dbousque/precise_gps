

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
	public static final int UNCLASSIFIED_LINK = 11;
	public static final int RESIDENTIAL = 12;
	public static final int RESIDENTIAL_LINK = 13;
	public static final int SERVICE = 14;
	public static final int SERVICE_LINK = 15;
	public static final int LIVING_STREET = 16;

	public Node start;
	public Node end;
	public double length;
	public Node[] nodes;
	public int speed_limit;
	public int id;
	public boolean oneway;
	public int type;
	public String name;

	public Way(int id, Node[] nodes, int speed_limit, int type)
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
		this.name = null;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}