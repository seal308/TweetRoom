public class main
{

	public static void main (String[] args)
	{
		System.out.println("Hi there");
		
		Tweeter twitterO = new Tweeter();
		twitterO.sendTweet();
		
		/*
		try {
			testPostingToTwitter();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		
	}
	
	/*

	public static void testPostingToTwitter() throws TwitterException{
		Twitter twitter = TwitterFactory.getSingleton();
		String message="Bhttp://imgur.com/oFDj1cn Room Link: [PUT ROOM LINK HERE] <@&288431932335718403> <@&319561543530446848>";
		Status status = twitter.updateStatus(message);
	}
	
	*/

}

