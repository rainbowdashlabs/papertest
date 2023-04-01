package de.chojo;

import com.google.gson.Gson;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Loader implements PluginLoader {
    @Override
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        MavenLibraryResolver resolver = new MavenLibraryResolver();
        PluginLibraries pluginLibraries = load();
        pluginLibraries.asDependencies().forEach(resolver::addDependency);
        pluginLibraries.asRepositories().forEach(resolver::addRepository);
        classpathBuilder.addLibrary(resolver);
    }

    public PluginLibraries load() {
        try (var in = Main.class.getResourceAsStream("/plugin-libraries.json")) {
            return new Gson().fromJson(new String(in.readAllBytes()), PluginLibraries.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private record PluginLibraries(List<String> repositories, List<String> dependencies) {
        public List<Dependency> asDependencies() {
            return dependencies.stream()
                    .map(d -> new Dependency(new DefaultArtifact(d), null))
                    .toList();
        }

        public List<RemoteRepository> asRepositories() {
            AtomicInteger integer = new AtomicInteger();
            return repositories.stream()
                    .map(d -> new RemoteRepository.Builder("maven" + integer.getAndIncrement(), "default", d).build())
                    .toList();
        }
    }
}
