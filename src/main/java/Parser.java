import entity.Column;
import entity.CsvFileDescription;
import entity.Row;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Виктор on 04.09.2017.
 */
public class Parser {

    private String folder;

    public Parser(String folder){
        this.folder = folder;
    }

    public File[] findCVS( String dirName){
        File dir = new File(dirName);
        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename)
            {
                return filename.endsWith(".csv");
            }
        } );

    }

    public static CsvFileDescription getDescription(String directory, String fileName){
        BufferedReader br = null;
        String pathToFile = directory + fileName;
        System.out.println("pathToFile - "+ pathToFile);

        CsvFileDescription fileDescription = new CsvFileDescription();
        fileDescription.setName(fileName);
        fileDescription.setPath(pathToFile);

        List<Column> columnList = new ArrayList<Column>();
        List<Row> rowList = new ArrayList<Row>();
        try {
            FileReader reader = new FileReader(pathToFile);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withQuote('"').withDelimiter(',').withSkipHeaderRecord(false).parse(reader);
            int countRow = 0;
            for (CSVRecord csvRecord: records) {
                if(csvRecord.getRecordNumber() == 1){
                    //header
                    setColumnToList(csvRecord,columnList);
                }else {
                   setRowToList(csvRecord,rowList,columnList,countRow);
                }
                countRow++;
            }

            fileDescription.setColumnList(columnList);
            fileDescription.setRowList(rowList);
            fileDescription.setCountRow(countRow);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("*** ENR ***");
        return fileDescription;
    }

    public static List<Column>  setColumnToList(CSVRecord csvRecord , List<Column> columnList){
        Iterator iterator = csvRecord.iterator();
        while (iterator.hasNext()){
            String value = (String)iterator.next();
            Column column = new Column();
            column.setColumnName(value);
            columnList.add(column);
            System.out.println("header - "+value);
        }
        return columnList;
    }

    public static List<Row>  setRowToList(CSVRecord csvRecord, List<Row> rowList, List<Column> columnList, int rowInt){
        Iterator iterator = csvRecord.iterator();
        Row row = new Row();
        String[] values = new String[columnList.size()];
        int size = 0;
        while (iterator.hasNext()){
            if(size > columnList.size()){
                System.out.println("SIZE ERROR - " + size+" row-"+rowInt);
                break;
            }
            String value = (String)iterator.next();
            if(value.equals("")){
                Column col = columnList.get(size);
                col.setCountEmptyRow(col.getCountEmptyRow()+1);
            }
            values[size] = value;
            size++;
        }
        row.setRowValues(values);
        rowList.add(row);
        return rowList;
    }


    public List<CsvFileDescription>  parse() {
        List<CsvFileDescription> fileDescriptions = new ArrayList<CsvFileDescription>();
        File[] listOfFiles =  findCVS(folder);
        if(listOfFiles!=null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    System.out.println("listOfFiles " + listOfFiles[i].getName());

                    CsvFileDescription description = getDescription(folder + "/", listOfFiles[i].getName());
                    fileDescriptions.add(description);

                } else if (listOfFiles[i].isDirectory()) {
                    System.out.println("Directory listOfFiles " + listOfFiles[i].getName());
                }
            }
        }
        return fileDescriptions;
    }
}
