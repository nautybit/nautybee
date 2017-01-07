package com.nautybit.nautybee.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Minutch on 16/1/14.
 */
@Slf4j
public class FileUtils {


    public static String readFileContent(String filePath) {

        if (StringUtils.isBlank(filePath)) {
            log.warn("readFileContent:filePath is empty.");
            return null;
        }

        StringBuffer content = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line =reader.readLine())!=null) {
                content.append(line);
            }

        } catch (FileNotFoundException e) {
            log.error("readFileContent:reader error.", e);
        } catch (IOException e) {
            log.error("readFileContent:reader content error.", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("readFileContent:close reader error.",e);
                }
            }
        }

        return content.toString();
    }
}
