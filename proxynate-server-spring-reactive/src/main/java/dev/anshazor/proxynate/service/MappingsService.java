package dev.anshazor.proxynate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class MappingsService {

	@Value("${proxynate.config.file}")
	private String configFilePath;

	private Map<String, String> proxyMappings;

	public Map<String, String> getProxyMappings() {
		return proxyMappings;
	}

	public void initMappings() throws IOException {
		var configFile = new File(configFilePath);
		if (configFile.exists()) {
			if (configFile.length() > 0) {
				proxyMappings = new ObjectMapper()
					.readValue(configFile, HashMap.class);
			} else {
				proxyMappings = new HashMap<>();
			}
		} else {
			configFile.createNewFile();
			proxyMappings = new HashMap<>();
		}
	}

	public Map<String, Set<String>> saveMappings(Map<String, String> mappings) throws IOException {
		var taken = new HashSet<String>();
		var valid = new HashMap<String, String>();
		for (String alias : mappings.keySet()) {
			if (alias.equalsIgnoreCase("mappings") || proxyMappings.containsKey(alias)) {
				taken.add(alias);
				mappings.remove(alias);
			} else {
				valid.put(alias.toLowerCase(), mappings.get(alias));
			}
		}

		if (mappings.size() > 0) {
			mappings.putAll(proxyMappings);
			try {
				new ObjectMapper()
					.writerWithDefaultPrettyPrinter()
					.writeValue(new File((configFilePath)), mappings);
			} catch (Exception e) {
				valid.keySet().forEach(alias -> proxyMappings.remove(alias));
				throw e;
			}
			proxyMappings = mappings;
		}

		var result = new HashMap<String, Set<String>>();
		result.put("saved", valid.keySet());
		if (taken.size() > 0) {
			result.put("taken", taken);
		}
		return result;
	}

}
