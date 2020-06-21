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
    // AutoConfig chokes on parameterized types as type parameters, so instead of List<List<KeyCode>> we'll just
    // have a list of objects that each contain a list. we use custom (de)serializes (see ModConfigSerializer)
    // to hide the existence of this hack
    public static class KeyCodeRow {
        public List<KeyCode> codes;

        // have a no-param constructor so that Gson doesn't choke on us
        public KeyCodeRow() {
            codes = new LinkedList<>();
        }

        public KeyCodeRow(KeyCode... codes) {
            this();
            this.codes.addAll(Arrays.asList(codes));
        }
    }

    public ModConfig() {
        codeRows = new LinkedList<>();
        //    W
        //  A S D
        // LMB RMB
        codeRows.add(new KeyCodeRow(InputUtil.UNKNOWN_KEYCODE,
                                    InputUtil.getKeyCode(GLFW.GLFW_KEY_W, -1),
                                    InputUtil.UNKNOWN_KEYCODE));
        codeRows.add(new KeyCodeRow(InputUtil.getKeyCode(GLFW.GLFW_KEY_A, -1),
                                    InputUtil.getKeyCode(GLFW.GLFW_KEY_S, -1),
                                    InputUtil.getKeyCode(GLFW.GLFW_KEY_D, -1)));
        codeRows.add(new KeyCodeRow(InputUtil.Type.MOUSE.createFromCode(GLFW.GLFW_MOUSE_BUTTON_1),
                                    InputUtil.Type.MOUSE.createFromCode(GLFW.GLFW_MOUSE_BUTTON_2)));
    }

    public enum Side { LEFT, RIGHT }

    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public Side side = Side.LEFT;
    public List<KeyCodeRow> codeRows;
}
