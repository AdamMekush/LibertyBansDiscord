package dev.slide_am.lbd.event;

import dev.slide_am.lbd.embed.PardonEmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import space.arim.libertybans.api.PlayerOperator;
import space.arim.libertybans.api.PlayerVictim;
import space.arim.libertybans.api.PunishmentType;
import space.arim.libertybans.api.event.PardonEvent;
import space.arim.omnibus.events.EventConsumer;

import static dev.slide_am.lbd.LibertyBansDiscord.bot;
import static dev.slide_am.lbd.LibertyBansDiscord.channel;

public class PardonEventListener {
    public static EventConsumer<PardonEvent> listener = event -> {
        if(!event.isCancelled()){
            PlayerOperator operator = (PlayerOperator) event.getOperator();
            PlayerVictim victim = (PlayerVictim) event.getPardonedVictim();
            PunishmentType type = event.getPunishmentType();

            MessageEmbed embed = new PardonEmbedBuilder("Нове помилування", type, operator, victim).build();

            bot.getTextChannelById(channel).sendMessageEmbeds(embed).queue();
        }
    };

}
