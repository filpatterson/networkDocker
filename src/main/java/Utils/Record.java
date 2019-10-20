package Utils;

public class Record extends BaseData {

    private String id;
    private String first_name;
    private String last_name;
    private String bitcoin_address;
    private String email;
    private String gender;
    private String ip_address;
    private String card_number;
    private String card_balance;
    private String card_currency;
    private String organization;
    private String full_name;
    private String employee_id;
    private String username;
    private String created_account_data;

    String getCreated_account_data() {
        return created_account_data;
    }

    void setCreated_account_data(String created_account_data) {
        this.created_account_data = created_account_data;
    }

    String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    String getFirst_name() {
        return first_name;
    }

    void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    String getLast_name() {
        return last_name;
    }

    void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    String getBitcoin_address() {
        return bitcoin_address;
    }

    public void setBitcoin_address(String bitcoin_address) {
        this.bitcoin_address = bitcoin_address;
    }

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    void setGender(String gender) {
        this.gender = gender;
    }

    String getIp_address() {
        return ip_address;
    }

    void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getCard_balance() {
        return card_balance;
    }

    public void setCard_balance(String card_balance) {
        this.card_balance = card_balance;
    }

    public String getCard_currency() {
        return card_currency;
    }

    public void setCard_currency(String card_currency) {
        this.card_currency = card_currency;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id='" + id + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", bitcoin_address='" + bitcoin_address + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", ip_address='" + ip_address + '\'' +
                ", card_number='" + card_number + '\'' +
                ", card_balance='" + card_balance + '\'' +
                ", card_currency='" + card_currency + '\'' +
                ", organization='" + organization + '\'' +
                ", full_name='" + full_name + '\'' +
                ", employee_id='" + employee_id + '\'' +
                ", username='" + username + '\'' +
                ", created_account_data='" + created_account_data + '\'' +
                '}';
    }
}

