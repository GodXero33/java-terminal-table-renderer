# SimpleTablePrinter

**Java utility to print clean, customizable ASCII tables from object lists using reflection.**  
Designed for beginner devs and students tired of writing `System.out.println()` manually for every assignment.  
Supports column alignment, captions, and fancy separators with just a few lines.

---

## Features

- Auto-generates table from any POJO with getters
- Custom column headers
- Alignment: `left`, `right`, `center`
- Table caption
- Custom row and column separators
- Fluent API with `.adjust().setX().finish()`

---

## How to Use

### 1. Define Your Class

```java
public class Student {
    private int id;
    private String name;
    private int age;
    private String email;
    private String phone;
    private String address;
    private String major;
    private double gpa;
    private boolean isFullTime;
    private String enrollmentDate;

    // Constructor + Getters only
    public Student(int id, String name, int age, String email, String phone, String address, String major, double gpa, boolean isFullTime, String enrollmentDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.major = major;
        this.gpa = gpa;
        this.isFullTime = isFullTime;
        this.enrollmentDate = enrollmentDate;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getMajor() { return major; }
    public double getGpa() { return gpa; }
    public boolean getIsFullTime() { return isFullTime; }
    public String getEnrollmentDate() { return enrollmentDate; }
}
````

### 2. Create Table in `main()`

```java
import java.util.List;

import tableprinter.SimpleTablePrinter;

