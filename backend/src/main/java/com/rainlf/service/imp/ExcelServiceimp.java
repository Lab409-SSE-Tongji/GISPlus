package com.rainlf.service.imp;

import com.rainlf.mongo.domain.WellDomain;
import com.rainlf.mongo.entity.WaterPipeLayer;
import com.rainlf.mongo.entity.WellLayer;
import com.rainlf.mongo.repository.MongoWellLayerRepository;
import com.rainlf.service.ExcelService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/7.
 */
@Service
public class ExcelServiceimp implements ExcelService {

    @Override
    public WellLayer importWellLayerFile(MultipartFile file)  {
        WellLayer wellLayer = new WellLayer();
        List<WellDomain> wellDomains = new ArrayList<>();
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            for (int rowNum=1; rowNum<=sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                WellDomain wellDomain = new WellDomain(row.getCell(0).toString(),
                        row.getCell(1).toString(),
                        row.getCell(2).toString(),
                        row.getCell(3).toString());
                wellDomains.add(wellDomain);
            }
            wellLayer.setWellDomains(wellDomains);
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        }

        return wellLayer;
    }

    @Override
    public WaterPipeLayer importWaterPipeLayerFile(MultipartFile file) {
        return null;
    }

}
