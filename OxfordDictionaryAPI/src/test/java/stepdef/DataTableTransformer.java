package stepdef;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import models.PathParameterConfig;
import models.ResponseMatcherConfig;

import java.util.Locale;

public class DataTableTransformer implements TypeRegistryConfigurer {

    @Override
    public Locale locale() {
        return Locale.ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry typeRegistry) {
        typeRegistry.defineDataTableType(DataTableType.entry(PathParameterConfig.class));
        typeRegistry.defineDataTableType(DataTableType.entry(ResponseMatcherConfig.class));
    }
}
