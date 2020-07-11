package org.fernando.meu;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class RecomentacaoEventDomain {

    private String nome;
    private String data;
    private LocalDate dtUtil;
    private Double valor;
    private Long sinistroId;
    private String uuid;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public LocalDate getDtUtil() {
        return dtUtil;
    }

    public void setDtUtil(LocalDate dtUtil) {
        this.dtUtil = dtUtil;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Long getSinistroId() {
        return sinistroId;
    }

    public void setSinistroId(Long sinistroId) {
        this.sinistroId = sinistroId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
