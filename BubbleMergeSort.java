import java.io.*;
import java.util.*;

public class BubbleMergeSort {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Generate an array of random integers and store it in a file");
            System.out.println("2. Read an existing file containing a list of integers, sort it with BubbleSort, and store the sorted array in another file");
            System.out.println("3. Read an existing file containing a list of integers, sort it with MergeSort, and store the sorted array in another file");
            System.out.println("4. Compare BubbleSort and MergeSort results");
            System.out.println("5. Exit");
    
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
    
                switch (choice) {
                    case 1:
                        int arrayLength;
                        do {
                            System.out.print("Enter the array length (0 - 100): ");
                            arrayLength = scanner.nextInt();
                            if (arrayLength < 0 || arrayLength > 100) {
                                System.out.println("Array length must be between 0 and 100.");
                            }
                        } while (arrayLength < 0 || arrayLength > 100);
    
                        ArrayList<Integer> randomArray = createRandomArrayList(arrayLength);
                        writeArrayListToFile(randomArray, "random_array.txt");
                        System.out.println("Random array has been generated and stored in 'random_array.txt'");
                        break;
                    case 2:
                        System.out.print("Enter the input file name: ");
                        String bubbleSortInputFilename = scanner.next();
                        ArrayList<Integer> bubbleSortArray = readArrayListFromFile(bubbleSortInputFilename);
                        bubbleSort(bubbleSortArray);
                        System.out.print("Enter the output file name: ");
                        String bubbleSortOutputFilename = scanner.next();
                        writeArrayListToFile(bubbleSortArray, bubbleSortOutputFilename);
                        System.out.println("Array has been sorted with BubbleSort and stored in '" + bubbleSortOutputFilename + "'");
                        break;
                    case 3:
                        System.out.print("Enter the input file name: ");
                        String mergeSortInputFilename = scanner.next();
                        ArrayList<Integer> mergeSortArray = readArrayListFromFile(mergeSortInputFilename);
                        mergeSort(mergeSortArray);
                        System.out.print("Enter the output file name: ");
                        String mergeSortOutputFilename = scanner.next();
                        writeArrayListToFile(mergeSortArray, mergeSortOutputFilename);
                        System.out.println("Array has been sorted with MergeSort and stored in '" + mergeSortOutputFilename + "'");
                        break;
                    case 4:
                        System.out.print("Enter the input file name for BubbleSort: ");
                        bubbleSortInputFilename = scanner.next();
                        ArrayList<Integer> bubbleSortResult = readArrayListFromFile(bubbleSortInputFilename);

                        System.out.print("Enter the input file name for MergeSort: ");
                        mergeSortInputFilename = scanner.next();
                        ArrayList<Integer> mergeSortResult = readArrayListFromFile(mergeSortInputFilename);

                        if (compareArrays(bubbleSortResult, mergeSortResult)) {
                            System.out.println("BubbleSort and MergeSort results are equal.");
                        } else {
                            System.out.println("BubbleSort and MergeSort results are not equal.");
                        }
                        break;
                    case 5:
                        System.out.println("Exiting the program.");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, 3, 4, or 5.");
                }
            } else {
                System.out.println("Invalid input. Please enter 1, 2, 3, 4, or 5.");
                scanner.next();
            }
        }
    }
    
    public static ArrayList<Integer> createRandomArrayList(int arrayLength) {
        if (arrayLength < 0 || arrayLength > 100) {
            throw new IllegalArgumentException("Array length must be between 0 and 100.");
        }
    
        ArrayList<Integer> randomArray = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < arrayLength; i++) {
            randomArray.add(rand.nextInt(101));
        }
        return randomArray;
    }
    
    public static void writeArrayListToFile(ArrayList<Integer> arrayList, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Integer value : arrayList) {
                writer.println(value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> readArrayListFromFile(String filename) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int value = Integer.parseInt(line);
                arrayList.add(value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static void bubbleSort(ArrayList<Integer> array) {
        int n = array.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array.get(j) > array.get(j + 1)) {
                    int temp = array.get(j);
                    array.set(j, array.get(j + 1));
                    array.set(j + 1, temp);
                }
            }
        }
    }

    public static void mergeSort(ArrayList<Integer> array) {
        if (array.size() <= 1) {
            return;
        }
        
        int mid = array.size() / 2;
        ArrayList<Integer> left = new ArrayList<>(array.subList(0, mid));
        ArrayList<Integer> right = new ArrayList<>(array.subList(mid, array.size()));
        
        mergeSort(left);
        mergeSort(right);
        
        merge(array, left, right);
    }

    public static void merge(ArrayList<Integer> array, ArrayList<Integer> left, ArrayList<Integer> right) {
        int i = 0, j = 0, k = 0;
        
        while (i < left.size() && j < right.size()) {
            if (left.get(i) < right.get(j)) {
                array.set(k++, left.get(i++));
            } else {
                array.set(k++, right.get(j++));
            }
        }
        
        while (i < left.size()) {
            array.set(k++, left.get(i++));
        }
        
        while (j < right.size()) {
            array.set(k++, right.get(j++));
        }
    }

    public static boolean compareArrays(ArrayList<Integer> array1, ArrayList<Integer> array2) {
        if (array1.size() != array2.size()) {
            return false;
        }

        for (int i = 0; i < array1.size(); i++) {
            if (!array1.get(i).equals(array2.get(i))) {
                return false;
            }
        }

        return true;
    }
}