package com.example.repositories;

import java.util.List;

import com.example.domain.Cliente;

public interface ClienteRepositoryCustom {

	List<Cliente> buscarPorFiltro(String razaoSocial, String cnpj, String uf);
}
