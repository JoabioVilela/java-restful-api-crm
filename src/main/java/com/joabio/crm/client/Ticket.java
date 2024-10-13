package com.joabio.crm.client;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderBy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @NotNull
    @Length(min = 5, max = 30)
    @Column(length = 30, nullable = false)
    private String title;

    @NotBlank
    @NotNull
    @Length(min = 100, max = 300)
    @Column(length = 300, nullable = false)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OrderBy("id ASC")
    @JoinColumn(name = "client_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Client client;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) obj;
        return id == ticket.id && title.equals(ticket.title) && descricao.equals(ticket.descricao);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Ticket [id=").append(id).append(", title=").append(title).append(", descricao=")
                .append(descricao).append(", client=").append(client).append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return 31 * id + title.hashCode() + descricao.hashCode();
    }

}
