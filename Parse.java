

import org.json.*;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.net.URLEncoder;

public class Parse
{

	public static final int COUNTRY_FRANCE = 1;

	public static Node jsonToNode(JSONObject json)
	{
		Node	res;

		res = new Node(json.getLong("id"), json.getDouble("lat"), json.getDouble("lon"));
		return (res);
	}

	public static int getSpeedForTypeNCountry(int type, int country, boolean urban)
	{
		if (country == COUNTRY_FRANCE && !urban)
			return France.speed_limits[type - 1];
		else if (country == COUNTRY_FRANCE && urban)
			return France.urban_speed_limits[type -1];
		System.out.println("COUNTRY NOT FOUND");
		return (50);
	}

	public static int getSpeedLimit(String maxspeed, int type)
	{
		int		speed;
		boolean	urban;

		urban = true;
		if (maxspeed == null)
			speed = getSpeedForTypeNCountry(type, COUNTRY_FRANCE, urban);
		else
			speed = Integer.parseInt(maxspeed);
		return (speed);
	}

	public static int getType(String type)
	{
		if (type.equals("motorway"))
			return (Way.MOTORWAY);
		if (type.equals("motorway_link"))
			return (Way.MOTORWAY_LINK);
		if (type.equals("trunk"))
			return (Way.TRUNK);
		if (type.equals("trunk_link"))
			return (Way.TRUNK_LINK);
		if (type.equals("primary"))
			return (Way.PRIMARY);
		if (type.equals("primary_link"))
			return (Way.PRIMARY_LINK);
		if (type.equals("secondary"))
			return (Way.SECONDARY);
		if (type.equals("secondary_link"))
			return (Way.SECONDARY_LINK);
		if (type.equals("tertiary"))
			return (Way.TERTIARY);
		if (type.equals("tertiary_link"))
			return (Way.TERTIARY_LINK);
		if (type.equals("unclassified"))
			return (Way.UNCLASSIFIED);
		if (type.equals("unclassified_link"))
			return (Way.UNCLASSIFIED_LINK);
		if (type.equals("residential"))
			return (Way.RESIDENTIAL);
		if (type.equals("residential_link"))
			return (Way.RESIDENTIAL_LINK);
		if (type.equals("service"))
			return (Way.SERVICE);
		if (type.equals("service_link"))
			return (Way.SERVICE_LINK);
		if (type.equals("living_street"))
			return (Way.LIVING_STREET);
		System.out.println("ERROR, type not found");
		return (0);
	}

	public static boolean getAccess(String access)
	{
		if (access == null)
			return (true);
		if (access.equals("yes"))
			return (true);
		if (access.equals("permissive"))
			return (true);
		if (access.equals("customers"))
			return (true);
		return (false);
	}

	public static boolean isOneway(String oneway)
	{
		if (oneway == null)
			return (false);
		if (oneway.equals("yes"))
			return (true);
		return (false);
	}

	public static void handleWay(JSONObject json, HashMap nodes, ArrayList<Way> ways)
	{
		Node[]		way_nodes;
		int			len;
		int			start;
		JSONArray	ori_nodes;
		JSONObject	tags;
		Way			tmp_way;
		int			speed_limit;
		boolean		oneway;
		int			type;
		boolean		accessGranted;
		int			i;

		i = 0;
		start = 0;
		ori_nodes = (JSONArray) json.get("nodes");
		way_nodes = new Node[ori_nodes.length()];
		len = ori_nodes.length();
		tags = (JSONObject) json.get("tags");
		accessGranted = getAccess(tags.isNull("access") ? null : tags.getString("access"));
		type = getType(tags.getString("highway"));
		speed_limit = getSpeedLimit(tags.isNull("maxspeed") ? null : tags.getString("maxspeed"), type);
		oneway = isOneway(tags.isNull("oneway") ? null : tags.getString("oneway"));
		while (i < len)
		{
			way_nodes[i] = (Node)nodes.get(ori_nodes.getLong(i));
			i++;
		}
		i = 1;
		while (i < len)
		{
			if (way_nodes[i].is_intersect)
			{
				if (start == 0 && i == len - 1)
					tmp_way = new Way(json.getLong("id"), way_nodes, speed_limit, type, oneway, accessGranted);
				else
					tmp_way = new Way(json.getLong("id"), Arrays.copyOfRange(way_nodes, start, i + 1),
																	speed_limit, type, oneway, accessGranted);
				way_nodes[start].addNewWay(tmp_way);
				way_nodes[i].addNewWay(tmp_way);
				ways.add(tmp_way);
				start = i;
			}
			else if (i == len - 1)
				System.out.println("ERROR, node is not intersect but should be");
			i++;
		}
	}

