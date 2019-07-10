import com.fasterxml.jackson.databind.ObjectMapper;
import constants.ServerConsts;
import dao.cache.ActiveStudentsCache;
import student.Student;

import java.io.*;
import java.util.Collections;


public class InitServer {

    ObjectMapper mapper = new ObjectMapper();

    public void initServer() throws IOException{
        File studentsFile = new File(ServerConsts.STUDENTS_FILE_NAME);
        if (!studentsFile.exists()){
            studentsFile.createNewFile();
        } else {
            initCache(studentsFile);
        }
    }

    private void initCache(File studentsFile) throws IOException{
        ActiveStudentsCache activeStudentsCache = ActiveStudentsCache.getInstance();
        try(BufferedReader reader = new BufferedReader(new FileReader(ServerConsts.STUDENTS_FILE_NAME))) {
            String line;
            while((line = reader.readLine()) != null) {
                if (line != ""){
                    Student student = mapper.readValue(line, Student.class);
                    activeStudentsCache.addStudent(student);
                }
            }
        }
    }

}
