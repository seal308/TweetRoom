import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.event.ListenerManager;

public abstract class Wizard implements MessageCreateListener {

	protected Set<Channel> channelSet = new HashSet<Channel>();;

	private Boolean on = false;
	private User wizUser;
	private Set<String> inWizardIDs = new HashSet<String>();
	HashMap<String, ListenerManager> lmDict = new HashMap<>();

	private String wizardInput = "";
	

	public abstract void validResponse(Message messageWiz);

	public abstract void invalidResponse(Message messageWiz);

	public abstract void validOperation(Message messageWiz, int index);

	public abstract void displayOptions(Message messageWiz);

	public abstract int getRange();
	
	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		Message messageWiz = event.getMessage();
		runWizard(messageWiz);
	}

	public void runWizard(Message messageWiz) {
		if (on == true && inWizardIDs.contains(messageWiz.getAuthor().getIdAsString()));
		{
			wizardInput = messageWiz.getContent();
			int index = -1;

			if (wizardInput.equals("exit"))
			{
				turnOff(messageWiz.getAuthor().getIdAsString());
			} else
			{
				try
				{
					index = Integer.parseInt(wizardInput);
					inRange(index, getRange(), messageWiz);

				} catch (NumberFormatException nfe)
				{
					invalidResponse(messageWiz);
				}
			}
		}
	}

	public void inRange(int index, int range, Message messageWiz) {
		if (index > 0 && index <= range)
		{
			validSelection(messageWiz, index);
		} else
		{
			invalidResponse(messageWiz);
		}
	}

	public void validSelection(Message msgWiz, int index) {
		validResponse(msgWiz);
		validOperation(msgWiz, index);
		turnOff(msgWiz.getAuthor().getIdAsString());
	}

	public void turnOn(Message msgWiz, ListenerManager lm) {

		displayOptions(msgWiz);
		on = true;
		
		if (msgWiz.getUserAuthor().isPresent())
		{
			wizUser = msgWiz.getUserAuthor().get();
			inWizardIDs.add(wizUser.getIdAsString());
			lmDict.put(wizUser.getIdAsString(), lm);
		}
	}
	
	public void turnOff(String userRemoveID) {

		inWizardIDs.remove(userRemoveID);
		ListenerManager lm = lmDict.get(userRemoveID);
		lm.remove();
		lmDict.remove(userRemoveID);
	}

}