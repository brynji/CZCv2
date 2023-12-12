package cz.cvut.fit.tjv.czcv2_authorization_server.domain;

import jakarta.persistence.*;

@Entity
public class Authorities {
    @Id
    @ManyToOne
    @JoinColumn(name = "username")
    private Users user;
    @Id
    private String authority;
}
