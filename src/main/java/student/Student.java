package student;

import java.util.Map;

public class Student {

    public static final String ID_MEMBER = "id";
    private static final String FIRSTNAME_MEMBER = "firstName";
    private static final String LASTNAME_MEMBER = "lastName";
    private static final String GENDER_MEMBER = "gender";
    private static final String AVERAGE_MEMBER = "average";


    private String id;
    private String firstName = "";
    private String lastName = "";
    private String gender = "";
    double average;

    public Student() {
    }

    public Student(Map<String,String> parms){
        String averageStr = parms.get(Student.AVERAGE_MEMBER);
        double average = parseNumber(averageStr);
        this.setId(parms.get(Student.ID_MEMBER));
        this.setFirstName(parms.get(Student.FIRSTNAME_MEMBER));
        this.setLastName(parms.get(Student.LASTNAME_MEMBER));
        this.setAverage(average);
        this.setGender(Student.GENDER_MEMBER);
    }

    private Double parseNumber(String number){
        Double parsedNumber = 0.0;
        try {
            parsedNumber = Double.parseDouble(number);
        } catch (Exception e) {
            // Then use the default value.
        }
        return parsedNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", average=" + average +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (Double.compare(student.average, average) != 0) return false;
        if (id != null ? !id.equals(student.id) : student.id != null) return false;
        if (firstName != null ? !firstName.equals(student.firstName) : student.firstName != null) return false;
        if (lastName != null ? !lastName.equals(student.lastName) : student.lastName != null) return false;
        return !(gender != null ? !gender.equals(student.gender) : student.gender != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        temp = Double.doubleToLongBits(average);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
