package adudecalledleo.keystrokeview;

import adudecalledleo.keystrokeview.config.ModConfig;
import adudecalledleo.keystrokeview.config.ModConfigGuiProviders;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.gui.registry.api.GuiProvider;
import me.sargunvohra.mcmods.autoconfig1u.gui.registry.api.GuiRegistryAccess;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.List;

public class KeystrokeViewMod implements ClientModInitializer {
    public static final String MOD_ID = "keystrokeview";
    public static final String MOD_NAME = "Keystroke View";
	
	public static Logger LOGGER = LogManager.getLogger(MOD_NAME);

    @Override
    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        ModConfigGuiProviders.register(AutoConfig.getGuiRegistry(ModConfig.class));
        HudRenderCallback.EVENT.register(KeystrokeViewWidget::onHudRender);
        log(Level.INFO, "GLHF");
    }

    public static void log(Level level, String message){
        LOGGER.log(level, message);
    }
}