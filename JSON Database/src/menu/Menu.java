package menu;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu implements Runnable {
    public static void menuExecute() {
        ArrayList<String> li = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            li.add("");
        }

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                String[] commands = sc.nextLine().split(" ", 3);

                switch (commands[0]) {
                    case "set":
                        try {
                            li.set(Integer.parseInt(commands[1]) - 1, commands[2]);
                            System.out.println("OK");

                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("ERROR");
                        }
                        continue;

                    case "get":
                        try {
                            System.out.println(li.get(Integer.parseInt(commands[1]) - 1));
                            if (li.get(Integer.parseInt(commands[1]) + 1).isBlank()) {
                                System.out.println("ERROR");
                            } else {
                                System.out.println("OK");
                            }
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("ERROR");
                        }
                        continue;

                    case "delete":
                        try {
                            li.set(Integer.parseInt(commands[1]) - 1, "");
                            System.out.println("OK");
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("ERROR");
                        }
                        continue;

                    case "exit":
                        break;

                    default:
                        System.out.println("Wrong input");
                        continue;
                }
                break;
            }
        } catch (IndexOutOfBoundsException oob) {
            System.out.println("ERROR");
        }
    }

    @Override
    public void run() {
        menuExecute();
    }
}


