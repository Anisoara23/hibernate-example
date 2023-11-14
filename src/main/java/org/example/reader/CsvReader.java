package org.example.reader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvReader implements Reader {

    private final String csvFile;

    public CsvReader(String csvFile) {
        this.csvFile = csvFile;
    }

    @Override
    public List<List<String>> readData() {
        List<List<String>> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFile))) {
            String[] values;

            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }

            return records;
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
}
