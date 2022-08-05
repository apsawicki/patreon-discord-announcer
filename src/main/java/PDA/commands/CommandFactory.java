package PDA.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class CommandFactory {

    private final Map<String, TestGenericCommand> commandMap = new HashMap<>();

    private ApplicationContext context;

    @Autowired
    public CommandFactory(ApplicationContext context) {
        this.context = context;
        System.out.println("command factory initalize");
        populateMapper(context.getBeansOfType(TestGenericCommand.class).values().iterator());
    }

    private void populateMapper(final Iterator<TestGenericCommand> classIterator) {
        while (classIterator.hasNext()) {
            TestGenericCommand testGenericCommandImpl = (TestGenericCommand) classIterator.next();
            System.out.println("putting: " + testGenericCommandImpl.getClass().getName());
            commandMap.put(testGenericCommandImpl.getClass().getName(), testGenericCommandImpl);
        }
    }

    public TestGenericCommand getCommand(String command) {
        System.out.println(commandMap.get(command));
        System.out.println(commandMap.keySet());
        System.out.println(command);
        return commandMap.get(command);
    }

}
