package adudecalledleo.keystrokeview.config;

import adudecalledleo.keystrokeview.KeystrokeViewMod;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.InputUtil.KeyCode;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Config(name = KeystrokeViewMod.MOD_ID)
public class ModConfig implements ConfigData {
    // trying to store KeyCodes directly causes AutoConfig to crash since KeyCode doesn't have a no-parameter init method
    public static class StoredKeyCode {
        @ConfigEntry.Gui.Excluded
        public InputUtil.Type type;
        @ConfigEntry.Gui.Excluded
        public int keyCode;

        @ConfigEntry.Gui.Excluded
        public static StoredKeyCode UNKNOWN = new StoredKeyCode();

        public static StoredKeyCode get(int i, int j) {
            StoredKeyCode kc = new StoredKeyCode();
            if (i > -1) {
                kc.type = InputUtil.Type.KEYSYM;
                kc.keyCode = i;
            } else if (j > -1) {
                kc.type = InputUtil.Type.SCANCODE;
                kc.keyCode = j;
            }
            return kc;
        }

        public static StoredKeyCode getMouse(int i) {
            StoredKeyCode kc = new StoredKeyCode();
            kc.type = InputUtil.Type.MOUSE;
            kc.keyCode = i;
            return kc;
        }

        public StoredKeyCode() {
            // equivalent to UNKNOWN_KEYCODE
            type = InputUtil.Type.KEYSYM;
            keyCode = -1;
        }

        public KeyCode toKeyCode() {
            if (this == UNKNOWN || type == InputUtil.Type.KEYSYM && keyCode == -1)
                return InputUtil.UNKNOWN_KEYCODE;
            else
                return type.createFromCode(keyCode);
        }
    }

    public static class KeyCodeRow {
        public List<StoredKeyCode> codes;

        public KeyCodeRow() {
            codes = new LinkedList<>();
        }

        public KeyCodeRow(StoredKeyCode... codes) {
            this.codes = Arrays.asList(codes);
        }
    }

    public ModConfig() {
        codes = new LinkedList<>();
        //    W
        //  A S D
        // LMB RMB
        codes.add(new KeyCodeRow(StoredKeyCode.UNKNOWN,
                                 StoredKeyCode.get(GLFW.GLFW_KEY_W, -1),
                                 StoredKeyCode.UNKNOWN));
        codes.add(new KeyCodeRow(StoredKeyCode.get(GLFW.GLFW_KEY_A, -1),
                                 StoredKeyCode.get(GLFW.GLFW_KEY_S, -1),
                                 StoredKeyCode.get(GLFW.GLFW_KEY_D, -1)));
        codes.add(new KeyCodeRow(StoredKeyCode.getMouse(GLFW.GLFW_MOUSE_BUTTON_1),
                                 StoredKeyCode.getMouse(GLFW.GLFW_MOUSE_BUTTON_2)));
    }

    public enum Side { LEFT, RIGHT }

    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public Side side = Side.LEFT;
    public List<KeyCodeRow> codes;
}
