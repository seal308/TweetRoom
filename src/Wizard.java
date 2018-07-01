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
	HashMap<String, AtomicReference<ListenerManager>> lmDict = new HashMap<>();

	private String wizardInput = "";
	
	//AtomicReference<ListenerManager> lm;

	public abstract void validResponse(Message messageWiz);

	public abstract void invalidResponse(Message messageWiz);

	public abstract void validOperation(Message messageWiz, int index);

	public abstract void displayOptions(Message messageWiz);

	public abstract int getRange();
	
	/*
	@Override
	public void onMessageCreate(DiscordAPI api, Message messageWiz) {

		runWizard(messageWiz);
	}
	*/
	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		Message messageWiz = event.getMessage();
		runWizard(messageWiz);
	}

	public void runWizard(Message messageWiz) {
		System.out.println("IN RUN_WIZARD");
		System.out.println("ON: " + on);
		//if (on == true && inWizardIDs.contains(messageWiz.getAuthor().getId()))
		if (on == true && inWizardIDs.contains(messageWiz.getAuthor().getIdAsString()));
		{
			System.out.println("IDsContainsIF");
			wizardInput = messageWiz.getContent();
			int index = -1;

			if (wizardInput.equals("exit"))
			{
				//turnOff(messageWiz.getAuthor().getId());
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
			System.out.println("inRange_valid");
			validSelection(messageWiz, index);
		} else
		{
			System.out.println("inRange_INvalid");
			invalidResponse(messageWiz);
		}
	}

	public void validSelection(Message msgWiz, int index) {
		validResponse(msgWiz);
		validOperation(msgWiz, index);
		//turnOff(msgWiz.getAuthor().getId());
		turnOff(msgWiz.getAuthor().getIdAsString());
	}

	public void turnOn(Message msgWiz, AtomicReference<ListenerManager> lmanager) {
		//lm = lmanager;
		AtomicReference<ListenerManager> lm = lmanager;
		
		displayOptions(msgWiz);
		on = true;
		//wizUser = msgWiz.getAuthor();
		// check if has value
		if (msgWiz.getUserAuthor().isPresent())
		{
			// below throws a NoSuchElementException if value not there
			wizUser = msgWiz.getUserAuthor().get();
			//inWizardIDs.add(wizUser.getId());
			inWizardIDs.add(wizUser.getIdAsString());
			lmDict.put(wizUser.getIdAsString(), lm);
		}
	}
	
	// turn off might need to call roomWizard method to clear the HP AL.
	public void turnOff(String userRemoveID) {

		inWizardIDs.remove(userRemoveID);
		AtomicReference<ListenerManager> lm = lmDict.get(userRemoveID);
		if (lm==null)
		{
			System.out.println("LM IS NULL");;
		}
		lm.get().remove();
		lmDict.remove(userRemoveID);
		if (lmDict.isEmpty())
		{
			System.out.println("lmDict IS NULL");;
		}
		if (inWizardIDs.isEmpty())
		{
			on = false;
		}
		
		//lm.get().remove();

	}

}