package onlineTest;

import java.io.Serializable;
import java.util.*;

public class Submission implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int examId;
    // Map of question number to the answer provided.
    // The answer object may be a Boolean or a String[].
    private Map<Integer, Object> answers;
    
    public Submission(int examId) {
        this.examId = examId;
        answers = new HashMap<>();
    }
    
    public void addAnswer(int questionNumber, Object answer) {
        answers.put(questionNumber, answer);
    }
    
    public Object getAnswer(int questionNumber) {
        return answers.get(questionNumber);
    }
}
