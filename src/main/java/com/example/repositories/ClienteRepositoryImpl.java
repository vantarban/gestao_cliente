package com.example.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.domain.Cliente;

public class ClienteRepositoryImpl implements ClienteRepositoryCustom{
	
	@Autowired
	private EntityManager em;

	@Override
	public List<Cliente> buscarPorFiltro(String razaoSocial, String cnpj, String uf) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Cliente> query = cb.createQuery(Cliente.class);

		Root<Cliente> root = query.from(Cliente.class);
		Predicate predicate = cb.and();
		
		if (razaoSocial != null ) {
		predicate = cb.and(predicate, cb.like(root.<String>get("razaoSocial"), "%" + razaoSocial + "%"));
		}
		
		if(cnpj != null) {
		predicate = cb.and(predicate, cb.equal(root.get("cnpj"), cnpj));
		}
		
		if(uf != null) {
		predicate = cb.and(predicate, cb.equal(root.get("uf"), uf));
		}
		
		TypedQuery<Cliente> tq = em.createQuery(query.select(root).where(predicate));
		return tq.getResultList();
		
	}
	
	/*@Override
	public List<Cliente> buscarPorRazaoSocialcnpjOuUf(String razaoSocial, String cnpj, String uf) {
		if (razaoSocial == null && cnpj == null && uf == null) {
			throw new ObjetoNaoEncontradoException("Informe pelo menos um parametro de busca");
		}

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Cliente> query = cb.createQuery(Cliente.class);
		
		Root<Cliente> root = query.from(Cliente.class);
		Predicate predicate = cb.and();

		if (razaoSocial != null && !razaoSocial.isEmpty()) {
			if (!razaoSocial.matches("[a-zA-Z\\s]+")) {
				throw new ErroValidacaoException("Razao Social aceita apenas letras");
			}
			predicate = cb.and(predicate, cb.like(root.<String>get("razaoSocial"), "%" + razaoSocial + "%"));
		}

		if (cnpj != null) {
			if(cnpj.length() != 14) {
				throw new ErroValidacaoException("Cnpj necessita ter 14 digitos");
			}
			
			CNPJValidator cnpjValidator = new CNPJValidator();
			cnpjValidator.assertValid(cnpj);
			predicate = cb.and(predicate, cb.equal(root.get("cnpj"), cnpj));
		}

		if (uf != null) {
			List<String> ufs = service.recuperarEstados();
			if (!ufs.contains(uf)) {
				throw new ErroValidacaoException("Uf nao existente");
			}
			predicate = cb.and(predicate, cb.equal(root.get("uf"), uf));
		}

		TypedQuery<Cliente> tq = em.createQuery(query.select(root).where(predicate));
		return tq.getResultList();
	}*/

}
