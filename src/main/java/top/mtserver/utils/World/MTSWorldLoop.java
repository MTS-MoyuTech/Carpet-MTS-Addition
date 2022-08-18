package top.mtserver.utils.World;

import top.mtserver.MTSCarpetSettings;
import top.mtserver.utils.CommandDatas;

import static top.mtserver.utils.CommandDatas.*;

public class MTSWorldLoop {
    public static void loop(){
        //AddMSPT
        try {
            int stime = MTSCarpetSettings.AddMSPT;
            Thread.sleep(stime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //SetblockAfterTime
        CommandDatas.SetBlockAfterTime();
    }
}