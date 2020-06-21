package adudecalledleo.keystrokeview.config;

import me.sargunvohra.mcmods.autoconfig1u.gui.registry.GuiRegistry;
import me.sargunvohra.mcmods.autoconfig1u.util.Utils;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.KeyCodeEntry;
import me.shedaniel.clothconfig2.gui.entries.MultiElementListEntry;
import me.shedaniel.clothconfig2.gui.entries.NestedListListEntry;
import net.minecraft.client.util.InputUtil;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static me.sargunvohra.mcmods.autoconfig1u.util.Utils.getUnsafely;
import static me.sargunvohra.mcmods.autoconfig1u.util.Utils.setUnsafely;

public class ModConfigGuiProviders {
    private static final ConfigEntryBuilder ENTRY_BUILDER = ConfigEntryBuilder.create();

    public static void register(GuiRegistry guiRegistry) {
        guiRegistry.registerTypeProvider((i13n, field, config, defaults, registry) ->
            Collections.singletonList(ENTRY_BUILDER.startKeyCodeField(i13n, getUnsafely(field, config))
                     .setDefaultValue(Utils.<InputUtil.KeyCode>getUnsafely(field, defaults))
                     .setAllowKey(true).setAllowMouse(true)
                     .setSaveConsumer(keyCode -> setUnsafely(field, config, keyCode))
                     .build()), InputUtil.KeyCode.class);

        // TODO this throws a ClassCastException??? because of course it does
        // TODO this *still* doesn't save changes. FML
        guiRegistry.registerPredicateProvider((i13n, field, config, defaults, registry1) -> {
            List<InputUtil.KeyCode> configValue = getUnsafely(field, config);

            String remainingI13n = i13n.substring(0, i13n.indexOf(".option") + ".option".length());
            String classI13n = String.format("%s.%s", remainingI13n, InputUtil.KeyCode.class.getSimpleName());

            return Collections.singletonList(
                    new NestedListListEntry<InputUtil.KeyCode, MultiElementListEntry<InputUtil.KeyCode>> (
                            i13n,
                            configValue,
                            false,
                            null,
                            keyCodes -> setUnsafely(field, config, keyCodes),
                            () -> getUnsafely(field, defaults),
                            ENTRY_BUILDER.getResetButtonKey(),
                            true,
                            true,
                            (elem, nestedListListEntry) -> {
                                int i = 0; // TODO find a way to get the actual index
                                return new MultiElementListEntry<>(classI13n, elem,
                                                                   Collections.singletonList(
                                                                           buildKeyCodeListEntry(i13n, configValue, i)),
                                                                   true);
                            }
                    )
            );
        }, ModConfigGuiProviders::isListOfKeyCodes);
    }

    private static KeyCodeEntry buildKeyCodeListEntry(String i13n, List<InputUtil.KeyCode> codes, int i) {
        return ENTRY_BUILDER.startKeyCodeField(i13n, codes.get(i)).build();
    }

    public static boolean isListOfKeyCodes(Field field) {
        if (List.class.isAssignableFrom(field.getType()) && field.getGenericType() instanceof ParameterizedType) {
            Type[] args = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
            return args.length == 1 && Objects.equals(args[0], InputUtil.KeyCode.class);
        } else {
            return false;
        }
    }
}
