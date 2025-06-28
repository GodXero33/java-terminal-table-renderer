package tableprinter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TablePrinter  {
	public TablePrinter() {}

	private List<Integer> getMaxWidths(List<String> headers, List<List<String>> data) {
		final int cols = headers.size();
		final int rows = data.size();
		final List<Integer> output = IntStream.range(0, cols).mapToObj(_ -> 0).collect(Collectors.toList());

		for (int a = 0; a < cols; a++) {
			final int headerLen = headers.get(a).length();

			if (headerLen > output.get(a)) output.set(a, headerLen);

			for (int b = 0; b < rows; b++) {
				final int dataLen = data.get(b).get(a).length();

				if (dataLen > output.get(a)) output.set(a, dataLen);
			}
		}

		return output;
	}

	public String getTable(Class<?> className, List<?> instances, List<String> columnHeaders) {
		final List<String> declaredFields = Arrays.asList(className.getDeclaredFields()).stream().map(field -> field.getName()).toList();
		final List<String> methodNames = declaredFields.stream().map(field -> "get" + Character.toUpperCase(field.charAt(0)) + field.substring(1)).toList();
		final List<String> headers = columnHeaders == null ? declaredFields : columnHeaders;
		final List<List<String>> data = new ArrayList<>();
		final int cols = declaredFields.size();
		final int rows = instances.size();

		for (int a = 0; a < rows; a++) {
			final List<String> row = new ArrayList<>();

			for (int b = 0; b < cols; b++) {
				try {
					final Method method = className.getMethod(methodNames.get(b));
					final Object value = method.invoke(instances.get(a));

					row.add(value.toString());
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
					System.err.println("Failed to invoke method '" + methodNames.get(b) + "' on class '" + className.getSimpleName() + "': " + exception.getMessage());
					exception.printStackTrace();
				}
			}

			data.add(row);
		}

		final List<Integer> maxWidths = this.getMaxWidths(headers, data);
		final String hr = "+%s+".formatted(maxWidths.stream().map(width -> "-".repeat(width + 2)).collect(Collectors.joining("+")));
		final StringBuilder outputBuilder = new StringBuilder(hr);

		outputBuilder.append("\n| ");

		IntStream.range(0, cols).mapToObj(i -> i).collect(Collectors.toList()).forEach(i -> outputBuilder.append(headers.get(i) + " ".repeat(maxWidths.get(i) - headers.get(i).length()) + " |" + (i == cols - 1 ? "" : " ")));

		outputBuilder.append('\n');
		outputBuilder.append(hr);

		data.forEach(row -> {
			outputBuilder.append("\n| ");

			IntStream.range(0, cols).mapToObj(i -> i).collect(Collectors.toList()).forEach(i -> outputBuilder.append(row.get(i) + " ".repeat(maxWidths.get(i) - row.get(i).length()) + " |" + (i == cols - 1 ? "" : " ")));

			outputBuilder.append('\n');
			outputBuilder.append(hr);
		});

		return outputBuilder.toString();
	}

	public String getTable(Class<?> className, List<?> instances) {
		return this.getTable(className, instances, null);
	}
}
