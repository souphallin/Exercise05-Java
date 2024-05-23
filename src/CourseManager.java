import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CourseManager {
    private static final String FILE_PATH = "courses.txt";
    private List<Course> courses = new ArrayList<>();

    public static void main(String[] args) {
        CourseManager manager = new CourseManager();
        Scanner scanner = new Scanner(System.in);

        int choice = 0;
        do {
            System.out.println("Choose an option:");
            System.out.println("1. Add new Course");
            System.out.println("2. List Courses");
            System.out.println("3. Find Course By ID");
            System.out.println("4. Find Course By Title");
            System.out.println("5. Remove Course By ID");
            System.out.println("6. Exit");
            System.out.println("==========***==========");
            System.out.print("Input Choose : ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        manager.addNewCourse();
                        break;
                    case 2:
                        manager.listCourses();
                        break;
                    case 3:
                        manager.findCourseById();
                        break;
                    case 4:
                        manager.findCourseByTitle();
                        break;
                    case 5:
                        manager.removeCourseById();
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        } while (choice!= 6);
    }

    private void addNewCourse() {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter course ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter course title: ");
        String title = scanner.nextLine();
        System.out.print("Enter instructor(s), separated by comma: ");
        String instructorsInput = scanner.nextLine();
        List<String> instructors = Arrays.asList(instructorsInput.split(","));
        System.out.print("Enter course requirement: ");
        String requirement = scanner.nextLine();
        System.out.print("Enter start date (format: yyyy-MM-dd): ");
        String startDate = scanner.nextLine();

        Course newCourse = new Course(id, title, instructors, requirement, startDate);

        this.courses.add(newCourse);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH, true))) {
            oos.writeObject(newCourse);
            System.out.println("Course added successfully!");
        } catch (IOException e) {
            System.out.println("Failed to add course. Please try again.");
            e.printStackTrace();
        }
    }

    private void listCourses(){

        Table table=new Table(5, BorderStyle.UNICODE_BOX);

        for (int i=0;i<5;i++){
            table.setColumnWidth(i,20,20);
        }
        table.addCell("ID",new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("Tittle",new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("Instructor",new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("Requirement",new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("Start Date",new CellStyle(CellStyle.HorizontalAlign.CENTER));

        for (Course course:getCourses()){
            table.addCell(course.getId(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell(course.getTitle(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell(String.valueOf(course.getInstructors()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell(course.getRequirement(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell(course.getStartDate(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        }
        System.out.println(table.render());

    }

    private void findCourseById() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter course ID to search: ");
        String courseIdToSearch = scanner.nextLine();

        List<Course> foundCourses = courses.stream()
                .filter(course -> course.getId().equals(courseIdToSearch))
                .collect(Collectors.toList());

        if (!foundCourses.isEmpty()) {
            System.out.println("Found " + foundCourses.size() + " courses matching ID '" + courseIdToSearch + "':");

            Table table=new Table(5, BorderStyle.UNICODE_BOX);

            for (int i=0;i<5;i++){
                table.setColumnWidth(i,20,20);
            }
            table.addCell("ID",new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Tittle",new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Instructor",new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Requirement",new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Start Date",new CellStyle(CellStyle.HorizontalAlign.CENTER));

            for (Course course : foundCourses) {
                table.addCell(course.getId(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(course.getTitle(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(String.valueOf(course.getInstructors()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(course.getRequirement(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(course.getStartDate(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            }
            System.out.println(table.render());
        } else {
            System.out.println("No courses found with ID '" + courseIdToSearch + "'.");
        }
    }


    private void findCourseByTitle() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter course title to search: ");
        String courseTitleToSearch = scanner.nextLine();

        List<Course> foundCourses = courses.stream()
                .filter(course -> course.getTitle().equalsIgnoreCase(courseTitleToSearch))
                .toList();

        if (!foundCourses.isEmpty()) {
            System.out.println("Found courses matching title '" + courseTitleToSearch + "':");

            Table table=new Table(5, BorderStyle.UNICODE_BOX);

            for (int i=0;i<5;i++){
                table.setColumnWidth(i,20,20);
            }
            table.addCell("ID",new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Tittle",new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Instructor",new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Requirement",new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Start Date",new CellStyle(CellStyle.HorizontalAlign.CENTER));

            for (Course course : foundCourses) {
                table.addCell(course.getId(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(course.getTitle(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(String.valueOf(course.getInstructors()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(course.getRequirement(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(course.getStartDate(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            }
            System.out.println(table.render());
        } else {
            System.out.println("No courses found with title '" + courseTitleToSearch + "'.");
        }
    }

    private void removeCourseById() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter course ID to remove: ");
        String courseIdToRemove = scanner.nextLine();

        courses.removeIf(course -> course.getId().equals(courseIdToRemove));

        System.out.println("Removed course with ID '" + courseIdToRemove + "'. Refreshing course list...");
        listCourses();
    }

    public CourseManager() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            courses = (List<Course>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading courses from file.");
            courses = new ArrayList<>();
        }
    }

}

@Getter
class Course implements Serializable {

    private final String id;
    private final String title;
    private final List<String> instructors;
    private final String requirement;
    private final String startDate;

    public Course(String id, String title, List<String> instructors, String requirement, String startDate) {
        this.id = id;
        this.title = title;
        this.instructors = instructors;
        this.requirement = requirement;
        this.startDate = startDate;
    }
}
