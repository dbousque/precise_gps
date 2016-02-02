

import org.json.*;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.HashMap;

public class Parse
{

	public static Node jsonToNode(JSONObject json)
	{
		Node	res;

		res = new Node(json.getLong("id"), json.getDouble("lat"), json.getDouble("lon"));
		return (res);
	}

	public static String readFile(String path, Charset encoding) throws IOException 
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public static void main(String[] args)
	{
		Iterator<Object>	iter;
		HashMap				nodes;
		JSONObject			tmp;

		try
		{
			nodes = new HashMap();
			JSONObject obj = new JSONObject(readFile("paris_data.json", StandardCharsets.UTF_8));
			JSONArray elts = (JSONArray)obj.get("elements");
			iter = elts.iterator();
			while (iter.hasNext())
			{
				tmp = (JSONObject) iter.next();
				if (tmp.get("type").equals("node"))
					System.out.println(jsonToNode(tmp));
			}
		}
		catch (IOException error)
		{
			System.out.println("Could not read file.");
		}
	}

}