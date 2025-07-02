package tableprinter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleTablePrinter {
	private SimpleTablePrinterSettings settings;
	private SettingAdjuster adjustSettings;
	private String caption;

	public SimpleTablePrinter() {
		this.adjustSettings = new SettingAdjuster(this);
	}

	private static List<Integer> getMaxWidths(List<String> headers, String[][] data) {
		final int rows = data.length;
		final int cols = headers.size();

		final List<Integer> output = IntStream.range(0, cols).mapToObj(_ -> 0).collect(Collectors.toList());

		for (int a = 0; a < cols; a++) {
			final int headerLen = headers.get(a).length();

			if (headerLen > output.get(a)) output.set(a, headerLen);

			for (int b = 0; b < rows; b++) {
				final int dataLen = data[b][a].length();

				if (dataLen > output.get(a)) output.set(a, dataLen);
			}
		}

		return output;
	}

	private static String formatCell(String content, int width, String align) {
		int padding = width - content.length();

		switch (align) {
			case "center": {
				int padRight = padding / 2;
				int padLeft = padding - padRight;
				return " ".repeat(padLeft) + content + " ".repeat(padRight);
			}

			case "right":
				return " ".repeat(padding) + content;

			case "left":
			default:
				return content + " ".repeat(padding);
		}
	}

	public SimpleTablePrinterSettings getSettings() {
		if (this.settings == null) this.settings = new SimpleTablePrinterSettings();
		return this.settings;
	}

	public SettingAdjuster adjust() {
		return this.adjustSettings;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getTable(Class<?> className, List<?> instances, List<String> columnHeaders) {
		final SimpleTablePrinterSettings settings = this.getSettings();
		final List<String> declaredFields = Arrays.asList(className.getDeclaredFields()).stream().map(field -> field.getName()).toList();
		final List<String> methodNames = declaredFields.stream().map(field -> "get" + Character.toUpperCase(field.charAt(0)) + field.substring(1)).toList();
		final List<String> headers = columnHeaders == null ? declaredFields : columnHeaders;
		final int cols = declaredFields.size();
		final int rows = instances.size();
		final String[][] data = new String[rows][cols];

		for (int a = 0; a < rows; a++) {
			for (int b = 0; b < cols; b++) {
				try {
					final Method method = className.getMethod(methodNames.get(b));
					final Object value = method.invoke(instances.get(a));

					data[a][b] = (value == null) ? "null" : value.toString();
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
					System.err.println("Failed to invoke method '" + methodNames.get(b) + "' on class '" + className.getSimpleName() + "': " + exception.getMessage());
					exception.printStackTrace();
				}
			}
		}

		final List<Integer> maxWidths = SimpleTablePrinter.getMaxWidths(headers, data);
		final char columnSeparator = this.settings.getColumnSeparator();
		final String[] rowSeparators = this.settings.getRowSeparator().split("");
		final String hr = "%s%s%s".formatted(rowSeparators[0], maxWidths.stream().map(width ->rowSeparators[1].repeat(width + 2)).collect(Collectors.joining(rowSeparators[0])), rowSeparators[0]);
		final StringBuilder outputBuilder = new StringBuilder();

		if (this.caption == null) {
			outputBuilder.append('\n').append(hr);
		} else {
			final int hrLen = hr.length();
			final int padding = hrLen - this.caption.length() - 2;
			final int paddingRight = padding / 2;
			final int paddingLeft = padding - paddingRight;

			outputBuilder.append('\n').append(rowSeparators[0]).append(rowSeparators[1].repeat(hrLen - 2)).append(rowSeparators[0]);
			outputBuilder.append("\n").append(columnSeparator);
			outputBuilder.append(" ".repeat(paddingLeft)).append(this.caption).append(" ".repeat(paddingRight)).append(columnSeparator).append('\n').append(hr);
		}

		outputBuilder.append("\n").append(columnSeparator);
		
		IntStream.range(0, cols).forEach(i -> outputBuilder.append(" ").append(SimpleTablePrinter.formatCell(headers.get(i), maxWidths.get(i), settings.getTextAlign())).append(" ").append(columnSeparator));

		outputBuilder.append('\n');
		outputBuilder.append(hr);

		for (int a = 0; a < rows; a++) {
			final String[] row = data[a];

			outputBuilder.append("\n").append(columnSeparator);

			IntStream.range(0, cols).forEach(i -> outputBuilder.append(" ").append(SimpleTablePrinter.formatCell(row[i], maxWidths.get(i), settings.getTextAlign())).append(" ").append(columnSeparator));

			outputBuilder.append('\n');
			outputBuilder.append(hr);
		}

		return outputBuilder.toString();
	}

	public static class SimpleTablePrinterSettings {
		private String textAlign = "left";
		private String rowSeparator = "+-";
		private char columnSeparator = '|';

		public SimpleTablePrinterSettings() {}

		public String getTextAlign() {
			return textAlign;
		}

		public SimpleTablePrinterSettings setTextAlign(String textAlign) {
			this.textAlign = textAlign;
			return this;
		}

		public String getRowSeparator() {
			return this.rowSeparator;
		}

		public SimpleTablePrinterSettings setRowSeparator(String separator) {
			if (separator.length() != 2) throw new IllegalArgumentException("Row separator must be exactly 2 characters long");

			this.rowSeparator = separator;
			return this;
		}

		public char getColumnSeparator() {
			return this.columnSeparator;
		}

		public SimpleTablePrinterSettings setColumnSeparator(char separator) {
			if (separator == '\0') throw new IllegalArgumentException("Column separator cannot be empty or null character");

			this.columnSeparator = separator;
			return this;
		}
	}

	public static class SettingAdjuster {
		private SimpleTablePrinter printer;

		public SettingAdjuster(SimpleTablePrinter printer) {
			this.printer = printer;
		}

		public SettingAdjuster setCaption(String caption) {
			this.printer.setCaption(caption);
			return this;
		}

		public SettingAdjuster setTextAlign(String textAlign) {
			this.printer.getSettings().setTextAlign(textAlign);
			return this;
		}

		public SettingAdjuster setRowSeparator(String separator) {
			this.printer.getSettings().setRowSeparator(separator);
			return this;
		}

		public SettingAdjuster setColumnSeparator(char separator) {
			this.printer.getSettings().setColumnSeparator(separator);
			return this;
		}

		public SimpleTablePrinter finish() {
			return this.printer;
		}
	}
}
