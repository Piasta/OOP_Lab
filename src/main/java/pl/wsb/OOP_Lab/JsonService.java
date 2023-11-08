package pl.wsb.OOP_Lab;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;


public class JsonService {

    private String filePath;
    public void objectToJson(ClientService clientObject) {
        String filePath = "C:\\Users\\ppiasta\\Downloads\\OOP_Lab-master\\Clients.json";
        setFilePath(filePath);
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filePath)) {
            System.out.println(gson.toJson(clientObject));
            gson.toJson(clientObject, writer);
            System.out.println("Client succesfully saved into: " + getFilePath());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public void getDataFromJson(String dataForSearch, String dataSearched) {

    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }
}
