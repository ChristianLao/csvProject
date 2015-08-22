package csvGenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
			e.printStackTrace();
			System.err.println("Error! " + e.getMessage() + e.getStackTrace());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
					System.err.print("There was an error closing the reader. Well poop.");
				}
			}
		}

		// Return the collection. Will be empty if an error occured
		return results;
	}

	public <U extends CsvWritable> void writeCsvFile(Collection<? extends CsvWritable> uList, String fileName) {
		File file = new File("src/main/resources/" + fileName);
		BufferedWriter bw = null;
		try {
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			if (uList.size() > 0) {
				String header = makeLine(uList.iterator().next().csvHeader());
				bw.write(header);
			}
			
			for (CsvWritable u : uList) {
				bw.write(makeLine(u.toCsvFields()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String makeLine(List<String> fields) {
		StringBuilder builder = new StringBuilder();
		Boolean first = true;
		for (String field : fields) {
			if (!first) {
				builder.append(delimiter);
			}
			builder.append(field);
			first = false;
		}
		builder.append('\n');
		return builder.toString();
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
			last = position + 1;
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
