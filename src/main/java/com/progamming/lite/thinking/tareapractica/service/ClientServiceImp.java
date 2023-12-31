package com.progamming.lite.thinking.tareapractica.service;

import com.progamming.lite.thinking.tareapractica.model.ClientModel;
import com.progamming.lite.thinking.tareapractica.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImp implements IClientService {
    private final ClientRepository clientRepository;

    @Override
    public ClientModel salvar(ClientModel clientModel) {
            return clientRepository.save(clientModel);
    }

    @Override
    public List<ClientModel> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<ClientModel> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public ClientModel delete(ClientModel clientModel) {
         clientRepository.delete(clientModel);
         return clientModel;
    }
}
