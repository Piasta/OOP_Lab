package pl.wsb.OOP_Lab;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class JsonService {
    private Path filePath;
    private final Path projectPath;
    private final Path jsonPath;

    public JsonService() {
        this.projectPath = Paths.get("").toAbsolutePath();
        this.jsonPath = getProjectPath().resolve("Json");
    }

    public void objectToJson(ClientService clientObject) {
        Path jsonPathClient = getJsonPath().resolve("Clients");
        setFilePath(jsonPathClient);
        Gson gson = new Gson();

        CreateDirIfNotExist(getJsonPath());
        CreateDirIfNotExist(jsonPathClient);

        try (FileWriter writer = new FileWriter(getFilePath().toString())) {
            System.out.println(gson.toJson(clientObject));
            gson.toJson(clientObject, writer);
            System.out.println("Client succesfully saved into: " + getFilePath());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private void CreateDirIfNotExist(Path dirToCheck) {
        if(!Files.exists(dirToCheck)) {
            new File(dirToCheck.toString()).mkdirs();
            System.out.println(dirToCheck + " - directory created.");
        }
    }

    public void setFilePath(Path filePath) {
        if(filePath.toString().toLowerCase().contains("json")) {
            if(filePath.toString().toLowerCase().contains("client")) {
                if(!filePath.toString().toLowerCase().contains(".json")) {
                    filePath = filePath.resolve("ClientsData.json");
                }
            }
            if(filePath.toString().toLowerCase().contains("warehouse")) {
                if(!filePath.toString().toLowerCase().contains(".json")){
                    filePath = filePath.resolve("WarehouseData.json");
                }
            }
        }
        this.filePath = filePath;
    }

    public Path getFilePath() {
        return this.filePath;
    }
    public Path getProjectPath() {
        return projectPath;
    }
    public Path getJsonPath() {
        return jsonPath;
    }
}
