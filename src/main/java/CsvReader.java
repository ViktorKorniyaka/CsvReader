import entity.Column;
import entity.CsvFileDescription;
import entity.Row;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Виктор on 02.09.2017.
 */
public class CsvReader {
    private String folder;

    public CsvReader(String folder){
        this.folder = folder;
    }

    public List<CsvFileDescription> getCsvFileDescription(){
        List<CsvFileDescription> fileDescriptions = new ArrayList<CsvFileDescription>();

        File[] listOfFiles =  findCVS(folder);
        if(listOfFiles!=null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    System.out.println("listOfFiles2 " + listOfFiles[i].getName());

                    CsvFileDescription description = getDescription(folder + "/", listOfFiles[i].getName());
                    fileDescriptions.add(description);

                } else if (listOfFiles[i].isDirectory()) {
                    System.out.println("Directory listOfFiles2 " + listOfFiles[i].getName());
                }
            }
        }

        return fileDescriptions;
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
        String line = "";
        String cvsSplitBy = ",";
        String pathToFile = directory + fileName;
        System.out.println("pathToFile - "+ pathToFile);

        CsvFileDescription fileDescription = new CsvFileDescription();
        fileDescription.setName(fileName);
        fileDescription.setPath(pathToFile);

        List<Column> columnList = new ArrayList<Column>();
        List<Row> rowList = new ArrayList<Row>();
        try {

            br = new BufferedReader(new FileReader(pathToFile));
            int countRow = 0;
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] values = line.split(cvsSplitBy);

                if(countRow == 0){
                    fileDescription.setColumnList(setColumnToList(values,columnList));
                    fileDescription.setRowList(setRowToList(values, rowList,columnList, countRow));
                }else {
                    fileDescription.setRowList(setRowToList(values,rowList,columnList, countRow));
                }
                countRow++;
            }
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

    public static List<Column>  setColumnToList(String[] values, List<Column> columnList){
        int countColumn = values.length;
        for(int i = 0 ; i < countColumn; i++){
            Column column = new Column();
            column.setColumnName(values[i]);
            columnList.add(column);
        }
        return columnList;
    }

    public static List<Row>  setRowToList(String[] values, List<Row> rowList, List<Column> columnList, int rowInt){
        int countColumn = values.length;
        Row row = new Row();
        for(int i = 0 ; i < countColumn; i++){
            if(values[i].equals("")){
                Column col = columnList.get(i);
                col.setCountEmptyRow(col.getCountEmptyRow()+1);
            }
            System.out.println(columnList.get(i).getColumnName()+ "rowInt " + rowInt);
            System.out.println( "values "+  values[i] );
        }
        row.setRowValues(values);
        rowList.add(row);
        return rowList;
    }
}
