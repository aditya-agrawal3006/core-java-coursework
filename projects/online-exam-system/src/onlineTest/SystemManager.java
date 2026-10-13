package onlineTest;

import java.io.*;
import java.util.*;

public class SystemManager implements Manager, Serializable {
    private static final long serialVersionUID = 1L;
    private Map<Integer, Exam> exams;
    private Map<String, Student> students;
    private String[] letterGrades;
    private double[] cutoffs;
    
    // constructor to initialize maps
    public SystemManager() {
        exams = new HashMap<>();
        students = new HashMap<>();
    }
    
    // add exam to the system
    public boolean addExam(int examId, String title) {
        if (exams.containsKey(examId)) {
            return false;
        }
        exams.put(examId, new Exam(examId, title));
        
        return true;
    }
    
    // add t/f question to exam
    public void addTrueFalseQuestion(int examId, int questionNumber, String text, double points, boolean answer) {
        Exam exam = getExistingExam(examId);
        TFQuestion q = new TFQuestion(questionNumber, text, points, answer);
        exam.addQuestion(questionNumber, q);
    }
    
    // add mc question to exam
    public void addMultipleChoiceQuestion(int examId, int questionNumber, String text, double points, String[] answer) {
        Exam exam = getExistingExam(examId);
        MCQuestion q = new MCQuestion(questionNumber, text, points, answer);
        exam.addQuestion(questionNumber, q);
    }
    
    // add fill in the blanks question to exam
    public void addFillInTheBlanksQuestion(int examId, int questionNumber, String text, double points, String[] answer) {
        Exam exam = getExistingExam(examId);
        FillInTheBlanksQuestion q = new FillInTheBlanksQuestion(questionNumber, text, points, answer);
        exam.addQuestion(questionNumber, q);
    }
    
    // add student to systm
    public boolean addStudent(String name) {
        if (students.containsKey(name)) {
            return false;
        }
        students.put(name, new Student(name));
        
        return true;
    }
    
    // record answer for t/f question for a student
    public void answerTrueFalseQuestion(String studentName, int examId, int questionNumber, boolean answer) {
        Student student = getExistingStudent(studentName);
        Submission sub = student.getSubmission(examId);
        sub.addAnswer(questionNumber, answer);
    }
    
    // record answer for mc question for a student
    public void answerMultipleChoiceQuestion(String studentName, int examId, int questionNumber, String[] answer) {
        Student student = getExistingStudent(studentName);
        Submission sub = student.getSubmission(examId);
        sub.addAnswer(questionNumber, answer);
    }
    
    // record answer for fill in the blanks question for a student
    public void answerFillInTheBlanksQuestion(String studentName, int examId, int questionNumber, String[] answer) {
        Student student = getExistingStudent(studentName);
        Submission sub = student.getSubmission(examId);
        sub.addAnswer(questionNumber, answer);
    }
    
 
    // compute exam score for a student
    public double getExamScore(String studentName, int examId) {
        Exam exam = getExistingExam(examId);
        Student student = getExistingStudent(studentName);
        Submission sub = student.getSubmissionIfExists(examId);
        
        if (sub == null) {
            return 0.0;
        }
        
        double score = 0.0;
        for (Map.Entry<Integer, Question> entry : exam.getQuestions().entrySet()) {
            int qnum = entry.getKey();
            Question q = entry.getValue();
            Object studentAns = sub.getAnswer(qnum);
            score += q.grade(studentAns);
        }
        
        return score;
    }
    
    // generate grading report for a student on an exam
    public String getGradingReport(String name, int examId) {
        Exam exam = getExistingExam(examId);
        Student student = getExistingStudent(name);
        Submission sub = student.getSubmissionIfExists(examId);
        StringBuilder report = new StringBuilder();
        double totalEarned = 0.0;
        double totalPossible = exam.getTotalPoints();
        

        SortedMap<Integer, Question> sortedQuestions = new TreeMap<>(exam.getQuestions());
        
        for (Map.Entry<Integer, Question> entry : sortedQuestions.entrySet()) {
            int qnum = entry.getKey();
            Question q = entry.getValue();
            Object studentAnswer = (sub == null ? null : sub.getAnswer(qnum));
            double earned = q.grade(studentAnswer);
            totalEarned += earned;
            report.append("Question #");
            report.append(qnum);
            report.append(" ");
            report.append(earned);
            report.append(" points out of ");
            report.append(q.getPoints());
            report.append("\n");
        }
        
        report.append("Final Score: ");
        report.append(totalEarned);
        report.append(" out of ");
        report.append(totalPossible);
        return report.toString();
    }
    
    // compute average exam score for an exam
    public double getAverageScore(int examId) {
        Exam exam = getExistingExam(examId);
        double total = 0.0;
        int count = 0;
        for (Student student : students.values()) {
            Submission sub = student.getSubmissionIfExists(examId);
            
            if (sub != null) {
                double score = 0.0;
                
                for (Map.Entry<Integer, Question> entry : exam.getQuestions().entrySet()) {
                    int qnum = entry.getKey();
                    Question q = entry.getValue();
                    Object studentAnswer = sub.getAnswer(qnum);
                    score += q.grade(studentAnswer);
                }
                
                total += score;
                count++;
            }
        }
        
        return count == 0 ? 0.0 : total / count;
    }
    
