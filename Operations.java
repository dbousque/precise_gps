

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

	public static double getDistanceLatLon(double lat1, double lon1, double lat2, double lon2)
	{
		double	theta;
		double	dist;

		theta = lon1 - lon2;
		dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) +
			Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
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

	public static int getActualSpeed(Way way)
	{
		return (way.speed_limit - 5);
	}

	public static double getTimeCostOfNode(Node node)
	{
		return (10.0);
	}

	public static double getTimeCostOfEndNode(Way way, boolean takeReverse)
	{
		if (takeReverse)
			return (way.startNodeTime);
		return (way.endNodeTime);
	}

	public static double getTimeToTravelWay(Way way)
	{
		return (way.length / way.actual_speed * 60.0 * 60.0);
	}

}