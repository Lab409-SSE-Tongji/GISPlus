package com.rainlf.util;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/7/10.
 */
@Service
public class ExcelUtil {

    @Value("${file.server.local.url}")
    private String fileServerLocalUrl;

    @Value("${file.server.remote.url}")
    private String fileServerRemoteUrl;

    @Value("${file.server.export}")
    private String fileServerExport;


    public String writeToFile(String fileName, Workbook workbook) {
        try {
            FileOutputStream out = new FileOutputStream(fileServerLocalUrl+fileServerExport+fileName);
            workbook.write(out);
            out.close();
            return fileServerRemoteUrl+fileServerExport+fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
