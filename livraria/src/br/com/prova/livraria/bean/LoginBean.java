package br.com.prova.livraria.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import br.com.prova.livraria.dao.AutorDao;
import br.com.prova.livraria.dao.JPAUtil;
import br.com.prova.livraria.dao.LivroDao;
import br.com.prova.livraria.dao.PopulaBanco;
import br.com.prova.livraria.dao.UsuarioDao;
import br.com.prova.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class LoginBean {

	private Usuario usuario = new Usuario();
	@Inject
	private UsuarioDao usuarioDao;
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void init(){
		
	}
	
	public String efetuaLogin() {
		
		Usuario usuarioEncontrado = usuarioDao.buscaPorEmail(this.usuario);
		
		PopulaBanco pb = new PopulaBanco();
		
		pb.fillLista();
		
		System.out.println("fazendo login do usuario " + this.usuario.getEmail());
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(usuarioEncontrado != null){
			context.getExternalContext().getSessionMap().put("usuarioLogado", usuarioEncontrado);
			return "livro?faces-redirect=true";
		}
		
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Usuário não encontrado"));
		
		pb.dropLista();
		
		return "login?faces-redirect=true";
	}
	
	public String deslogar() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		AutorDao.LSAutor.clear();
		LivroDao.LSLivro.clear();
		return "login?faces-redirect=true";
	}
}
