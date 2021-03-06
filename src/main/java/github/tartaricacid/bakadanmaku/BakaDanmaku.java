package github.tartaricacid.bakadanmaku;

import github.tartaricacid.bakadanmaku.api.thread.DanmakuThreadFactory;
import github.tartaricacid.bakadanmaku.command.CommandBakaDM;
import github.tartaricacid.bakadanmaku.handler.ChatMsgHandler;
import github.tartaricacid.bakadanmaku.handler.ScreenMsgHandler;
import github.tartaricacid.bakadanmaku.handler.StartStopHandler;
import github.tartaricacid.bakadanmaku.thread.BilibiliDanmakuThread;
import github.tartaricacid.bakadanmaku.thread.ChushouDanmakuThread;
import github.tartaricacid.bakadanmaku.thread.DouyuDanmakuThread;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = BakaDanmaku.MOD_ID, name = BakaDanmaku.MOD_NAME, acceptedMinecraftVersions = "[1.12]", version = BakaDanmaku.VERSION, clientSideOnly = true)
public class BakaDanmaku {
    public static final String MOD_ID = "baka_danmaku";
    public static final String MOD_NAME = "Baka Danmaku";
    public static final String VERSION = "1.0.0";

    public static final Logger logger = LogManager.getLogger(MOD_ID);

    public static EntityPlayer player;

    @Mod.Instance(MOD_ID)
    public static BakaDanmaku INSTANCE;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // 装载各大平台的弹幕线程
        DanmakuThreadFactory.setDanmakuThread("bilibili", new BilibiliDanmakuThread());
        DanmakuThreadFactory.setDanmakuThread("douyu", new DouyuDanmakuThread());
        DanmakuThreadFactory.setDanmakuThread("chushou", new ChushouDanmakuThread());

        // 注册开启，关闭弹幕事件处理器
        MinecraftForge.EVENT_BUS.register(StartStopHandler.class);

        // 注册聊天事件处理器
        MinecraftForge.EVENT_BUS.register(ChatMsgHandler.class);

        // 注册屏幕信息事件处理器
        MinecraftForge.EVENT_BUS.register(ScreenMsgHandler.class);

        // 注册命令
        ClientCommandHandler.instance.registerCommand(new CommandBakaDM());
    }
}
