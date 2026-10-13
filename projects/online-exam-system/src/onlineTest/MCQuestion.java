package onlineTest;

import java.util.*;

public class MCQuestion extends Question {
    private static final long serialVersionUID = 1L;
    private SortedSet<String> correctAnswers;
    
    // constructor
    public MCQuestion(int questionNumber, String text, double points, String[] answer) {
        super(questionNumber, text, points);
        correctAnswers = new TreeSet<>();
        
        for (String s : answer) {
            correctAnswers.add(s.trim());
        }
    }
    
    // grade answer for mc question
    public double grade(Object answer) {
        if (answer == null || !(answer instanceof String[])) {
            return 0;
        }
        
        String[] studentAnswers = (String[]) answer;
        SortedSet<String> studentSet = new TreeSet<>();
        
        for (String s : studentAnswers) {
            studentSet.add(s.trim());
        }
        
        return studentSet.equals(correctAnswers) ? points : 0;
    }
    
    // get correct answer as string
    public String getCorrectAnswer() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        boolean first = true;
        
        for (String ans : correctAnswers) {
        	
            if (!first) {
                sb.append(", ");
            }
            
            sb.append(ans);
            first = false;
        }
        
        sb.append("]");
        
        return sb.toString();
    }
}
