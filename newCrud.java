import java.io.*;
import java.util.Scanner;

public class newCrud {

    static final String FILE_PATH = "CSVReaderCRUD.csv";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("RECORDS MENU");
            System.out.println("1. Add Record");
            System.out.println("2. Display All Records");
            System.out.println("3. Update Record");
            System.out.println("4. Delete Record");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = readInt();

            switch (choice) {
                case 1:
                    addRecord();
                    break;
                case 2:
                    displayRecords();
                    break;
                case 3:
                    updateRecord();
                    break;
                case 4:
                    deleteRecord();
                    break;
                case 5:
                    System.out.println("Program exited.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 5);

        sc.close();
    }

    public static void addRecord() {
        try (FileWriter fw = new FileWriter(FILE_PATH, true)) {
            System.out.println("Add Record");

            System.out.print("Enter name: ");
            String name = sc.nextLine();

            System.out.print("Enter age: ");
            String age = sc.nextLine();

            System.out.print("Enter course: ");
            String course = sc.nextLine();

            System.out.print("Enter year level: ");
            String year = sc.nextLine();

            System.out.print("Enter student ID: ");
            String id = sc.nextLine();

            fw.write(name + "," + age + "," + course + "," + year + "," + id + "\n");
            System.out.println("Record added successfully.");

        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    public static void displayRecords() {
        System.out.println("Current Records");

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            int lineNumber = 1;
            boolean hasRecord = false;

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                System.out.println("Record #" + lineNumber);
                System.out.println("  Name: " + fields[0]);
                System.out.println("  Age: " + fields[1]);
                System.out.println("  Course: " + fields[2]);
                System.out.println("  Year Level: " + fields[3]);
                System.out.println("  Student ID: " + fields[4]);
                System.out.println();
                lineNumber++;
                hasRecord = true;
            }

            if (!hasRecord) {
                System.out.println("No records found.");
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found yet.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static void updateRecord() {
        String[] records = readAllRecords();
        int count = countRecords(records);

        if (count == 0) {
            System.out.println("No records to update.");
            return;
        }

        displayRecords();

        System.out.print("Enter record number to update: ");
        int recordNumber = readInt();

        if (recordNumber < 1 || recordNumber > count) {
            System.out.println("Invalid record number.");
            return;
        }

        System.out.print("Enter new name: ");
        String name = sc.nextLine();

        System.out.print("Enter new age: ");
        String age = sc.nextLine();

        System.out.print("Enter new course: ");
        String course = sc.nextLine();

        System.out.print("Enter new year level: ");
        String year = sc.nextLine();

        System.out.print("Enter new student ID: ");
        String id = sc.nextLine();

        records[recordNumber - 1] = name + "," + age + "," + course + "," + year + "," + id;
        writeAllRecords(records, count);

        System.out.println("Record updated successfully.");
    }

    public static void deleteRecord() {
        String[] records = readAllRecords();
        int count = countRecords(records);

        if (count == 0) {
            System.out.println("No records to delete.");
            return;
        }

        displayRecords();

        System.out.print("Enter record number to delete: ");
        int recordNumber = readInt();

        if (recordNumber < 1 || recordNumber > count) {
            System.out.println("Invalid record number.");
            return;
        }

        for (int i = recordNumber - 1; i < count - 1; i++) {
            records[i] = records[i + 1];
        }

        records[count - 1] = null;
        count--;

        writeAllRecords(records, count);

        System.out.println("Record deleted successfully.");
    }

    public static String[] readAllRecords() {
        String[] records = new String[100];
        int index = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null && index < records.length) {
                if (!line.trim().isEmpty()) {
                    records[index] = line;
                    index++;
                }
            }

        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return records;
    }

    public static int countRecords(String[] records) {
        int count = 0;

        for (String record : records) {
            if (record != null) {
                count++;
            }
        }

        return count;
    }

    public static void writeAllRecords(String[] records, int count) {
        try (FileWriter fw = new FileWriter(FILE_PATH, false)) {
            for (int i = 0; i < count; i++) {
                fw.write(records[i] + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    public static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid number: ");
            }
        }
    }
}