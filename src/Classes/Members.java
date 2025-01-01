package Classes;

public class Members extends users{
    private String membershipType;
    private String membershipDate;
    private String membershipExpiry;
    private String membershipStatus;
    private String membershipID;
    private String membershipFee;
    private String membershipPaymentStatus;

    public Members(String name, String email, String address, String contact, String sexe,String membershipType, String membershipDate, String membershipExpiry, String membershipStatus, String membershipID, String membershipFee, String membershipPaymentStatus) {
        super(name, email, address, contact,sexe);
        this.membershipType = membershipType;
        this.membershipDate = membershipDate;
        this.membershipExpiry = membershipExpiry;
        this.membershipStatus = membershipStatus;
        this.membershipID = membershipID;
        this.membershipFee = membershipFee;
        this.membershipPaymentStatus = membershipPaymentStatus;
    }


    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public String getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(String membershipDate) {
        this.membershipDate = membershipDate;
    }

    public String getMembershipExpiry() {
        return membershipExpiry;
    }

    public void setMembershipExpiry(String membershipExpiry) {
        this.membershipExpiry = membershipExpiry;
    }

    public String getMembershipStatus() {
        return membershipStatus;
    }

    public void setMembershipStatus(String membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    public String getMembershipID() {
        return membershipID;
    }

    public void setMembershipID(String membershipID) {
        this.membershipID = membershipID;
    }

    public String getMembershipFee() {
        return membershipFee;
    }

    public void setMembershipFee(String membershipFee) {
        this.membershipFee = membershipFee;
    }

    public String getMembershipPaymentStatus() {
        return membershipPaymentStatus;
    }

    public void setMembershipPaymentStatus(String membershipPaymentStatus) {
        this.membershipPaymentStatus = membershipPaymentStatus;
    }

}
