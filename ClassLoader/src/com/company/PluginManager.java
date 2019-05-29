package com.company;


import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginManager {

    private final String pluginRootDirectory;

    public PluginManager(String pluginRootDirectory){

        this.pluginRootDirectory = pluginRootDirectory;
    }

    public Plugin load(String pluginName, String pluginClassName) throws ClassNotFoundException,
            MalformedURLException, IllegalAccessException, InstantiationException {

        URL url = new URL("file:/" + pluginRootDirectory + "/" + pluginName + "/");

        URLClassLoader classloader = new URLClassLoader(new URL[]{url});

        return (Plugin) classloader.loadClass(pluginClassName).newInstance();
    }
}