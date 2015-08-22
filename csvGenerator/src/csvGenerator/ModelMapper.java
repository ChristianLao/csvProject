package csvGenerator;

import java.util.List;
import java.util.Map;

public interface ModelMapper<T> {

	public T mapLine(List<String> splitCsvLine, Map<String, Integer> headerMap);

}
