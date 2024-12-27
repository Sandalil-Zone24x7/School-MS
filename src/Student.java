import java.time.LocalDate;

public class Student {

    private int studentId;
    private String studentName;
    private int age;

    public Student(int studentId, String studentName, int age){
        this.studentId = studentId;
        this.studentName = studentName;
        this.age = age;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
