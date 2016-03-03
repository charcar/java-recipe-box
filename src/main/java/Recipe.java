import org.sql2o.*;
import java.util.List;

public class Recipe {
  private String name;
  private String instructions;
  private int id;

  public Recipe(String name, String instructions) {
    this.name = name;
    this.instructions = instructions;
  }

  public String getName() {
    return name;
  }

  public String getInstructions() {
    return instructions;
  }

  public int getId() {
    return id;
  }

  public static List<Recipe> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM recipes";
      return con.createQuery(sql).executeAndFetch(Recipe.class);
    }
  }

  @Override
  public boolean equals(Object otherRecipe) {
    if(!(otherRecipe instanceof Recipe)) {
      return false;
    } else {
      Recipe newRecipe = (Recipe) otherRecipe;
      return this.getName().equals(newRecipe.getName()) && this.getId() == newRecipe.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipes (name, instructions) VALUES (:name, :instructions)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("instructions", this.instructions)
        .executeUpdate()
        .getKey();
    }
  }

  public static Recipe find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM recipes WHERE id = :id";
      Recipe recipe = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Recipe.class);
      return recipe;
    }
  }

  public void update(String newName, String newInstructions) {
    this.name = newName;
    this.instructions = newInstructions;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE recipes SET name = :newName, instructions = :newInstructions WHERE id = :id";
      con.createQuery(sql)
        .addParameter("newName", newName)
        .addParameter("newInstructions", newInstructions)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void deleteRecipe() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM recipes_ingredients WHERE recipe_id = :id;" + "DELETE FROM recipes_tags WHERE recipe_id = :id;" + "DELETE FROM recipes WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void addIngredient(Ingredient ingredient) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipes_ingredients (recipe_id, ingredient_id) VALUES (:recipe_id, :ingredient_id);";
      con.createQuery(sql)
        .addParameter("recipe_id", this.id)
        .addParameter("ingredient_id", ingredient.getId())
        .executeUpdate();
    }
  }

  public List<Ingredient> getIngredients() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT ingredients.* FROM recipes JOIN recipes_ingredients ON recipes.id = recipes_ingredients.recipe_id JOIN ingredients ON recipes_ingredients.ingredient_id = ingredients.id WHERE recipes.id = :id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Ingredient.class);
    }
  }


}
