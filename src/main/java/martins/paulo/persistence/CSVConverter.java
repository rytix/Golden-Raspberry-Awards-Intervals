package martins.paulo.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import martins.paulo.film.Film;

public class CSVConverter {

	public static List<Film> getFilms() throws IOException {
		List<Film> films = new ArrayList<Film>();
		String fileName = "./movielist.csv";
		Path myPath = Paths.get(fileName);

		CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

		try (BufferedReader br = Files.newBufferedReader(myPath, StandardCharsets.UTF_8);
				CSVReader reader = new CSVReaderBuilder(br).withCSVParser(parser).build()) {

			List<String[]> rows = reader.readAll();
			rows.remove(0);
			for (String[] row : rows) {
				
				Film film = new Film();
				
				film.setYear(Integer.parseInt(row[0]));
				film.setTitle(row[1]);
				film.setStudio(row[2]);
				film.setProducers(new ArrayList<String>(Arrays.asList(row[3].split("( and |, )"))));
				film.setWinner(row[4].equalsIgnoreCase("yes") ? true : false);
				
				films.add(film);
			}
		}
		return films;
	}
}
