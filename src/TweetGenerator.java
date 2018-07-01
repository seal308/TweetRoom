import java.util.Random;

public class TweetGenerator {
	
	private static char prefix = 'A';
	
	/*
	
	public static String getTweet(String roomLink)
	{
		String tweet = createTweet(roomLink);
		return tweet;
	}
	
	*/

	public static String createTweet(String roomLink)
	{
		Random r = new Random();
		char rChar = (char)(r.nextInt(26) + 'A');
		// prefix+rChar gave numbers so converted to String to work
		String rString = Character.toString(rChar);
		
		String combinedTweet = prefix + rString + roomLink;
		
		if (prefix == 'Z')
		{
			prefix = 'A';
		}
		else
		{
			prefix++;
		}
		
		return combinedTweet;
	}
}

