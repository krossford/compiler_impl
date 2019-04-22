package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Util {

    public static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
        List<T> list = new ArrayList<T>(c);
        java.util.Collections.sort(list);
        return list;
    }

    public static String makeInputReadly(String s) {
        if (s.equals("")) {
            return "ε";
        } else {
            return s;
        }
    }

    public static String makeInputRaw(String s) {
        if (s.equals("ε")) {
            return "";
        } else {
            return s;
        }
    }
}
