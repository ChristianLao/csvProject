package csvGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvReader<T> {
	public static char delimiter = ',';
	private ModelMapper<T> modelMapper;

	public Collection<T> parseCsvFile(File file) {
		Collection<T> results = new ArrayList<>();
		BufferedReader br = null;
		try {
			// Open the file
			br = openReader(file);

			// Read the header line from the file
			String line = br.readLine();
			Map<String, Integer> headerMap = mapHeaderToPosition(line);

			// Read lines and map them into the model
			while((line = br.readLine()) != null) {
				T object = modelMapper.mapLine(splitCsvLine(line), headerMap);
				results.add(object);
			}

		} catch (Exception e) {
			System.err.println("Error! " + e.getMessage() + e.getStackTrace());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.err.print("There was an error closing the reader. Well poop.");
				}
			}
		}

		// Return the collection. Will be empty if an error occured
		return results;
	}

	private Map<String, Integer> mapHeaderToPosition(String line) {
		Map<String, Integer> map = new HashMap<>();
		List<String> fields = splitCsvLine(line);
		for (Integer i = 0; i < fields.size(); i++) {
			map.put(fields.get(i), i);
		}
		return map;
	}

	private BufferedReader openReader(File file) throws FileNotFoundException {
		InputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		BufferedReader br = new BufferedReader(isr);
		return br;
	}

	private List<String> splitCsvLine(String line) {
		List<Integer> delimiterPositions = new ArrayList<>();
		List<String> splits = new ArrayList<>();

		// Loop through, count delimiters that aren't escaped
		// Count number of quotes since last delimiter. This is so we don't mistakenly add a delimiter that's escaped
		Integer quoteCount = 0;
		char[] charArray = line.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char cur = charArray[i];
			if (cur == delimiter) {
				if (!isInQuote(quoteCount)) {
					delimiterPositions.add(i);
					quoteCount = 0;
				}
			}

			if (cur == '"') {
				quoteCount++;
			}
		}

		Integer last = 0;
		for (Integer position : delimiterPositions) {
			splits.add(line.substring(last, position));
			last = position;
		}
		// Also add the part after the last delimiter. This also takes care of the single-column case
		if (line.length() > 0) {
			splits.add(line.substring(last, line.length()));
		}
		return splits;
	}

	private boolean isInQuote(Integer quoteCount) {
		// If quotes are over, the number will be even
		return quoteCount % 2 != 0;
	}

	public ModelMapper<T> getModelMapper() {
		return modelMapper;
	}

	public void setModelMapper(ModelMapper<T> modelMapper) {
		this.modelMapper = modelMapper;
	}
}
