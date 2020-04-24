package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Cliente;
@Repository	
public interface ClienteRepository extends JpaRepository<Cliente, Integer>, ClienteRepositoryCustom {

//	@Transactional(readOnly = true)
//	@Query(" select cli from cliente cli where (cli.razao_social = null or cli.razao_social LIKE %:razaoSocial% ) "
//			+ "and (cli.cnpj = null or cli.cnpj = :cnpj) and (cli.uf = null or cli.uf = :uf)")
//	List<ClienteDTO> pesquisaCompleta(@Param("razaoSocial") String razaoSocial, @Param("cnpj") String cnpj,
//			@Param("uf") String uf);
	
	@Transactional(readOnly = true)
	Cliente findByCnpj(String cnpj);
}
