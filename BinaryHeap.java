

public class BinaryHeap<T extends Comparable<T>>
{
	public static final boolean MAX = true;
	public static final boolean MIN = false;

	private T[] elts;
	private int last;
	private boolean isMax;

	public BinaryHeap()
	{
		elts = (T[])new Comparable[2];
		elts[0] = null;
		elts[1] = null;
		last = 0;
		isMax = true;
	}

	public BinaryHeap(boolean isMax)
	{
		elts = (T[])new Comparable[2];
		elts[0] = null;
		elts[1] = null;
		last = 0;
		this.isMax = isMax;
	}

	public int size()
	{
		return (last);
	}

	public boolean isEmpty()
	{
		return (last == 0);
	}

	private void doubleTabSize()
	{
		T[]		newElts;
		int		i;

		newElts = (T[])new Comparable[elts.length * 2];
		i = 0;
		while (i <= last)
		{
			newElts[i] = elts[i];
			i++;
		}
		elts = null;
		elts = newElts;
	}

	public void add(T newElt)
	{
		int		i;
		T		tmp;

		if (last == elts.length - 1)
			doubleTabSize();
		last++;
		elts[last] = newElt;
		i = last;
		while (i > 1 && ((isMax && elts[i].compareTo(elts[i / 2]) > 0) || elts[i].compareTo(elts[i / 2]) < 0))
		{
			System.out.println("Swap");
			tmp = elts[i];
			elts[i] = elts[i / 2];
			elts[i] = tmp;
			i /= 2;
		}
	}

	private boolean goodPosition(int i)
	{
		if (isMax && elts[i].compareTo(elts[i * 2]) >= 0 && (i * 2 + 1 > last || elts[i].compareTo(elts[i * 2 + 1]) >= 0))
			return (true);
		if (!isMax && elts[i].compareTo(elts[i * 2]) <= 0 && (i * 2 + 1 > last || elts[i].compareTo(elts[i * 2 + 1]) <= 0))
			return (true);
		return (false);
	}

	private int getToSwap(int i)
	{
		if (isMax)
		{
			if (i * 2 + 1 > last || elts[i * 2].compareTo(elts[i * 2 + 1]) > 0)
				return (i * 2);
			return (i * 2 + 1);
		}
		else
		{
			if (i * 2 + 1 > last || elts[i * 2].compareTo(elts[i * 2 + 1]) < 0)
				return (i * 2);
			return (i * 2 + 1);
		}
	}

	public T pop()
	{
		T		ret;
		T		tmp;
		int		i;
		int		to_swap;

		if (last < 1)
			return (null);
		ret = elts[1];
		elts[1] = elts[last];
		last--;
		i = 1;
		while (i * 2 <= last)
		{
			if (goodPosition(i))
				break;
			to_swap = getToSwap(i);
			tmp = elts[i];
			elts[i] = elts[to_swap];
			elts[to_swap] = tmp;
			if (to_swap == i * 2)
				i *= 2;
			else
				i = i * 2 + 1;
		}
		return (ret);
	}

}