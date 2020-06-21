package adudecalledleo.keystrokeview.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.resource.language.I18n;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class ModConfigGui {
    public static ConfigBuilder getConfigBuilder() {
        final ModConfig cfg = ModConfigHolder.getConfig();
        ConfigBuilder cb = ConfigBuilder.create().setTitle(k("title"));
        cb.setSavingRunnable(ModConfigHolder::saveConfig);
        addGeneralCategory(cfg, cb);
        return cb;
    }

    private static final ModConfig DEFAULTS = new ModConfig();

    private static String k(String key) {
        return "keystrokeview.config." + key;
    }

    private static Supplier<Optional<String[]>> tooltip(final String key) {
        final Optional<String[]> data = Optional.of(new String[] {I18n.translate(key) });
        return () -> data;
    }

    private static Supplier<Optional<String[]>> tooltip(final String... keys) {
        final Optional<String[]> data = Optional.of(Arrays.stream(keys).map(I18n::translate).toArray(String[]::new));
        return () -> data;
    }

    @SuppressWarnings("SameParameterValue")
    private static Supplier<Optional<String[]>> tooltip(final String key, final int count) {
        if (count == 1)
            return tooltip(key);
        return tooltip(IntStream.range(0, count).mapToObj(i -> String.format("%s[%d]", key, i)).toArray(String[]::new));
    }

    private static void addGeneralCategory(ModConfig cfg, ConfigBuilder cb) {
        ConfigEntryBuilder eb = cb.entryBuilder();
        ConfigCategory cGeneral = cb.getOrCreateCategory(k("category.general"));
    }
}
