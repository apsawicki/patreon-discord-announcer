package PDA.config;

import PDA.commands.CommandFactory;
import PDA.discord.DiscordEventListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
@Import({JPAConfiguration.class})
public class PDAConfiguration {

    @Autowired
    CommandFactory commandFactory;

    @Bean
    public JDA jda() {
        JDA jda;
        try {
            jda = JDABuilder.createDefault(parseToken()).build();
            jda.awaitReady();
            jda.addEventListener(new DiscordEventListener(commandFactory));
//            jda.getPresence().setActivity(Activity.playing("Type " + prefix + "help for commands"));
            return jda;
        } catch (LoginException | InterruptedException e) {
            System.exit(1);
        }
        return null;
    }

    private String parseToken() {
        String jsonText;
        JSONObject jsonToken;

        try {
            jsonText = IOUtils.toString(new FileInputStream("config.json"), StandardCharsets.UTF_8);
            jsonToken = (JSONObject) new JSONParser().parse(jsonText);
            return (String) jsonToken.get("TOKEN");
        }
        catch (IOException | ParseException e) {
            return null;
        }
    }
}
