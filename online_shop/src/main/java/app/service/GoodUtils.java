package app.service;

import javax.servlet.ServletContext;
import java.util.AbstractMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GoodUtils {

    public static Map<java.lang.String, Double> buildShopingMap(String[] textGoods){
        Map<String, Double> shopingMap = new HashMap<>();
        for(String record : textGoods) {
            Map.Entry<String, Double> pair = parseRecord(record);
            shopingMap.put(pair.getKey(), pair.getValue());
        }
        return shopingMap;
    }

    private static Map.Entry<String, Double> parseRecord(String record) {
        Map.Entry<String, Double> entry;
        Pattern serbParameters = Pattern.compile("(.+)\\s{1}\\(([0-9]+\\.[0-9]+)\\s\\$\\)");
        Matcher matcher = serbParameters.matcher(record);
        Double price = null;
        String product = null;
        while (matcher.find()) {
            product = matcher.group(1);
            price = Double.parseDouble(matcher.group(2));
        }
        if(product == null | price == null) {
           throw new NullPointerException("The purchase didn't take place");
        }
        entry = new AbstractMap.SimpleEntry<>(product, price);
        return entry;
    }

    public static Map<String, Double> buildGoodsMap(final ServletContext servletContext) {
        Map<String, Double> goodsMap = new HashMap<>();
        Enumeration<java.lang.String> goods = servletContext.getInitParameterNames();
        while (goods.hasMoreElements()) {
            String name = goods.nextElement();
            goodsMap.put(name, Double.parseDouble(servletContext.getInitParameter(name)));
        }
        return goodsMap;
    }
}
