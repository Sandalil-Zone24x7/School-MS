import java.io.IOException;
import java.util.*;

public class StudentManagementSystem  {

    private List<Student> studentList;
    private Map<Integer, Student> idMapToStudent;
    private Set<Integer> idSet;
    private final StudentRecordHandlingSystem studentRecordHandlingSystem;

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

    //classes to implement runnable interfaces for multiple threads
    class StudentUpdateSystem implements Runnable{
        public void run(){
            try{
                Student newStudent = createStudent();
                addNewStudentToTheSystem(newStudent);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    class StudentInsertionSystem implements Runnable{
        public void run(){
            try{
                Student newStudent = createStudent();
                addNewStudentToTheSystem(newStudent);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    class StudentRemovalSystem implements Runnable{
        public void run(){
            try{
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    class StudentDisplaySystem implements Runnable{
        public void run(){
            try{
                displayList();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    //populate the StudentList by reading the file.
    public void populate() throws IOException{
        ArrayList<Student> studentList = studentRecordHandlingSystem.getExistingStudents();
        setStudentList(studentList);

    }

    //create a new Student.
    private Student createStudent(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student name:");
        String studentName = scanner.nextLine();
        System.out.println("Enter student Id:");
        int studentId = scanner.nextInt();
        System.out.println("Enter student age:");
        int age = scanner.nextInt();
        return new Student(studentId, studentName, age);
    }

    //add student to the list.
    private void addStudentToList(Student student){
        this.studentList.add(student);
    }

    //remove student from the list.
    private void removeStudentFromTheList(Student student){
        this.studentList.remove(student);
    }

    //add id to the map.
    private void addIdAndStudentToTheMap(int id, Student student){
        this.idMapToStudent.put(id, student);
    }

    //add id to the set.
    private void addIdToTheSet(int id){
        this.idSet.add(id);
    }


    //Method to add a new student to the system.
    public synchronized void addNewStudentToTheSystem(Student student){
        int studentId = student.getStudentId();
        //update the idMapToStudent, studentList and idSet
        addIdToTheSet(studentId);
        addStudentToList(student);
        addIdAndStudentToTheMap(studentId, student);
        //Write the new student details to the file
        try {
            studentRecordHandlingSystem.appendToFile(student);
        }catch(IOException e){
            System.out.println("hi");
        }catch(NumberFormatException e){
            System.out.println("Error occurred while reading the file.");
        }

    }
    //Get the student from the id using map.
    private Student getStudentFromId(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student Id:");
        int studentId = scanner.nextInt();
        Student student = idMapToStudent.get(studentId);
        return student;

    }

    //update Student details.
    public synchronized void updateStudentDetails(Student studentToUpdate){
        int studentId = studentToUpdate.getStudentId();
        String studentName = studentToUpdate.getStudentName();

        //remove the student from the list
        removeStudentFromTheList(studentToUpdate);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student updated age:");
        int newAge = scanner.nextInt();
        Student updatedStudent = new Student(studentId, studentName, newAge);

        //update the map and the list with new details
        idMapToStudent.put(studentId, updatedStudent);
        addStudentToList(updatedStudent);

        try{
            studentRecordHandlingSystem.writeTheFile(studentList);
        }catch(IOException e){
            System.out.println("Error populating student records: " + e.getMessage());
        }


    }

    //remove existing student.
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
        studentList.forEach(student -> System.out.println("Student id:" + student.getStudentId() + ", Student Name:" + student.getStudentName()+", Age:" + student.getAge()));

    }

}
