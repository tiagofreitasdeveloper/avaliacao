package br.com.prova.livraria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import br.com.prova.livraria.dao.PerfilDao;
import br.com.prova.livraria.dao.UsuarioDao;
import br.com.prova.livraria.modelo.Perfil;
import br.com.prova.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1709003704193202750L;

	@Inject
	private UsuarioDao usuarioDao;
	@Inject
	private PerfilDao perfilDao;
	
	private Usuario usuario;
	private List<Usuario> usuarios;
	private List<Usuario> usuariosRemovidos;
	
	private List<Perfil> perfis;
	
	public UsuarioBean() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void init(){
		this.usuario = new Usuario();
		this.usuariosRemovidos = new ArrayList<Usuario>();
		carregaUsuarios();
	}
	
	public void gravar(){
		for (Usuario usuario : this.usuarios) {
			usuarioDao.merge(usuario);
		}
		deletarUsuarios();
		carregaUsuarios();
	}
	
	public void deletarUsuarios(){
		for(Usuario u : this.usuariosRemovidos){
			if(u.getId() != null){
				usuarioDao.delete(u);
			}
		}
	}
	
	public void adicionar(){
		this.usuarios.add(usuario);
		this.usuario = new Usuario();
	}
	
	public void removerItem(Usuario usuario){
		this.usuarios.remove(usuario);
		this.usuariosRemovidos.add(usuario);
	}
	
	public void carregaUsuarios(){
		this.usuarios = usuarioDao.findAll(Usuario.class);
	}
	
	 public void onRowEdit(RowEditEvent event) {
	        FacesMessage msg = new FacesMessage("Usuário editado", ((Usuario) event.getObject()).getEmail());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    }
	     
	    public void onRowCancel(RowEditEvent event) {
	        FacesMessage msg = new FacesMessage("Edição cancelada", ((Usuario) event.getObject()).getEmail());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    }
	     
	    public void onCellEdit(CellEditEvent event) {
	        Object oldValue = event.getOldValue();
	        Object newValue = event.getNewValue();
	         
	        if(newValue != null && !newValue.equals(oldValue)) {
	            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "linha modificada", "Old: " + oldValue + ", Novo:" + newValue);
	            FacesContext.getCurrentInstance().addMessage(null, msg);
	        }
	    }

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Perfil> getPerfis() {
		return perfilDao.findAll(Perfil.class);
	}
	
	
}
