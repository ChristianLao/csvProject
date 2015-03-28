package eclipseServer.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eclipseServer.model.GameState;

@Configuration
public class GameConfiguration {
	@Bean(name="gameState")
	public GameState gameState() {
		GameState state = new GameState();
		state.setTest("hi");
		return state;
	}
}
