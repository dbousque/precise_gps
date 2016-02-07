

public class Priority
{

	public static double getShortestPathPriority(SearchNode node, Node endPoint, boolean takeReverse)
	{
		Node	currentLastNode;

		if (takeReverse)
			currentLastNode = node.ways[node.ways.length - 1].start;
		else
			currentLastNode = node.ways[node.ways.length - 1].end;
		return (node.length + Operations.getDistance(currentLastNode, endPoint));
	}

	public static double getPriority(SearchNode node, Node endPoint, boolean takeReverse)
	{
		return (getShortestPathPriority(node, endPoint, takeReverse));
	}

}