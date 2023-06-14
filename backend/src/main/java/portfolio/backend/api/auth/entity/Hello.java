package portfolio.backend.api.auth.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Hello {


    //rudgusee branch
    @Id @GeneratedValue
    private Long id;
}
