package Classes;
public class librant extends users {

    private String librantID;
    private String librantType;
    private String password;

    public librant(String name, String email, String address, String contact, String sex, String librantID, String
     librantType, String password) {
        super(name, email, address, contact, sex);
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

    public String getLIbrantType() {
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



    public void processTransaction() {

    }

   

  
    }


