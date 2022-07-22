package net.suqatri.redicloud.node.commands;

import net.suqatri.redicloud.node.NodeLauncher;
import net.suqatri.commands.CommandSender;
import net.suqatri.commands.ConsoleCommand;
import net.suqatri.commands.annotation.CommandAlias;
import net.suqatri.commands.annotation.Default;

@CommandAlias("version|info|ver|v")
public class VersionCommand extends ConsoleCommand {

    @Default
    public void onVersion(CommandSender commandSender){
        NodeLauncher.getInstance().getConsole().printCloudHeader(false);
    }

}