<%@page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,  initial-scale=1">
<body>
    <h2>CSV Reader!</h2>
    <br>If you want view csv files
    <br>send get request to /CsvReader whith param ?folder=pathToFolder
    <br>example
    <br><b>http://localhost:8080/CsvReader?folder=D:/TASK</b>

<br>

    <c:forEach items="${descriptionList}" var="csv">
        <br>Path to file: ${csv.path}
        <br>File name: ${csv.name}
        <br>Count of ROW: ${csv.countRow}
        <br>
        <c:forEach items="${csv.columnList}" var="column">
            In column <b><c:out value= " ${column.columnName}"/></b>
            Empty values are <c:out value= " ${column.countEmptyRow}"/><br>
        </c:forEach>
        <table>
                <tr>
                    <td style="width: auto">
                         <c:forEach items="${csv.rowList}" var="row">
                            <br>
                            <c:forEach items="${row.rowValues}" var="rowStr">
                                <c:out value= "${rowStr}"/>
                            </c:forEach>
                            <br>
                        </c:forEach>
                    </td>
                </tr>

        </table>
    </c:forEach>
<br>
<b>${csvFilesIsEmpty}</b>
</body>
</html>
