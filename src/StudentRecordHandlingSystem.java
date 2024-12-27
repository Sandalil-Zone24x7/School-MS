import java.io.*;

public class StudentRecordHandlingSystem {

    //Method to write details of a student to a file
    public void writeToFile(Student student) throws IOException {

        String  fileToWrite = "studentRecordBook.txt";
        FileWriter fw = null;
        int studentId = student.getStudentId();
        String studentName = student.getStudentName();
        int age = student.getAge();

        try{
            fw = new FileWriter(fileToWrite, true);
            String newRecord = "\nStudent id:" + studentId + ", Student Name:" + studentName+", Age:" +age;

            fw.write(newRecord);

        }catch(IOException e){
            System.out.println("An error occurred while writing to the file");
        }


        fw.close();

    }

    //Method to read student records
    public void readFromFile(String fileName) throws IOException{

        FileReader fr = null;
        File fileToRead = new File(fileName);

        try{
            fr = new FileReader(fileToRead);
            BufferedReader br = new BufferedReader(fr);
            String record;
            while(((record = br.readLine()) != null)){
                String[] det  = record.split(",");
                String[] det1 = det[0].split(":");
                System.out.println(det[0]);
                System.out.println(det1[-1]);
                for(String i: det1){
                    System.out.println(i);
                }
//                int studentId = Integer.parseInt(det[0].split(":")[1]);
//                String studentName = det[1].split(":")[1];
//                int age = Integer.parseInt(det[2].split(":")[1]);


                System.out.println("reading");
                System.out.println(record);
            }
        }catch (IOException e){
            throw new IOException();
        }
        fr.close();
    }

    //Method to remove a record from a file
    public void removeFromFile(){

    }

}
