import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Tweeter {
	
	Twitter twitter2;
	
	private final int CK_INDEX = 0; //Consumer Key
	private final int CS_INDEX = 1; //Consumer Secret
	private final int ATOKEN_INDEX = 2; //Access Token
	private final int ATOKENS_INDEX = 3; //Access Token Secret
	
	public Tweeter()
	{
		String[] twitterInfo = getTwitterInfo();
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(twitterInfo[CK_INDEX])
		  .setOAuthConsumerSecret(twitterInfo[CS_INDEX])
		  .setOAuthAccessToken(twitterInfo[ATOKEN_INDEX])
		  .setOAuthAccessTokenSecret(twitterInfo[ATOKENS_INDEX]);
		TwitterFactory tf = new TwitterFactory(cb.build());
		 twitter2 = tf.getInstance();
	}
	
	public String[] getTwitterInfo()
	{
		String[] infoArray = new String[4];
		
		InputStream in = getClass().getResourceAsStream("/TwitterInfo.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		// WRITE TO ROOM FILE, PATH RELEVENT TO MACHINE
		
		File roomsInfoFile = new File("C:\\Users\\Glenn\\Desktop\\RoomsInfo.txt");
		FileWriter fw;
		try {
			fw = new FileWriter(roomsInfoFile, true);
			BufferedWriter bw = null;
			bw = new BufferedWriter(fw);
			//BELOW LINE WORKS AND WRITES TO FILE
			//bw.write("123456");
			
			//fw.close();
			bw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String line;
		try {
			line = reader.readLine();
			int counter = 0;
			while (line != null)
			{
				System.out.println(line);
				
				switch (counter)
				{
					// could do a test to see if cKey etc matches up
					case CK_INDEX:
						infoArray[CK_INDEX] = line;
						//System.out.println("cKey is: " + infoArray[CK_INDEX]);
						break;
					case CS_INDEX:
						infoArray[CS_INDEX] = line;
						//System.out.println("cSecret is: " + infoArray[CS_INDEX]);
						break;
					case ATOKEN_INDEX:
						infoArray[ATOKEN_INDEX] = line;
						//System.out.println("aToken is: " + infoArray[ATOKEN_INDEX]);
						break;
					case ATOKENS_INDEX:
						infoArray[ATOKENS_INDEX] = line;
						//System.out.println("aTokentSecret is " + infoArray[ATOKENS_INDEX]);
						break;
				}
				
				
				line = reader.readLine();
				counter++;
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return infoArray;
	}
	
	public void sendTweet(String roomLink)
	{
		String tweet = TweetGenerator.createTweet(roomLink);
		
		try {
			postToTwitter(tweet);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e.getErrorCode()==187)
			{
				sendTweet(tweet);
			}
		}
	}
	
	public void postToTwitter(String tweet) throws TwitterException{
		//String message="Ihttp://imgur.com/oFDj1cn Room Link: [PUT ROOM LINK HERE] <@&288431932335718403> <@&319561543530446848>";
		//String message=tweet;
		//Status status = twitter2.updateStatus(message);
		Status status = twitter2.updateStatus(tweet);
	}
	
}
