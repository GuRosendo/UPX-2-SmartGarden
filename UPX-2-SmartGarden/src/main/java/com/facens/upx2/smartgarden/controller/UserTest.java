package com.facens.upx2.smartgarden.controller;

import com.facens.upx2.smartgarden.model.dao.*;
import com.facens.upx2.smartgarden.model.domain.*;
import java.util.List;

public class UserTest {
    public static void main(String[] args) {
        UsersDao usersDao = new UsersDao();

        // 1️⃣ Montar os objetos de localização
        CitiesDao citiesDao = new CitiesDao();
        Cities cidade = citiesDao.searchCityById(5311L); // cidade, estado e país completos

        // 2️⃣ Criar o novo endereço
        Addresses novoEndereco = new Addresses();
        novoEndereco.setStreetName("Rua dos Desenvolvedores");
        novoEndereco.setNumber("123");
        novoEndereco.setNeighborhoodName("Centro");
        novoEndereco.setCEP("18270001");
        novoEndereco.setCity(cidade);
        novoEndereco.setState(cidade.getUf());
        novoEndereco.setCountry(cidade.getUf().getCountry());
        novoEndereco.setType(1);

        // 3️⃣ Buscar instituição existente (ID = 1)
        InstitutionsDao institutionsDao = new InstitutionsDao();
        Institutions instituicao = institutionsDao.searchInstitutionById(1L);

        // 4️⃣ Criar usuário completo
        Users novoUsuario = new Users();
        novoUsuario.setFullName("Gustavo Dev Tester 3");
        novoUsuario.setUserName("gustavoTestes3");
        novoUsuario.setUserEmail("gustavo.dev3@example.com");
        novoUsuario.setUserPassword("12345678");
        novoUsuario.setInstitution(instituicao);
        novoUsuario.setUserAddress(novoEndereco);

        // 5️⃣ Salvar usuário
        String resultado = usersDao.save(novoUsuario);
        System.out.println("\n===== RESULTADO DA INSERÇÃO =====");
        System.out.println(resultado);

        // 6️⃣ Confirmar ID gerado do endereço e testar busca
        System.out.println("\nID do endereço criado: " + novoEndereco.getId());
        AddressesDao addressesDao = new AddressesDao();
        Addresses enderecoSalvo = addressesDao.searchAddressById(novoEndereco.getId());
        if (enderecoSalvo != null) {
            System.out.println("Endereço salvo: " + enderecoSalvo.getStreetName() + ", " + enderecoSalvo.getNumber());
        }

        // 8️⃣ Listar todos os usuários no sistema
        System.out.println("\n===== LISTAGEM DE TODOS OS USUÁRIOS =====");
        List<Users> usersList = usersDao.searchAllUsers();

        if (usersList.isEmpty()) {
            System.out.println("Nenhum usuário encontrado no sistema.");
        } else {
            for (Users user : usersList) {
                System.out.println("=======================================");
                System.out.println("ID: " + user.getId());
                System.out.println("Nome completo: " + user.getFullName());
                System.out.println("E-mail: " + user.getUserEmail());
                System.out.println("Usuário: " + user.getUserName());
                System.out.println("Instituição: " + 
                    (user.getInstitution() != null ? user.getInstitution().getInstitutionName() : "—"));

                System.out.println("Endereço:");
                if (user.getUserAddress() != null) {
                    System.out.println("   • Rua: " + user.getUserAddress().getStreetName());
                    System.out.println("   • Número: " + user.getUserAddress().getNumber());
                    System.out.println("   • Bairro: " + user.getUserAddress().getNeighborhoodName());
                    System.out.println("   • Cidade: " + 
                        (user.getUserAddress().getCity() != null ? user.getUserAddress().getCity().getName() : "—"));
                    System.out.println("   • Estado: " + 
                        (user.getUserAddress().getState() != null ? user.getUserAddress().getState().getName() : "—"));
                    System.out.println("   • País: " + 
                        (user.getUserAddress().getCountry() != null ? user.getUserAddress().getCountry().getNamePt(): "—"));
                    System.out.println("   • CEP: " + user.getUserAddress().getCEP());
                } else {
                    System.out.println("   (Endereço não informado)");
                }

                System.out.println("🕓 Criado em: " + user.getCreatedAt());
                System.out.println("🕓 Atualizado em: " + user.getUpdatedAt());
                System.out.println("🚫 Deletado em: " + user.getDeletedAt());
            }
        }
    }
}
