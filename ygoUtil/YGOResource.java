package ygoUtil;

public class YGOResource {
	private int unlockedPacks;
	private int unlockedDecks;
	private int totalDuelPoints;
	private int wins;
	private int losses;
	private int debugMode;
	private int versionNumber;

	public void setUnlockedPacks(int unlockedPacks) {
		this.unlockedPacks = unlockedPacks;
	}

	public int getUnlockedPacks() {
		return unlockedPacks;
	}

	public void setUnlockedDecks(int unlockedDecks) {
		this.unlockedDecks = unlockedDecks;
	}

	public int getUnlockedDecks() {
		return unlockedDecks;
	}

	public void setTotalDuelPoints(int totalDuelPoints) {
		this.totalDuelPoints = totalDuelPoints;
	}

	public int getTotalDuelPoints() {
		return totalDuelPoints;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getWins() {
		return wins;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public int getLosses() {
		return losses;
	}

	public void setDebugMode(int debugMode) {
		this.debugMode = debugMode;
	}

	public int getDebugMode() {
		return debugMode;
	}

	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}

	public int getVersionNumber() {
		return versionNumber;
	}
}
