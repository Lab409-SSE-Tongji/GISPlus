package com.rainlf.service.imp;

import com.rainlf.mongo.domain.WaterPipeDomain;
import com.rainlf.mongo.domain.WellDomain;
import com.rainlf.mongo.entity.WaterPipeLayer;
import com.rainlf.mongo.entity.WellLayer;
import com.rainlf.service.ExcelService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
                if (row.getCell(1).toString() == null) {
                    break;
                }
                try {
                    WellDomain wellDomain = new WellDomain(row.getCell(0).toString(),
                            row.getCell(1).toString(),
                            row.getCell(2).toString(),
                            row.getCell(3).toString());
                    wellDomains.add(wellDomain);
                } catch (Exception e) {
//                    System.out.println("文件微微不规范");
//                    e.printStackTrace();
                }
            }
            wellLayer.setWellDomains(wellDomains);
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        }

        return wellLayer;
    }

    @Override
    public WaterPipeLayer importWaterPipeLayerFile(MultipartFile file) {
        WaterPipeLayer waterPipeLayer = new WaterPipeLayer();
        List<WaterPipeDomain> waterPipeDomains = new ArrayList<>();
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            for (int rowNum=1; rowNum<=sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                try {
                    WaterPipeDomain waterPipeDomain = new WaterPipeDomain(row.getCell(0).toString(),
                            row.getCell(1).toString(),
                            row.getCell(2).toString(),
                            row.getCell(3).toString(),
                            row.getCell(4).toString(),
                            row.getCell(5).toString(),
                            row.getCell(6).toString());
                    waterPipeDomains.add(waterPipeDomain);
                } catch (Exception e) {
//                    System.out.println("文件微微不规范");
//                    e.printStackTrace();
                }
            }
            waterPipeLayer.setWaterPipeDomains(waterPipeDomains);
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }

        return waterPipeLayer;
    }

    @Override
    public Workbook exportWellLayerFile(WellLayer wellLayer) {
        Workbook workbook = workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("窨井盖");

        // 表头
        int index = 0;
        Row row = sheet.createRow(index);
        row.createCell(0).setCellValue("X");
        row.createCell(1).setCellValue("Y");
        row.createCell(2).setCellValue("Z");
        row.createCell(3).setCellValue("Status");

        // 内容
        List<WellDomain> wellDomains = wellLayer.getWellDomains();
        for (WellDomain wellDomain : wellDomains) {
            row = sheet.createRow(++index);
            row.createCell(0).setCellValue(wellDomain.getX());
            row.createCell(1).setCellValue(wellDomain.getY());
            row.createCell(2).setCellValue(wellDomain.getZ());
            row.createCell(3).setCellValue(wellDomain.getStatus());
        }

        return workbook;
    }

    @Override
    public Workbook exportWaterPipeLayerFile(WaterPipeLayer waterPipeLayer) {
        Workbook workbook = workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("下水管道");

        // 表头
        int index = 0;
        Row row = sheet.createRow(index);
        row.createCell(0).setCellValue("X1");
        row.createCell(1).setCellValue("Y1");
        row.createCell(2).setCellValue("Z1");
        row.createCell(3).setCellValue("X2");
        row.createCell(4).setCellValue("Y2");
        row.createCell(5).setCellValue("Z2");
        row.createCell(6).setCellValue("Status");

        // 内容
        List<WaterPipeDomain> waterPipeLayers = waterPipeLayer.getWaterPipeDomains();
        for (WaterPipeDomain wellDomains : waterPipeLayers) {
            row = sheet.createRow(++index);
            row.createCell(0).setCellValue(wellDomains.getX1());
            row.createCell(1).setCellValue(wellDomains.getY1());
            row.createCell(2).setCellValue(wellDomains.getZ1());
            row.createCell(3).setCellValue(wellDomains.getX2());
            row.createCell(4).setCellValue(wellDomains.getY2());
            row.createCell(5).setCellValue(wellDomains.getZ2());
            row.createCell(6).setCellValue(wellDomains.getStatus());
        }

        return workbook;
    }

}
