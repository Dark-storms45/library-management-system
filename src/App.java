import utility.Utilities.*;
import java.util.Scanner;
import  utility.login_utils.login;
import  utility.login_utils.register;
public class App {

     /*Main menu function */
    public void main_menu(){
        String [] main_menu={
        "1.login",
        "2.Register",
        "3.Exit"};
        Display.Display_menu(main_menu);
        Scanner input = new Scanner(System.in); 
        System.out.println("Enter your choice:");
        int choice = input.nextInt();

        if (choice==1){
            login();
        }
        else if (choice==2){
            register();
        }
        else if (choice==3){
            System.exit(0);
        }
        else{
            System.out.println("Invalid choice");
        }


    }
    public static void main(String[] args) throws Exception {
        
    }
}
