package dev.slide_am.lbd.embed;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import space.arim.libertybans.api.PlayerOperator;
import space.arim.libertybans.api.PlayerVictim;
import space.arim.libertybans.api.PunishmentType;

import java.awt.*;
import java.time.Duration;

import static org.bukkit.Bukkit.getServer;


public class PunishEmbedBuilder extends PardonEmbedBuilder {

    private final String reason;
    private final Duration duration;


    public PunishEmbedBuilder(String title, PunishmentType type, PlayerOperator operator, PlayerVictim victim, String reason, Duration duration){
        super(title, type, operator, victim);
        this.reason = reason;
        this.duration = duration;
    }

    @Override
    public MessageEmbed build(){

        return new EmbedBuilder()
                .setTitle(title)
                .addField("Тип", type.toString().toUpperCase(), false)
                .addField("Звинувачувач", getServer().getPlayer(operator.getUUID()).getName(), false)
                .addField("Звинувачуваний", getServer().getPlayer(victim.getUUID()).getName(), false)
                .addField("Причина", reason, false)
                .addField("Час", duration.toString(), false)
                .setThumbnail(String.format("https://minotar.net/cube/%s/100.png", victim.getUUID()))
                .setColor(Color.RED)
                .build();
    }
}
