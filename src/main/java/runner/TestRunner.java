package runner;

import domain.ClientStatus;
import domain.Role;
import domain.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import repository.*;

@Profile({"default"})
@Component
public class TestRunner implements CommandLineRunner {

    private UserRepository userRepository;
    private ClientRepository clientRepository;
    private ClientStatusRepository clientStatusRepository;
    private ManagerRepository managerRepository;
    private RoleRepository roleRepository;

    public TestRunner(UserRepository userRepository, ClientRepository clientRepository, ClientStatusRepository clientStatusRepository, ManagerRepository managerRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.clientStatusRepository = clientStatusRepository;
        this.managerRepository = managerRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Role roleUser = new Role("ROLE_CLIENT", "Client role");
        Role roleManager = new Role("ROLE_MANAGER", "Manager role");
        Role roleAdmin= new Role("ROLE_ADMIN", "Admin role");

        roleRepository.save(roleUser);
        roleRepository.save(roleManager);
        roleRepository.save(roleAdmin);

        User admin = new User();
        admin.setEmail("admin@gmail.com");
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setBanned(false);
        admin.setEnabled(true);
        admin.setRole(roleAdmin);

        userRepository.save(admin);

        clientStatusRepository.save(new ClientStatus("Regular", 0, 3, 0));
        clientStatusRepository.save(new ClientStatus("Silver", 4, 8, 5));
        clientStatusRepository.save(new ClientStatus("Gold", 9, 14, 10));
        clientStatusRepository.save(new ClientStatus("Platinum", 15, 20,  15));



    }
}
