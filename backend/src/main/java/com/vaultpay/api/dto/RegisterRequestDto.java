package com.vaultpay.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(

                @NotBlank(message = "O nome está em branco.") String fullName,

                @NotBlank(message = "O CPF/CNPJ é obrigatório.") @Size(min = 11, max = 14, message = "O CPF/CNPJ deve ter entre 11 e 14 caracteres.") String cpfCnpj,

                @NotBlank(message = "O email é obrigatório.") @Email(message = "O email não é válido.") String email,

                @NotBlank(message = "A senha é obrigatória.") @Size(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres.") String password) {
}