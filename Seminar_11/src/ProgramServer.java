import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

class Contact implements Serializable {
int cod;
String nume;
private String telefon;

    public Contact(int cod, String nume, String telefon) {
        this.cod = cod;
        this.nume = nume;
        this.telefon = telefon;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "cod=" + cod +
                ", nume='" + nume + '\'' +
                ", telefon='" + telefon + '\'' +
                '}';
    }

}

public class ProgramServer {
    static final String DB_URL="jdbc:sqlite:contacte.db";
    static final int SERVER_PORT=9876;
    static List<Contact> citireContacte(){
        var rezultat=new ArrayList<Contact>();
        try(var conexiune= DriverManager.getConnection(DB_URL);
        var comanda=conexiune.createStatement();
        var cursor=comanda.executeQuery("SELECT * FROM Contacte");){
            while(cursor.next()){
                rezultat.add(new Contact(cursor.getInt(1), cursor.getString(2), cursor.getString(3) ));
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        return rezultat;
    }
    public static void main(String[] args) {
        var contacte=citireContacte();
        //contacte.forEach(System.out::println);

        try(var serverSoket=new ServerSocket(SERVER_PORT)){
            while(true){
                try(var soket=serverSoket.accept();
                    var in=new ObjectInputStream(soket.getInputStream());
                    var out=new ObjectOutputStream(soket.getOutputStream());
                    ){
                    System.out.println("SRV:Avem o conexiune.");
                    var mesaj=in.readObject();
                    System.out.println("SRV: Am primit "+mesaj);
                    out.writeObject(contacte);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

class ProgramClient{
    public static void main(String[] args) throws Exception{
        try(var socket=new Socket("localhost",ProgramServer.SERVER_PORT);
        var out=new ObjectOutputStream(socket.getOutputStream());
        var in=new ObjectInputStream(socket.getInputStream());){
            System.out.println("CL:Avem o conexiune.");
            out.writeObject("Ion");
            var contacte=(List<Contact>)in.readObject();
            contacte.forEach(System.out::println);
        }
    }
}