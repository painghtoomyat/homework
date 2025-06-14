package studentapp;

import java.io.Serializable;
import java.util.Map;
import java.util.stream.Collectors;

public class Student implements Serializable {
    private String id;
    private String name;
    private Map<String, Integer> subjectMarks;

    // Constructor
    public Student(String id, String name, Map<String, Integer> subjectMarks) {
        this.id = id;
        this.name = name;
        this.subjectMarks = subjectMarks;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getSubjectMarks() {
        return subjectMarks;
    }

    // Calculate total marks using Stream API
    public int getTotalMarks() {
        return subjectMarks.values().stream() //Get all the marks from the map (values()),
                .mapToInt(Integer::intValue) //Convert them to integers,
                .sum(); //Add them together using sum().
    }

    // Calculate average marks using Stream API
    public double getAverageMarks() {
        return subjectMarks.values().stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    // Format output
    @Override
    public String toString() {
        String subjects = subjectMarks.entrySet().stream()
                .map(entry -> "  " + entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("\n"));

        return "Student ID: " + id + "\n"
             + "Name: " + name + "\n"
             + "Subjects:\n" + subjects + "\n"
             + "Total Marks: " + getTotalMarks() + "\n"
             + "Average: " + String.format("%.2f", getAverageMarks()) + "\n";
    }
}
