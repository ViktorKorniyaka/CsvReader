import com.sun.net.httpserver.HttpServer;
import entity.CsvFileDescription;

import java.io.IOException;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.List;

/**
 * Created by Виктор on 02.09.2017.
 */
public class CsvReaderServlet extends   HttpServlet{
    public void doGet(HttpServletRequest req,HttpServletResponse res)
            throws ServletException,IOException
    {
        String folder= req.getParameter("folder");
        System.out.println("folder " + folder);

       // CsvReader csvReader = new CsvReader(folder);
        //List<CsvFileDescription> descriptionList = csvReader.getCsvFileDescription();
        Parser parser = new Parser(folder);
        List<CsvFileDescription> descriptionList = parser.parse();

        req.setAttribute("descriptionList", descriptionList);

        if(descriptionList.size() == 0){
            req.setAttribute("csvFilesIsEmpty", "Not any files or wrong path");
        }

        req.getRequestDispatcher("index.jsp").forward(req, res);



    }

}
