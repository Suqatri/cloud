package net.suqatri.cloud.node.console.setup;

public interface SetupInputParser<T> {

    T parse(SetupEntry entry, String input);

}
