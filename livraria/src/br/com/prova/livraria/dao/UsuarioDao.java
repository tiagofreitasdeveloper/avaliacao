package br.com.prova.livraria.dao;

import java.util.ArrayList;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import br.com.prova.livraria.modelo.Autor;
import br.com.prova.livraria.modelo.Livro;
import br.com.prova.livraria.modelo.Usuario;


public class UsuarioDao extends AbstractDao<Usuario>{
	
	public static ArrayList<Usuario> LSUsuario = new ArrayList<Usuario>();
	
	public boolean existe(Usuario usuario) {
		
		for (Usuario u : LSUsuario) {
			if(usuario.getEmail().equals(u.getEmail()) && usuario.getSenha().equals(u.getSenha()) ){
				
				return true;			
			}
		}
		
		return false;
				
	}
	
	public Usuario buscaPorEmail(Usuario usuario){
		TypedQuery<Usuario> query = getEm().createQuery("select u from Usuario u join fetch u.perfis where u.email = :email", Usuario.class);
		
		query.setParameter("email", usuario.getEmail());
		return query.getSingleResult();
	}
	
	public void pesist(Usuario usuario){
		LSUsuario.add(usuario);
	}

	public void drop() {
		// TODO Auto-generated method stub
		LSUsuario.clear();
	}
}
