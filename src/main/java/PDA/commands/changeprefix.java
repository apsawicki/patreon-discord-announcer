package PDA.commands;

import org.springframework.stereotype.Component;

@Component
public class changeprefix extends AbstractCommand {

    @Override
    public void execute() {
        // TODO: fix prefix

        String prefix = "";

        if (args.length <= 1){
            send("no prefix provided");
        }
        else if (args[0].length() > 10){
            send("no prefix with length greater than 10 characters allowed");
        }

        prefix = args[1];
        send("\"" + args[1] + "\"" + " has been set as the command prefix");
    }
}
