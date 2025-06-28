public class Student {
	private int id;
	private String name;

	public Student (int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Student (String name) {
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
}
