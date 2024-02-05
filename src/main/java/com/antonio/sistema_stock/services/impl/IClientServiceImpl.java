package com.antonio.sistema_stock.services.impl;

import com.antonio.sistema_stock.dto.dtoRequest.ClientDtoRequest;
import com.antonio.sistema_stock.entities.Client;
import com.antonio.sistema_stock.entities.User;
import com.antonio.sistema_stock.exceptions.user.UserNotFound;
import com.antonio.sistema_stock.repositories.IClientRepository;
import com.antonio.sistema_stock.repositories.IUserRepository;
import com.antonio.sistema_stock.services.IClientService;
import com.sun.jdi.BooleanValue;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class IClientServiceImpl implements IClientService {

    @Autowired
    private IClientRepository clientsRepository;
    @Autowired
    private IUserRepository userRepository;


    @Transactional()
    @Override
    public String insert(ClientDtoRequest client, String username) throws Exception {

        clientsRepository.save(mapClientDtoToClient(client,username));
        return "se agrego con exito";
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClientDtoRequest> getAll(String username) throws Exception {
        Optional<User> userOptional= userRepository.findByUsername(username);
        if(userOptional.isEmpty()) throw new UserNotFound("user not found");
        return clientsRepository.findAll(userOptional.get());
    }

    @Transactional(readOnly = true)
    @Override
    public ClientDtoRequest getById(Long id) throws Exception {
        return mapClientToClientDto(clientsRepository.findById(id).orElseThrow(Exception::new));
    }

    @Transactional()
    @Override
    public String updateById(Long id, ClientDtoRequest client) throws Exception {
      Client client1=  clientsRepository.findById(id).orElseThrow(()-> new UserNotFound("client not found")) ;
        client1.setDirection(! Strings.isBlank(client.getDirection()) ? client.getDirection() : client1.getDirection());
        client1.setName(!Strings.isBlank(client.getName()) ? client.getName() : client1.getName());
        client1.setLastname(!Strings.isBlank(client.getLastname()) ? client.getLastname() : client1.getLastname());
        if (client.getEnable()== null || client.getEnable()) client1.setEnable(true);
        client1.setGross_income(!Strings.isBlank(client.getGross_income()) ? client.getGross_income() : client1.getGross_income());
        client1.setEmail(!Strings.isBlank(client.getEmail()) ? client.getEmail() : client1.getEmail());
      clientsRepository.save(client1);
      return "se modifico perfectamente";
    }

    @Transactional()
    @Override
    public String deleteById(Long id) throws Exception {
         clientsRepository.deleteById(id);
        return "se elimino correctamente";
    }


    private Client mapClientDtoToClient(ClientDtoRequest clientDtoRequest, String username){
        Optional<User> userOptional= userRepository.findByUsername(username);
        if (userOptional.isEmpty()) throw new UserNotFound("user not found");
        System.out.println(clientDtoRequest);
        Client client = new Client(clientDtoRequest.getCuit(),
                clientDtoRequest.getName(),
                clientDtoRequest.getEmail(),
                clientDtoRequest.getLastname(),
                clientDtoRequest.getDirection(),
                clientDtoRequest.getGross_income(),
                clientDtoRequest.getEnable(),
                userOptional.get());
        System.out.println("asiq uedo el objeto "+client);
        return client;
    }
    private ClientDtoRequest mapClientToClientDto(Client client){
        return  new ClientDtoRequest(client.getCuit(),
                client.getName(),
                client.getEmail(),
                client.getLastname(),
                client.getDirection(),
                client.getGross_income(),
                client.isEnable()
        );

    }
}
