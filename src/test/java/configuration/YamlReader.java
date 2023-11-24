package configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class YamlReader {


    public Map<String, Object> readYamlFile(String filePath) {
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


