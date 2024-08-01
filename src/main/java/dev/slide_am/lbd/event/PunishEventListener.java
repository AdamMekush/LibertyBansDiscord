package dev.slide_am.lbd.event;

import dev.slide_am.lbd.embed.PunishEmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import space.arim.libertybans.api.PlayerOperator;
import space.arim.libertybans.api.PlayerVictim;
import space.arim.libertybans.api.PunishmentType;
import space.arim.libertybans.api.event.PunishEvent;
import space.arim.libertybans.api.punish.DraftPunishment;
import space.arim.omnibus.events.EventConsumer;

import java.time.Duration;

import static dev.slide_am.lbd.LibertyBansDiscord.bot;
import static dev.slide_am.lbd.LibertyBansDiscord.channel;

public class PunishEventListener {
    public static EventConsumer<PunishEvent> listener = event -> {
        if(!event.isCancelled()){
            DraftPunishment punishment = event.getDraftSanction();
            PlayerOperator operator = (PlayerOperator) punishment.getOperator();
            PlayerVictim victim = (PlayerVictim) punishment.getVictim();
            Duration duration = punishment.getDuration();
            String reason = punishment.getReason();
            PunishmentType type = punishment.getType();
            MessageEmbed embed = new PunishEmbedBuilder("Новий репорт", type, operator, victim, reason, duration).build();

            bot.getTextChannelById(channel).sendMessageEmbeds(embed).queue();
        }
    };
}
