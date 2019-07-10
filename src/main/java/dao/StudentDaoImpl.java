package dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import constants.ServerConsts;
import dao.cache.ActiveStudentsCache;
import dao.cache.cacheManeger;
import student.Student;

import java.io.*;
import java.text.MessageFormat;
import java.util.HashMap;

public class StudentDaoImpl implements StudentDao{

    private static class InstanceHolder {
        private static final StudentDao instance = new StudentDaoImpl();
    }

    public static StudentDao getInstance() {
        return InstanceHolder.instance;
    }

    private ActiveStudentsCache activeStudentsCache = ActiveStudentsCache.getInstance();
    private cacheManeger ch = new cacheManeger();
    private StudentDaoImpl() {
    }

    private Object lock = new Object();

    public String saveStudent(Student student) throws IOException{
        String saveStudentMessage;
        synchronized (lock) {
            saveStudentMessage = MessageFormat.format("The amount of students exceeded {0} or Id is wrong.",
                    ServerConsts.MAX_SIZE);
            if (activeStudentsCache.isSizeBelowMax() && student.getId() != null) {
                saveStudentInDataBaseAndCache(student);
                saveStudentMessage = MessageFormat.format("Student with id {0} was successfully added.",
                        student.getId());
            }
        }
        return saveStudentMessage;
    }

    public String removeStudent(String studentId) throws IOException{
        String returnedMessage = "";
        synchronized (lock) {
            returnedMessage = MessageFormat.format("User with id {0} doesn't exist!", studentId);
            if (activeStudentsCache.isStudentExists(studentId)) {
                removeExistingStudent(studentId);
                returnedMessage = MessageFormat.format("Successfully removed user with id {0}!", studentId);
            }
        }
        return returnedMessage;
    }

    public Student getStudent(String studentId){
        return activeStudentsCache.getStudent(studentId);
    }

    private void saveStudentInDataBaseAndCache(Student student) throws IOException{
        String studentId = student.getId();
        if (activeStudentsCache.isStudentExists(studentId)) {
            removeExistingStudent(studentId);
        }
        saveNewStudent(student);
    }

    private void saveNewStudent(Student student) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        String studentJson = mapper.writeValueAsString(student);
        try (FileWriter fw = new FileWriter(ServerConsts.STUDENTS_FILE_NAME, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(studentJson);
        } catch (IOException e) {
            throw new IOException("An exception occurred while trying to save student to file");
        }
        activeStudentsCache.addStudent(student);
    }

    private void removeExistingStudent(String studentId) throws IOException{
        int lineNumberOfStudentInFile = activeStudentsCache.getLineNumberOfStudentInFile(studentId);
        removeStudentFromFile(lineNumberOfStudentInFile);
        removeStudentFromCache(studentId);
    }

    private void removeStudentFromFile(int removeContentInLine) throws IOException{

        try(BufferedReader reader = new BufferedReader(new FileReader(ServerConsts.STUDENTS_FILE_NAME));
            FileWriter fw = new FileWriter(ServerConsts.STUDENTS_FILE_NAME_TEMPORARY, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {

            String currentLine;
            for (int currentLineIndex = 0; currentLineIndex < removeContentInLine - 1; currentLineIndex++){
                String lineContent = reader.readLine();
                out.println(lineContent);
            }
            reader.readLine(); // skip this line
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals("")) continue;
                out.println(currentLine);
            }
        } finally {
            File tempFile = new File(ServerConsts.STUDENTS_FILE_NAME_TEMPORARY);
            if (tempFile.exists()) {
                File originalFile = new File(ServerConsts.STUDENTS_FILE_NAME);
                tempFile.renameTo(originalFile);
            }
        }
    }

    private void removeStudentFromCache(String studentId){
        activeStudentsCache.removeStudent(studentId);
    }

}
