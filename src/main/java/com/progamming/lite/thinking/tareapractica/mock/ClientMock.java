package com.progamming.lite.thinking.tareapractica.mock;

import com.progamming.lite.thinking.tareapractica.dto.ClientRequest;


public final class ClientMock {
    private ClientMock(){
    }
    public static ClientRequest getClientMock(){

        return ClientRequest.builder()
                .city("Republica Dominicana")
                .documentType("Cedula")
                .numberDocument(12345678)
                .numberPhone("879-985-9654")
                .address("Direccion Cliente Mock")
                .firstlastName("Los")
                .secondName("De Mock")
                .firstName("Juan Mock")
                .firstlastName("Palotes")
                .secondlastName("A palo limpio")
                .build();

    }
}
