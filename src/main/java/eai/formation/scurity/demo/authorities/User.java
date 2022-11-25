package eai.formation.scurity.demo.authorities;

import org.springframework.security.core.GrantedAuthority;

public class User implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "User";
    }
}
