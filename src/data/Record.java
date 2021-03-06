package data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Record implements Serializable {
    private String address; // Сайт
    private String login;
    private String password;
    private Date date;

    public Record() {}

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}

    public String getLogin() {return login;}

    public void setLogin(String login) {this.login = login;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public Date getData() {return date;}

    public void setDate(Date date) {this.date = date;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return address.equals(record.address) && login.equals(record.login) && password.equals(record.password) && date.equals(record.date);
    }

    @Override
    public int hashCode() {return Objects.hash(address, login, password, date);}

    @Override
    public String toString() {
        return "Address is: " + address + ", Login is: " + login + ", password is: " + password + ", date is: " + date;
    }
}
