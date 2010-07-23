package net.homelinux.paubox;

import java.io.Serializable;
import java.util.ArrayList;

public class Inning implements Serializable {

	// To be serializable
	public static final long serialVersionUID = 1L;

	ArrayList<Deal> deals;
	protected Deal currentDeal() {
		return deals.get(deals.size()-1);
	}

	private void newInning() {
		deals = new ArrayList<Deal>();
		deals.add(new Deal());

	}

	public Inning () {
		newInning();
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		for(Deal i : deals) {
			result.append(i.toString()).append("\n");
		}
		result.deleteCharAt(result.length()-1);
		return result.toString();
	}

	protected void newDeal() {
		deals.add(new Deal());
	}

}
