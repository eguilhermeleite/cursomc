package com.leite.edvaldo.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.leite.edvaldo.cursomc.domain.Categoria;
import com.leite.edvaldo.cursomc.domain.Produto;
import com.leite.edvaldo.cursomc.repositories.CategoriaRepository;
import com.leite.edvaldo.cursomc.repositories.ProdutoRepository;
import com.leite.edvaldo.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto com Id " + id + " não encontrado..."));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,
				org.springframework.data.domain.Sort.Direction.valueOf(direction), orderBy);
		
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
			return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
		}

	}

