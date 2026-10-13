package onlineTest;

import java.util.*;

public class FillInTheBlanksQuestion extends Question {
    private static final long serialVersionUID = 1L;
    private List<String> correctAnswers;
    
    // Constructor
    public FillInTheBlanksQuestion(int questionNumber, String text, double points, String[] answer) {
        super(questionNumber, text, points);
        correctAnswers = new ArrayList<>();
        
        for (String s : answer) {
            correctAnswers.add(s.trim());
        }
    }
    
    // grade answer for fill in blanks question
    public double grade(Object answer) {
        if (answer == null || !(answer instanceof String[])) {
            return 0;
        }
        String[] studentAnswers = (String[]) answer;
     
        double perBlank = points / correctAnswers.size();
        double score = 0.0;
        
        // copy of the correct answers to allow each to be matched only once
        List<String> remaining = new ArrayList<>(correctAnswers);
        for (String studentAns : studentAnswers) {
            String trimmedStudentAns = studentAns.trim();
            Iterator<String> it = remaining.iterator();
            
            while (it.hasNext()) {
                String corr = it.next();
                
                if (trimmedStudentAns.equalsIgnoreCase(corr)) {
                    score += perBlank;
                    it.remove();
                    break;
                }
            }
        }
        
        return Math.min(score, points);
    }
    
    // get correct answer as string in sorted order
    public String getCorrectAnswer() {
        List<String> sortedAnswers = new ArrayList<>(correctAnswers);
        Collections.sort(sortedAnswers, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        boolean first = true;
        
        for (String s : sortedAnswers) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(s);
            first = false;
        }
        
        sb.append("]");
        
        return sb.toString();
    }
}
