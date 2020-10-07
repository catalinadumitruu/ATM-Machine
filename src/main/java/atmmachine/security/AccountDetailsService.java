package atmmachine.security;

import atmmachine.DAO.BankAccountDAO;
import atmmachine.model.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountDetailsService implements UserDetailsService {

    @Autowired
    BankAccountDAO repository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String pin) throws UsernameNotFoundException {
        Optional<BankAccount> optionalUser = repository.findByPassword(Integer.parseInt(pin));
        System.out.println("============================ " + optionalUser.toString());
        if(optionalUser.isPresent()) {
            BankAccount users = optionalUser.get();
            System.out.println("----------------- " + users.getUsername() + " " + users.getPIN());

            return User.builder()
                    .username(users.getUsername())
                    .password(bCryptPasswordEncoder.encode(String.valueOf(users.getPIN())))
                    .disabled(users.isDisabled())
                    .accountExpired(users.isAccountExpired())
                    .accountLocked(users.isAccountLocked())
                    .credentialsExpired(users.isCredentialsExpired())
                    .roles("USER")
                    .build();
        } else {
            throw new UsernameNotFoundException("PIN is not Found");
        }
    }
}
