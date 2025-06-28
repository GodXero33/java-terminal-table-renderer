import java.util.List;

import tableprinter.TablePrinter;

public class Main {
	public static void main(String[] args) {
		final TablePrinter tblPrinter = new TablePrinter();
		final List<Student> students = List.of(
			new Student(1, "Sathish Shan"),
			new Student(2, "Alice Johnson"),
			new Student(3, "Bob Smith"),
			new Student(4, "Charlie Brown"),
			new Student(5, "Diana Prince"),
			new Student(6, "Ethan Hunt"),
			new Student(7, "Fiona Gallagher"),
			new Student(8, "George Michael"),
			new Student(9, "Hannah Baker"),
			new Student(10, "Ivan Drago")
		);

		System.out.println(tblPrinter.getTable(Student.class, students));
	}
}
