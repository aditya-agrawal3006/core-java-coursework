package onlineTest;

import java.io.Serializable;
import java.util.*;

public class Exam implements Serializable {
    private static final long serialVersionUID = 1L;
    private int examId;
    private String title;
    private Map<Integer, Question> questions;
    
    // constructor
    public Exam(int examId, String title) {
        this.examId = examId;
        this.title = title;
        questions = new HashMap<>();
    }
    
    // get exam id
    public int getExamId() {
        return examId;
    }
    
    // get title of exam
    public String getTitle() {
        return title;
    }
    
    // get map of questions ab exam
    public Map<Integer, Question> getQuestions() {
        return questions;
    }
    
    // add question to exam
    public void addQuestion(int questionNumber, Question q) {
        questions.put(questionNumber, q);
    }
    
    // compute total points ab exam
    public double getTotalPoints() {
        double total = 0.0;
        
        for (Question q : questions.values()) {
            total += q.getPoints();
        }
        
        return total;
    }
}
