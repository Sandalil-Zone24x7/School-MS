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
        public synchronized void run(){
            try{
                System.out.println("Data required to update student details.");
                Student studentToUpdate = getStudentFromId();
                updateStudentDetails(studentToUpdate);
            }catch(StudentNotFoundException e){
                System.out.println("An error occurred:" + e.getMessage());
            }catch(Exception e){
                System.out.println("An error occurred");
            }
        }
    }

    class StudentInsertionSystem implements Runnable{
        public void run(){
            try{
                Student newStudent = createStudent();
                addNewStudentToTheSystem(newStudent);
            }catch(InputMismatchException e){
                System.out.println("An error occurred:" + e.getMessage());
            }catch(DuplicateStudentException e){
                System.out.println("An error occurred:" + e.getMessage());
            }catch(Exception e){
                System.out.println("Error");
            }
        }
    }

    class StudentRemovalSystem implements Runnable{
        public synchronized void run(){
            try{
                System.out.println("Data required to removing student details.");
                Student student = getStudentFromId();
                removeStudent(student);
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
        studentList.forEach(student -> {
            addIdToTheSet(student.getStudentId());
            addIdAndStudentToTheMap(student.getStudentId(), student);

        });


    }

    //create a new Student.
    private synchronized Student createStudent() throws InputMismatchException, DuplicateStudentException{
        try {
            System.out.println("Registering a new student.");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter student name:");
            String studentName = scanner.nextLine();
            System.out.println("Enter student Id:");
            int studentId = scanner.nextInt();
            scanner.nextLine();
            if(idSet.contains(studentId)){
                throw new DuplicateStudentException("The index number already exists.");
            }
            System.out.println("Enter student age:");
            int age = scanner.nextInt();
            return new Student(studentId, studentName, age);
        }catch(InputMismatchException e){
            throw new InputMismatchException("Integer required.");
        }
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
    public void addNewStudentToTheSystem(Student student){
        int studentId = student.getStudentId();
        //update the idMapToStudent, studentList and idSet
        addIdToTheSet(studentId);
        addStudentToList(student);
        addIdAndStudentToTheMap(studentId, student);
        //Write the new student details to the file
        synchronized (student){
            try {
                studentRecordHandlingSystem.appendStudentToFile(student);
            }catch(IOException e){
                System.out.println("hi");
            }catch(NumberFormatException e){
                System.out.println("Error occurred while reading the file.");
            }
        }


    }

    //Get student id as input and return the student using the map
    private synchronized Student getStudentFromId() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student Id:");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        Student student = idMapToStudent.get(studentId);
        if(student != null){
            return student;
        }else{
            throw new StudentNotFoundException("invalid student id");
        }
    }

    //update Student details.
    public void updateStudentDetails(Student studentToUpdate){
        int studentId = studentToUpdate.getStudentId();
        String studentName = studentToUpdate.getStudentName();

        //remove the student from the list
        synchronized (studentList) {
            removeStudentFromTheList(studentToUpdate);
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter student updated age:");
            int newAge = scanner.nextInt();
            Student updatedStudent = new Student(studentId, studentName, newAge);

            //update the map and the list with new details
            idMapToStudent.put(studentId, updatedStudent);
            addStudentToList(updatedStudent);

            try {
                studentRecordHandlingSystem.writeStudentsToFile(studentList);
            } catch (IOException e) {
                System.out.println("Error updating student records: " + e.getMessage());
            }
        }
    }

    //remove existing student.
    public void removeStudent(Student student){
        int studentId = student.getStudentId();
        idSet.remove(studentId);
        idMapToStudent.remove(studentId);
        synchronized (studentList){
            try{
                studentList.remove(student);
                studentRecordHandlingSystem.writeStudentsToFile(studentList);
            }catch(IOException e){
                System.out.println("Error removing student: " + e.getMessage());

            }
        }





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
