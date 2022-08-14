package top.mtserver.utils;

import com.google.common.collect.Maps;
import net.minecraft.util.Util;

import java.util.Map;

public class CatType {
    public static String getCatType(int type){
        if (type < 0 || type > 10){
            return "Unkown Cat";
        }
        return Types.get(type);
    }

    public static final Map<Integer, String> Types = Util.make(Maps.newHashMap(), hashMap -> {
        hashMap.put(0, "虎斑猫");
        hashMap.put(1, "西服猫");
        hashMap.put(2, "红虎斑猫");
        hashMap.put(3, "暹罗猫");
        hashMap.put(4, "英国短毛猫");
        hashMap.put(5, "花猫");
        hashMap.put(6, "波斯猫");
        hashMap.put(7, "布偶猫");
        hashMap.put(8, "白猫");
        hashMap.put(9, "Jellie");
        hashMap.put(10, "黑猫");
    });
}
