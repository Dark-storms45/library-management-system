public class test {

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
               // System.out.print("|");
            }
          
                   
               }
              
       
        
               // Print the bottom border
               for (int i = 0; i < boxWidth; i++) {
                   System.out.print("-");
               }
               System.out.println();
       
           }
           public static void main (String[] Args){
            String []arr={
                "good boy",
                "Strong boy",
                "classic boy"
            };
            Display_menu(arr);
           }
       
       
       }
       


