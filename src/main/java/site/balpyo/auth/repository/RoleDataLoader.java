package site.balpyo.auth.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import site.balpyo.auth.entity.ERole;
import site.balpyo.auth.entity.Role;

@Component
public class RoleDataLoader implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_USER));
        }
        if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
        }
        if (roleRepository.findByName(ERole.ROLE_MODERATOR).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_MODERATOR));
        }
    }
}
