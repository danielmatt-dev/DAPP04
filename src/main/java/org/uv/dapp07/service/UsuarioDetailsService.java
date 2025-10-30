package org.uv.dapp07.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.uv.dapp07.models.Usuario;
import org.uv.dapp07.models.enums.Estado;
import org.uv.dapp07.repository.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario u = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User.builder()
                .username(u.getUsername())
                .password(u.getPassword()) // ya debe venir encriptado
                .roles(u.getRol().name())
                .disabled(u.getEstado() == Estado.DESHABILITADO)
                .build();
    }
}
