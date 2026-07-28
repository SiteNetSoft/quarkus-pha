package org.sitenetsoft.quarkus.pha.qute;

import io.quarkus.qute.TemplateLocator;
import io.quarkus.qute.Variant;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Resolves the {@code pha:} template URI scheme — the stable, descriptor-friendly address of any
 * Qute template on the classpath: {@code pha:components/data-display/badge} loads
 * {@code templates/components/data-display/badge.html}.
 *
 * <p>This is the first View Descriptor Protocol bridge point: a VDP descriptor names a pha
 * template by URI, the consuming service binds the JSON payload to the matching
 * {@code org.sitenetsoft.quarkus.pha.model} type (the models are Jackson-constructible) and
 * renders via {@code engine.getTemplate("pha:...")}. The scheme resolves templates from any
 * classpath {@code templates/} root — pha's own components, the application's templates, and
 * template jars published by API services (the supported distribution flow) — so every fragment
 * is addressable without knowing which artifact ships it.
 *
 * <p>Paths are strictly {@code segment(/segment)*} of {@code [A-Za-z0-9_.-]} with no {@code ..}
 * segments — a descriptor can never address resources outside {@code templates/}.
 */
@ApplicationScoped
public class PhaTemplateLocator implements TemplateLocator {

    public static final String SCHEME = "pha:";

    private static final Pattern SAFE_PATH =
            Pattern.compile("[A-Za-z0-9_-][A-Za-z0-9_.-]*(/[A-Za-z0-9_-][A-Za-z0-9_.-]*)*");

    @Override
    public Optional<TemplateLocation> locate(String id) {
        if (id == null || !id.startsWith(SCHEME)) {
            return Optional.empty();
        }
        String path = id.substring(SCHEME.length());
        if (!SAFE_PATH.matcher(path).matches() || path.contains("..")) {
            return Optional.empty();
        }
        String resource = "templates/" + path + ".html";
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null || cl.getResource(resource) == null) {
            cl = PhaTemplateLocator.class.getClassLoader();
        }
        if (cl.getResource(resource) == null) {
            return Optional.empty();
        }
        final ClassLoader loader = cl;
        return Optional.of(new TemplateLocation() {
            @Override
            public Reader read() {
                try (InputStream in = loader.getResourceAsStream(resource)) {
                    if (in == null) {
                        throw new UncheckedIOException(new IOException("Vanished: " + resource));
                    }
                    return new StringReader(new String(in.readAllBytes(), StandardCharsets.UTF_8));
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public Optional<Variant> getVariant() {
                return Optional.of(new Variant(null, StandardCharsets.UTF_8, "text/html"));
            }
        });
    }
}
