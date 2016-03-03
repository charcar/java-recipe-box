import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class RecipeTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void tag_instantiatesCorrectly_true() {
    Recipe newRecipe = new Recipe("Italian");
    assertTrue(newRecipe instanceof Recipe);
  }

  @Test
  public void getName_returnsName_string() {
    Recipe newRecipe = new Recipe("Italian");
    assertEquals("Italian", newRecipe.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Recipe.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfRecipesAreTheSame() {
    Recipe newRecipe = new Recipe("Breakfast");
    Recipe newRecipe2 = new Recipe("Breakfast");
    assertTrue(newRecipe.equals(newRecipe2));
  }

  @Test
  public void save_savesRecipeToDatabase_true() {
    Recipe newRecipe = new Recipe("Lunch");
    newRecipe.save();
    assertTrue(newRecipe.all().contains(newRecipe));
  }

  @Test
  public void getId_returnsId() {
    Recipe newRecipe = new Recipe("lunch");
    newRecipe.save();
    Recipe savedRecipe = Recipe.find(newRecipe.getId());
    assertTrue(newRecipe.getId() == savedRecipe.getId());
  }

  @Test
  public void find_returnsTagFromDatabase() {
    Recipe newRecipe = new Recipe("Italian");
    newRecipe.save();
    assertEquals(newRecipe, Recipe.find(newRecipe.getId()));
  }

  @Test
  public void update_updatesRecipeProperties() {
    Recipe newRecipe = new Recipe("Bulgarian");
    newRecipe.save();
    newRecipe.update("Romainian");
    assertEquals("Romainian", newRecipe.getName());
  }

  @Test
  public void delete_removesRecipeFromDatabase_true() {
    Recipe newRecipe = new Recipe("Latvian");
    newRecipe.save();
    assertTrue(Recipe.all().contains(newRecipe));
    newRecipe.delete();
    assertFalse(Recipe.all().contains(newRecipe));
  }

}
