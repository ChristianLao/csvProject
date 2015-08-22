package csvGenerator;

import java.util.List;
import java.util.Map;

public class GameModelMapper implements ModelMapper<Game> {

	@Override
	public Game mapLine(List<String> splitCsvLine,
			Map<String, Integer> headerMap) {
		Game game = new Game();
		game.setGameId(Long.valueOf(splitCsvLine.get(headerMap.get("Game ID"))));
		game.setGameName(splitCsvLine.get(headerMap.get("Game Name")));
		game.setDescription(splitCsvLine.get(headerMap.get("Description")));
		game.setMsrp(Double.valueOf(splitCsvLine.get(headerMap.get("MSRP"))));
		return game;
	}

}
