package org.example.databases;

import javax.persistence.*;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "records")
@Entity
public class Cat {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private String record;
}
