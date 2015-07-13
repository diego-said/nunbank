package br.com.doublelogic.nubanktest.util;

/**
 * Created by diegoalvessaidsimao on 12/07/15.
 */
public  class LogUtil {

     public static String getTag(Class<?> clazz) {
        return "NubankTest " + clazz.getSimpleName();
    }

}
