package student;

public class StudentLineNumber {
    private int lineNumber;
    private Student student;

    public StudentLineNumber() {
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getId(){
        return this.student.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentLineNumber that = (StudentLineNumber) o;

        if (lineNumber != that.lineNumber) return false;
        return !(student != null ? !student.equals(that.student) : that.student != null);

    }

    @Override
    public int hashCode() {
        int result = lineNumber;
        result = 31 * result + (student != null ? student.hashCode() : 0);
        return result;
    }
}
