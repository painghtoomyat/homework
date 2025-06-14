package studentapp;

import java.io.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


//Stores all students in a list, keeps unique IDs in a set, and defines the filename for saving/loading.
public class StudentManager {
    private List<Student> students = new ArrayList<>();
    private Set<String> studentIds = new HashSet<>();
    private final String fileName = "students.dat";

    
    //addStudents
    public void addStudent(Scanner scanner) {
    	String id;
    	while(true) { System.out.print("Enter Student ID: ");
        id = scanner.nextLine().trim();
        if (studentIds.contains(id.toLowerCase())) {
            System.out.println("Error: Student ID already exists. Please use a unique ID.");
        } else if (id.isEmpty()) {
            System.out.println("Error: Student ID cannot be empty.");
        } else {
        	break;
        }
        }
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine().trim();

        Map<String, Integer> subjectMarks = new HashMap<>();
        //Checks if student ID is already used to avoid duplicates.
        while (true) {
        	//Input subjects and marks, validate marks are 0-100.
            System.out.print("Enter subject name (or 'y' to finish): ");
            String subject = scanner.nextLine().trim();
            if (subject.equalsIgnoreCase("y")) break;

            System.out.print("Enter marks for " + subject + ": ");
            try {
                int marks = Integer.parseInt(scanner.nextLine().trim());
                if (marks < 0 || marks > 100) {
                    System.out.println("Marks must be between 0 and 100.");
                    continue;
                }
                subjectMarks.put(subject, marks);
            } catch (NumberFormatException e) {
                System.out.println("Invalid marks. Please enter a number.");
            }
        }

        Student student = new Student(id, name, subjectMarks);
        students.add(student);
        studentIds.add(id.toLowerCase());

        System.out.println("Student added successfully.");
        System.out.println("Press 5 to save data to files.");
       
    }

    
    //searchStudent
    public void searchStudent(Scanner scanner) {
        System.out.print("Enter Id to search: ");
        String id = scanner.nextLine().trim().toLowerCase();

        boolean found = false;
        for (Student s : students) {
            if (s.getId().toLowerCase().contains(id)) {
                System.out.println("\n" + s);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No student found with that Id.");
        }
    }

    
    //deleteStudent
    public void deleteStudent(Scanner scanner) {
        System.out.print("Enter Student ID to delete: ");
        String deleteId = scanner.nextLine().trim();

        boolean removed = students.removeIf(s -> s.getId().equalsIgnoreCase(deleteId));
        //try debugging
      //System.out.println(removed);true
        if (removed) {
            studentIds.remove(deleteId.toLowerCase()); // Also remove from the ID set
            System.out.println("Student with ID '" + deleteId + "' deleted successfully.");
        } else {
            System.out.println("Student with ID '" + deleteId + "' not found.");
        }
    }

    
    //displayAllStudents
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records to display.");
            return;
        }

        System.out.println("\nCalculating all results Asynchronously....");
        System.out.println();
        System.out.println("---------Student Records-------------");
        System.out.println();
        List<CompletableFuture<String>> futures = new ArrayList<>();
        for(Student student : students) {
        	futures.add(CompletableFuture.supplyAsync(student::toString));
        	        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        futures.forEach(future -> {
            try {
                System.out.println(future.get());
                System.out.println("-------------------------------");
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Error Student Displayed: " + e.getMessage());
            }
        });

    }

    
    //saveStudents , Saves the student list to students.dat file by serializing the list.
    public void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(students);
            System.out.println("Students saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    
    //loadStudents , Loads the student list from students.dat by deserializing it.
    public void loadStudents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            students = (List<Student>) ois.readObject();
            studentIds.clear();
            for (Student s : students) {
                studentIds.add(s.getId());
            }
            System.out.println("Students loaded from " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading students: " + e.getMessage());
        }
    }
       
}
