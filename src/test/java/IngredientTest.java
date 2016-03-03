import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class IngredientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void tag_instantiatesCorrectly_true() {
    Ingredient newIngredient = new Ingredient("Italian");
    assertTrue(newIngredient instanceof Ingredient);
  }

  @Test
  public void getName_returnsName_string() {
    Ingredient newIngredient = new Ingredient("Italian");
    assertEquals("Italian", newIngredient.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Ingredient.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfIngredientsAreTheSame() {
    Ingredient newIngredient = new Ingredient("Breakfast");
    Ingredient newIngredient2 = new Ingredient("Breakfast");
    assertTrue(newIngredient.equals(newIngredient2));
  }

  @Test
  public void save_savesIngredientToDatabase_true() {
    Ingredient newIngredient = new Ingredient("Lunch");
    newIngredient.save();
    assertTrue(newIngredient.all().contains(newIngredient));
  }

  @Test
  public void getId_returnsId() {
    Ingredient newIngredient = new Ingredient("lunch");
    newIngredient.save();
    Ingredient savedIngredient = Ingredient.find(newIngredient.getId());
    assertTrue(newIngredient.getId() == savedIngredient.getId());
  }

  @Test
  public void find_returnsTagFromDatabase() {
    Ingredient newIngredient = new Ingredient("Italian");
    newIngredient.save();
    assertEquals(newIngredient, Ingredient.find(newIngredient.getId()));
  }

  @Test
  public void update_updatesIngredientProperties() {
    Ingredient newIngredient = new Ingredient("Bulgarian");
    newIngredient.save();
    newIngredient.update("Romainian");
    assertEquals("Romainian", newIngredient.getName());
  }

  @Test
  public void delete_removesIngredientFromDatabase_true() {
    Ingredient newIngredient = new Ingredient("Latvian");
    newIngredient.save();
    assertTrue(Ingredient.all().contains(newIngredient));
    newIngredient.delete();
    assertFalse(Ingredient.all().contains(newIngredient));
  }

}
