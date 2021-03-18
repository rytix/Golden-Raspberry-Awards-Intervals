package martins.paulo.film;

import java.util.ArrayList;
import java.util.List;

public class Film {
	private int year;
	private String title;
	private String studio;
	private List<String> producers;
	private boolean winner;
	
	public Film() {
		
	}
	
	public Film(int year, String title, String studio, String producers, boolean winner) {
		this.year = year;
		this.title = title;
		this.studio = studio;
		this.producers = new ArrayList<>();
		this.winner = winner;
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStudio() {
		return studio;
	}
	public void setStudio(String studio) {
		this.studio = studio;
	}
	public List<String> getProducer() {
		return producers;
	}
	public void setProducers(List<String> producers) {
		this.producers = producers;
	}
	public boolean isWinner() {
		return winner;
	}
	public void setWinner(boolean winner) {
		this.winner = winner;
	}
	
	
}
