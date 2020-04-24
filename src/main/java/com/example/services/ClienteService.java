package com.example.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Cliente;
import com.example.dto.ClienteDTO;
import com.example.dto.ClienteNewDTO;
import com.example.repositories.ClienteRepository;
import com.example.services.exception.ErroValidacaoException;
import com.example.services.exception.ObjetoNaoEncontradoException;

import br.com.caelum.stella.validation.CNPJValidator;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;	

	public Cliente insert(Cliente obj) {
		obj.setId(null);

		validarParams(obj);
		validarCnpj(obj.getCnpj());

		obj = repo.save(obj);
		return obj;
	}

	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getRazaoSocial(), null, objDto.getUf());
	}

	public Cliente fromDTO(ClienteNewDTO objDto) {
		return new Cliente(null, objDto.getRazaoSocial(), objDto.getCnpj(), objDto.getUf());
	}

	public List<String> recuperarEstados() {
		List<String> ufs = new ArrayList<>();
		ufs.addAll(Arrays.asList("SP", "MG", "RJ"));
		return ufs;
	}

	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontradoException(
				"Objeto nao encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public Cliente atualizar(Cliente obj) {
		validarParams(obj);
		Cliente novoCliente = buscar(obj.getId());
		dadoAtualizado(novoCliente, obj);
		return repo.save(novoCliente);
	}

	private void dadoAtualizado(Cliente novoCliente, Cliente obj) {
		novoCliente.setRazaoSocial(obj.getRazaoSocial());
		novoCliente.setUf(obj.getUf());
	}

	private void validarCnpj(String cnpj) {
		if (cnpj == null) {
			throw new ErroValidacaoException("Cnpj preenchimento obrigatorio");
		}

		if (cnpj.length() != 14) {
			throw new ErroValidacaoException("Cnpj necessita ter 14 digitos");
		}

		CNPJValidator cnpjValidator = new CNPJValidator();
		cnpjValidator.assertValid(cnpj);

		if (repo.findByCnpj(cnpj) != null) {
			throw new ErroValidacaoException("Cnpj ja existente");
		}
	}

	private void validarParams(Cliente obj) {
		if (obj.getRazaoSocial() == null) {
			throw new ErroValidacaoException("Razao Social preenchimento obrigatorio");
		}
		if (obj.getRazaoSocial().length() < 5 || obj.getRazaoSocial().length() > 50) {
			throw new ErroValidacaoException("O tamanho deve ser entre 5 e 50 caracteres");
		}
		String razao = obj.getRazaoSocial().trim();
		if (!razao.matches("^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÊÍÏÓÒÖÚÇÑ ]+$")) {
			throw new ErroValidacaoException(razao, "Apenas letras");
		}
		if (obj.getUf() == null) {
			throw new ErroValidacaoException("Uf preenchimento obrigatorio");
		}
		List<String> ufs = recuperarEstados();
		if (!ufs.contains(obj.getUf())) {
			throw new ErroValidacaoException(obj.getUf(), "Uf nao existente");
		}
	}

	public void excluir(Integer id) {
		buscar(id);
		repo.deleteById(id);
	}

	public List<Cliente> buscarTodos() {
		return repo.findAll();
	}

	public List<Cliente> buscarPorRazaoSocialcnpjOuUf(String razaoSocial, String cnpj, String uf) {
		if (razaoSocial == null && cnpj == null && uf == null) {
			throw new ObjetoNaoEncontradoException("Informe pelo menos um parametro de busca");
		}

		if (razaoSocial != null && !razaoSocial.isEmpty()) {
			if (!razaoSocial.matches("[a-zA-Z\\s]+")) {
				throw new ErroValidacaoException("Razao Social aceita apenas letras");
			}
		}

		if (cnpj != null) {
			if (cnpj.length() != 14) {
				throw new ErroValidacaoException("Cnpj necessita ter 14 digitos");
			}

			CNPJValidator cnpjValidator = new CNPJValidator();
			cnpjValidator.assertValid(cnpj);
		}

		if (uf != null) {
			List<String> ufs = recuperarEstados();
			if (!ufs.contains(uf)) {
				throw new ErroValidacaoException("Uf nao existente");
			}
		}

		return repo.buscarPorFiltro(razaoSocial, cnpj, uf);
	}
}
