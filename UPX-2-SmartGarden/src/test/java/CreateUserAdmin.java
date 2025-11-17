import com.facens.upx2.smartgarden.model.dao.*;
import com.facens.upx2.smartgarden.model.database.connection.MySQLDatabaseConnection;
import com.facens.upx2.smartgarden.model.domain.*;

import java.sql.Connection;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class CreateUserAdmin {
    public static void main(String[] args) {

        MySQLDatabaseConnection db = new MySQLDatabaseConnection();
        Connection connection = null;

        try {
            connection = db.getConnection();

            AddressesDao addressesDao = new AddressesDao();
            InstitutionsDao institutionsDao = new InstitutionsDao();
            UsersDao usersDao = new UsersDao();
            CitiesDao citiesDao = new CitiesDao();
            StatesDao statesDao = new StatesDao();
            CountriesDao countriesDao = new CountriesDao();
            
            Users existEmail = usersDao.searchUserByEmail("admin@gmail.com"); 
            
            if(existEmail != null){ 
                System.out.println("O usuário admin já existe. Abortando criação."); 
                return; 
            }

            Cities cidadeInst = citiesDao.searchCityById(5311L);
            States stateInst = statesDao.searchStateById(26L); 
            Countries countryInst = countriesDao.searchCountryById(1L); 

            Addresses enderecoInstituicao = new Addresses();
            enderecoInstituicao.setCEP("18270670");
            enderecoInstituicao.setCity(cidadeInst);
            enderecoInstituicao.setState(stateInst);
            enderecoInstituicao.setCountry(countryInst);
            enderecoInstituicao.setNeighborhoodName("Centro");
            enderecoInstituicao.setStreetName("RUA XI DE AGOSTO");
            enderecoInstituicao.setNumber("22");
            enderecoInstituicao.setType(2);

            String resAddressInst = addressesDao.save(enderecoInstituicao);

            if (!resAddressInst.contains("sucesso")) {
                System.out.println(resAddressInst);
                return;
            }

            Institutions inst = new Institutions();
            inst.setInstitutionName("Instituição Principal do Sistema");
            inst.setInstitutionCNPJ("00000000");
            inst.setInstitutionAddress(enderecoInstituicao);

            String resInst = institutionsDao.save(inst);

            if (!resInst.contains("sucesso")) {
                System.out.println(resInst);
                return;
            }

            Cities cidadeUser = citiesDao.searchCityById(5311L);
            States stateUser = statesDao.searchStateById(26L); 
            Countries countryUser = countriesDao.searchCountryById(1L);

            Addresses enderecoAdmin = new Addresses();
            enderecoAdmin.setCEP("18270001");
            enderecoAdmin.setCity(cidadeUser);
            enderecoAdmin.setState(stateUser);
            enderecoAdmin.setCountry(countryUser);
            enderecoAdmin.setNeighborhoodName("Centro");
            enderecoAdmin.setStreetName("Rua dos Desenvolvedores");
            enderecoAdmin.setNumber("123");
            enderecoAdmin.setType(1);

            String resAddressAdmin = addressesDao.save(enderecoAdmin);

            if (!resAddressAdmin.contains("sucesso")) {
                System.out.println(resAddressAdmin);
                return;
            }

            Users admin = new Users();
            admin.setFullName("Admin");
            admin.setUserName("admin");
            admin.setUserEmail("admin@gmail.com");
            admin.setUserPassword("12345678");
            admin.setIsAdmin(1);
            admin.setInstitution(inst);
            admin.setUserAddress(enderecoAdmin);

            String resAdmin = usersDao.save(admin);

            if (!resAdmin.contains("sucesso")) {
                System.out.println(resAdmin);
                return;
            }

            System.out.println("Admin criado com sucesso!");

        } catch (Exception e) {

            System.err.println("Falha ao criar Admin: " + e.getMessage());
            e.printStackTrace(); 

        } finally {
            if (connection != null) {
                db.closeConnection();
            }
        }
    }
}