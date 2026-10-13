package onlineTest;

import java.io.Serializable;
import java.util.*;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private Map<Integer, Submission> submissions;
    
    // constructor
    public Student(String name) {
        this.name = name;
        submissions = new HashMap<>();
    }
    
    // get student name
    public String getName() {
        return name;
    }
    
    // return existing submission or create new one
    public Submission getSubmission(int examId) {
        Submission sub = submissions.get(examId);
        if (sub == null) {
            sub = new Submission(examId);
            submissions.put(examId, sub);
        }
        return sub;
    }
    
    // return submission if exists
    public Submission getSubmissionIfExists(int examId) {
        return submissions.get(examId);
    }
}
