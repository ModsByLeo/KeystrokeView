package adudecalledleo.keystrokeview;

import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KeystrokeViewMod implements ClientModInitializer {
    public static final String MOD_ID = "keystrokeview";
    public static final String MOD_NAME = "Keystroke View";
	
	public static Logger LOGGER = LogManager.getLogger(MOD_NAME);

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(KeystrokeViewWidget::onHudRender);
        log(Level.INFO, "GLHF");
    }

    public static void log(Level level, String message){
        LOGGER.log(level, message);
    }
}