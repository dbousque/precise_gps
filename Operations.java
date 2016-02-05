

public class Operations
{

	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	public static double getDistance(Node node1, Node node2)
	{
		double	theta;
		double	dist;

		theta = node1.lon - node2.lon;
		dist = Math.sin(deg2rad(node1.lat)) * Math.sin(deg2rad(node2.lat)) +
			Math.cos(deg2rad(node1.lat)) * Math.cos(deg2rad(node2.lat)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist *= 60 * 1.1515;
		dist *= 1.609344;
		return (dist);
	}

	public static double getLengthOfWay(Way way)
	{
		double	length;
		int		i;

		if (way.nodes.length == 0)
			return getDistance(way.start, way.end);
		length = getDistance(way.start, way.nodes[0]);
		i = 0;
		while (i < way.nodes.length - 1)
		{
			length += getDistance(way.nodes[i], way.nodes[i + 1]);
			i++;
		}
		length += getDistance(way.nodes[way.nodes.length - 1], way.end);
		return (length);
	}

}