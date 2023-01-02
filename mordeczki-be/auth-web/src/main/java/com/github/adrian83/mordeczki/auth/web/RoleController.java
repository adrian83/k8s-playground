package com.github.adrian83.mordeczki.auth.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.adrian83.mordeczki.auth.model.command.NewRoleCommand;
import com.github.adrian83.mordeczki.auth.model.entity.Role;
import com.github.adrian83.mordeczki.auth.repository.RoleRepository;

@RestController
@RequestMapping("/roles")
public class RoleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleRepository roleRepository;

	@GetMapping
	public ResponseEntity<Page<Role>> listRoles(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		LOGGER.info("list roles [page: {}, size: {}]", page, size);
		var roles = roleRepository.findAll(PageRequest.of(page, size));
		return ResponseEntity.ok().body(roles);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Role>> showRole(@PathVariable final Long id) {
		var role = roleRepository.findById(id);
		return ResponseEntity.ok().body(role);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Void> createRole(@Valid @RequestBody NewRoleCommand command) throws URISyntaxException {
		var role = new Role(command.name());
		var saved = roleRepository.save(role);
		return ResponseEntity.created(new URI("/roles/" + saved.getId())).build();
	}
}