	public static String readFile(String path, Charset encoding) throws IOException 
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public static JSONArray readJsonAndBuildNodes(String filename, HashMap<Long,Node> nodes)
	{
		Iterator<Object>	iter;
		JSONObject			tmp;
		JSONObject			obj;

		try
		{
			obj = new JSONObject(readFile(filename, StandardCharsets.UTF_8));
		}
		catch (Exception e)
		{
			System.out.println("Error while reading file.");
			return (null);
		}
		JSONArray elts = (JSONArray)obj.get("elements");
		iter = elts.iterator();
		while (iter.hasNext())
		{
			tmp = (JSONObject) iter.next();
			if (tmp.get("type").equals("node"))
				nodes.put(tmp.getLong("id"), jsonToNode(tmp));
		}
		return (elts);
	}

	public static void getIntersectNodes(Iterator<Object> iter, HashMap<Long,Node> nodes)
	{
		JSONObject			tmp;
		JSONArray			way_nodes;
		int					i;
		int					len;
		Node				tmpNode;

		while (iter.hasNext())
		{
			tmp = (JSONObject) iter.next();
			if (tmp.get("type").equals("way"))
			{
				way_nodes = (JSONArray) tmp.get("nodes");
				len = way_nodes.length();
				nodes.get(way_nodes.getLong(0)).is_intersect = true;
				nodes.get(way_nodes.getLong(len - 1)).is_intersect = true;
				i = 1;
				while (i < len - 1)
				{
					tmpNode = nodes.get(way_nodes.get(i));
					if (tmpNode != null)
						tmpNode.is_intersect = true;
					i++;
				}
			}
		}
	}

	public static void readWays(Iterator<Object> iter, HashMap<Long,Node> nodes)
	{
		JSONObject		tmp;
		int				count;
		ArrayList<Way>	ways;

		ways = new ArrayList();
		count = 0;
		while (iter.hasNext())
		{
			tmp = (JSONObject) iter.next();
			if (tmp.get("type").equals("way"))
			{
				count++;
				handleWay(tmp, nodes, ways);
			}
		}
		Shapefile.saveWaysToFile(ways);
	}

	public static void validateWaysInNodes(HashMap<Long,Node> nodes)
	{
		for (Node node : nodes.values())
			node.validateWays();
	}

	public static HashMap<Long,Node> getNodesFromJson(String filename)
	{
		HashMap<Long,Node>	nodes;
		JSONArray			elts;

		nodes = new HashMap();
		try
		{
			elts = readJsonAndBuildNodes(filename, nodes);
		}
		catch (Exception e)
		{
			System.out.println("Error while reading file.");
			return (null);
		}
		getIntersectNodes(elts.iterator(), nodes);
		readWays(elts.iterator(), nodes);
		validateWaysInNodes(nodes);
		return (nodes);
	}