    // compute max exam score for an exam
    public double getMaxScore(int examId) {
        Exam exam = getExistingExam(examId);
        double max = -1;
        
        for (Student student : students.values()) {
            Submission sub = student.getSubmissionIfExists(examId);
            
            if (sub != null) {
                double score = 0.0;
                
                for (Map.Entry<Integer, Question> entry : exam.getQuestions().entrySet()) {
                    int qnum = entry.getKey();
                    Question q = entry.getValue();
                    Object studentAnswer = sub.getAnswer(qnum);
                    score += q.grade(studentAnswer);
                }
                
                if (score > max) {
                	max = score;
                }
            }
        }
        
        return max < 0 ? 0 : max;
    }
    
    // compute min exam score for an exam
    public double getMinScore(int examId) {
        Exam exam = getExistingExam(examId);
        double min = Double.MAX_VALUE;
        boolean found = false;
        
        for (Student student : students.values()) {
            Submission sub = student.getSubmissionIfExists(examId);
            
            if (sub != null) {
                double score = 0.0;
                
                for (Map.Entry<Integer, Question> entry : exam.getQuestions().entrySet()) {
                    int qnum = entry.getKey();
                    Question q = entry.getValue();
                    Object studentAnswer = sub.getAnswer(qnum);
                    score += q.grade(studentAnswer);
                }
                
                if (score < min) {
                    min = score;
                }
                
                found = true;
            }
        }
        
        return found ? min : 0;
    }
    
    // get exam key with question text points and correct answer
    public String getKey(int examId) {
        Exam exam = exams.get(examId);
        
        if (exam == null) {
            return "Exam not found";
        }
        
        StringBuilder sb = new StringBuilder();
        SortedMap<Integer, Question> sortedQuestions = new TreeMap<>(exam.getQuestions());
        
        for (Map.Entry<Integer, Question> entry : sortedQuestions.entrySet()) {
            Question q = entry.getValue();
            sb.append("Question Text: ").append(q.getText()).append("\n")
              .append("Points: ").append(q.getPoints()).append("\n")
              .append("Correct Answer: ").append(q.getCorrectAnswer()).append("\n");
        }
       
        return sb.toString().trim();
    }
    

    // compute course numeric grade for a student
    public double getCourseNumericGrade(String studentName) {
        Student student = getExistingStudent(studentName);
        double totalPercentage = 0.0;
        int examCount = exams.size();
        
        if (examCount == 0) {
            return 0.0;
        }
        
        for (Exam exam : exams.values()) {
            Submission sub = student.getSubmissionIfExists(exam.getExamId());
            double examScore = 0.0;
            double totalPoints = exam.getTotalPoints();
            if (totalPoints == 0) {
                continue;
            }
            
            if (sub != null) {
            	
                for (Map.Entry<Integer, Question> entry : exam.getQuestions().entrySet()) {
                    int qnum = entry.getKey();
                    Question q = entry.getValue();
                    Object studentAnswer = sub.getAnswer(qnum);
                    examScore += q.grade(studentAnswer);
                }
            }
            
            double percentage = (examScore / totalPoints) * 100.0;
            totalPercentage += percentage;
        }
        
        return totalPercentage / examCount;
    }
    
    // compute course letter grade for a student
    public String getCourseLetterGrade(String studentName) {
        double numericGrade = getCourseNumericGrade(studentName);
        if (letterGrades == null || cutoffs == null) {
            throw new IllegalStateException("Letter grade cutoffs have not been set");
        }
        
        for (int i = 0; i < cutoffs.length; i++) {
            if (numericGrade >= cutoffs[i]) {
                return letterGrades[i];
            }
        }
        
        return "F";
    }
    
    // get list of course grades for all students in sorted order
    public String getCourseGrades() {
        List<String> studentNames = new ArrayList<>(students.keySet());
        Collections.sort(studentNames);
        StringBuilder sb = new StringBuilder();
       
        for (String name : studentNames) {
            double numericGrade = getCourseNumericGrade(name);
            String letter = getCourseLetterGrade(name);
            sb.append(name)
              .append(" ")
              .append(String.format("%.1f", numericGrade))
              .append(" ")
              .append(letter)
              .append("\n");
        }
        
        return sb.toString().trim();
    }
    

    // serialize manager object to file
    public void saveManager(Manager manager, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(manager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // restore manager object from file
    public Manager restoreManager(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Manager) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    // set letter grade cutoffs for course
    public void setLetterGradesCutoffs(String[] letterGrades, double[] cutoffs) {
        if (letterGrades == null || cutoffs == null || letterGrades.length != cutoffs.length) {
            throw new IllegalArgumentException("Cutoff arrays must be non-null and of equal length");
        }
        
        this.letterGrades = letterGrades;
        this.cutoffs = cutoffs;
    }
    

    // get existing student from students map or throw exception
    private Exam getExistingExam(int examId) {
        Exam exam = exams.get(examId);
        
        if (exam == null) {
            throw new IllegalArgumentException("Exam with id " + examId + " not found.");
        }
        
        return exam;
    }
    
    private Student getExistingStudent(String name) {
        Student student = students.get(name);
        
        if (student == null) {
            throw new IllegalArgumentException("Student " + name + " not found.");
        }
        
        return student;
    }
}
