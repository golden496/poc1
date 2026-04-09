package com.saucedemo.utilities;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.LogManager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReaderJSON {
    
    private static final Logger logger = LogManager.getLogger(DataReaderJSON.class);
    
    /**
     * Read JSON file and convert to List of HashMap
     * @param filePath - Path to JSON file
     * @return - List of data (each row is a HashMap)
     */
    public List<HashMap<String, String>> getJsonData(String filePath) {
        try {
            logger.info("📄 Reading JSON file: " + filePath);
            ObjectMapper mapper = new ObjectMapper();
            
            List<HashMap<String, String>> data = mapper.readValue(
                    new File(filePath),
                    new TypeReference<List<HashMap<String, String>>>() {}
            );
            
            logger.info("✅ Loaded " + data.size() + " test data records");
            return data;
            
        } catch (IOException e) {
            logger.error("❌ Error reading JSON file: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}