package com.example.mp.common;


import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.example.mp.dto.KosdaqStockDto;
import com.example.mp.dto.KospiStockDto;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class StockUtils {

    public List<KospiStockDto> getKospiStockList(String url) {
        final String stockList = url;
        Connection conn = Jsoup.connect(stockList);
        try {
            Document document = conn.get();
            return getKopiStockList(document);
        } catch (IOException ignored) {
        }
        return null;
    }

    public List<KospiStockDto> getKopiStockList(Document document) {
        Elements kosPiTable = document.select("table.type_2 tbody tr");
        List<KospiStockDto> list = new ArrayList<>();
        for (Element element : kosPiTable) {
            if (element.attr("onmouseover").isEmpty()) {
                continue;
            }
            list.add(createkospiStockDto(element.select("td")));
        }
        return list;
    }

    public KospiStockDto createkospiStockDto(Elements td) {
        KospiStockDto kospiStockDto = KospiStockDto.builder().build();
        Class<?> clazz = kospiStockDto.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (int i = 0; i < td.size() && i < fields.length;  i++) {
            String text;
            if (td.get(i).select(".center a").attr("href").isEmpty()) {
                text = td.get(i).text();
            } else {
                text = "https://finance.naver.com" + td.get(i).select(".center a").attr("href");
            }
            fields[i].setAccessible(true);
            try {
                fields[i].set(kospiStockDto, text);
            } catch (Exception ignored) {
            }
        }
        return kospiStockDto;
    }

    // ------------------------ Kosdaq -------------------------

    public List<KosdaqStockDto> getKosdaqStockList(String url) {
        final String stockList = url;
        Connection conn = Jsoup.connect(stockList);
        try {
            Document document = conn.get();
            return getKosdaqStockList(document);
        } catch (IOException ignored) {
        }
        return null;
    }

    public List<KosdaqStockDto> getKosdaqStockList(Document document) {
        Elements kosDaqTable = document.select("table.type_2 tbody tr");
        List<KosdaqStockDto> list = new ArrayList<>();
        for (Element element : kosDaqTable) {
            if (element.attr("onmouseover").isEmpty()) {
                continue;
            }
            list.add(createkosdaqStockDto(element.select("td")));
        }
        return list;
    }

    public KosdaqStockDto createkosdaqStockDto(Elements td) {
        KosdaqStockDto kosdaqStockDto = KosdaqStockDto.builder().build();
        Class<?> clazz = kosdaqStockDto.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (int i = 0; i < td.size() && i < fields.length;  i++) {
            String text;
//            if (td.get(i).select(".center a").attr("href").isEmpty()) {
//                text = td.get(i).text();
//            } else {
//                text = "https://finance.naver.com" + td.get(i).select(".center a").attr("href");
//            }
            text = td.get(i).text();
            fields[i].setAccessible(true);
            try {
                fields[i].set(kosdaqStockDto, text);
            } catch (Exception ignored) {
            }
        }

        return kosdaqStockDto;
    }
}
