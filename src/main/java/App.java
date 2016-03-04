import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.List;

public class App {

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/ingredients", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Ingredient> ingredients = Ingredient.all();
        if(ingredients.size() == 0) {
          ingredients = null;
        }
      model.put("ingredients", ingredients);
      model.put("template", "templates/ingredients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/ingredients/add", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Ingredient newIngredient = new Ingredient(name);
      newIngredient.save();
      response.redirect("/ingredients");
      return null;
    });

    get("/ingredients/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Integer ingredientId = Integer.parseInt(request.params(":id"));
      Ingredient ingredient = Ingredient.find(ingredientId);
      model.put("ingredient", ingredient);
      model.put("template", "templates/ingredient.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/ingredients/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Integer ingredientId = Integer.parseInt(request.params(":id"));
      Ingredient ingredient = Ingredient.find(ingredientId);
      ingredient.delete();
      response.redirect("/ingredients");
      return null;
    });

    get("/recipes", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Recipe> recipes = Recipe.all();
      model.put("recipes", recipes);
      model.put("template", "templates/recipes.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/recipes/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int recipeId = Integer.parseInt(request.params(":id"));
      Recipe recipe = Recipe.find(recipeId);
      model.put("recipe", recipe);
      List<Ingredient> ingredients = Ingredient.all();
      model.put("ingredients", ingredients);
      model.put("template", "templates/recipe.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/recipes/add", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String instructions = request.queryParams("instructions");
      Recipe newRecipe = new Recipe(name, instructions);
      newRecipe.save();
      response.redirect("/recipes");
      return null;
    });

  }
}
