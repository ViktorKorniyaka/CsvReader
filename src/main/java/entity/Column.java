package entity;

/**
 * Created by Виктор on 02.09.2017.
 */
public class Column {
    private String columnName;
    private int countEmptyRow;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getCountEmptyRow() {
        return countEmptyRow;
    }

    public void setCountEmptyRow(int countEmptyRow) {
        this.countEmptyRow = countEmptyRow;
    }
}
