import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFileChooser;

class Student {
    private String lastName;
    private String firstName;
    private String studentId;
    private List<Integer> marks;
    private int totalMarks;


    public Student(String lastName, String firstName, String studentId, List<Integer> marks) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.studentId = studentId;
        this.marks = marks;
    }
 public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getStudentId() {
        return studentId;
    }

    public List<Integer> getMarks() {
        return marks;
    }
    @Override
    public String toString() {
        return lastName + ", " + firstName + ", " + studentId + ", Marks: " + marks.get(0) + ", " +
                marks.get(1) + ", " + marks.get(2);
    }
}

public class MainProgram {
    private static List<Student> studentInfo = new ArrayList<>();

    public static void displayMainMenu() {
        System.out.println("Welcome to the Main Menu:");
        System.out.println("1. Read student information from a CSV file");
        System.out.println("2. Calculate total marks of students");
        System.out.println("3. Print students with total marks below a threshold");
        System.out.println("4. Print top 5 students with the highest total marks");
        System.out.println("5. Print top 5 students with the lowest total marks");
        System.out.println("6. Exit");
    }

public static List<Student> readStudentsInfo(String filePath) {
    List<Student> students = new ArrayList<>();

    try (Scanner scanner = new Scanner(new File(filePath))) {
        // Skip the header row
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            // Split using tabs or commas
            String[] parts = line.split("[\t,]+");

            // Check if the line has the expected number of elements
            if (parts.length < 6) {
                System.err.println("Skipping line due to insufficient elements: " + line);
                continue;
            }

            String lastName = parts[0].trim();
            String firstName = parts[1].trim();
            String studentId = parts[2].trim();

            List<Integer> marks = new ArrayList<>();
            for (int i = 3; i < 6; i++) {
                marks.add((int) Math.round(Double.parseDouble(parts[i].trim())));
            }

            Student student = new Student(lastName, firstName, studentId, marks);
            students.add(student);

            // Print student information in the specified format
            System.out.println(student);
        }
    } catch (FileNotFoundException e) {
        System.err.println("File not found: " + filePath);
    }

    return students;
}





    public static void calculateTotalMarks() {
        System.out.println("Calculating total marks for students...");

        for (Student student : studentInfo) {
            int totalMarks = student.getMarks().stream().mapToInt(Integer::intValue).sum();
            System.out.println("Name: " + student.getLastName() + ", " + student.getFirstName() +
                    ", Student ID: " + student.getStudentId() +
                    ", Total Marks: " + totalMarks +
                    ", Marks: " + student.getMarks());
        }
    }
public static void printStudentsBelowThreshold(int threshold) {
    System.out.println("Students with Total Marks Below Threshold (" + threshold + "):");
    for (Student student : studentInfo) {
        int totalMarks = student.getMarks().stream().mapToInt(Integer::intValue).sum();
        if (totalMarks < threshold) {
            System.out.println("Name: " + student.getLastName() + ", " + student.getFirstName() +
                    ", Student ID: " + student.getStudentId() +
                    ", Total Marks: " + totalMarks +
                    ", Marks: " + student.getMarks());
        }
    }
}

public static void printTopStudents(int count) {
    System.out.println("Top " + count + " Students with the highest total marks:");
    studentInfo.stream()
            .sorted((s1, s2) -> Integer.compare(s2.getMarks().stream().mapToInt(Integer::intValue).sum(),
                    s1.getMarks().stream().mapToInt(Integer::intValue).sum()))
            .limit(count)
            .forEach(student -> {
                int totalMarks = student.getMarks().stream().mapToInt(Integer::intValue).sum();
                System.out.println("Name: " + student.getLastName() + ", " + student.getFirstName() +
                        ", Student ID: " + student.getStudentId() +
                        ", Total Marks: " + totalMarks +
                        ", Marks: " + student.getMarks());
            });
}

public static void printBottomStudents(int count) {
    System.out.println("Bottom " + count + " Students with the lowest total marks:");
    studentInfo.stream()
            .sorted((s1, s2) -> Integer.compare(s1.getMarks().stream().mapToInt(Integer::intValue).sum(),
                    s2.getMarks().stream().mapToInt(Integer::intValue).sum()))
            .limit(count)
            .forEach(student -> {
                int totalMarks = student.getMarks().stream().mapToInt(Integer::intValue).sum();
                System.out.println("Name: " + student.getLastName() + ", " + student.getFirstName() +
                        ", Student ID: " + student.getStudentId() +
                        ", Total Marks: " + totalMarks +
                        ", Marks: " + student.getMarks());
            });
}

// ... (main method)

public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
        displayMainMenu();
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        switch (choice) {
            case 1:
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    studentInfo = readStudentsInfo(filePath);
                }
                break;
            case 2:
                calculateTotalMarks();
                System.out.println("Total marks calculated for students.");
                break;
            case 3:
                System.out.print("Enter threshold for total marks: ");
                int threshold = scanner.nextInt();
                scanner.nextLine();  // Consume the newline character
                printStudentsBelowThreshold(threshold);
                break;
            case 4:
                printTopStudents(5); // Print top 5 students
                break;
            case 5:
                printBottomStudents(5); // Print bottom 5 students
                break;
            case 6:
                System.out.println("Exiting the program.");
                scanner.close();
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }
}
}