	public static JSONArray getInfoOfAddr(String addr)
	{
		String	oriUrl;
		try
		{
			oriUrl = "http://nominatim.openstreetmap.org/search/" + URLEncoder.encode(addr, "UTF-8").replace("+", "%20") + "?format=json";
		}
		catch (Exception e)
		{
			return (null);
		}
		HttpURLConnection connection = null;  
		try {
			URL url = new URL(oriUrl);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
			wr.close();
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+ 
			String line;
			while((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return (new JSONArray(response.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(connection != null) {
				connection.disconnect();
			}
		}
	}

	public static Node findClosestNode(HashMap<Long,Node> nodes, double lat, double lon)
	{
		double 		closestScore;
		double		tmpScore;
		Node		closest;

		closest = null;
		closestScore = 0.0;
		for (Node node : nodes.values())
		{
			tmpScore = Operations.getDistanceLatLon(lat, lon, node.lat, node.lon);
			if (node.is_intersect && (closest == null || tmpScore < closestScore))
			{
				closestScore = tmpScore;
				closest = node;
			}
		}
		return (closest);
	}

	public static Node getPoint(HashMap<Long,Node> nodes, String place)
	{
		JSONArray	res;
		JSONObject	info;

		res = (JSONArray)getInfoOfAddr(place);
		info = (JSONObject)res.get(0);
		return (findClosestNode(nodes, info.getDouble("lat"), info.getDouble("lon")));
	}

	public static String secondsToTime(double seconds)
	{
		double 		time;
		int			hours;

		time = seconds;
		hours = (int)(time / 60 / 60);
		time -= (hours * 60 * 60);
		if ((int)(time / 60) < 10)
			return ("" + hours + "h0" + (int)(time / 60));
		return ("" + hours + "h" + (int)(time / 60));
	}

	public static void printBestWay(SearchNode node, double startTime, String start, String end)
	{
		System.out.println("Best way found in " + ((System.currentTimeMillis() - startTime) / 1000) + " seconds !");
		System.out.println("Length : " + node.length + " km");
		System.out.println("Time   : " + secondsToTime(node.time));
		Shapefile.saveWayToFile(node, start, end);
	}

	public static Node getCurrentLastNode(SearchNode node)
	{
		if (node.takeReverses[node.takeReverses.length - 1])
			return (node.ways[node.ways.length - 1].start);
		else
			return (node.ways[node.ways.length - 1].end);
	}

	public static boolean endOfSearch(SearchNode node, Node endPoint)
	{
		Node	currentLastNode;

		currentLastNode = getCurrentLastNode(node);
		if (currentLastNode == endPoint)
			return (true);
		return (false);
	}

	public static void addInitalNodesToTree(BinaryHeap<SearchNode> tree, Node startPoint, Node endPoint)
	{
		int		i;

		i = 0;
		while (i < startPoint.ways.length)
		{
			tree.add(new SearchNode(null, startPoint.ways[i], !startPoint.isAtStart[i], endPoint));
			i++;
		}
	}

	public static void main(String[] args)
	{
		HashMap<Long,Node>		nodes;
		Node					startPoint;
		Node					endPoint;
		BinaryHeap<SearchNode>	tree;
		SearchNode				topNode;
		Node					currentLastNode;
		double					startTime;
		String					start;
		String					end;
		Scanner					read;

		nodes = getNodesFromJson("paris_data.json");
		read = new Scanner(System.in);
		System.out.print("Enter a start adress : ");
		start = read.nextLine();
		System.out.print("Enter an end adress : ");
		end = read.nextLine();
		startPoint = getPoint(nodes, start);
		endPoint = getPoint(nodes, end);
		System.out.println(startPoint);
		System.out.println(endPoint);
		startTime = System.currentTimeMillis();
		if (startPoint == endPoint)
			printBestWay(new SearchNode(null, null, false, null), startTime, start, end);
		else
		{
			tree = new BinaryHeap(BinaryHeap.MIN);
			addInitalNodesToTree(tree, startPoint, endPoint);
			while (!tree.isEmpty())
			{
				topNode = tree.pop();
				topNode.ways[topNode.ways.length - 1].alreadyTaken = true;
				if (endOfSearch(topNode, endPoint))
				{
					printBestWay(topNode, startTime, start, end);
					break;
				}
				currentLastNode = getCurrentLastNode(topNode);
				for (int i = 0; i < currentLastNode.ways.length; i++)
				{
					if (!currentLastNode.ways[i].alreadyTaken && currentLastNode.ways[i].accessGranted && (!currentLastNode.ways[i].oneway || currentLastNode.isAtStart[i]))
					{
						tree.add(new SearchNode(topNode, currentLastNode.ways[i], !currentLastNode.isAtStart[i], endPoint));
						//currentLastNode.ways[i].alreadyTaken = true;
					}
				}
			}
		}
	}

}