package adudecalledleo.keystrokeview.compat;

import adudecalledleo.keystrokeview.KeystrokeViewMod;
import adudecalledleo.keystrokeview.config.ModConfig;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;

public class ModMenuApiImpl implements ModMenuApi {
    @Override
    public String getModId() {
        return KeystrokeViewMod.MOD_ID;
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(ModConfig.class, parent).get();
    }
}
