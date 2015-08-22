package csvGenerator;

import java.util.List;
import java.util.Map;

public class InventoryModelMapper implements ModelMapper<Inventory> {

	@Override
	public Inventory mapLine(List<String> splitCsvLine,
			Map<String, Integer> headerMap) {
		Inventory inventory = new Inventory();
		inventory.setGameId(Long.valueOf(splitCsvLine.get(headerMap.get("Game ID"))));
		inventory.setPrice(Double.valueOf(splitCsvLine.get(headerMap.get("Price"))));
		inventory.setStock(Long.valueOf(splitCsvLine.get(headerMap.get("Stock"))));
		return inventory;
	}

}
