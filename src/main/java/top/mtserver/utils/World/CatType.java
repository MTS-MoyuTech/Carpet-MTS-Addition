package top.mtserver.utils.World;

import top.mtserver.utils.StringUtils.MessageUtil;

public class CatType {
    public static String getCatType(int type){
        if (type < 0 || type > 10){
            return "Unkown Cat";
        }
        return MessageUtil.getLang("top.mtserver.string.cat" + type);
    }
}