public class Main {
    public static void main(String[] args) {
        final List<Student> students = List.of(
            new Student(1, "Sathish Shan", 22, "sathish@example.com", "0771234567", "Colombo, SL", "Computer Science", 3.9, true, "2021-09-01"),
            new Student(2, "Alice Johnson", 21, "alicej@example.com", "0712345678", "New York, USA", "Physics", 3.6, true, "2022-01-10"),
            new Student(3, "Bob Smith", 24, "bobsmith@example.com", "0756789012", "London, UK", "Engineering", 2.8, false, "2020-06-15"),
            new Student(4, "Charlie Brown", 23, "charlieb@example.com", "0789876543", "Melbourne, AUS", "Art", 3.2, true, "2021-02-20"),
            new Student(5, "Diana Prince", 22, "diana.p@example.com", "0701029384", "Paris, FR", "History", 3.7, false, "2023-03-12"),
            new Student(6, "Ethan Hunt", 25, "ethanhunt@example.com", "0793456789", "Berlin, DE", "Law", 3.1, true, "2019-08-22"),
            new Student(7, "Fiona Gallagher", 20, "fionag@example.com", "0764567890", "Chicago, USA", "Biology", 3.5, true, "2022-09-01"),
            new Student(8, "George Michael", 26, "georgem@example.com", "0726789012", "Rome, IT", "Music", 2.9, false, "2018-01-18"),
            new Student(9, "Hannah Baker", 21, "hannahb@example.com", "0747890123", "Tokyo, JP", "Psychology", 3.8, true, "2021-11-11"),
            new Student(10, "Ivan Drago", 28, "ivand@example.com", "0738901234", "Moscow, RU", "Sports Science", 2.5, false, "2017-05-30")
        );

        final SimpleTablePrinter printer = new SimpleTablePrinter().adjust()
            .setTextAlign("center")
            .setRowSeparator("`-")
            .setColumnSeparator('|')
            .set
            .finish();

        System.out.println(printer.getTable(
            Student.class,
            students,
            List.of("ID", "Name", "Age", "Email", "Phone", "Address", "Major", "GPA", "Full Time", "Enr. Date")
        ));
    }
}
```

---

## Sample Output

```plaintext
`------------------------------------------------------------------------------------------------------------------------------------`
|                                                           Students Portal                                                          |
`----`-----------------`-----`-----------------------`------------`----------------`------------------`-----`-----------`------------`
| ID |       Name      | Age |         Email         |    Phone   |     Address    |       Major      | GPA | Full Time |  Enr. Date |
`----`-----------------`-----`-----------------------`------------`----------------`------------------`-----`-----------`------------`
|  1 |   Sathish Shan  |  22 |  sathish@example.com  | 0771234567 |   Colombo, SL  | Computer Science | 3.9 |    true   | 2021-09-01 |
`----`-----------------`-----`-----------------------`------------`----------------`------------------`-----`-----------`------------`
|  2 |  Alice Johnson  |  21 |   alicej@example.com  | 0712345678 |  New York, USA |      Physics     | 3.6 |    true   | 2022-01-10 |
`----`-----------------`-----`-----------------------`------------`----------------`------------------`-----`-----------`------------`
|  3 |    Bob Smith    |  24 |  bobsmith@example.com | 0756789012 |   London, UK   |    Engineering   | 2.8 |   false   | 2020-06-15 |
`----`-----------------`-----`-----------------------`------------`----------------`------------------`-----`-----------`------------`
|  4 |  Charlie Brown  |  23 |  charlieb@example.com | 0789876543 | Melbourne, AUS |        Art       | 3.2 |    true   | 2021-02-20 |
`----`-----------------`-----`-----------------------`------------`----------------`------------------`-----`-----------`------------`
|  5 |   Diana Prince  |  22 |  diana.p@example.com  | 0701029384 |    Paris, FR   |      History     | 3.7 |   false   | 2023-03-12 |
`----`-----------------`-----`-----------------------`------------`----------------`------------------`-----`-----------`------------`
|  6 |    Ethan Hunt   |  25 | ethanhunt@example.com | 0793456789 |   Berlin, DE   |        Law       | 3.1 |    true   | 2019-08-22 |
`----`-----------------`-----`-----------------------`------------`----------------`------------------`-----`-----------`------------`
|  7 | Fiona Gallagher |  20 |   fionag@example.com  | 0764567890 |  Chicago, USA  |      Biology     | 3.5 |    true   | 2022-09-01 |
`----`-----------------`-----`-----------------------`------------`----------------`------------------`-----`-----------`------------`
|  8 |  George Michael |  26 |  georgem@example.com  | 0726789012 |    Rome, IT    |       Music      | 2.9 |   false   | 2018-01-18 |
`----`-----------------`-----`-----------------------`------------`----------------`------------------`-----`-----------`------------`
|  9 |   Hannah Baker  |  21 |  hannahb@example.com  | 0747890123 |    Tokyo, JP   |    Psychology    | 3.8 |    true   | 2021-11-11 |
`----`-----------------`-----`-----------------------`------------`----------------`------------------`-----`-----------`------------`
| 10 |    Ivan Drago   |  28 |   ivand@example.com   | 0738901234 |   Moscow, RU   |  Sports Science  | 2.5 |   false   | 2017-05-30 |
`----`-----------------`-----`-----------------------`------------`----------------`------------------`-----`-----------`------------`
```

---

## Default Settings Explained

| Setting          | Default Value  | Description                                                                       |
|------------------|----------------|-----------------------------------------------------------------------------------|
| Alignment        | `"left"`       | Text in columns will align to the left by default.                                |
| Row Separator    | `"+-"`         | `+` is used at the junctions (corners), `-` is used to draw the horizontal lines. |
| Column Separator | `'|'`          | Used to separate columns vertically.                                              |
| Caption          | `null`         | If no caption is set, the table header appears without a title row.               |

You can customize all these using `.adjust()` method chain:

```java
SimpleTablePrinter printer = new SimpleTablePrinter()
    .adjust()
    .setTextAlign("center")
    .setRowSeparator("=-")
    .setColumnSeparator('|')
    .setCaption("My Cool Table")
    .finish();
```

---

## Important Note on Package

Make sure to **change the package** of `SimpleTablePrinter.java` to match your project’s structure if you're not using:

```java
package tableprinter;
```

If your project uses a different package name, update it at the top of the file like this:

```java
package your.custom.package.name.tableprinter;
```

Failing to do this will cause import errors in your code.

---

## Tips

* It only works with **getters**: fields must have `getX()` methods.
* Column headers can be passed manually (optional).
* Data will auto-resize columns based on content.
* If any value is `null`, it will be shown as `"null"`.

---

## Author

**GodXero**

---

## License

MIT License — Use it, break it, make it better.
