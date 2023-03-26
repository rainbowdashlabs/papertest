package de.chojo;

import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import org.jetbrains.annotations.NotNull;

public class Bootstrapper implements PluginBootstrap {
    @Override
    public void bootstrap(@NotNull PluginProviderContext context) {
        context.getLogger().info("Bootstrapped");
    }
}
