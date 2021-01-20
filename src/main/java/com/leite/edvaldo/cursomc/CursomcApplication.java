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
import com.leite.edvaldo.cursomc.domain.ItemPedido;
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
import com.leite.edvaldo.cursomc.repositories.ItemPedidoRepository;
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
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Instancias
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Ferramentas");
		Categoria cat4 = new Categoria(null, "Cama mesa e banho");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Perfumaria");
		Categoria cat7 = new Categoria(null, "Automóvel");
		Categoria cat8 = new Categoria(null, "Eletrônico");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Mesa de escritório", 300.00);
		Produto p5 = new Produto(null, "Parafusadeira", 750.00);
		Produto p6 = new Produto(null, "Colcha", 200.00);
		Produto p7 = new Produto(null, "Abajour", 500.00);
		Produto p8 = new Produto(null, "Tv True Color", 1400.00);
		Produto p9 = new Produto(null, "Toalha", 29.90);
		Produto p10 = new Produto(null, "Pneu aro 20", 350.00);
		Produto p11 = new Produto(null, "Regador", 40.00);
		Produto p12 = new Produto(null, "Shampoo", 30.00);

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		Cidade c4 = new Cidade(null, "Vinhedo", est2);

		// cliente 1
		Cliente cli1 = new Cliente(null, "Edvaldo Leite", "eguilhermeleite@gmail.com", "22117214860",
				TipoCliente.PESSOA_FISICA);
		cli1.getTelefones().addAll(Arrays.asList("(11)96125-7615", "(11)96932-4269"));

		// cliente 2
		Cliente cli2 = new Cliente(null, "Luciene da Silva Leite", "lsilvaleite14@gmail.com", "27158787885",
				TipoCliente.PESSOA_FISICA);
		cli2.getTelefones().addAll(Arrays.asList("(11)96125-7615", "(11)96932-4269"));

		Endereco e1 = new Endereco(null, "Rua Manuel Dias de Oliveira", "59", "Torre A Apto. 172", "Butantã",
				"05528-010", cli1, c2);
		Endereco e2 = new Endereco(null, "Rua Antunes Ferreira", "102", "-", "Siopi", "03252-025", cli1, c4);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cli2.getEnderecos().addAll(Arrays.asList(e1, e2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("25/05/2020 14:23"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("28/06/2020 10:41"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2020 00:00"),
				null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.0, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.0, 1, 800.00);

		// Objetos
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2,p4,p7));
		cat3.getProdutos().addAll(Arrays.asList(p5));
		cat4.getProdutos().addAll(Arrays.asList(p6,p9));
		cat5.getProdutos().addAll(Arrays.asList(p11));
		cat6.getProdutos().addAll(Arrays.asList(p12));
		cat7.getProdutos().addAll(Arrays.asList(p10));
		cat8.getProdutos().addAll(Arrays.asList(p8));
		

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		p4.getCategorias().addAll(Arrays.asList(cat1));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat4));
		p7.getCategorias().addAll(Arrays.asList(cat2));
		p8.getCategorias().addAll(Arrays.asList(cat8));
		p9.getCategorias().addAll(Arrays.asList(cat4));
		p10.getCategorias().addAll(Arrays.asList(cat7));
		p11.getCategorias().addAll(Arrays.asList(cat5));
		p12.getCategorias().addAll(Arrays.asList(cat6));

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		// repositorios
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3,p4,p5,p6,p7,p8,p9,p10,p11,p12));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3, c4));
		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		pedidoRepository.saveAll(Arrays.asList(ped1));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));

	}

}
