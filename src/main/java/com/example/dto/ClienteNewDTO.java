package com.example.dto;

import com.example.domain.Cliente;

public class ClienteNewDTO {

	private String cnpj;
	private String razaoSocial;
	private String uf;
	
	public ClienteNewDTO() {
	}

	public ClienteNewDTO(Cliente obj) {
		cnpj = obj.getCnpj();
		razaoSocial= obj.getRazaoSocial();
		uf = obj.getUf();
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
}
