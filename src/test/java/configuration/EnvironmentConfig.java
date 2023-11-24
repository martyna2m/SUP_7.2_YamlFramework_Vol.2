package configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class EnvironmentConfig {
    static YamlReader yamlReader = new YamlReader();
    static Logger log = LoggerFactory.getLogger(testbase.TestBase.class);
    static Map<String, Object> properties = yamlReader.readYamlFile("src/test/resources/config.yaml");


    public static EnvironmentConfig getInstance() {
        initAppEnv();
        return EnvironmentConfig.EnvironmentConfigSingleton.INSTANCE;
    }


    private static void initAppEnv() {
        setActiveEnvironment(properties);
    }


    public static Environment setActiveEnvironment(Map<String, Object> data) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Environment activeEnvironment = null;
        Map<String, Object> environments = (Map<String, Object>) data.get("environment");

        for (Map.Entry<String, Object> entry : environments.entrySet()) {
            Map<String, Object> envProperties = (Map<String, Object>) entry.getValue();
            Boolean isActive = (Boolean) envProperties.get("active");
            if (isActive != null && isActive) {
                envProperties.forEach((key, value) -> {
                    if (!key.equals("active")) {
                        System.setProperty(key, value.toString());
                    }
                });

                Environment environment = mapper.convertValue(envProperties, Environment.class);
                System.out.println("Active Environment: " + environment.getEnvName());
                log.debug("Active Environment: " + environment.getEnvName());
                activeEnvironment = environment;
                break;
            }
        }
        if (activeEnvironment == null){
            System.out.println("Active environment not found. Check configuration file.");
        }
        return activeEnvironment;
    }


    private static class EnvironmentConfigSingleton {
        private static final EnvironmentConfig INSTANCE = new EnvironmentConfig();

    }
}





