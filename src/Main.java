public class Main {
    public static void main(String[] args) {
        Task task1 = new Task("Finish homework", "High");
        Task task2 = new Task("Clean room", "Medium");
        Task task3 = new Task("Go grocery shopping", "Low");

        System.out.println(task1);
        System.out.println(task2);
        System.out.println(task3);

        task1.markCompleted();
        System.out.println(task1);
    }
}
