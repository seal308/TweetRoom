import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.event.ListenerManager;
import org.javacord.api.util.logging.ExceptionLogger;

public class DiscordConnect {
	public DiscordConnect()
	{
		String token = getToken();
		RoomWizard rWizard = new RoomWizard();
		
        new DiscordApiBuilder().setToken(token).login().thenAccept(api -> {
            api.addMessageCreateListener(event -> {
                if (event.getMessage().getContent().equalsIgnoreCase("++ping")) {
                    event.getChannel().sendMessage("Pong!");
                }
              
                //TODO we must change below to attaching a listener to a channel object
                if (event.getChannel().getIdAsString().equals("461813132088836096") ||
                		event.getChannel().getIdAsString().equals("286873797813075968"))
                {
                	if (event.getMessage().getContent().equalsIgnoreCase("++room")) {

                    	event.getMessage().getUserAuthor().ifPresent(user -> {
                            ListenerManager<MessageCreateListener> lm = user.addMessageCreateListener(rWizard);
                            rWizard.turnOn(event.getMessage(),lm);
                        });
                    }
                }
                
            });

            // Print the invite url of your bot
            System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
            
        }).exceptionally(ExceptionLogger.get());
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
		return token;
	}
}
