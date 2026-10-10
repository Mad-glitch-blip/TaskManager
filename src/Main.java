import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        ArrayList<Task> tasks = loadTasks();

        boolean running = true;

        while (running) {
            running = displayMenu(input, tasks);
        }

    }

    public static void viewTasks(ArrayList<Task> tasks) {

        if (tasks.isEmpty()) {
            System.out.println("No tasks yet.");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        System.out.println("");
    }

    public static boolean displayMenu(Scanner input, ArrayList<Task> tasks) {

        System.out.println("===== Task Manager ======");
        System.out.println("1. View Tasks");
        System.out.println("2. Add Task");
        System.out.println("3. Complete Task");
        System.out.println("4. Delete Task");
        System.out.println("5. Mark Task Incomplete.");
        System.out.println("6. View Completed Tasks");
        System.out.println("7. View Incomplete Tasks");
        System.out.println("8. Filter Tasks by Priority");
        System.out.println("9. Sort Tasks by Priority");
        System.out.println("10. Search Tasks");
        System.out.println("11. Exit");

        System.out.println("");
        // Get the user's menu choice
        System.out.print("What action would you like to do #: ");
        int choice;
        try {
            choice = input.nextInt();
            input.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number");
            input.nextLine();
            return true;// continues the loop without stopping
        }

        if (choice == 1) {
            // Check if there are any tasks
            viewTasks(tasks);
            return true;
        } else if (choice == 2) {
            // Ask the user for a task
            System.out.println("");
            System.out.print("What task do you want to add? ");
            String taskName = input.nextLine();
            System.out.println("");
            System.out.print("Set your task priority (High/Medium/Low): ");
            String taskPriority = input.nextLine();
            while (!taskPriority.equalsIgnoreCase("High") && !taskPriority.equalsIgnoreCase("Medium")
                    && !taskPriority.equalsIgnoreCase("Low")) {
                System.out.println("Invalid priority. Enter High/Medium/Low.");
                taskPriority = input.nextLine();
            }
            System.out.println("");
            Task newTask = new Task(taskName, taskPriority);
            tasks.add(newTask);
            return true;
        } else if (choice == 3) {
            System.out.print("Choose which task to complete first #: ");
            int taskNumber;
            try {
                taskNumber = input.nextInt();
                input.nextLine();// consumes the new line character
                if (taskNumber >= 1 && taskNumber <= tasks.size()) {
                    int index = taskNumber - 1;
                    Task taskToComplete = tasks.get(index);
                    taskToComplete.markCompleted();
                    System.out.println("Task completed: " + taskToComplete.getName());
                } else {
                    System.out.println("Invalid number. Please try again.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine();
                return true;
            }
            return true;
        } else if (choice == 4) {
            if (tasks.isEmpty()) {
                System.out.println("No tasks to delete");
                return true;
            }
            viewTasks(tasks);
            System.out.println("");
            System.out.println("Choose which tasks to delete #:");
            try {
                int taskNumber = input.nextInt();
                input.nextLine();
                if (taskNumber >= 1 && taskNumber <= tasks.size()) {
                    int index = taskNumber - 1;
                    Task deleteTask = tasks.remove(index);
                    System.out.println("Task deleted: " + deleteTask.getName());
                } else {
                    System.out.println("Invalid number. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please try again.");
                input.nextLine();
                return true;
            }
            return true;
        } else if (choice == 5) {
            if (tasks.isEmpty()) {
                System.out.println("No tasks to update.");
                return true;
            }
            System.out.print("Choose which task to mark Incomplete #: ");
            int taskNumber;
            try {
                taskNumber = input.nextInt();
                input.nextLine();// consumes the new line character
                if (taskNumber >= 1 && taskNumber <= tasks.size()) {
                    int index = taskNumber - 1;
                    Task taskToComplete = tasks.get(index);
                    taskToComplete.markIncomplete();
                    System.out.println("Task marked Incomplete: " + taskToComplete.getName());
                } else {
                    System.out.println("Invalid number. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please try again.");
                input.nextLine();
                return true;
            }
            return true;
        } else if (choice == 6) {
            filterTasks(tasks, true);
            return true;
        } else if (choice == 7) {
            filterTasks(tasks, false);
            return true;
        } else if (choice == 8) {
            System.out.print("Enter the priority to filter by (High/Medium/Low): ");
            String priority = input.nextLine();
            while (!priority.equalsIgnoreCase("High") && !priority.equalsIgnoreCase("Medium")
                    && !priority.equalsIgnoreCase("Low")) {
                System.out.println("Invalid priority. Enter High/Medium/Low.");
                priority = input.nextLine();
            }
            filterTasksByPriority(tasks, priority);
            return true;

        } else if (choice == 9) {
            sortTasksByPriority(tasks);
            return true;

        } else if (choice == 10) {
            System.out.println("Search tasks by keyword: ");
            String keyword = input.nextLine();
            searchTasks(tasks, keyword);
            return true;
        } else if (choice == 11) {
            System.out.println("Ending.....");
            saveTasks(tasks);
            return false;
        } else {
            System.out.println("Invalid choice.");
            return true;
        }
    }

    // things to remember to improve next time: Task Numbering based on the filtered
    // list, not the original list. We do this after i finish other things as well.
    public static void filterTasks(ArrayList<Task> tasks, boolean showCompleted) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks yet.");
            return;
        }

        boolean found = false;
        int displayIndex = 1;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.isCompleted() == showCompleted) {
                System.out.println((displayIndex++) + ". " + task);
                found = true;
            }

        }
        if (!found) {
            if (showCompleted) {
                System.out.println("No completed tasks to show.");
            } else {
                System.out.println("No Incomplete tasks to show.");
            }
        }
        System.out.println("");
    }

    public static void filterTasksByPriority(ArrayList<Task> tasks, String priority) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks yet.");
            return;
        }

        boolean found = false;
        int displayIndex = 1;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getPriority().equalsIgnoreCase(priority)) {
                System.out.println((displayIndex++) + ". " + task);
                found = true;
            }

        }
        if (!found) {
            System.out.println("No tasks found with priority: " + priority);
        }

    }

    public static void sortTasksByPriority(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks yet.");
            return;
        }

        String[] priorities = { "High", "Medium", "Low" };
        int displayIndex = 1;

        for (int p = 0; p < priorities.length; p++) {
            String currentPriority = priorities[p];
            boolean foundPriority = false;
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);

                if (task.getPriority().equalsIgnoreCase(currentPriority)) {
                    System.out.println((displayIndex++) + ". " + task);
                    foundPriority = true;
                }
            }
            if (!foundPriority) {
                System.out.println("No tasks found with any valid prority: " + currentPriority);
            }
        }
        System.out.println("");

    }

    public static void searchTasks(ArrayList<Task> tasks, String keyword) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }

        if (keyword.isBlank()) {
            System.out.print("No keyword entered. Please enter a search keyword.");
            return;
        }

        boolean found = false;
        int displayIndex = 1;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println((displayIndex++) + ". " + task);
                found = true;
            }
        }
        if (!found) {
            System.out.print("No tasks found with keyword: " + keyword);
        }
        System.out.println("");
    }

    public static void saveTasks(ArrayList<Task> tasks) {
        try {
            FileWriter writer = new FileWriter("tasks.txt");
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                writer.write(task.getName() + "|" + task.getPriority() + "|" + task.isCompleted() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.print("Error saving tasks: " + e.getMessage());
        }
    }

    public static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        try {
            Scanner fileScanner = new Scanner(new File("tasks.txt"));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\|");
                Task task = new Task(parts[0], parts[1]);
                boolean completed = Boolean.parseBoolean(parts[2]);
                if (completed) {
                    task.markCompleted();
                }
                tasks.add(task);
            }
            fileScanner.close();
        } catch (IOException e) {
            System.out.print("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }
}
