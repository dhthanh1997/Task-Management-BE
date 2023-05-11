package com.ansv.taskmanagement.service;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayOutputStream;
import java.util.List;

public interface XlsxWriterService {
    <T> void write(List<T> data, ByteArrayOutputStream bos, List<String> columnTitles, Workbook workbook);

    <T> void writeFile(List<T> data, ByteArrayOutputStream bos, List<String> columnTitles, Workbook workbook, String title);
}
