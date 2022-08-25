package top.mtserver.utils.NetWork;

import net.minecraft.server.command.ServerCommandSource;
import top.mtserver.utils.StringUtils.MessageUtil;

import java.util.ArrayList;

public class NetWorkThread extends Thread{
    public final ServerCommandSource source;
    public final String name;
    public NetWorkThread(ServerCommandSource source, String name){
        super();
        this.name = name;
        this.source = source;
    }
    @Override
    public void run(){
        try {
            ArrayList<String> names = NetworkUtil.getPlayerNames(name);
            MessageUtil.StringOut(source.getWorld(),MessageUtil.getLang("top.mtserver.command.getHistoryNameSuccess_PlayerHaveHistoryName").replace("%Player",name),true);
            for(String PlayerName:names){
                MessageUtil.StringOut(source.getWorld(),PlayerName,true);
            }
        } catch (Exception e){
            MessageUtil.Out(source.getWorld(),"top.mtserver.command.getHistoryNameFailed",true);
        }
        System.out.println();
    }
}
