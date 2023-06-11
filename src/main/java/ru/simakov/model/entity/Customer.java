package ru.simakov.model.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import ru.simakov.BaseEntityCU;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(callSuper = true)
@Accessors(chain = true)
public class Customer extends BaseEntityCU {
    private String firstName;
    private String lastName;
    private String email;
}
