import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRecordHandlingSystem {

    private final String studentRecordBook = "studentRecordBook.txt";

    //Method to write details of a student to a file
    public void appendStudentToFile(Student student) throws IOException, NumberFormatException {

        FileWriter fw = null;
        int studentId = student.getStudentId();
        String studentName = student.getStudentName();
        int age = student.getAge();

        try{
            fw = new FileWriter(studentRecordBook, true);
            String newRecord = "Student id:" + studentId + ", Student Name:" + studentName+", Age:" +age+"\n";
            fw.write(newRecord);

        }catch(IOException e){
            System.out.println("An error occurred while writing to the file");
        }
        finally{
            if(fw != null){
                fw.close();
            }
        }
    }

    //Method to read student records
    public void readFromFile() throws IOException{
        FileReader fr = null;
        File fileToRead = new File(studentRecordBook);
        ArrayList<Student> studentArrayFromExistingObjects = new ArrayList<>();
        try{
            fr = new FileReader(fileToRead);
            BufferedReader br = new BufferedReader(fr);
            String record;
            System.out.println("Printing the existing student records.");
            while(((record = br.readLine()) != null)){
                System.out.println(record);
            }
        }catch (IOException e){
            throw new IOException(":IOException");
        }finally{
            if(fr != null){
                fr.close();
            }
        }

    }

    //Method to remove a record from a file
    public void removeFromFile(){

    }

    //Get a list of existing records as an Array from the file.
    public ArrayList<Student> getExistingStudents() throws IOException{

        FileReader fr = null;
        File fileToRead = new File(studentRecordBook);
        ArrayList<Student> studentArrayFromExistingObjects = new ArrayList<>();
        try{
            fr = new FileReader(fileToRead);
            BufferedReader br = new BufferedReader(fr);
            String record;
            //Loop through the records
            while(((record = br.readLine()) != null)){
                //Split by the comma and the colan to extract student name, student id and the age.
                String[] det  = record.split(",");
                int studentId = Integer.parseInt(det[0].split(":")[1]);
                String studentName = det[1].split(":")[1];
                int age = Integer.parseInt(det[2].split(":")[1]);
//                System.out.println("reading" + studentName + studentId+age);
//                System.out.println(record);
                Student student = new Student(studentId, studentName, age);
                studentArrayFromExistingObjects.add(student);
            }

            return studentArrayFromExistingObjects;
        }catch (IOException e){
            throw new IOException();
        }catch (NumberFormatException e){
            throw new NumberFormatException("Number format exception");
        }finally {
            if(fr != null){
                fr.close();
            }
        }
    }

    //Method to write the entire array list to the file
    public void writeStudentsToFile(List<Student> studentList) throws IOException{

        FileWriter fw = null;

        try {
            fw = new FileWriter(studentRecordBook);
            for (Student student : studentList) {
                int studentId = student.getStudentId();
                String studentName = student.getStudentName();
                int age = student.getAge();
                String recordToWrite = "Student id:" + studentId + ", Student Name:" + studentName+", Age:" +age+"\n";
                fw.write(recordToWrite);
            }
        }catch (IOException e){
                throw new IOException(e);
        }finally{
            if(fw != null){
                fw.close();
            }
        }
    }

}
