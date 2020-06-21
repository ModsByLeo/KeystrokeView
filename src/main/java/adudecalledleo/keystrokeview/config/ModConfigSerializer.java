package adudecalledleo.keystrokeview.config;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.minecraft.client.util.InputUtil;

import java.lang.reflect.Type;
import java.util.List;

public class ModConfigSerializer {
    public static Gson GSON = new GsonBuilder().registerTypeAdapter(InputUtil.KeyCode.class, new KeyCodeJsonAdapter())
                                               .registerTypeAdapter(ModConfig.KeyCodeRow.class, new KeyCodeRowJsonAdapter())
                                               .setPrettyPrinting().create();

    // I'm pretty sure Gson can serialize KeyCode just fine? but just in case...
    // besides, this is technically an optimization - KeyCode also has a "name" field that's safe to ignore
    public static class KeyCodeJsonAdapter implements JsonSerializer<InputUtil.KeyCode>,
                                                      JsonDeserializer<InputUtil.KeyCode> {
        @Override
        public JsonElement serialize(InputUtil.KeyCode src, Type srcT, JsonSerializationContext ctx) {
            final JsonObject jObj = new JsonObject();
            jObj.add("type", ctx.serialize(src.getCategory()));
            jObj.add("code", ctx.serialize(src.getKeyCode(), Integer.class)); // why u converting ints to floats
            return jObj;
        }

        @Override
        public InputUtil.KeyCode deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx)
                throws JsonParseException {
            final JsonObject jObj = json.getAsJsonObject();
            final InputUtil.Type type = ctx.deserialize(jObj.get("type"), InputUtil.Type.class);
            final int code = jObj.get("code").getAsInt();
            return type.createFromCode(code);
        }
    }

    // hides the existence of KeyCodeRow by serializing it as if it was a List<KeyCode>
    public static class KeyCodeRowJsonAdapter implements JsonSerializer<ModConfig.KeyCodeRow>,
                                                         JsonDeserializer<ModConfig.KeyCodeRow> {
        private static final Class<? super List<InputUtil.KeyCode>> KEYCODE_LIST_CLASS =
                new TypeToken<List<InputUtil.KeyCode>>() {}.getRawType();

        @Override
        public JsonElement serialize(ModConfig.KeyCodeRow src, Type srcT, JsonSerializationContext ctx) {
            return ctx.serialize(src.codes);
        }

        @Override
        public ModConfig.KeyCodeRow deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx)
                throws JsonParseException {
            final ModConfig.KeyCodeRow row = new ModConfig.KeyCodeRow();
            row.codes = ctx.deserialize(json.getAsJsonArray(), KEYCODE_LIST_CLASS);
            return row;
        }
    }

    public static <T extends ConfigData> GsonConfigSerializer<T> createSerializer(Config definition, Class<T> configClass) {
        return new GsonConfigSerializer<>(definition, configClass, GSON);
    }
}
