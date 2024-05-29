import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentGradeAnalyzer extends JFrame {
    private static class Student {
        private int id;
        private String name;
        private List<Double> grades;

        public Student(int id, String name) {
            this.id = id;
            this.name = name;
            this.grades = new ArrayList<>();
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public List<Double> getGrades() {
            return grades;
        }

        public void addGrade(double grade) {
            grades.add(grade);
        }

        public double getAverageGrade() {
            return grades.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        }

        @Override
        public String toString() {
            return "ID: " + id + ", Name: " + name + ", Grades: " + grades;
        }
    }

    private Map<Integer, Student> students = new HashMap<>();
    private int studentIdCounter = 1;

    private JTextField studentNameField;
    private JTextField studentGradeField;
    private JTextField studentIdField;
    private JTextArea displayArea;

    public StudentGradeAnalyzer() {
        setTitle("Student Grade Analyzer");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        inputPanel.add(new JLabel("Student Name:"));
        studentNameField = new JTextField();
        inputPanel.add(studentNameField);

        JButton addStudentButton = new JButton("Add Student");
        inputPanel.add(addStudentButton);
        inputPanel.add(new JLabel(""));

        inputPanel.add(new JLabel("Student ID:"));
        studentIdField = new JTextField();
        inputPanel.add(studentIdField);

        inputPanel.add(new JLabel("Grade:"));
        studentGradeField = new JTextField();
        inputPanel.add(studentGradeField);

        JButton addGradeButton = new JButton("Add Grade");
        inputPanel.add(addGradeButton);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        addGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGrade();
            }
        });
    }

    private void addStudent() {
        String name = studentNameField.getText();
        if (!name.isEmpty()) {
            Student student = new Student(studentIdCounter, name);
            students.put(studentIdCounter, student);
            studentIdCounter++;
            studentNameField.setText("");
            updateDisplay();
        }
    }

    private void addGrade() {
        try {
            int studentId = Integer.parseInt(studentIdField.getText());
            double grade = Double.parseDouble(studentGradeField.getText());

            if (students.containsKey(studentId)) {
                Student student = students.get(studentId);
                student.addGrade(grade);
                studentIdField.setText("");
                studentGradeField.setText("");
                updateDisplay();
            } else {
                JOptionPane.showMessageDialog(this, "Student ID not found.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void updateDisplay() {
        StringBuilder displayText = new StringBuilder();
        for (Student student : students.values()) {
            displayText.append(student).append(", Average Grade: ").append(student.getAverageGrade()).append("\n");
        }
        displayArea.setText(displayText.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentGradeAnalyzer().setVisible(true);
            }
        });
    }
}
