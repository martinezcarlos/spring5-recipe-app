package guru.springframework.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;

/**
 * Created by carlosmartinez on 29/11/2018 16:31
 */
@Entity
@Data
public class Recipe {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String description;
  private Integer prepTime;
  private Integer cookTime;
  private Integer servings;
  private String source;
  private String url;
  private String directions;
  @Lob
  private Byte[] image;
  @OneToOne(cascade = CascadeType.ALL)
  private Notes notes;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
  private Set<Ingredient> ingredients = new HashSet<>();
  @Enumerated(value = EnumType.STRING)
  private Difficulty difficulty;
  @ManyToMany
  @JoinTable(name = "recipe_category", joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
  private Set<Category> categories = new HashSet<>();

  public void setNotes(final Notes notes) {
    this.notes = notes;
    notes.setRecipe(this);
  }

  public void addIngredient(final Ingredient ingredient) {
    ingredient.setRecipe(this);
    ingredients.add(ingredient);
  }
}
