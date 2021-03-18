package martins.paulo.film;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import martins.paulo.persistence.CSVConverter;


@RestController
public class FilmController {

	@GetMapping("/awardInterval")
	public Map<String, List<AwardInterval>> awardInterval() {
		try {
			List<Film> films = CSVConverter.getFilms();
			Map<String, AwardInterval> shortAwardIntervalMap = new HashMap<>();
			Map<String, AwardInterval> longAwardIntervalMap = new HashMap<>();

			for (Film film : films) {
				for (String producer : film.getProducer()) {
					AwardInterval shortAwardInterval = shortAwardIntervalMap.get(producer);
					AwardInterval longAwardInterval = longAwardIntervalMap.get(producer);

					shortAwardInterval = compareAndSetShortAwardInterval(shortAwardInterval, producer, film.getYear());
					longAwardInterval = compareAndSetAwardLongInterval(longAwardInterval, producer, film.getYear());

					shortAwardIntervalMap.put(producer, shortAwardInterval);
					longAwardIntervalMap.put(producer, longAwardInterval);
				}
			}

			return getMinMaxAwardInterval(shortAwardIntervalMap, longAwardIntervalMap);
		} catch (IOException e) {
			System.out.println(e);
		}
		return null;
	}

	private Map<String, List<AwardInterval>> getMinMaxAwardInterval(Map<String, AwardInterval> shortAwardIntervalMap,
			Map<String, AwardInterval> longAwardIntervalMap) {
		List<AwardInterval> minAwardInterval = new ArrayList<>();
		List<AwardInterval> maxAwardInterval = new ArrayList<>();
		for(AwardInterval awardInterval : shortAwardIntervalMap.values()) {
			if(awardInterval.getInterval() == null) {
				continue;
			}else if(minAwardInterval.isEmpty()) {
				minAwardInterval.add(awardInterval);
			}else if(minAwardInterval.get(0).getInterval() > awardInterval.getInterval()) {
				minAwardInterval.clear();
				minAwardInterval.add(awardInterval);
			}else if(minAwardInterval.get(0).getInterval() == awardInterval.getInterval()) {
				minAwardInterval.add(awardInterval);
			}
			
		}
		
		for(AwardInterval awardInterval : longAwardIntervalMap.values()) {
			if(awardInterval.getInterval() == null) {
				continue;
			}else if(maxAwardInterval.isEmpty()) {
				maxAwardInterval.add(awardInterval);
			}else if(maxAwardInterval.get(0).getInterval() < awardInterval.getInterval()) {
				maxAwardInterval.clear();
				maxAwardInterval.add(awardInterval);
			}else if(maxAwardInterval.get(0).getInterval() == awardInterval.getInterval()) {
				maxAwardInterval.add(awardInterval);
			}
		}
		
		Map<String, List<AwardInterval>> resultMap = new HashMap<>();
		resultMap.put("min", minAwardInterval);
		resultMap.put("max", maxAwardInterval);
		
		return resultMap;
	}

	private AwardInterval compareAndSetAwardIntervalSetup(AwardInterval awardInterval, String producer, Integer year) {
		if (awardInterval == null) {
			awardInterval = new AwardInterval(producer, null, year, null);
			return awardInterval;
		} else if (awardInterval.getFollowingWin() == null) {
			if (awardInterval.getPreviousWin() < year) {
				awardInterval.setFollowingWin(year);
			} else {
				awardInterval.setFollowingWin(awardInterval.getPreviousWin());
				awardInterval.setPreviousWin(year);
			}
			awardInterval.setInterval(awardInterval.getFollowingWin() - awardInterval.getPreviousWin());
			return awardInterval;
		}
		return awardInterval;
	}

	private AwardInterval compareAndSetShortAwardInterval(AwardInterval awardInterval, String producer, Integer year) {
		awardInterval = compareAndSetAwardIntervalSetup(awardInterval, producer, year);

		if (awardInterval.getFollowingWin() == null) {
			return awardInterval;
		} else if (awardInterval.getPreviousWin() < year && year < awardInterval.getFollowingWin()) {
			awardInterval.setPreviousWin(year);
		} else if (awardInterval.getFollowingWin() > year && year > awardInterval.getPreviousWin()) {
			awardInterval.setFollowingWin(year);
		}

		awardInterval.setInterval(awardInterval.getFollowingWin() - awardInterval.getPreviousWin());
		return awardInterval;
	}

	private AwardInterval compareAndSetAwardLongInterval(AwardInterval awardInterval, String producer, Integer year) {
		awardInterval = compareAndSetAwardIntervalSetup(awardInterval, producer, year);

		if (awardInterval.getFollowingWin() == null) {
			return awardInterval;
		} else if (awardInterval.getPreviousWin() > year) {
			awardInterval.setPreviousWin(year);
		} else if (awardInterval.getFollowingWin() < year) {
			awardInterval.setFollowingWin(year);
		}

		awardInterval.setInterval(awardInterval.getFollowingWin() - awardInterval.getPreviousWin());
		return awardInterval;
	}
}
