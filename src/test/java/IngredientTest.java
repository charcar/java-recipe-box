import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class IngredientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void ingredient_instantiatesCorrectly_true() {
    Ingredient newIngredient = new Ingredient("Ground beef");
    assertTrue(newIngredient instanceof Ingredient);
  }

  @Test
  public void getName_returnsName_string() {
    Ingredient newIngredient = new Ingredient("Ground beef");
    assertEquals("Ground beef", newIngredient.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Ingredient.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfIngredientsAreTheSame() {
    Ingredient newIngredient = new Ingredient("Eggs");
    Ingredient newIngredient2 = new Ingredient("Eggs");
    assertTrue(newIngredient.equals(newIngredient2));
  }

  @Test
  public void save_savesIngredientToDatabase_true() {
    Ingredient newIngredient = new Ingredient("Potato");
    newIngredient.save();
    assertTrue(newIngredient.all().contains(newIngredient));
  }

  @Test
  public void getId_returnsId() {
    Ingredient newIngredient = new Ingredient("Potato");
    newIngredient.save();
    Ingredient savedIngredient = Ingredient.find(newIngredient.getId());
    assertTrue(newIngredient.getId() == savedIngredient.getId());
  }

  @Test
  public void find_returnsIngredientFromDatabase() {
    Ingredient newIngredient = new Ingredient("Ground beef");
    newIngredient.save();
    assertEquals(newIngredient, Ingredient.find(newIngredient.getId()));
  }

  @Test
  public void update_updatesIngredientProperties() {
    Ingredient newIngredient = new Ingredient("Bulgarian Cheese");
    newIngredient.save();
    newIngredient.update("Romaine Lettuce");
    assertEquals("Romaine Lettuce", newIngredient.getName());
  }

  @Test
  public void delete_removesIngredientFromDatabase_true() {
    Ingredient newIngredient = new Ingredient("Ricolas");
    newIngredient.save();
    Recipe newRecipe = new Recipe("Cheese balls", "Mash together every cheese");
    newRecipe.save();
    newRecipe.addIngredient(newIngredient);
    assertTrue(Ingredient.all().contains(newIngredient));
    assertTrue(newRecipe.getIngredients().contains(newIngredient));
    newIngredient.delete();
    assertFalse(Ingredient.all().contains(newIngredient));
    assertFalse(newRecipe.getIngredients().contains(newIngredient));
  }


}
