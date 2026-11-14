package by.bseu.pp.lab06;

import com.google.gson.*;
import java.io.Reader;
/** Готовое преобразование полей, чтобы сосредоточиться на файловом обмене.
 * Используется дерево JSON, поэтому LocalDate не передаётся в Gson через reflection. */
public final class JsonSupport {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting()
            .setStrictness(Strictness.STRICT).create();
    private JsonSupport() { }
    public static JsonArray parseArray(Reader reader) {
        try {
            JsonElement root = GSON.fromJson(reader, JsonElement.class);
            if (root == null || !root.isJsonArray())
                throw new DataFormatException("JSON: корень должен быть массивом");
            return root.getAsJsonArray();
        } catch (JsonParseException ex) {
            throw new DataFormatException("JSON: некорректный синтаксис", ex);
        }
    }
    public static Sale toSale(JsonElement element, int recordNumber) {
        try {
            if (element == null || !element.isJsonObject())
                throw new DataFormatException("нужен объект продажи");
            JsonObject obj = element.getAsJsonObject();
            JsonElement amount = obj.get("amount");
            if (amount == null || !amount.isJsonPrimitive() || !amount.getAsJsonPrimitive().isNumber())
                throw new DataFormatException("amount: нужно JSON-число");
            return new Sale(string(obj, "id"), DateParser.parseDate(string(obj, "date")),
                    string(obj, "customer"), string(obj, "category"), amount.getAsDouble());
        } catch (IllegalArgumentException ex) {
            throw new DataFormatException("JSON запись " + recordNumber + ": " + ex.getMessage(), ex);
        }
    }
    private static String string(JsonObject obj, String field) {
        JsonElement value = obj.get(field);
        if (value == null || !value.isJsonPrimitive() || !value.getAsJsonPrimitive().isString())
            throw new DataFormatException(field + ": нужна JSON-строка");
        return value.getAsString();
    }
    public static JsonObject toJson(Sale sale) {
        JsonObject obj = new JsonObject();
        obj.addProperty("id", sale.getId());
        obj.addProperty("date", sale.getDate().toString());
        obj.addProperty("customer", sale.getCustomer());
        obj.addProperty("category", sale.getCategory());
        obj.addProperty("amount", sale.getAmount());
        return obj;
    }
}
