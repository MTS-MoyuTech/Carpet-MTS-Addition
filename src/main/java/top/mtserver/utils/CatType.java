package top.mtserver.utils;

public class CatType {
    public static String getCatType(int type){
        if (type < 0 || type > 10){
            return "Unkown Cat";
        }
        return MessageUtil.getLang("top.mtserver.string.cat" + type);
    }
}
