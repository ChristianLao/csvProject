package csvGenerator;

public class Inventory {
	@Override
	public String toString() {
		return "Inventory [gameId=" + gameId + ", price=" + price + ", stock="
				+ stock + "]";
	}
	private Long gameId;
	private double price;
	private Long stock;
	public Long getGameId() {
		return gameId;
	}
	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Long getStock() {
		return stock;
	}
	public void setStock(Long stock) {
		this.stock = stock;
	}
}
