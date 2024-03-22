package com.project.crudclient.dto;

import com.project.crudclient.entities.Client;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class    ClientDTO {
    private Long id;
    @NotBlank(message = "Nome Obrigatorio")
    @Size(min = 3, max = 45, message = "Corrija para o padrão")
    private String name;
    @Size(min = 11,max = 14)
    private String cpf;
    @Positive(message = "apenas valores positivo")
    private Double income;
    private LocalDate birthDate;
    private Integer children;

    public ClientDTO(){}
    public ClientDTO(Client client) {
        id = client.getId();
        name = client.getName();
        cpf = client.getCpf();
        income = client.getIncome();
        birthDate = client.getBirthDate();
        children = client.getChildren();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public Double getIncome() {
        return income;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Integer getChildren() {
        return children;
    }
}
