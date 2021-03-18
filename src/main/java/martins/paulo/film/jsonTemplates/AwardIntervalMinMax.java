package martins.paulo.film.jsonTemplates;

import java.util.ArrayList;
import java.util.List;

public class AwardIntervalMinMax {
	private List<Details> min;
	private List<Details> max;
	
	public AwardIntervalMinMax() {
		this.min = new ArrayList<>();
		this.max = new ArrayList<>();
	}
	
	public AwardIntervalMinMax(List<Details> min, List<Details> max) {
		this.min = min;
		this.max = max;
	}
	
	public List<Details> getMin() {
		return min;
	}

	public void setMin(List<Details> min) {
		this.min = min;
	}

	public List<Details> getMax() {
		return max;
	}

	public void setMax(List<Details> max) {
		this.max = max;
	}

	public class Details{
		private int interval;
		private int previousWin;
		private int followingWin;
		private String producer;
		
		public Details(String producer, int interval, int previousWin, int followingWin) {
			this.producer = producer;
			this.interval = interval;
			this.previousWin = previousWin;
			this.followingWin = followingWin;
		}
		
		public String getProducer() {
			return producer;
		}
		public void setProducer(String producer) {
			this.producer = producer;
		}
		public int getInterval() {
			return interval;
		}
		public void setInterval(int interval) {
			this.interval = interval;
		}
		public int getPreviousWin() {
			return previousWin;
		}
		public void setPreviousWin(int previousWin) {
			this.previousWin = previousWin;
		}
		public int getFollowingWin() {
			return followingWin;
		}
		public void setFollowingWin(int followingWin) {
			this.followingWin = followingWin;
		}
	}
}
