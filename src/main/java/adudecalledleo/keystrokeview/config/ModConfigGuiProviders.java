package adudecalledleo.keystrokeview.config;

import me.sargunvohra.mcmods.autoconfig1u.gui.registry.GuiRegistry;
import me.sargunvohra.mcmods.autoconfig1u.util.Utils;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

import java.util.Collections;

public class ModConfigGuiProviders {
    private static final ConfigEntryBuilder ENTRY_BUILDER = ConfigEntryBuilder.create();

    public static void register(GuiRegistry guiRegistry) {
        guiRegistry.registerTypeProvider((i13n, field, config, defaults, registry) -> {
            ModConfig.StoredKeyCode stc = Utils.getUnsafely(field, config);
            ModConfig.StoredKeyCode stcDef = Utils.getUnsafely(field, defaults);
            return Collections.singletonList(ENTRY_BUILDER.startKeyCodeField(i13n, stc.toKeyCode())
                                            .setDefaultValue(stcDef.toKeyCode())
                                            .setAllowKey(true).setAllowMouse(true)
                                            .setSaveConsumer(keyCode -> {
                                                ModConfig.StoredKeyCode stcNew = new ModConfig.StoredKeyCode();
                                                stcNew.type = keyCode.getCategory();
                                                stcNew.keyCode = keyCode.getKeyCode();
                                                Utils.setUnsafely(field, config, stcNew);
                                            }).build());
        }, ModConfig.StoredKeyCode.class);
    }
}
