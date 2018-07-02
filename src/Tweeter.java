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
		
		String line;
		try {
			line = reader.readLine();
			int counter = 0;
			while (line != null)
			{
				switch (counter)
				{
					// could do a test to see if cKey etc matches up
					case CK_INDEX:
						infoArray[CK_INDEX] = line;
						break;
					case CS_INDEX:
						infoArray[CS_INDEX] = line;
						break;
					case ATOKEN_INDEX:
						infoArray[ATOKEN_INDEX] = line;
						break;
					case ATOKENS_INDEX:
						infoArray[ATOKENS_INDEX] = line;
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
		Status status = twitter2.updateStatus(tweet);
	}
	
}
