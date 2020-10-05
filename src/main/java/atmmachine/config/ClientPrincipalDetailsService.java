//package atmmachine.config;
//
//import atmmachine.DAO.BankAccountDAO;
//import atmmachine.model.BankAccount;
//import org.springframework.stereotype.Service;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//@Service
//public class ClientPrincipalDetailsService implements UserDetailsService {
//    private BankAccountDAO dao;
//
//    public ClientPrincipalDetailsService(BankAccountDAO dao) {
//        this.dao = dao;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String pin) throws UsernameNotFoundException {
//        BankAccount account = this.dao.findByPassword(Integer.parseInt(pin));
//
//        return new ClientPrincipal(account);
//    }
//}
