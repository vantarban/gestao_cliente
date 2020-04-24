package com.example.dto;

import com.example.domain.Cliente;

public class ClienteDTO {

	private Integer id;
	private String razaoSocial;
	private String cnpj;
	private String uf;
	
	public ClienteDTO() {
	}

	public ClienteDTO(Cliente obj) {
		id = obj.getId();
		razaoSocial= obj.getRazaoSocial();
		cnpj = obj.getCnpj();
		uf = obj.getUf();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
}
