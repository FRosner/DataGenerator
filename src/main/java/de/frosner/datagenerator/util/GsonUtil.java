package de.frosner.datagenerator.util;

import java.lang.reflect.Type;

import net.sf.qualitycheck.Check;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import de.frosner.datagenerator.distributions.Distribution;
import de.frosner.datagenerator.features.FeatureDefinition;

public class GsonUtil {

	private static class InterfaceAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
		@Override
		public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
			final JsonObject wrapper = new JsonObject();
			wrapper.addProperty("type", src.getClass().getName());
			wrapper.add("data", context.serialize(src));
			return wrapper;
		}

		@Override
		public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			final JsonObject wrapper = (JsonObject) json;
			final JsonElement typeName = get(wrapper, "type");
			final JsonElement data = get(wrapper, "data");
			final Type actualType = typeForName(typeName);
			return context.deserialize(data, actualType);
		}

		private Type typeForName(final JsonElement typeElem) {
			try {
				return Class.forName(typeElem.getAsString());
			} catch (ClassNotFoundException e) {
				throw new JsonParseException(e);
			}
		}

		private JsonElement get(final JsonObject wrapper, String memberName) {
			final JsonElement elem = wrapper.get(memberName);
			if (elem == null)
				throw new JsonParseException("no '" + memberName
						+ "' member found in what was expected to be an interface wrapper");
			return elem;
		}

	}

	private static final Gson _gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(
			Distribution.class, new InterfaceAdapter<Distribution>()).create();

	public static String featureDefinitionToJson(FeatureDefinition featureDefinition) {
		Check.instanceOf(FeatureDefinition.class, featureDefinition);
		return _gson.toJson(featureDefinition);
	}

	public static FeatureDefinition createFeatureDefinitionFromJson(String jsonString) {
		return _gson.fromJson(jsonString, FeatureDefinition.class);
	}

}
