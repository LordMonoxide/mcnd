package us.corielicio.mcnd.utils.loader;

import org.yaml.snakeyaml.Yaml;
import us.corielicio.mcnd.Mcnd;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

public abstract class Loader<T extends GameData> {
  private final Map<String, T> data = new HashMap<>();
  private final String type;

  protected Loader(final String type) {
    this.type = type;
  }

  protected abstract T builder(final ObjectBuilder builder, final Map<String, Object> root);

  public void addFromDirectory(final Path directory) throws IOException {
    Mcnd.logger.info("Loading {} from {}", this.type, directory.toString());

    for(final Path file : Files.newDirectoryStream(directory, "*.yaml")) {
      try {
        this.addFromPath(file);
      } catch(final IOException e) {
        Mcnd.logger.error("Error opening " + this.type, e);
      } catch(final ObjectBuilder.ObjectBuilderException e) {
        Mcnd.logger.error("Malformed " + this.type, e);
      }
    }
  }

  public T addFromPath(final Path path) throws ObjectBuilder.ObjectBuilderException, IOException {
    final Yaml yaml = new Yaml();

    final Map<String, Object> root = yaml.load(Files.newInputStream(path, StandardOpenOption.READ));

    return this.add(ObjectBuilder.build(root, this::builder));
  }

  public T add(final T data) {
    if(this.data.put(data.id, data) == null) {
      Mcnd.logger.info("Loaded {} {}", this.type, data.id);
    } else {
      Mcnd.logger.info("Reloaded {} {}", this.type, data.id);
    }

    return data;
  }

  public T get(final String id) {
    return this.data.get(id);
  }

  public void forEach(final Consumer<T> consumer) {
    this.data.values().forEach(consumer);
  }

  public Stream<T> stream() {
    return this.data.values().stream();
  }

  public int size() {
    return this.data.size();
  }
}
