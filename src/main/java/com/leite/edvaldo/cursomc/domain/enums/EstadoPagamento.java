package com.leite.edvaldo.cursomc.domain.enums;

public enum EstadoPagamento {
PENDENTE(1,"Pendente"),
QUITADO(2,"Quitado"),
CANCELADO(3,"Cancelado");

private Integer cod;
private String desc;

private EstadoPagamento(Integer cod, String desc) {
	this.cod = cod;
	this.desc = desc;
}

public Integer getCod() {
	return cod;
}

public String getDesc() {
	return desc;
}

public static EstadoPagamento toEnum(Integer cod) {

	if (cod == null) {
		return null;
	}
	for (EstadoPagamento x : EstadoPagamento.values()) {
		if (cod.equals(x.getCod())) {
			return x;
		}
	}
	throw new IllegalArgumentException("Código " + cod + "  Inválido");
}

}
