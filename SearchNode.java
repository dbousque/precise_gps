

public class SearchNode implements Comparable<SearchNode>
{

	public Way[] ways;
	public boolean[] takeReverses;
	public double length;
	public double time;
	public double priority;

	public SearchNode(SearchNode currentNode, Way newWay, boolean takeReverse, Node endPoint)
	{
		if (currentNode == null)
		{
			if (newWay == null)
			{
				ways = new Way[0];
				takeReverses = new boolean[0];
				length = 0.0;
				time = 0.0;
				priority = 0.0;
			}
			else
			{
				ways = new Way[1];
				ways[0] = newWay;
				takeReverses = new boolean[1];
				takeReverses[0] = takeReverse;
				length = newWay.length;
				time = newWay.time;
				priority = Priority.getPriority(this, endPoint, takeReverse);
			}
		}
		else
		{
			ways = copyWay(currentNode.ways, newWay);
			takeReverses = copyReverses(currentNode.takeReverses, takeReverse);
			length = currentNode.length + newWay.length;
			time = currentNode.time + newWay.time + Operations.getTimeCostOfEndNode(newWay, takeReverse);
			priority = Priority.getPriority(this, endPoint, takeReverse);
		}
	}

	public static Way[] copyWay(Way[] oldWays, Way newWay)
	{
		int		i;
		Way[]	res;

		res = new Way[oldWays.length + 1];
		i = 0;
		while (i < oldWays.length)
		{
			res[i] = oldWays[i];
			i++;
		}
		res[i] = newWay;
		return (res);
	}

	public static boolean[] copyReverses(boolean[] oldReverses, boolean takeReverse)
	{
		int			i;
		boolean[]	res;

		res = new boolean[oldReverses.length + 1];
		i = 0;
		while (i < oldReverses.length)
		{
			res[i] = oldReverses[i];
			i++;
		}
		res[i] = takeReverse;
		return (res);
	}

	public int compareTo(SearchNode otherNode)
	{
		if (priority > otherNode.priority)
			return (1);
		if (priority == otherNode.priority)
			return (0);
		return (-1);
	}

}