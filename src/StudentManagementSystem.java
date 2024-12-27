import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class StudentManagementSystem {
    private List<Student> studentList;
    private Map<Integer, Student> idMapToStudent;
    private Set<Integer> idSet;
    private StudentRecordHandlingSystem studentRecordHandlingSystem;

    public StudentManagementSystem(){
        this.studentList = new ArrayList<>();
        this.idMapToStudent = new HashMap<>();
        this.studentRecordHandlingSystem = new StudentRecordHandlingSystem();
        this.idSet = new HashSet<>();
        try {
            populate();
        } catch (IOException e) {
            System.out.println("Error populating student records: " + e.getMessage());
        }
    }

    public void populate() throws IOException{
        studentRecordHandlingSystem.readFromFile("studentRecordBook.txt");

    }

    //update Student details
    public void updateStudent(){

    }

    //create a new Student
    public Student createStudent(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student name:");
        String studentName = scanner.nextLine();
        System.out.println("Enter student Id:");
        int studentId = scanner.nextInt();
        System.out.println("Enter student age:");
        int age = scanner.nextInt();
        return new Student(studentId, studentName, age);
    }

    //add student to the list
    private void addStudentToList(Student student){
        this.studentList.add(student);
    }

    private void addIdToTheMap(int id, Student student){
        this.idMapToStudent.put(id, student);
    }

    private void addIdToTheSet(int id){
        this.idSet.add(id);
    }

    public synchronized void addNewStudentToTheSystem(Student student){
        int studentId = student.getStudentId();
        addIdToTheSet(studentId);
        addStudentToList(student);
        addIdToTheMap(studentId, student);
        try {
            studentRecordHandlingSystem.writeToFile(student);
        }catch(IOException e){
            System.out.println("hi");
        }

    }

    //remove existing student
    public void removeStudent(){

    }

    //Getter and Setter for the student list
    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public void displayList(){
        try {
            studentRecordHandlingSystem.readFromFile("studentRecordBook.txt");
        }catch(IOException e){
            System.out.println(e);
        }

    }

}
