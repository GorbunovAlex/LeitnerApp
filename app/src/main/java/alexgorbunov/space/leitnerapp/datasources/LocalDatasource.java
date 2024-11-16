package alexgorbunov.space.leitnerapp.datasources;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import alexgorbunov.space.leitnerapp.models.Card;

public class LocalDatasource {
    private ObjectMapper objectMapper;

    public void JsonDataSource() {
        this.objectMapper = new ObjectMapper();
    }
    private <T> T readJsonFile(String filePath, Class<T> valueType) throws IOException {
        File file = new File(filePath);
        return objectMapper.readValue(file, valueType);
    }

    public List<Card> readCards() {}
}
