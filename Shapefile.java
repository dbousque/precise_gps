

import java.util.ArrayList;
import java.io.PrintWriter;

public class Shapefile
{

	public static void saveWaysToFile(ArrayList<Way> ways)
	{
		PrintWriter		writer;
		int				i;

		writer = null;
		try
		{
			writer = new PrintWriter("./shapefiles/all_ways.ways", "ASCII");
		}
		catch (Exception e)
		{
			System.out.println("Could not write to file.");
		}
		i = 0;
		for (Way way : ways)
		{
			writer.println(Way.getTypeName(way.type));
			writer.println(way.length);
			writer.println(way.time);
			writer.println(way.speed_limit);
			writer.println(way.id);
			writer.println(way.oneway);
			writer.println(way.accessGranted);
			writer.println(way.start.lon + "," + way.start.lat);
			for (Node node : way.nodes)
				writer.println(node.lon + "," + node.lat);
			writer.println(way.end.lon + "," + way.end.lat);
			i++;
			if (i != ways.size())
				writer.println("");
		}
		writer.close();
	}

	public static void saveWayToFile(SearchNode searchNode, String start, String end)
	{
		PrintWriter		writer;
		int				i;

		writer = null;
		try
		{
			writer = new PrintWriter("./shapefiles/" + start.replace(" ", "_") + "_" + end.replace(" ", "_") + ".ways", "ASCII");
			System.out.println("Best way saved to " + "shapefiles/" + start.replace(" ", "_") + "_" + end.replace(" ", "_") + ".ways");
		}
		catch (Exception e)
		{
			System.out.println("Could not write to file.");
		}
		i = 0;
		for (Way way : searchNode.ways)
		{
			writer.println(Way.getTypeName(way.type));
			writer.println(way.length);
			writer.println(way.time);
			writer.println(way.speed_limit);
			writer.println(way.id);
			writer.println(way.oneway);
			writer.println(way.accessGranted);
			writer.println(way.start.lon + "," + way.start.lat);
			for (Node node : way.nodes)
				writer.println(node.lon + "," + node.lat);
			writer.println(way.end.lon + "," + way.end.lat);
			i++;
			if (i != searchNode.ways.length)
				writer.println("");
		}
		writer.close();
	}

}