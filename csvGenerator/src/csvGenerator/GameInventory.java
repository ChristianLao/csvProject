package csvGenerator;

import java.util.ArrayList;
import java.util.List;

public class GameInventory implements CsvWritable {
	private Game game;
	private Inventory inventory;

	public GameInventory(Game game, Inventory inventory) {
		this.game = game;
		this.inventory = inventory;
	}
	
	@Override
	public List<String> csvHeader() {
		List<String> fields = new ArrayList<String>();
		fields.add("Game ID");
		fields.add("Game Name");
		fields.add("Description");
		fields.add("Price");
		fields.add("Stock");
		fields.add("Percent Off");
		return fields;
	}
	
	@Override
	public List<String> toCsvFields() {
		List<String> fields = new ArrayList<String>();
		fields.add(String.valueOf(game.getGameId()));
		fields.add(game.getGameName());
		fields.add(game.getDescription());
		fields.add(String.valueOf(inventory.getPrice()));
		fields.add(String.valueOf(inventory.getStock()));
		fields.add(String.valueOf(percentOff(inventory.getPrice(), game.getMsrp())));
		return fields;
	}
	
	private Double percentOff(Double price, Double msrp) {
		if (Double.compare(msrp, Double.valueOf("0")) == 0) {
			return -1.00;
		} else {
			return (1.00-price/msrp)*100;
		}
	}
}
