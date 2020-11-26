package com.leite.edvaldo.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.leite.edvaldo.cursomc.domain.Categoria;
import com.leite.edvaldo.cursomc.domain.Cidade;
import com.leite.edvaldo.cursomc.domain.Cliente;
import com.leite.edvaldo.cursomc.domain.Endereco;
import com.leite.edvaldo.cursomc.domain.Estado;
import com.leite.edvaldo.cursomc.domain.Pagamento;
import com.leite.edvaldo.cursomc.domain.PagamentoComBoleto;
import com.leite.edvaldo.cursomc.domain.PagamentoComCartao;
import com.leite.edvaldo.cursomc.domain.Pedido;
import com.leite.edvaldo.cursomc.domain.Produto;
import com.leite.edvaldo.cursomc.domain.enums.EstadoPagamento;
import com.leite.edvaldo.cursomc.domain.enums.TipoCliente;
import com.leite.edvaldo.cursomc.repositories.CategoriaRepository;
import com.leite.edvaldo.cursomc.repositories.CidadeRepository;
import com.leite.edvaldo.cursomc.repositories.ClienteRepository;
import com.leite.edvaldo.cursomc.repositories.EnderecoRepository;
import com.leite.edvaldo.cursomc.repositories.EstadoRepository;
import com.leite.edvaldo.cursomc.repositories.PagamentoRepository;
import com.leite.edvaldo.cursomc.repositories.PedidoRepository;
import com.leite.edvaldo.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	// dependencias
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Instancias
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		Cidade c4 = new Cidade(null, "Vinhedo", est2);

		Cliente cli1 = new Cliente(null, "Edvaldo Leite", "eguilhermeleite@gmail.com", "22117214860",
				TipoCliente.PESSOA_FISICA);
		cli1.getTelefones().addAll(Arrays.asList("(11)96125-7615", "(11)96932-4269"));

		Endereco e1 = new Endereco(null, "Rua Manuel Dias de Oliveira", "59", "Torre A Apto. 172", "Butantã",
				"05528-010", cli1, c2);

		Endereco e2 = new Endereco(null, "Rua Antunes Ferreira", "102", "-", "Siopi", "03252-025", cli1, c4);
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("25/05/2020 14:23"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("28/06/2020 10:41"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2020 00:00"),
				null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
	

		// Objetos
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		// repositorios
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3, c4));
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		pedidoRepository.saveAll(Arrays.asList(ped1));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));

	}

}
