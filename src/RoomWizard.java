import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class RoomWizard extends Wizard implements MessageCreateListener {
	
	HashMap<String, String> roomDict = new HashMap<>();
	ArrayList<String> hostProviders = new ArrayList<String>();
	
	public RoomWizard()
	{
		readRooms();
	}
	
	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		System.out.println("FUCK IS THIS WORKING?");
		runWizard(event.getMessage());
	}
	
	//Might want to make below method it's own class, RoomWizardIO
	public void readRooms()
	{
		//InputStream in = getClass().getResourceAsStream("C:\\Users\\Glenn\\Desktop\\RoomsInfo.txt");
		BufferedReader reader = null;
		FileReader fr;
		try {
			fr = new FileReader("C:\\Users\\Glenn\\Desktop\\RoomsInfo.txt");
			reader = new BufferedReader(fr);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//InputStream in = getClass().getResourceAsStream("/TwitterInfo.txt");
		//BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		String line;
		try {
			line = reader.readLine();
			while (line != null)
			{
				System.out.println(line);
				String[] keyValue = line.split("\\|");
				String key = keyValue[0];
				String value = keyValue[1];
				//System.out.println("the Key is: "+key+", the value is: " + value);
				roomDict.put(key, value);
				hostProviders.add(key);
				line = reader.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//sort alphabetically
		Collections.sort(hostProviders, String.CASE_INSENSITIVE_ORDER);
	}
	

	@Override
	public void validResponse(Message messageWiz) {
		// TODO Auto-generated method stub
		messageWiz.getChannel().sendMessage("Valid Room\nPlease check #simulcast_updates\nIf the announcement isn't there type ++room again");
	}

	@Override
	public void invalidResponse(Message messageWiz) {
		// TODO Auto-generated method stub
		messageWiz.getChannel().sendMessage("Invalid Room #, try again or type exit to leave wizard");
		
	}

	@Override
	public void validOperation(Message messageWiz, int index) {
		// TODO Auto-generated method stub
		// send tweet
		System.out.println("valid index:" + index);
		System.out.println("validOp, " + "ALval: " + hostProviders.get(index-1));
		String hpName = hostProviders.get(index-1);
		Tweeter tweetO = new Tweeter();
		tweetO.sendTweet(roomDict.get(hpName));
	}

	@Override
	public void displayOptions(Message messageWiz) {
		// TODO Auto-generated method stub
		String replyString = "Please enter the number of the room you want to announce\n";
		replyString += "Or type exit to get out of this wizard\n\n";
		
		for (int index = 1; index <= hostProviders.size(); index++)
		{
			replyString += index + ": " + hostProviders.get(index-1) + "\n";
		}
		messageWiz.getChannel().sendMessage(replyString);
	}

	@Override
	public int getRange() {
		// TODO Auto-generated method stub
		int returnVal = -1;
		returnVal = hostProviders.size();
		return returnVal;
	}
	
}
