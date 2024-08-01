package dev.slide_am.lbd;

import dev.slide_am.lbd.event.PardonEventListener;
import dev.slide_am.lbd.event.PunishEventListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import space.arim.libertybans.api.LibertyBans;
import space.arim.libertybans.api.event.PardonEvent;
import space.arim.libertybans.api.event.PunishEvent;
import space.arim.omnibus.Omnibus;
import space.arim.omnibus.OmnibusProvider;
import space.arim.omnibus.events.ListenerPriorities;

import java.util.EnumSet;

public final class LibertyBansDiscord extends JavaPlugin {

    Logger logger = LoggerFactory.getLogger(LibertyBansDiscord.class);

    private static String token;
    public static String channel;
    public static JDA bot;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        PluginDescriptionFile desc = getDescription();
        if (
                !desc.getName().equals("LibertyBansDiscord")
                        || desc.getAuthors().size() != 1
                        || !desc.getAuthors().contains("Slide_AM")
        ) {
            logger.error("The file plugin.yml has been edited without authorization.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        token = getConfig().getString("BotToken");
        channel = getConfig().getString("DiscordChannelId");

        if(token == null){
            logger.error("You need to setup BotToken in config.yml");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        if(channel == null){
            logger.error("You need to setup DiscordChannelId in config.yml");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }


        try{
            bot = JDABuilder.createLight(token, EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)).build();
        } catch (InvalidTokenException e){
            logger.error(e.toString());
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }


        Omnibus omnibus = OmnibusProvider.getOmnibus();
        LibertyBans libertyBans = omnibus.getRegistry().getProvider(LibertyBans.class).orElseThrow();

        omnibus.getEventBus().registerListener(PunishEvent.class, ListenerPriorities.NORMAL, PunishEventListener.listener);
        omnibus.getEventBus().registerListener(PardonEvent.class, ListenerPriorities.NORMAL, PardonEventListener.listener);

    }

    @Override
    public void onDisable() {
        bot.shutdown();
    }

    public static LibertyBansDiscord getInstance(){
        return getPlugin(LibertyBansDiscord.class);
    }
}
