package services;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import models.EndpointConfig;
import models.YamlConfig;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Slf4j
public class EndpointService {
    private YamlConfig yamlConfig;

    private String webserviceName;

    /**
     * Load yaml file to the POJO of YamlConfig
     *
     * @throws IOException io exception
     */
    private void loadYamlFile() throws IOException {
        //  log.info("Load yaml file and convert it to POJO");
        Constructor constructor = new Constructor(YamlConfig.class);
        Yaml yaml = new Yaml(constructor);

        File file = new File("src/test/resources/test_properties.yml");
        CharSource source = Files.asCharSource(file, Charsets.UTF_8);
        yamlConfig = yaml.loadAs(source.read(), YamlConfig.class);
    }

    /**
     * Retrieve the base path of web services
     *
     * @param env            environment
     * @param webserviceName web services name
     * @return url string
     * @throws IOException io exception
     */
    public String retrieveEndpoint(String env, String webserviceName) throws IOException {
        //   log.info("Retrieve web services endpoint");
        loadYamlFile();

        Map<String, EndpointConfig> envMap = new HashMap<>(0);
        switch (env) {
            case "Dev":
                envMap = yamlConfig.getDev();
                break;
            case "Test":
                envMap = yamlConfig.getTest();
                break;
            case "Stress":
                envMap = yamlConfig.getStress();
                break;
            case "Local":
                envMap = yamlConfig.getLocal();
                break;
            default:
                //        log.error("Unknown environment!!!");
                break;
        }
        return envMap.get(webserviceName).getUrl();
    }
}
