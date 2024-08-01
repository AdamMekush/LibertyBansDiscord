package dev.slide_am.lbd.embed;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import space.arim.libertybans.api.PlayerOperator;
import space.arim.libertybans.api.PlayerVictim;
import space.arim.libertybans.api.PunishmentType;

import java.awt.*;

import static org.bukkit.Bukkit.getServer;

public class PardonEmbedBuilder {
    protected final String title;
    protected final PunishmentType type;
    protected final PlayerOperator operator;
    protected final PlayerVictim victim;


    public PardonEmbedBuilder(String title, PunishmentType type, PlayerOperator operator, PlayerVictim victim){
        this.title = title;
        this.type = type;
        this.operator = operator;
        this.victim = victim;
    }

    public MessageEmbed build(){
        return new EmbedBuilder()
                .setTitle(title)
                .addField("Тип", type.toString().toUpperCase(), false)
                .addField("Звинувачувач", getServer().getPlayer(operator.getUUID()).getName(), false)
                .addField("Звинувачуваний", getServer().getPlayer(victim.getUUID()).getName(), false)
                .setThumbnail(String.format("https://minotar.net/cube/%s/100.png", victim.getUUID()))
                .setColor(Color.RED)
                .build();
    }

    protected String getOperatorName(){
        switch (operator.getType()){
            case PLAYER -> {
                return getServer().getPlayer(operator.getUUID()).getName();
            }
            case CONSOLE -> {
                return "CONSOLE";
            }
        }
        return "";
    }
}
