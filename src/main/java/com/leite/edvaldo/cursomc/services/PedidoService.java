package com.leite.edvaldo.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leite.edvaldo.cursomc.domain.ItemPedido;
import com.leite.edvaldo.cursomc.domain.PagamentoComBoleto;
import com.leite.edvaldo.cursomc.domain.Pedido;
import com.leite.edvaldo.cursomc.domain.enums.EstadoPagamento;
import com.leite.edvaldo.cursomc.repositories.ItemPedidoRepository;
import com.leite.edvaldo.cursomc.repositories.PagamentoRepository;
import com.leite.edvaldo.cursomc.repositories.PedidoRepository;
import com.leite.edvaldo.cursomc.repositories.ProdutoRepository;
import com.leite.edvaldo.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	@Autowired
	BoletoService boletoService;

	@Autowired
	PagamentoRepository pagamentoRepository;

	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	EmailService emailService;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto com Id " + id + " não encontrado..."));
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);

		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());

		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
	    emailService.sendOrderConfirmationEmail(obj);
		return obj;
	}
}
