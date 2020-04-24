package com.example.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domain.Cliente;
import com.example.dto.ClienteDTO;
import com.example.dto.ClienteNewDTO;
import com.example.services.ClienteService;

@RestController
@RequestMapping(value ="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@PostMapping
	public ResponseEntity<ClienteNewDTO> cadastrar(@RequestBody ClienteNewDTO objDto){
		Cliente cliente = service.fromDTO(objDto);
		cliente = service.insert(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).body(new ClienteNewDTO(cliente));
	}
	
	@GetMapping(value = "/pesquisarpor")
	public ResponseEntity<List<Cliente>> buscarPor(
			@RequestParam (value= "razaoSocial", required = false) String razaoSocial,
			@RequestParam (value = "cnpj", required = false) String cnpj,
			@RequestParam (value = "uf", required = false) String uf){
		List<Cliente> list = service.buscarPorRazaoSocialcnpjOuUf(razaoSocial, cnpj, uf);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Cliente> buscar(@PathVariable Integer id){
		Cliente obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> buscarTodos(){
		List<Cliente> list = service.buscarTodos();
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> atualizar(@RequestBody ClienteDTO objDto, @PathVariable Integer id){
		Cliente obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.atualizar(obj);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Integer id){
		service.excluir(id);
		return ResponseEntity.noContent().build();
	}
	
}
