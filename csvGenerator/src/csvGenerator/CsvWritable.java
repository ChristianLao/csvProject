package csvGenerator;

import java.util.List;

public interface CsvWritable {
	public List<String> csvHeader();
	public List<String> toCsvFields();
}
