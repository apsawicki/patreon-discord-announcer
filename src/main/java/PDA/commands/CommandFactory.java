package PDA.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class CommandFactory {

    private final Map<String, AbstractCommand> commandMap = new HashMap<>();

    private ApplicationContext context;

    @Autowired
    public CommandFactory(ApplicationContext context) {
        this.context = context;
        populateMapper(context.getBeansOfType(AbstractCommand.class).values().iterator());
    }

    private void populateMapper(final Iterator<AbstractCommand> classIterator) {
        while (classIterator.hasNext()) {
            AbstractCommand abstractCommandImpl = (AbstractCommand) classIterator.next();
            commandMap.put(abstractCommandImpl.getClass().getName(), abstractCommandImpl);
        }
    }

    public AbstractCommand getCommand(String command) {
        return commandMap.get(command);
    }

}
