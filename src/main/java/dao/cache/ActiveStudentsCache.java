package dao.cache;

import constants.ServerConsts;
import student.Student;
import student.StudentLineNumber;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActiveStudentsCache {

    private static class InstanceHolder {
        private static final ActiveStudentsCache instance = new ActiveStudentsCache();
    }

    public static ActiveStudentsCache getInstance() {
        return InstanceHolder.instance;
    }

    private final Map<String, StudentLineNumber> studentsCache = new HashMap<>();
    private final List<StudentLineNumber> lruCache = new ArrayList();

    public ActiveStudentsCache() {
    }

    public String addStudent(Student student){
        StudentLineNumber studentLineNumber = new StudentLineNumber();
        studentLineNumber.setStudent(student);
        studentLineNumber.setLineNumber(studentsCache.size() + 1);
        return addStudent(studentLineNumber);
    }

    private String addStudent(StudentLineNumber studentLineNumber){
        String addStudentMessage = MessageFormat.format("The amount of students exceeded {0}.", ServerConsts.MAX_SIZE);
        if (studentsCache.size() < ServerConsts.MAX_SIZE) {
            String studentId = studentLineNumber.getId();
            studentsCache.put(studentId, studentLineNumber);
            addStudentMessage = MessageFormat.format("student.Student with id {0} was successfully added.", studentId);
        }

        return addStudentMessage;
    }

    public boolean isSizeBelowMax(){
        return studentsCache.size() < ServerConsts.MAX_SIZE;
    }

    public boolean isStudentExists(String studentId){
        return studentsCache.containsKey(studentId);
    }

    public int getLineNumberOfStudentInFile(String studentId){
        return studentsCache.get(studentId).getLineNumber();
    }

    public Student getStudent(String studentId){
        StudentLineNumber studentLineNumber = studentsCache.get(studentId);
        if (studentLineNumber != null){
            return studentLineNumber.getStudent();
        }
        return new Student();
    }

    public void removeStudent(String studentId){
        int lineNumberOfRemovedStudent = this.getLineNumberOfStudentInFile(studentId);
        studentsCache.remove(studentId);
        for (Map.Entry<String, StudentLineNumber> entry : studentsCache.entrySet()){
            StudentLineNumber currentStudentLineNumber = entry.getValue();
            int lineNumber = currentStudentLineNumber.getLineNumber();
            if (lineNumber > lineNumberOfRemovedStudent){
                currentStudentLineNumber.setLineNumber(lineNumber - 1);
                studentsCache.put(currentStudentLineNumber.getId(), currentStudentLineNumber);
            }
        }
    }


}
