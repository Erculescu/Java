import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Long.compare;

public class Main {
    public static Connection connect(){
        Connection con=null;
        String url="jdbc:sqlite:Date\\baza.db";
        try{
            con= DriverManager.getConnection(url);
            System.out.println("Conectat");
        }catch (SQLException e){
            System.out.println("Eroare la conectare: "+e.getMessage());
        }
        return con;
    }

    public static void creareTabele(){
        String comanda="CREATE TABLE IF NOT EXISTS Aventuri("+"cod_aventura INTEGER PRIMARY KEY,"+"denumire TEXT,"+"tarif REAL,"+"locuri_disponibile INTEGER"+");";
        try(Connection conn=connect(); Statement statement=conn.createStatement()){
            statement.execute(comanda);
            System.out.println("Tabela creata");
        }catch (SQLException e){
            System.out.println("Eroare la crearea tabelei "+e.getMessage());
        }
    }
    public static  void inserareAventura(int cod_aventura,String denumire,double tarif,int locuri_disponibile){
        String comanda="INSERT INTO Aventuri(cod_aventura,denumire,tarif,locuri_disponibile) VALUES(?,?,?,?)";
        try(Connection conn=connect(); PreparedStatement preparedStatement= connect().prepareStatement(comanda)){
            preparedStatement.setInt(1,cod_aventura);
            preparedStatement.setString(2,denumire);
            preparedStatement.setDouble(3,tarif);
            preparedStatement.setInt(4,locuri_disponibile);
            preparedStatement.executeUpdate();
            System.out.println("Aventura inserata");
        }catch (SQLException e){
            System.out.println("Eroare la inserarea aventurii "+e.getMessage());
        }
    }

    public static Map<Integer,Aventura> aventuri=new HashMap<>();
    public static void main(String[] args)throws  Exception {
        try(FileReader fisier=new FileReader("Date\\aventuri.json")){
            var javentura=new JSONArray(new JSONTokener(fisier));
            for(int i=0;i< javentura.length();i++){
                var javent=javentura.getJSONObject(i);
                int cod_aventura=javent.getInt("cod_aventura");
                String denumire=javent.getString("denumire");
                double tarif=javent.getDouble("tarif");
                int locuri_disponibile=javent.getInt("locuri_disponibile");
                aventuri.put(cod_aventura,new Aventura(cod_aventura,denumire,tarif,locuri_disponibile));
            }
        }catch (IOException e){
            System.out.println("Eroare la citirea fisierului .json "+e.getMessage());
        }
        try(BufferedReader br=new BufferedReader(new FileReader("Date\\rezervari.txt"))){
            String linie;
            while((linie=br.readLine())!=null){
                String[] tokens=linie.split(",");
                int id_rez=Integer.parseInt(tokens[0]);
                int cod_aventura=Integer.parseInt(tokens[1]);
                int nr_locuri=Integer.parseInt(tokens[2]);
                aventuri.get(cod_aventura).addRezervare(new rezervare(id_rez,cod_aventura,nr_locuri));
            }
        }catch (IOException e){
            System.out.println("Eroare la citirea fisierului .txt "+e.getMessage());
        }
        System.out.println("Cerinta: Sa se afiseze lista aventurilo care au un numar de locuri disponibile mai mare sau egal cu 20");
       for(Map.Entry<Integer,Aventura> aventura: aventuri.entrySet()){
           if(aventura.getValue().locuri_disponibile>=20){
               System.out.println(aventura.getValue());
           }
       }
        System.out.println("Cerinta: Sa se afiseze lista aventurilor care in urma rezervarilor mai au cel putin 5 locuri disponibile");
       for(Map.Entry<Integer,Aventura> aventura: aventuri.entrySet()){
           if(aventura.getValue().locuri_ramase>=5){
               System.out.println(aventura.getValue().cod_aventura+" "+aventura.getValue().denumire+" "+aventura.getValue().locuri_ramase);
           }
       }
        System.out.println("Cerinta: Sa se salveze in fisierul venituri.txt valoarea obtinuta din fiecare activitate");
       try(BufferedWriter bw=new BufferedWriter(new FileWriter("Date\\venituri.txt"))){
           for(Map.Entry<Integer,Aventura> aventura: aventuri.entrySet()){
               bw.write(aventura.getValue().denumire+" "+(aventura.getValue().locuri_disponibile-aventura.getValue().locuri_ramase)+" "+aventura.getValue().tarif*(aventura.getValue().locuri_disponibile-aventura.getValue().locuri_ramase));
               bw.newLine();
           }
       }catch (IOException e){
           System.out.println("Eroare la scrierea fisierului .txt "+e.getMessage());
       }
        System.out.println("Cerinta: Sa se implementeze functionalitatile TCP/IP");
       final int PORT=3929;
       new Thread(()->{
           try(
                   var server=new ServerSocket(PORT);
                   var sokcet=server.accept();
                   var in=new BufferedReader(new InputStreamReader(sokcet.getInputStream()));
                   var out=new PrintWriter(sokcet.getOutputStream(),true);
                   ){
               String denumire=in.readLine();
               System.out.println("SERVER: AM PRIMIT "+denumire);
               for(Map.Entry<Integer,Aventura> aventura: aventuri.entrySet()){
                   if(aventura.getValue().denumire.equals(denumire)){
                       out.println(aventura.getValue().locuri_ramase);
                   }
               }
           }catch (Exception e){
               e.printStackTrace();
           }
       }
       ).start();
       Thread.sleep(500);
       try(
               var socket=new Socket("localhost",PORT);
               var out=new PrintWriter(socket.getOutputStream(),true);
               var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
               ){
           out.println("TIROLIANA");
           System.out.println("CLIENT: AM PRIMIT "+in.readLine());
       }catch (Exception e){
           e.printStackTrace();
       }
       creareTabele();
       for(Map.Entry<Integer,Aventura> aventura:aventuri.entrySet()){
           inserareAventura(aventura.getValue().cod_aventura,aventura.getValue().denumire,aventura.getValue().tarif,aventura.getValue().locuri_disponibile);
       }
    }
}