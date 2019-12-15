package app.servlets;

import app.service.GoodUtils;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class GoodsSelectionServlet extends HttpServlet {
    private static final String USER_NAME = "name";

    private Map<String, Double> goodsMap;

    @Override
    public void init(final ServletConfig config) throws ServletException {
        final ServletContext servletContext = config.getServletContext();
        goodsMap = GoodUtils.buildGoodsMap(servletContext);
        super.init(config);
    }

    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        String userName = request.getParameter(USER_NAME);
        HttpSession session = request.getSession(true);
        session.setAttribute(USER_NAME, userName);
        PrintWriter writer = response.getWriter();
        writer.println(
                "<!DOCTYPE html>"
                        + " <html lang=\"en\">"
                        + "  <head>"
                        + "    <meta charset=\"UTF-8\">"
                        + "    <title>Online Shop</title>"
                        + "       <style type=\"text/css\">"
                        + "         #greetingStyle {"
                        + "           margin-left: 10%;"
                        + "           margin-right: 10%;"
                        + "           background: #fc0;"
                        + "           padding: 10px;"
                        + "           text-align: center;"
                        + "         }"
                        + "       </style>"
                        + "       <style type=\"text/css\">"
                        + "         #formStyle {"
                        + "           margin-left: 10%;"
                        + "           margin-right: 10%;"
                        + "           background: #01DF01;"
                        + "           padding: 10px;"
                        + "           text-align: center;"
                        + "         }"
                        + "       </style>"
                        + "   </head>"
                        + "   <body>"
                        + "     <div id=\"greetingStyle\">"
                        + "       <h3>Hello " + userName + "!</h3>"
                        + "     </div>"
                        + "     <div id=\"formStyle\"> "
                        + "       <p>Make your order</p>"
                        + "       <form action=\"printCheckServlet\" method=\"post\">"
                        + "         <p><select name=\"good\" multiple>");

        for (Map.Entry<String, Double> pair : goodsMap.entrySet()) {
            writer.printf("<option> %s (%s $)</option><br/>\n", pair.getKey(), pair.getValue());
        }
        writer.println(
                "        </select></p>"
                        + "         <p><input type=\"submit\" value=\"Submit\"></p>"
                        + "        </form>"
                        + "      </div>"
                        + "   </body>"
                        + " </html>");
    }
}
