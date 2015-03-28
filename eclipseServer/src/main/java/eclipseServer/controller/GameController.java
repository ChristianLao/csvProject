package eclipseServer.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eclipseServer.model.GameState;

@Controller
public class GameController {
	@Resource(name="gameState")
	private GameState state;
	
	@RequestMapping(value="/gameState")
	@ResponseBody
	public String getGameState() {
		return state.getTest();
	}
}
