package adudecalledleo.keystrokeview;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class KeystrokeViewWidget extends DrawableHelper {
    private static KeystrokeViewWidget instance;

    private KeystrokeViewWidget() { }

    public static void onHudRender(float tickDelta) {
        if (instance == null)
            instance = new KeystrokeViewWidget();
        instance.render(tickDelta);
    }

    private MinecraftClient mc;
    private TextRenderer textRenderer;
    private int width, height;

    public void render(float tickDelta) {
        mc = MinecraftClient.getInstance();
        textRenderer = mc.textRenderer;
        width = mc.getWindow().getScaledWidth();
        height = mc.getWindow().getScaledHeight();

        drawKeyMovement(mc.options.keyForward, 24, height / 2 - 10);
        drawKeyMovement(mc.options.keyLeft, 4, height / 2 + 10);
        drawKeyMovement(mc.options.keyBack, 24, height / 2 + 10);
        drawKeyMovement(mc.options.keyRight, 44, height / 2 + 10);
    }

    private void drawKeyMovement(KeyBinding binding, int x, int y) {
        fill(x, y, x + 16, y + 16, (binding.isPressed() ? 0xAFAAAAAA : 0xAF222222));
        RenderSystem.pushMatrix();
        RenderSystem.scalef(2, 2, 1);
        drawCenteredString(textRenderer, getBindingKey(binding),
                MathHelper.ceil(x / 2f), MathHelper.ceil(y / 2f) - textRenderer.fontHeight / 2, 0xFFFFFF);
        RenderSystem.popMatrix();
    }

    private String getBindingKey(KeyBinding binding) {
        InputUtil.KeyCode code = KeyBindingHelper.getBoundKeyOf(binding);
        String name;
        switch (code.getCategory()) {
        case KEYSYM:
            // TODO probably a better way to do this?
            // TODO i18n
            if (code.getKeyCode() == GLFW.GLFW_KEY_LEFT_SHIFT || code.getKeyCode() == GLFW.GLFW_KEY_RIGHT_SHIFT)
                return "SHIFT";
            if (code.getKeyCode() == GLFW.GLFW_KEY_LEFT_CONTROL || code.getKeyCode() == GLFW.GLFW_KEY_RIGHT_CONTROL)
                return "CTRL";
            if (code.getKeyCode() == GLFW.GLFW_KEY_LEFT_ALT || code.getKeyCode() == GLFW.GLFW_KEY_RIGHT_ALT)
                return "ALT";
            if (code.getKeyCode() == GLFW.GLFW_KEY_LEFT_SUPER || code.getKeyCode() == GLFW.GLFW_KEY_RIGHT_SUPER)
                return "SUPER";
            name = InputUtil.getKeycodeName(code.getKeyCode());
            return (name == null ? binding.getLocalizedName() : name).toUpperCase();
        case SCANCODE:
            name = InputUtil.getScancodeName(code.getKeyCode());
            return (name == null ? binding.getLocalizedName() : name).toUpperCase();
        case MOUSE:
            switch (code.getKeyCode()) {
            case 0:
                return "LMB";
            case 1:
                return "RMB";
            case 2:
                return "MMB";
            default:
                return (code.getKeyCode() + 1) + "MB";
            }
        default:
            return "how the fuck " + code.getCategory();
        }
    }
}
