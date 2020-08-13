package com.joao.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class GsonDeserializer<T> implements Deserializer<T> {
	
	public static final String TYPE_CONFIG = "com.joao.consumer.type_config";
	private final Gson gson = new GsonBuilder().create();
	private Class<T> clazz;
	
	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		String typeName = String.valueOf(configs.get(TYPE_CONFIG));
		try {
			this.clazz = (Class<T>) Class.forName(typeName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Type for deserialization does not exist in the classpath", e);
		}
	}
	
	
	@Override
	public T deserialize(String topic, byte[] data) {
		return gson.fromJson(new String(data), clazz);
	}
}
