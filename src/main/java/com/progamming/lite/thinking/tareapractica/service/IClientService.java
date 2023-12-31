package com.progamming.lite.thinking.tareapractica.service;

import com.progamming.lite.thinking.tareapractica.model.ClientModel;
import java.util.List;
import java.util.Optional;

public interface IClientService {
     ClientModel salvar(ClientModel clientModel) ;
     List<ClientModel> findAll();
     Optional<ClientModel> findById(Long id);
     void delete(ClientModel clientModel);

}
