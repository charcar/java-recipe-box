import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class RecipeTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void recipe_instantiatesCorrectly_true() {
    Recipe newRecipe = new Recipe("Grilled Cheese", "Make a grilled cheese sandwich");
    assertTrue(newRecipe instanceof Recipe);
  }

  @Test
  public void getName_returnsName_string() {
    Recipe newRecipe = new Recipe("Grilled Cheese", "Make a grilled cheese sandwich");
    assertEquals("Grilled Cheese", newRecipe.getName());
  }

  @Test
  public void getInstructions_returnsInstructions_string() {
    Recipe newRecipe = new Recipe("Grilled Cheese", "Make a grilled cheese sandwich");
    assertEquals("Make a grilled cheese sandwich", newRecipe.getInstructions());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Recipe.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfIngredientsAreTheSame() {
    Recipe newRecipe = new Recipe("Scrambled Eggs", "Do the wishy washy");
    Recipe newRecipe2 = new Recipe("Scrambled Eggs", "Do the wishy washy");
    assertTrue(newRecipe.equals(newRecipe2));
  }

  @Test
  public void save_savesIngredientToDatabase_true() {
    Recipe newRecipe = new Recipe("Scrambled Eggs", "Do the wishy washy");
    newRecipe.save();
    assertTrue(newRecipe.all().contains(newRecipe));
  }

  @Test
  public void getId_returnsId() {
    Recipe newRecipe = new Recipe("Coffee", "Put the hotdog in the bun and put it in your mouth");
    newRecipe.save();
    Recipe savedIngredient = Recipe.find(newRecipe.getId());
    assertTrue(newRecipe.getId() == savedIngredient.getId());
  }

  @Test
  public void find_returnsRecipeFromDatabase() {
    Recipe newRecipe = new Recipe("Coffee", "Put the hotdog in the bun and put it in your mouth");
    newRecipe.save();
    assertEquals(newRecipe, Recipe.find(newRecipe.getId()));
  }

  @Test
  public void update_updatesRecipeProperties() {
    Recipe newRecipe = new Recipe("Coffee", "Put the hotdog in the bun and put it in your mouth");
    newRecipe.save();
    newRecipe.update("Romainian", "Put the bulldog in hottub");
    assertEquals("Romainian", newRecipe.getName());
  }

  @Test
  public void delete_removesRecipeFromDatabaseTables_true() {
    Recipe newRecipe = new Recipe("Latvian", "cook well");
    newRecipe.save();
    Ingredient newGreedy = new Ingredient("Mozza");
    newGreedy.save();
    newRecipe.addIngredient(newGreedy);
    assertTrue(Recipe.all().contains(newRecipe));
    assertTrue(newGreedy.getRecipes().contains(newRecipe));
    newRecipe.deleteRecipe();
    assertFalse(Recipe.all().contains(newRecipe));
    assertFalse(newGreedy.getRecipes().contains(newRecipe));
  }

}
