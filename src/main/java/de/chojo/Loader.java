package de.chojo;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class Loader implements PluginLoader {
    private static final Logger log = getLogger(Loader.class);

    @Override
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        log.info("Loader started");
    }
}
