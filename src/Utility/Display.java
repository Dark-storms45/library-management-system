package Utility;

import java.util.HashMap;
import java.util.List;

public class Display {


    public static void displayBox(String text) {
        int textLength = text.length(); // Get the length of the text
        int paddingSize = 4; // Set padding size
        int boxWidth = textLength + paddingSize * 2; // Calculate the width of the box   
        // Print the top border
        for (int i = 0; i < boxWidth; i++) {
            System.out.print("-");
        }
        System.out.println();

        // Print the padding
        System.out.print("|");
        for (int i = 0; i < paddingSize; i++) {
            System.out.print(" ");
        }
        System.out.print(text); // Print the text

        // Print the rest of the padding
        for (int i = 0; i < paddingSize; i++) {
            System.out.print(" ");
        }
        System.out.println("|");

        // Print the bottom border
        for (int i = 0; i < boxWidth; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
    /**
     * @param arr
     */
    public static void Display_menu(String [] arr){
        int textLength = arr.length; // Get the length of the text
               int paddingSize = textLength*2; // Set padding size
               int boxWidth =  textLength+paddingSize * 2; // Calculate the width of the box       
               // Print the top border
               for (int i = 0; i < boxWidth; i++) {
                   System.out.print("-");
               }
               System.out.println();
       
               // Print the padding
              
               for (int i = 0; i <1; i++) {
                 // Print the  array content
               for (int j = 0; j < textLength; j++) {
                System.out.print("|");
                System.out.print("  "+(j+1)+"."+arr[j]+"\n"); 
               
            }
          
                   
               }
                  // Print the bottom border
               for (int i = 0; i < boxWidth; i++) {
                   System.out.print("-");
               }
               System.out.println();
       
           }

 public static void Display_tables(HashMap<String, List<String>> tableData) {
        // Print the table header
        for (String key : tableData.keySet()) {
            System.out.printf("%-50s |", key,"%-170s");
        }
        System.out.println();

        // Determine the maximum number of rows
        int maxRows = 0;
        for (List<String> values : tableData.values()) {
            if (values.size() > maxRows) {
                maxRows = values.size();
            }
        }

        // Print the table rows
        for (int i = 0; i < maxRows; i++) {
            for (String key : tableData.keySet()) {
                List<String> values = tableData.get(key);
                if (i < values.size()) {
                    System.out.printf("%-50s |", values.get(i) ,"%-90s");
                } else {
                    System.out.printf("%-50s |", "%-90s","");
                }
            }
            System.out.println();
        }
    }
}


