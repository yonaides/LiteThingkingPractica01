package com.progamming.lite.thinking.tareapractica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientRequest {
    @Length(min =3, max = 3, message = "documentType solo permite 3 caracteres")
    private String documentType;
    private Integer numberDocument;
    @NotNull(message = "firstName no puede ser null")
    @NotBlank(message = "firstName no puede estar en blanco")
    private String firstName;
    @NotNull(message = "secondName no puede ser null")
    @NotBlank(message = "secondName no puede estar en blanco")
    private String secondName;
    private String numberPhone;
    private String address;
    private String city;
}
