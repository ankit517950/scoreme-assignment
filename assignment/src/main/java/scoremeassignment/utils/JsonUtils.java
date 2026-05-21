package scoremeassignment.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import scoremeassignment.model.Instance;
import scoremeassignment.model.Result;

import java.io.File;

public class JsonUtils {

    private static final ObjectMapper mapper =
            new ObjectMapper();

    public static Instance readInstance(String path)
            throws Exception {

        return mapper.readValue(
                new File(path),
                Instance.class
        );
    }

    public static void writeResult(
            String path,
            Result result) throws Exception {

        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(path), result);
    }
}