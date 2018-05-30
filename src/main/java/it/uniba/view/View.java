package it.uniba.view;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.ArrayList;
import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;

//import it.uniba.model.Edge;
import it.uniba.workdata.User;
import it.uniba.workdata.Channel;

/**
 * This class consist of methods for the all prints needed.
 */
public class View {

	///////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * View's constructor.
	 */
	public View() {
		// This constructor is intentionally empty. Nothing special is needed here.
	}

	// -u
	/**
	 * Prints a list of <b>users</b>.
	 * 
	 * @param users
	 *            <i>Collection</i> of users to print
	 */
	public void printMembers(final Collection<User> users) {
		for (final User us : users) {
			System.out.println(us.getRealName() + "\t@" + us.getName());
		}
	}

	// -c
	/**
	 * Prints a list of <b>channels</b>.
	 * 
	 * @param channels
	 *            <i>Collection</i> of channels to print
	 */
	public void printChannels(final Collection<Channel> channels) {
		for (final Channel ch : channels) {
			System.out.println(ch.getName());
		}
	}

	// -uc
	/**
	 * Prints all the <b>channels</b> and the respective <b>members</b> .
	 * 
	 * @param users
	 *            <i>HashMap</i> of users
	 * @param channels
	 *            <i>Collection</i> of channels
	 */
	public void printMembers4Channel(final MasterWrapper.UsersWrapper usersW,
			final MasterWrapper.ChannelsWrapper channelsW) {
		for (final Channel channel : channelsW.values()) {
			System.out.print(" + " + channel.getName() + "\n\t");
			for (final String usersId : channel.getMemberList()) {
				String tmp = usersW.getRealName(usersId);
				System.out.print(" - " + tmp);
				tmp = usersW.getName(usersId);
				System.out.print("\t@ " + tmp + "\n\t");
			}
			System.out.println();
		}
	}

	// -cu <channel>
	/**
	 * Prints all the <b>members</b> of a specified <b>channel</b>.
	 * 
	 * @param users
	 *            <i>HashMap</i> of users
	 * @param channels
	 *            <i>HashMap</i> of channels
	 * @param nameChannel
	 *            <i>String</i> name of the channel specified
	 */
	public void printChannelMembers(final MasterWrapper.UsersWrapper usersW,
			final MasterWrapper.ChannelsWrapper channelsW, final String nameChannel) {
		if (nameChannel != null && channelsW.containsKey(nameChannel)) {
			System.out.print(" + " + nameChannel + "\n");
			final Channel channel = new Channel(channelsW.get(nameChannel));
			for (final String key : channel.getMemberList()) {
				String tmp = usersW.getRealName(key);
				System.out.print("\t -" + tmp);
				tmp = usersW.getName(key);
				System.out.print(" @" + tmp + "\n");
			}
			System.out.println();
		} else {
			WarningMessage.missingChannel(nameChannel);
		}
	}

	/**
	 * Prints all the mention (<i>all the 'edges'</i>).
	 * 
	 * @param edges
	 *            <i>Collection</i> of Edges (mentions)
	 * @param weight
	 *            <i>boolean</i> used to decide if print the weight of the edges.
	 */
	public void printMention(final MasterWrapper.EdgesWrapper edgesW, final boolean weight) {
		printEdges(edgesW, weight);
	}

	/**
	 * Prints a collection of edges.
	 * 
	 * @param edges
	 *            <i>Collection</i> of Edges
	 * @param weight
	 *            <i>boolean</i> used to decide if print the weight of the edges.
	 */
	private void printEdges(final MasterWrapper.EdgesWrapper edgesW, final boolean weight) {
		if (edgesW.isEmpty()) {
			WarningMessage.noMention();
		} else {
			int position = 0;
//			 = new MasterWrapper.EdgesWrapper(edges);
			while (position < edgesW.size()) {
				// for (final Edge edtmp : edgesW.getEdges()) {
				// final EdgeWrapper edgeTmp = new EdgeWrapper(ed);
				// final User userFrom = edgesW.getFrom(i);
				// final User userTo = edgesW.getTo(i);
				String temp = edgesW.getFromRealName(position);
				System.out.print("From: " + temp);
				temp = edgesW.getToRealName(position);
				System.out.print("\tTo: " + temp);
				if (weight) {
					System.out.print("\t n. mention: " + (int) edgesW.getWeight(position));
				}
				System.out.println("");
				position++;
				// }
			}
		}
	}

}
