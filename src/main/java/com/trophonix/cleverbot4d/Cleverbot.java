package com.trophonix.cleverbot4d;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.Status;
import sx.blah.discord.util.DiscordException;

import java.util.Scanner;

/**
 * Created by Lucas on 1/10/17.
 */
public class Cleverbot {

    // Discord bot stuff
    private IDiscordClient client;

    // Cleverbot stuff
    private ChatterBot cleverbot;
    private ChatterBotSession cleverbotSession;

    public Cleverbot() {

        Scanner in = new Scanner(System.in);
        System.out.print("Enter Bot Token: ");
        String token = in.next();

        // Discord bot stuff
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(token);

        try {
            this.client = clientBuilder.login();
        } catch (DiscordException ex) {
            ex.printStackTrace();
            System.exit(0);
        }

        client.getDispatcher().registerListener(this);

        // Cleverbot stuff
        ChatterBotFactory factory = new ChatterBotFactory();

        try {
            this.cleverbot = factory.create(ChatterBotType.CLEVERBOT);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }

        this.cleverbotSession = cleverbot.createSession();

    }

    @EventSubscriber
    public void onReady(ReadyEvent event) {
        client.changeStatus(Status.game("MENTION ME!"));
    }

    @EventSubscriber
    public void onMessage(MessageReceivedEvent event) {
        if (event.getMessage().toString().startsWith("<@267080991754027018>")) {
            String message = event.getMessage().toString().replace("<@267080991754027018>", "");
            if (message.isEmpty() || message.equals(" ")) {
                try {
                    event.getMessage().reply("Hi, I'm Cleverbot for Discord!\n\nMy Creator is Trophonix (#5300).\n\nTo speak to me, simply mention me like so:\n<@267080991754027018> Hi!\n\nEnjoy!\n");
                    return;
                } catch (Exception ex) {
                    System.out.println("SOMETHING WENT WRONG!");
                    ex.printStackTrace();
                }
            }
            try {
                event.getMessage().reply(cleverbotSession.think(message));
            } catch (Exception ex) {
                System.out.println("SOMETHING WENT WRONG!");
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Cleverbot cleverbot = new Cleverbot();
    }

}
