package com.github.adrian83.mordeczki.auth.web.api;

import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
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
import com.github.adrian83.mordeczki.auth.model.query.GetRoleQuery;
import com.github.adrian83.mordeczki.auth.service.RoleService;
import com.github.adrian83.mordeczki.auth.web.model.request.PageQuery;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<Page<Role>> listRoles(
	    @RequestParam(value = Const.PAGE, defaultValue = Const.NUM_ZERO) int page,
	    @RequestParam(value = Const.SIZE, defaultValue = Const.NUM_TEN) int size) {
	LOGGER.info("list roles [page: {}, size: {}]", page, size);
	var query = new PageQuery(page, size);
	var roles = roleService.listRole(query);
	return ResponseEntity.ok().body(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> showRole(@PathVariable final Long id) {
	var query = new GetRoleQuery(id);
	var maybeRole = roleService.getRoleById(query);
	return maybeRole.map(role -> ResponseEntity.ok().body(role)).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createRole(@Valid @RequestBody NewRoleCommand command) throws URISyntaxException {
	var role = roleService.createRole(command);
	return ResponseEntity.created(new URI("/roles/" + role.getId())).build();
    }
}
