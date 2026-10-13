package onlineTest;

public class TFQuestion extends Question {
    private static final long serialVersionUID = 1L;
    private boolean correctAnswer;
    
    // constructor
    public TFQuestion(int questionNumber, String text, double points, boolean correctAnswer) {
        super(questionNumber, text, points);
        this.correctAnswer = correctAnswer;
    }
    
    // grade the answer
    public double grade(Object answer) {
        if (answer == null || !(answer instanceof Boolean)) {
            return 0;
        }
        
        boolean studentAns = (Boolean) answer;
        return (studentAns == correctAnswer) ? points : 0;
    }
    
    // get correct answer as string
    public String getCorrectAnswer() {
        return correctAnswer ? "True" : "False";
    }
}
