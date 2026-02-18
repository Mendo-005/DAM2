package mendo.dev.dominio.modelo;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "perfil")
public class Perfil implements Serializable {

        // Añadimos una clave para el serializado y deserializado 
        private static final long serialVersionUID = 1L;

        /**
         * Las etiquetas @ID y @GeneratedValue van juntas, ya que una marca la PK
         * de la tabla y la otra si es AUTO_INCREMENT 
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_perfil") // @Column marca un atributo como columna de la tabla
        private Long id;

        /**
         * El @Column puede tener diferentes atributos:
         *      name: nombre de la columna 
         *      nullable: boolean de si puede estar vacío
         *      unique: boolean de si debe ser un elemento unico
         * */ 
        @Column(name = "username", nullable = false, unique = true)
        private String userName;

        @Column(name = "password_hash", nullable = false, unique = true)
        private String password;
        
        @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
        @JoinColumn(name = "usuario_id", unique = true, nullable = true)
        private Usuario usuario;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Usuario getUsuario() {
            return usuario;
        }

        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
        }

        public Perfil() {}

        public Perfil(String userName, String password, Usuario usuario) {
            this.userName = userName;
            this.password = password;
            this.usuario = usuario;
        }

        @Override
        public String toString() {
            return "Perfil [id=" + id + ", userName=" + userName + ", password=" + password + "]";
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((userName == null) ? 0 : userName.hashCode());
            result = prime * result + ((password == null) ? 0 : password.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Perfil other = (Perfil) obj;
            if (userName == null) {
                if (other.userName != null)
                    return false;
            } else if (!userName.equals(other.userName))
                return false;
            if (password == null) {
                if (other.password != null)
                    return false;
            } else if (!password.equals(other.password))
                return false;
            return true;
        }

        
        

    
}
