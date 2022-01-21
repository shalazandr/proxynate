package dev.anshazor.proxynate.controller;

import dev.anshazor.proxynate.service.MappingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/mappings")
public class MappingsController {

	@Autowired
	private MappingsService mappingsService;

	@GetMapping
	@ResponseBody
	public Map<String, String> getProxyMappings() {
		return mappingsService.getProxyMappings();
	}

	@PostMapping
	@ResponseBody
	public Map<String, Set<String>> saveMappings(@RequestBody Map<String, String> mappings) throws IOException {
		return mappingsService.saveMappings(mappings);
	}

}
