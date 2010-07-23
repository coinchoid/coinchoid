package net.homelinux.paubox;

import java.util.ArrayList;

public class Inning {

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

}
