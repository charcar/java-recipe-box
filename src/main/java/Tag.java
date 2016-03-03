import org.sql2o.*;
import java.util.List;


public class Tag {
  private String name;
  private int id;

  public Tag (String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public static List<Tag> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "Select * FROM tags";
      return con.createQuery(sql).executeAndFetch(Tag.class);
    }
  }

  @Override
  public boolean equals(Object otherTag) {
    if(!(otherTag instanceof Tag)) {
      return false;
    } else {
      Tag newTag = (Tag) otherTag;
      return this.getName().equals(newTag.getName()) && this.getId() == newTag.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tags (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Tag find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tags WHERE id=:id;";
      Tag tag = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Tag.class);
      return tag;
    }
  }

  public void update(String newName) {
    this.name = newName;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tags SET name = :newName WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("newName", newName)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM tags WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public List<Recipe> getRecipes() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT recipes.* FROM tags JOIN recipes_tags ON tags.id = recipes_tags.tag_id JOIN recipes ON recipes_tags.recipe_id = recipes.id WHERE tags.id = :id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Recipe.class);
    }
  }



}
