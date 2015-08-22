package csvGenerator;

import java.io.File;
import java.util.Collection;

public class Main {

	public static void main(String[] args) {
		CsvReader<Game> gameReader = new CsvReader<>();
		gameReader.setModelMapper(new GameModelMapper());
		Collection<Game> games = gameReader.parseCsvFile(new File("src/main/resources/game.csv"));

		CsvReader<Inventory> inventoryReader = new CsvReader<>();
		inventoryReader.setModelMapper(new InventoryModelMapper());
		Collection<Inventory> inventory = inventoryReader.parseCsvFile(new File("src/main/resources/inventory.csv"));
		System.out.println("Didn't blow up yet!");
	}

}
