import java.util.Scanner;

class HelloUser {
    public static void main(String[] args) {
        System.out.println("Please enter user name:");
        Scanner scanner = new Scanner(System.in);
        String userName = scanner.nextLine();
        System.out.println("Привет, " + userName + "!");
    }
}