package Classes;

public class librant extends users {
    private String librantID;
   private String librantType;
   private String password;

    public librant(String name, String email, String address, String contact, String sexe,String librantID, String librantType, String password) {
        super(name, email, address, contact,sexe);
        this.librantID = librantID;
        this.librantType = librantType;
        this.password = password;

    }


       

    public String getLibrantID() {
        return librantID;
    }

    public void setLibrantID(String librantID) {
        this.librantID = librantID;
    }

    public String getLibrantType() {
        return librantType;
    }
    public String getLIbrantType(){
        return librantType;
    }
    public void setLibrantType(String librantType) {
        this.librantType = librantType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



         public void addBook(){

        }

         public  void removeBook(){

          }


          public void login(){

          }

          public void registerMember(){

          }

          public void processTransaction() {
            
          }





    public void display() {
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Address: " + getAddress());
        System.out.println("Contact: " + getContact());
        System.out.println("Librant ID: " + librantID);
        System.out.println("Librant Type: " + librantType);
        
    }

    public void add_record(){



    }

    public void delete_record(){

    }

    public void update_record(){

    }

    public void send_Notification(){

    }

    

}
