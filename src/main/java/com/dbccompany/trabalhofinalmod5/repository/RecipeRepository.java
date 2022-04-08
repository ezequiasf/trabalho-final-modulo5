package com.dbccompany.trabalhofinalmod5.repository;

import com.dbccompany.trabalhofinalmod5.config.ConnectionMongo;
import com.dbccompany.trabalhofinalmod5.entity.Classification;
import com.dbccompany.trabalhofinalmod5.entity.RecipeEntity;
import com.dbccompany.trabalhofinalmod5.entity.UserEntity;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Projections.fields;

@Repository
public class RecipeRepository {

    private final static String DATABASE = "recipes_app";
    private final static String COLLECTION = "recipes";

    public RecipeEntity findByRecipeName(String recipeName) {
        MongoClient client = ConnectionMongo.createConnection();
        Document docRecipe = getCollectionRecipe(client)
                .find(new Document("recipeName", recipeName)).first();
        ;
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

    private Document convertUserEntity(UserEntity user) {
        return new Document("username", user.getUsername())
                .append("email", user.getEmail())
                .append(("age"), user.getAge())
                .append("password", user.getPassword())
                .append("isactive", user.isIsactive());
    }

    private MongoCollection<Document> getCollectionRecipe(MongoClient client) {
        return client.getDatabase(DATABASE).getCollection(COLLECTION);
    }
}
