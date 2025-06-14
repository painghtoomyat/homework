package studentapp;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        manager.loadStudents();

        
        //Starts the application.
        while (running) {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. Search Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Save Students");           
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            
            //Runs a loop showing the menu and processing user commands until exit.
            switch (scanner.nextLine().trim()) {
                case "1": manager.addStudent(scanner); break;
                case "2": manager.searchStudent(scanner); break;
                case "3": manager.deleteStudent(scanner); break;
                case "4": manager.displayAllStudents(); break;
                case "5": manager.saveStudents(); break;                
                case "0": System.out.println("Existing Application.Bye.....!");return;
                default: System.out.println("Invalid option.");
                
            } System.out.println("\nPress Enter to Continue...... ");
            scanner.nextLine();
        }
        
        scanner.close();
    }
}
