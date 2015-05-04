package sk.tomsik68.mclauncher.api.servers;

import java.util.HashMap;

/**
 * Builder class for FoundServerInfo. May be used in server finders
 */
public final class FoundServerInfoBuilder {
    private String motd, ip;
    private int port;
    private IServerFinder finder;
    private HashMap<String, Object> properties = new HashMap<String, Object>();

    public final FoundServerInfoBuilder ip(String s){
        ip = s;
        return this;
    }

    public final FoundServerInfoBuilder motd(String s){
        motd = s;
        return this;
    }

    public final FoundServerInfoBuilder finder(IServerFinder sf){
        finder = sf;
        return this;
    }

    public final FoundServerInfoBuilder port(int p){
        port = p;
        return this;
    }


    public final FoundServerInfoBuilder property(String key, Object value){
        properties.put(key, value);
        return this;
    }

    public final FoundServerInfo build(){
        return new FoundServerInfo(finder, ip, port, motd, properties);
    }


}