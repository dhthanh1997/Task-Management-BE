package com.ansv.taskmanagement.service.impl;

import com.ansv.taskmanagement.dto.excel.XlsxField;
import com.ansv.taskmanagement.service.XlsxWriterService;
import com.ansv.taskmanagement.util.excelDynamic.ExcelUtils;
import org.apache.poi.common.usermodel.HyperlinkType;
import com.ansv.taskmanagement.annotation.*;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.ansv.taskmanagement.util.excelDynamic.ExcelUtils.*;


@Service
public class XlsxWriterServiceImpl implements XlsxWriterService {

    private static final Logger logger = LoggerFactory.getLogger(XlsxWriterServiceImpl.class);

    @Override
    public <T> void write(List<T> data, ByteArrayOutputStream bos, List<String> columnTitles, Workbook workbook) {
        if (data.isEmpty()) {
            logger.error("No data received to write excel file..");
            return;
        }

        long start = System.currentTimeMillis();

        //      setting up the basic styles for the workbook
        Font boldFont = getBoldFont(workbook);
        Font genericFont = getGenericFont(workbook);
        CellStyle headerStyle = getLeftAlignedCellStyle(workbook, boldFont);
        CellStyle currencyStyle = setCurrencyCellStyle(workbook);
        CellStyle centerAlignedStyle = getCenterAlignedCellStyle(workbook);
        CellStyle genericStyle = getLeftAlignedCellStyle(workbook, genericFont);
        try {
            // using POJO class metadata for the sheet name
//            XlsxSheet annotationSheet = null;
//            Annotation[] annotations = data.get(0).getClass().getAnnotations();
//            for (Annotation anno : annotations) {
//                if (anno.annotationType().getSimpleName().equals(XlsxSheet.class)) {
//                    annotationSheet = (XlsxSheet) anno;
//                }
//            }
//            XlsxSheet annotationSheet = (XlsxSheet) data.get(0).getClass().getAnnotation(XlsxSheet.class);
            XlsxSheet annotationSheet = data.get(0).getClass().getAnnotation(XlsxSheet.class);
            String sheetName = annotationSheet.value();
            Sheet sheet = workbook.createSheet(sheetName);

            //  get the metadata for each field of the POJO class into a list
            List<XlsxField> xlsColumnFields = getFieldNamesForClass(data.get(0).getClass());

            int tempRowNo = 0;
            int recordBeginRowNo = 0;
            int recordEndRowNo = 0;

            //    set spreadsheet titles
            Row mainRow = sheet.createRow(tempRowNo);
            Cell columnTitleCell;

            for (int i = 0; i < columnTitles.size(); i++) {
                columnTitleCell = mainRow.createCell(i);
                columnTitleCell.setCellStyle(headerStyle);
                columnTitleCell.setCellValue(columnTitles.get(i));
            }
            recordEndRowNo++;

            //    get class of the passed dataset
            Class<?> clazz = data.get(0).getClass();
            //    looping the past dataset
            for (T record : data) {
                tempRowNo = recordEndRowNo;
                recordBeginRowNo = tempRowNo;
                mainRow = sheet.createRow(tempRowNo++);
                boolean isFirstValue;
                boolean isFirstRow;
                boolean isRowNoToDecrease = false;
                Method xlsMethod;
                Object xlsObjValue;
                ArrayList<Object> objValueList;

                //      get max size of the record if its multiple row
                int maxListSize = getMaxListSize(record, xlsColumnFields, clazz);


                //      looping through the fields of the current record
                for (XlsxField xlsColumnField : xlsColumnFields) {
                    //       writing a single field
                    if (!xlsColumnField.isAnArray() && !xlsColumnField.isComposite()) {
                        writeSingleFieldRow(mainRow, xlsColumnField, clazz, currencyStyle, centerAlignedStyle, genericStyle,
                                record, workbook);

                        //       overlooking the next field and adjusting the starting row
                        if (isNextColumnAnArray(xlsColumnFields, xlsColumnField, clazz, record)) {
                            isRowNoToDecrease = true;
                            tempRowNo = recordBeginRowNo + 1;
                        }

                        //       writing an single array field
                    } else if (xlsColumnField.isAnArray() && !xlsColumnField.isComposite()) {
                        xlsMethod = getMethod(clazz, xlsColumnField);
                        xlsObjValue = xlsMethod.invoke(record, (Object[]) null);
                        objValueList = (ArrayList<Object>) xlsObjValue;
                        isFirstValue = true;

                        //       looping through the items of the single array
                        for (Object objectValue : objValueList) {
                            Row childRow;
                            if (isFirstValue) {
                                childRow = mainRow;
                                writeArrayFieldRow(childRow, xlsColumnField, objectValue, currencyStyle, centerAlignedStyle, genericStyle, workbook);
                                isFirstValue = false;
                            } else if (isRowNoToDecrease) {
                                childRow = getOrCreateNextRow(sheet, tempRowNo++);
                                writeArrayFieldRow(childRow, xlsColumnField, objectValue, currencyStyle, centerAlignedStyle, genericStyle, workbook);
                                isRowNoToDecrease = false;
                            } else {
                                childRow = getOrCreateNextRow(sheet, tempRowNo++);
                                writeArrayFieldRow(childRow, xlsColumnField, objectValue, currencyStyle, centerAlignedStyle, genericStyle, workbook);
                            }
                        }

                        //      overlooking the next field and adjusting the starting row
                        if (isNextColumnAnArray(xlsColumnFields, xlsColumnField, clazz, record)) {
                            isRowNoToDecrease = true;
                            tempRowNo = recordBeginRowNo + 1;
                        }

                        //      writing a composite array field
                    } else if (xlsColumnField.isAnArray() && xlsColumnField.isComposite()) {
                        xlsMethod = getMethod(clazz, xlsColumnField);
                        xlsObjValue = xlsMethod.invoke(record, (Object[]) null);
                        objValueList = (ArrayList<Object>) xlsObjValue;
                        isFirstRow = true;

                        //       looping through the items of the composite array
                        for (Object objectValue : objValueList) {
                            Row childRow;
                            List<XlsxField> xlsCompositeColumnFields = getFieldNamesForClass(objectValue.getClass());
                            if (isFirstRow) {
                                childRow = mainRow;
                                for (XlsxField xlsCompositeColumnField : xlsCompositeColumnFields) {
                                    writeCompositeFieldRow(objectValue, xlsCompositeColumnField, childRow, currencyStyle, centerAlignedStyle, genericStyle, workbook);
                                }
                                isFirstRow = false;
                            } else if (isRowNoToDecrease) {
                                childRow = getOrCreateNextRow(sheet, tempRowNo++);
                                for (XlsxField xlsCompositeColumnField : xlsCompositeColumnFields) {
                                    writeCompositeFieldRow(objectValue, xlsCompositeColumnField, childRow, currencyStyle, centerAlignedStyle, genericStyle, workbook);
                                }
                                isRowNoToDecrease = false;
                            } else {
                                childRow = getOrCreateNextRow(sheet, tempRowNo++);
                                for (XlsxField xlsCompositeColumnField : xlsCompositeColumnFields) {
                                    writeCompositeFieldRow(objectValue, xlsCompositeColumnField, childRow, currencyStyle, centerAlignedStyle, genericStyle, workbook);
                                }
                            }
                        }

                        //       overlooking the next field and adjusting the starting row
                        if (isNextColumnAnArray(xlsColumnFields, xlsColumnField, clazz, record)) {
                            isRowNoToDecrease = true;
                            tempRowNo = recordBeginRowNo + 1;
                        }
                    }
                }

                //      adjusting the ending row number for the current record
                recordEndRowNo = maxListSize + recordBeginRowNo;
            }

            // auto sizing the columns of the whole sheet
            autoSizeColumns(sheet, xlsColumnFields.size());
            workbook.write(bos);
            logger.info("Xls file generated in [{}] seconds", processTime(start));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

}
