package bc.utils.string;

public class StringUtils
{
	public static String capitalize(String str)
	{
		if (str != null)
		{
			if (str.length() > 0)
			{
				return Character.toUpperCase(str.charAt(0)) + (str.length() > 1 ? str.substring(1) : "");
			}
			
			return str;
		}
		
		return null;
	}
}
