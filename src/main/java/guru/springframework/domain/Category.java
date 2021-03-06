package guru.springframework.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/** Created by carlosmartinez on 04/12/2018 15:15 */
@Entity
@Data
@EqualsAndHashCode(exclude = {"recipes"})
@ToString(exclude = {"recipes"})
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String description;

  @ManyToMany(mappedBy = "categories")
  private Set<Recipe> recipes = new HashSet<>();
}
