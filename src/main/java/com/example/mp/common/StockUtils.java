package com.example.mp.common;


import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.example.mp.dto.KorStockDto;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class StockUtils {

    public List<KorStockDto> getKorStockList(String url) {
        final String stockList = url;
        Connection conn = Jsoup.connect(stockList);
        try {
            Document document = conn.get();
            return getKorStockList(document);
        } catch (IOException ignored) {
        }
        return null;
    }

    public List<KorStockDto> getKorStockList(Document document) {
        Elements kosPiTable = document.select("table.type_2 tbody tr");
        List<KorStockDto> list = new ArrayList<>();
        for (Element element : kosPiTable) {
            if (element.attr("onmouseover").isEmpty()) {
                continue;
            }
            list.add(createkorStockDto(element.select("td")));
        }
        return list;
    }

    public KorStockDto createkorStockDto(Elements td) {
        KorStockDto korStockDto = KorStockDto.builder().build();
        Class<?> clazz = korStockDto.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (int i = 0; i < td.size(); i++) {
            String text;
            if(td.get(i).select(".center a").attr("href").isEmpty()){
                text = td.get(i).text();
            }else{
                text = "https://finance.naver.com" + td.get(i).select(".center a").attr("href");
            }
            fields[i].setAccessible(true);
            try{
                fields[i].set(korStockDto,text);
            }catch (Exception ignored){
            }
        }
        return korStockDto;


// URL 뺴고 받아오기
//        for (int i = 0; i < td.size(); i++) {
//            String text;
//            text = td.get(i).text();
//
//            fields[i].setAccessible(true);
//            try{
//                fields[i].set(korStockDto,text);
//            }catch (Exception ignored){
//            }
//        }
//        return korStockDto;
    }
}
