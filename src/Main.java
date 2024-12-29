import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {

        StudentManagementSystem studentManagementSystem = new StudentManagementSystem();
        StudentManagementSystem.StudentInsertionSystem studentInsertionSystem = studentManagementSystem.new StudentInsertionSystem();
        StudentManagementSystem.StudentUpdateSystem studentUpdateSystem = studentManagementSystem.new StudentUpdateSystem();
        StudentManagementSystem.StudentDisplaySystem studentDisplaySystem = studentManagementSystem.new StudentDisplaySystem();
        StudentManagementSystem.StudentRemovalSystem studentRemovalSystem = studentManagementSystem.new StudentRemovalSystem();


        Thread insertionThread1 = new Thread(studentInsertionSystem);
        Thread insertionThread2 = new Thread(studentInsertionSystem);

        Thread updateThread1 = new Thread(studentUpdateSystem);
        Thread updateThread2 = new Thread(studentUpdateSystem);

        Thread displayThread1 = new Thread(studentDisplaySystem);
        Thread displayThread2 = new Thread(studentDisplaySystem);

        insertionThread1.start();
//        insertionThread2.start();

//        updateThread1.start();
//        updateThread2.start();

//        displayThread1.start();
//        displayThread2.start();

//        Thread removalThread1 = new Thread(studentRemovalSystem);
//        Thread removalThread2 = new Thread(studentRemovalSystem);






    }
}