package csvGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		CsvReader<Game> gameReader = new CsvReader<>();
		gameReader.setModelMapper(new GameModelMapper());
		Collection<Game> games = gameReader.parseCsvFile(new File("src/main/resources/game.csv"));

		CsvReader<Inventory> inventoryReader = new CsvReader<>();
		inventoryReader.setModelMapper(new InventoryModelMapper());
		Collection<Inventory> inventory = inventoryReader.parseCsvFile(new File("src/main/resources/inventory.csv"));
		
		System.out.println("Didn't blow up yet!");
		
		Collection<GameInventory> gameInventories = combineGameInventories(games, inventory);
		gameReader.writeCsvFile(gameInventories, "output.csv");
	}

	private static Collection<GameInventory> combineGameInventories(
			Collection<Game> games, Collection<Inventory> inventories) {
		Collection<GameInventory> gameInventories = new ArrayList<GameInventory>();
		Map<Long, Game> gameIdToGame = new HashMap<>();
		for (Game game : games) {
			gameIdToGame.put(game.getGameId(), game);
		}
		
		for (Inventory inventory : inventories) {
			gameInventories.add(new GameInventory(gameIdToGame.get(inventory.getGameId()), inventory));
		}
		return gameInventories;
	}

}
