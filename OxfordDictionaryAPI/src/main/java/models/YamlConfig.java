package models;

        import lombok.Getter;
        import lombok.Setter;

        import java.util.Map;


@Getter
@Setter
public class YamlConfig {

    private Map<String, EndpointConfig> dev;
    private Map<String, EndpointConfig> test;
    private Map<String, EndpointConfig> stress;
    private Map<String, EndpointConfig> local;
}
