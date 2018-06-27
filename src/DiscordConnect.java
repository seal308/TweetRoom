import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.util.logging.ExceptionLogger;

public class DiscordConnect {
	public DiscordConnect()
	{
		String token = getToken();

        new DiscordApiBuilder().setToken(token).login().thenAccept(api -> {
            
            // Add a listener which answers with "Pong!" if someone writes "!ping"
            api.addMessageCreateListener(event -> {
                if (event.getMessage().getContent().equalsIgnoreCase("++ping")) {
                    event.getChannel().sendMessage("Pong!");
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
		System.out.println("The token is!");
		return token;
	}
}
