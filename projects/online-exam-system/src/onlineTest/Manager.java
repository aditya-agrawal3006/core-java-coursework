package onlineTest;

public interface Manager {
	/**
	 * Adds the specified exam to the database.
	 * @param examId
	 * @param title
	 * @return false is exam already exists.
	 */
	public boolean addExam(int examId, String title);
	
	
	/**
	 * Adds a true and false question to the specified exam.  If the question
	 * already exists it is overwritten.
	 * @param examId
	 * @param questionNumber
	 * @param text Question text
	 * @param points total points
	 * @param answer expected answer
	 */
	public void addTrueFalseQuestion(int examId, int questionNumber,
									    String text, double points, boolean answer);
	
	
	/**
	 * Adds a multiple choice question to the specified exam.   If the question
	 * already exists it is overwritten.
	 * @param examId
	 * @param questionNumber
	 * @param text Question text
	 * @param points total points
	 * @param answer expected answer
	 */
	public void addMultipleChoiceQuestion(int examId, int questionNumber,
		    String text, double points, String[] answer);
	
	
	/**
	 * Adds a fill-in-the-blanks question to the specified exam.  If the question
	 * already exits it is overwritten.  Each correct response is worth
	 * points/entries in the answer.
	 * @param examId
	 * @param questionNumber
	 * @param text Question text
	 * @param points total points
	 * @param answer expected answer
	 */
	public void addFillInTheBlanksQuestion(int examId, int questionNumber,
		    String text, double points, String[] answer);
	
	
	/**
	 * Returns a string with the following information per question:<br />
	 * "Question Text: " followed by the question's text<br />
	 * "Points: " followed by the points for the question<br />
	 * "Correct Answer: " followed by the correct answer. <br />
	 * The format for the correct answer will be: <br /> 
	 *    a. True or false question: "True" or "False"<br />
	 *    b. Multiple choice question: [ ] enclosing the answer (each entry separated by commas) and in
	 *       sorted order. <br />
	 *    c. Fill in the blanks question: [ ] enclosing the answer (each entry separated by commas) and
	 *       in sorted order. <br />
	 * @param examId
	 * @return "Exam not found" if exam not found, otherwise the key
	 */
	public String getKey(int examId);
	
	
	/**
	 * Adds the specified student to the database.  Names are specified in the format
	 * LastName,FirstName
	 * @param name
	 * @return false if student already exists.
	 */
	public boolean addStudent(String name);
	
	
	/**
	 * Enters the question's answer into the database.
	 * @param studentName
	 * @param examId
	 * @param questionNumber
	 * @param answer
	 */
	public void answerTrueFalseQuestion(String studentName, int examId, int questionNumber, boolean answer);
	
	
	/**
	 * Enters the question's answer into the database.
	 * @param studentName
	 * @param examId
	 * @param questionNumber
	 * @param answer
	 */
	public void answerMultipleChoiceQuestion(String studentName, int examId, int questionNumber, String[] answer);
	
	
	/**
	 * Enters the question's answer into the database.
	 * @param studentName
	 * @param examId
	 * @param questionNumber
	 * @param answer
	 */
	public void answerFillInTheBlanksQuestion(String studentName, int examId, int questionNumber, String[] answer);
	

	/**
	 * Returns the score the student got for the specified exam.
	 * @param studentName
	 * @param examId
	 * @return score
	 */
	public double getExamScore(String studentName, int examId);
	
	
	/**
	 * Generates a grading report for the specified exam.  The report will include
	 * the following information for each exam question:<br />
	 * "Question #" {questionNumber} {questionScore} " points out of " {totalQuestionPoints}<br />
	 * The report will end with the following information:<br />
	 * "Final Score: " {score} " out of " {totalExamPoints};  
	 * @param studentName
	 * @param examId
	 * @return report
	 */
	public String getGradingReport(String studentName, int examId);
	
	
	/**
	 * Sets the cutoffs for letter grades.  For example, a typical curve we will have
	 * new String[]{"A","B","C","D","F"}, new double[] {90,80,70,60,0}.  Anyone with a 90 or
	 * above gets an A, anyone with an 80 or above gets a B, etc.  Notice we can have different
	 * letter grades and cutoffs (not just the typical curve).
	 * @param letterGrades
	 * @param cutoffs
	 */
	public void setLetterGradesCutoffs(String[] letterGrades, double[] cutoffs);
	
	
	/**
	 * Computes a numeric grade (value between 0 and a 100) for the student taking
	 * into consideration all the exams.  All exams have the same weight. 
	 * @param studentName
	 * @return grade
	 */
	public double getCourseNumericGrade(String studentName);
	
	
	/** 
	 * Computes a letter grade based on cutoffs provided.  It is assumed the cutoffs have
	 * been set before the method is called.
	 * @param studentName
	 * @return letter grade
	 */
	public String getCourseLetterGrade(String studentName);
	
	
	/**
	 * Returns a listing with the grades for each student.  For each student the report will
	 * include the following information: <br />
	 * {studentName} {courseNumericGrade} {courseLetterGrade}<br />
	 * The names will appear in sorted order.
	 * @return grades
	 */
	public String getCourseGrades();
	
	
	/**
	 * Returns the maximum score (among all the students) for the specified exam.
	 * @param examId
	 * @return maximum
	 */
	public double getMaxScore(int examId);
	
	
	/**
	 * Returns the minimum score (among all the students) for the specified exam.
	 * @param examId
	 * @return minimum
	 */
	public double getMinScore(int examId);
	
	
	/**
	 * Returns the average score for the specified exam.
	 * @param examId
	 * @return average
	 */
	public double getAverageScore(int examId);
	
	
	/**
	 * It will serialize the Manager object and store it in the
	 * specified file.
	 */
	public void saveManager(Manager manager, String fileName);
	
	
	/**
	 * It will return a Manager object based on the serialized data
	 * found in the specified file.
	 */
	public Manager restoreManager(String fileName);	
}