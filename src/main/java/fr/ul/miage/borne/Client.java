package fr.ul.miage.borne;
import java.util.List;

public class Client {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String mobileNumber;
    private String email;
    private String debitCardNumber;
    private List<String> licensePlates;


    public Client(String firstName, String lastName, String address, String mobileNumber, String email, String debitCardNumber, List<String> licensePlates) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.debitCardNumber = debitCardNumber;
        this.licensePlates = licensePlates;
    }

    public Client() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDebitCardNumber() {
        return debitCardNumber;
    }

    public void setDebitCardNumber(String debitCardNumber) {
        this.debitCardNumber = debitCardNumber;
    }

    public List<String> getLicensePlates() {
        return licensePlates;
    }
    public String getInfo() {
        return "Nom: " + lastName + ", Prénom: " + firstName + ", Téléphone: " + mobileNumber + ", Email: " + email;
    }

    public void setLicensePlates(List<String> licensePlates) {
        this.licensePlates = licensePlates;
    }
    
}
