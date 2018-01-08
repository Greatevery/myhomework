package faction;

import java.util.ArrayList;

import creature.Creature;
import creature.Location;

public class Faction {
	protected ArrayList<Creature> creatures;
	protected String name;
	public Faction(String name) {
		creatures = new ArrayList<Creature>();
		this.name = name;
	}

	public ArrayList<Creature> getCreatures() {
		return creatures;
	}
	
	public String getName() {
		return name;
	}
}
