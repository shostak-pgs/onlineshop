package app.servlets;

import app.service.GoodUtils;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class PrintCheckServlet extends HttpServlet {
    private static final String USER_NAME = "name";
    private static final String GOODS = "good";

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        String userName = (String) req.getSession().getAttribute(USER_NAME);
        PrintWriter writer = resp.getWriter();
        double totalPrice = 0;
        String[] stringGoods = req.getParameterValues(GOODS);
        Map<String,Double> shopingMap = GoodUtils.buildShopingMap(stringGoods);

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
                        + "       <h3>Dear " + userName + ", your order:</h3>"
                        + "     </div>"
                        + "     <div id=\"formStyle\"> ");
        int i = 1;
        for (Map.Entry<String, Double> pair : shopingMap.entrySet()) {
            writer.printf("<p>%d) %s %.2f $</p>\n", i, pair.getKey(), pair.getValue());
            i += 1;
            totalPrice += pair.getValue();
        }
        writer.printf("<p>Total: $ %.2f </p>\n", totalPrice);
        writer.println(
                        "     </div>"
                        + "   </body>"
                        + " </html>");
    }
}
