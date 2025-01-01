package Classes;

public  abstract class users {
    private String name;
    private String email;
    private String address;
    private String contact;

public users(String name, String email, String address, String contact) {
    this.name = name;
    this.email = email;
    this.address = address;
    this.contact = contact;
}
public String getName() {
    return name;
}
public void setName(String name) {
    this.name = name;
}
public String getEmail() {
    return email;
}

public void setEmail(String email) { 
    this.email = email;
}    
public String getAddress() {
    return address;
}
public void setAddress(String address) {    
    this.address = address;
}
public String getContact() {    
    return contact;
}
public void setContact(String contact) {    
    this.contact = contact;     
}
public void display(){
    System.out.println("Name: "+name);
    System.out.println("Email: "+email);
    System.out.println("Address: "+address);
    System.out.println("Contact: "+contact);
}
}
