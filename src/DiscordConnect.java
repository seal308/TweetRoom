import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicReference;

import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.util.event.ListenerManager;
import org.javacord.api.util.logging.ExceptionLogger;

public class DiscordConnect {
	public DiscordConnect()
	{
		String token = getToken();
		RoomWizard rWizard = new RoomWizard();
		AtomicReference<ListenerManager> lm = new AtomicReference<>();

        new DiscordApiBuilder().setToken(token).login().thenAccept(api -> {
        	
        	//api.addMessageCreateListener(rWizard);
            
            // Add a listener which answers with "Pong!" if someone writes "!ping"
            api.addMessageCreateListener(event -> {
                if (event.getMessage().getContent().equalsIgnoreCase("++ping")) {
                    event.getChannel().sendMessage("Pong!");
                }
                if (event.getMessage().getContent().equalsIgnoreCase("++room")) {
                	
                	
                	//MUST DO CHANNEL THING
                	
                	/*
                	if (event.getMessage().getChannel().getIdAsString()=="461813132088836096")
                	{
                		
                	}
                	*/
                	
                    //event.getChannel().sendMessage("ROOM!");
                    //RoomWizard rWizard = new RoomWizard();
                    //api.addMessageCreateListener(rWizard);
                	event.getMessage().getUserAuthor().ifPresent(user -> {
                        //user.addMessageCreateListener(rWizard);
                        lm.set(user.addMessageCreateListener(rWizard));
                        //rWizard.turnOn(event.getMessage());
                        rWizard.turnOn(event.getMessage(),lm);
                    });
                    //rWizard.turnOn(event.getMessage());
                }
            });
            
            // Print the invite url of your bot
            System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
            
        }).exceptionally(ExceptionLogger.get());
        
        //RoomWizard rWizard = new RoomWizard();
     
	}
	
	public String getToken()
	{
		String token = "";
		InputStream in = getClass().getResourceAsStream("/DiscordToken.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		try {
			line = reader.readLine();
			if (line != null)
				token = line;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("The token is!");
		return token;
	}
}
