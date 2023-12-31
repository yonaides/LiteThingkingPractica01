package com.progamming.lite.thinking.tareapractica.controller;

import com.progamming.lite.thinking.tareapractica.dto.ClientRequest;
import com.progamming.lite.thinking.tareapractica.dto.CustomResponseHandler;
import com.progamming.lite.thinking.tareapractica.exception.ClientNotFoundException;
import com.progamming.lite.thinking.tareapractica.mock.ClientMock;
import com.progamming.lite.thinking.tareapractica.model.ClientModel;
import com.progamming.lite.thinking.tareapractica.service.IClientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client")
@Slf4j
public class ClientController {

    private final IClientService iClientService;
    private final Integer NUMBER_DOCUMENT_MOCK = 12345678;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> salvar(@RequestBody @Valid @NotNull ClientRequest clientRequest,
                                             BindingResult bidingResult){

        if(bidingResult.hasErrors()){
            return CustomResponseHandler.
                    generateResponse(bidingResult
                            .getAllErrors()
                            .stream()
                            .map(ObjectError::getDefaultMessage)
                            .collect(Collectors.joining()),
                            HttpStatus.BAD_REQUEST,
                            clientRequest);
        }

        ClientModel.ClientModelBuilder clientBuilderSave = ClientModel.builder();
        clientBuilderSave.firstName(clientRequest.getFirstName());
        clientBuilderSave.secondName(clientRequest.getSecondName());
        clientBuilderSave.city(clientRequest.getCity());
        clientBuilderSave.address(clientRequest.getAddress());
        clientBuilderSave.numberDocument(clientRequest.getNumberDocument());
        clientBuilderSave.documentType(clientRequest.getDocumentType());
        clientBuilderSave.numberPhone(clientRequest.getNumberPhone());

        ClientModel clientModel = clientBuilderSave.build();

        iClientService.salvar(clientModel);
        log.info("Client {} guardado ", clientModel.getId());
        return CustomResponseHandler.generateResponse("Client Save",HttpStatus.CREATED, clientModel) ;

    }

    @GetMapping(path= "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> listar(){

        List<ClientModel> listClient =iClientService.findAll();

        if(!listClient.isEmpty()) {
            return CustomResponseHandler.
                    generateResponse("Listado de clientes", HttpStatus.OK,listClient);

        }else {
            return CustomResponseHandler.
                    generateResponse("Listado de clientes vacio", HttpStatus.NO_CONTENT,listClient);
        }
    }

    @GetMapping(path = "/listar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> listar(@PathVariable Long id){

        Optional<ClientModel> clientModelOptional = iClientService.findById(id);

        if(clientModelOptional.isPresent()) {
            return CustomResponseHandler.
                    generateResponse("Cliente Encontrado", HttpStatus.OK,clientModelOptional);
        }else {
            throw new ClientNotFoundException("Cliente no existe");
        }

    }

    @GetMapping(path = "/listar/document/{numberDocument}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> listar(@PathVariable Integer numberDocument){

        if(NUMBER_DOCUMENT_MOCK.equals(numberDocument))
            return CustomResponseHandler.
                    generateResponse("Cliente Mock encontrado",HttpStatus.OK, ClientMock.getClientMock());

        Optional<ClientModel> clientModelOptional = iClientService.findByNumberDocument(numberDocument);

        if(clientModelOptional.isPresent()) {
            return CustomResponseHandler.
                    generateResponse("Cliente Encontrado", HttpStatus.OK,clientModelOptional);
        }else {
            throw new ClientNotFoundException("Cliente no existe");
        }

    }


    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> actualizar(@PathVariable @NotNull Long id,
                                             @RequestBody @Valid ClientRequest clientRequest,
                                            BindingResult bidingResult){

        if(bidingResult.hasErrors()){
            return CustomResponseHandler.
                    generateResponse(bidingResult
                                    .getAllErrors()
                                    .stream()
                                    .map(ObjectError::getDefaultMessage)
                                    .collect(Collectors.joining()),
                            HttpStatus.BAD_REQUEST,
                            clientRequest);
        }

        Optional<ClientModel> clientModelOptional = iClientService.findById(id);

        if(clientModelOptional.isEmpty()) {
            throw new ClientNotFoundException("Cliente no existe");
        }

        ClientModel clientUpdate = ClientModel.builder()
                .id(id)
                .firstName(clientModelOptional.get().getFirstName())
                .secondName(clientModelOptional.get().getSecondName())
                .city(clientModelOptional.get().getCity())
                .address(clientModelOptional.get().getAddress())
                .documentType(clientModelOptional.get().getDocumentType())
                .numberDocument(clientModelOptional.get().getNumberDocument())
                .numberPhone(clientModelOptional.get().getNumberPhone())
                .build();

        iClientService.salvar(clientUpdate);

        return CustomResponseHandler.
                generateResponse("Cliente actualizado", HttpStatus.OK,clientUpdate);

    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> eliminar(@PathVariable Long id){
        Optional<ClientModel> clientModelOptional =iClientService.findById(id);

        if(clientModelOptional.isEmpty()) {
            throw new ClientNotFoundException("Cliente no existe");
        }

        ClientModel clientModel = clientModelOptional.get();
        iClientService.delete(clientModel);

        return CustomResponseHandler.
                generateResponse("Cliente eliminado", HttpStatus.OK, clientModel);
    }

}
