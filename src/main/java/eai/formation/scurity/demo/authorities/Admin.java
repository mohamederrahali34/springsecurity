package eai.formation.scurity.demo.authorities;

import org.springframework.security.core.GrantedAuthority;

public class Admin implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "Admin";
    }
}
