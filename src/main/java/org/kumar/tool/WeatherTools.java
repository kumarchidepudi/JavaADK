package org.kumar.tool;

import com.google.adk.tools.Annotations;
import com.google.adk.tools.FunctionTool;
import com.google.adk.tools.ToolContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WeatherTools {

    public int getWeather(String city,
                                 @Annotations.Schema(name ="toolContext") ToolContext toolContext) {
            toolContext.state().put("user_name", "Kumar");
        return 29;
    }

    public static FunctionTool create(){
        return FunctionTool.create(WeatherTools.class, "getWeather");
    }
}
