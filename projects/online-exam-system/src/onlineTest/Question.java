package onlineTest;

import java.io.Serializable;

public abstract class Question implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected int qNum;
    protected String text;
    protected double points;
    
    public Question(int questionNumber, String text, double points) {
        this.qNum = questionNumber;
        this.text = text;
        this.points = points;
    }
    
    public int getQuestionNumber() {
        return qNum;
    }
    
    public String getText() {
        return text;
    }
    
    public double getPoints() {
        return points;
    }
    
    /**
     * Grades the provided answer and returns the earned points.
     * The type of answer is expected to match the question type.
     */
    public abstract double grade(Object answer);
    
    /**
     * Returns a string representation of the correct answer in the specified format.
     */
    public abstract String getCorrectAnswer();
}
