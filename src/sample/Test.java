package sample;

import java.util.LinkedHashMap;

public class Test {
    public static void main(String[] args) {
        LinkedHashMap<String, String> t = new LinkedHashMap<>();
        t.put("1", "1");
        t.put("2", "1");
        t.put("1", "2");
        System.out.println(t);
    }
}
