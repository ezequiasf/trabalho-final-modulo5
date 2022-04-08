package com.dbccompany.trabalhofinalmod5.repository;

import com.dbccompany.trabalhofinalmod5.config.ConnectionMongo;
import com.dbccompany.trabalhofinalmod5.entity.Classification;
import com.dbccompany.trabalhofinalmod5.entity.RecipeEntity;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;

@Repository
public class RecipeRepository {

    private final static String DATABASE = "recipes_app";
    private final static String COLLECTION = "recipes";

    public RecipeEntity findByRecipeName(String recipeName) {
        MongoClient client = ConnectionMongo.createConnection();
        Document docRecipe = getCollectionRecipe(client)
                .find(new Document("recipeName", recipeName)).first();
        ConnectionMongo.closeConnection(client);
        return convertDocument(docRecipe);
    }

    public void saveRecipe(RecipeEntity recipe) {
        MongoClient client = ConnectionMongo.createConnection();
        getCollectionRecipe(client).insertOne(
                new Document("author", recipe.getAuthor())
                        .append("recipeName", recipe.getRecipeName())
                        .append("prepareRecipe", recipe.getPrepareRecipe())
                        .append("prepareTime", recipe.getPrepareTime())
                        .append("price", recipe.getPrice())
                        .append("calories", recipe.getCalories())
                        .append("ingredients", recipe.getIngredients())
                        .append("classifications", recipe.getClassifications())
        );
        ConnectionMongo.closeConnection(client);
    }

    public void updateRecipe(String recipeName, String author, RecipeEntity recipe) {
        MongoClient client = ConnectionMongo.createConnection();
        getCollectionRecipe(client).updateOne(new Document("recipeName", recipeName).append("author", author),
                new Document("$set", convertRecipeEntity(recipe)));
        ConnectionMongo.closeConnection(client);
    }

    public void deleteRecipe (String recipeName, String author){
        MongoClient client = ConnectionMongo.createConnection();
        getCollectionRecipe(client).deleteOne(new Document("recipeName", recipeName).append("author", author));
        ConnectionMongo.closeConnection(client);
    }

    private RecipeEntity convertDocument(Document docRecipe) {
        return RecipeEntity.builder().id(docRecipe.getObjectId("_id").toString())
                .recipeName(docRecipe.getString("recipeName"))
                .author(docRecipe.getString("author"))
                .calories(docRecipe.getDouble("calories"))
                .prepareRecipe(docRecipe.getString("prepareRecipe"))
                .ingredients(docRecipe.getList("ingredients", String.class))
                .classifications(docRecipe.getList("classifications", Classification.class))
                .build();
    }

    private Document convertRecipeEntity(RecipeEntity recipe) {
        return new Document("author", recipe.getAuthor())
                .append("recipeName", recipe.getRecipeName())
                .append(("prepareRecipe"), recipe.getPrepareRecipe())
                .append("prepareTime", recipe.getPrepareTime())
                .append("calories", recipe.getCalories())
                .append("ingredients", recipe.getIngredients())
                .append("classifications", recipe.getClassifications());
    }

    private MongoCollection<Document> getCollectionRecipe(MongoClient client) {
        return client.getDatabase(DATABASE).getCollection(COLLECTION);
    }
}
