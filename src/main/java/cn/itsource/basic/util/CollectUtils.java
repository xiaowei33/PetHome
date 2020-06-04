package cn.itsource.basic.util;

import java.util.Collection;

public class CollectUtils {

    /***
     * 判断某字符串是否包含集合中的一个
     * @param str 字符串
     * @param target 集合
     * @return 返回
     */
    public static boolean contain(String str, Collection<String> target){
        if (target== null || target.size()<1)
            return  false;

        for (String s : target) {
            boolean flag = str.contains(s);
            if (flag){
                return flag;
            }
        }
        return  false;
    }
}
