package configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class YamlReader {
    private final String filePath = "src/test/resources/config.yaml";



    public Map<String, Object> readYamlFile() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Map<String, Object> data = null;

        try {
            File yamlFile = new File(filePath);
            data = mapper.readValue(yamlFile, Map.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


}


