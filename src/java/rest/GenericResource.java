/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author Bobbie
 */
@Path("quote")
public class GenericResource {

    private static Map<Integer, String> quotes = new HashMap() {
        {
            put(1, "Friends are kisses blown to us by angels");
            put(2, "Do not take life too seriously. You will never get out of it alive");
            put(3, "Behind every great man, is a woman rolling her eyes");
        }
    };

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    private Integer nextId() {
        int i = 0;
        for (Integer j : quotes.keySet()) {
            if (i == j) {
                return nextId(++i);
            }
        }
        return i;
    }

    private Integer nextId(int i) {
        for (Integer j : quotes.keySet()) {
            if (i == j) {
                return nextId(++i);
            }
        }
        return i;
    }

    /**
     * Retrieves representation of an instance of rest.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    public String get(@PathParam("id") int key) {
        JsonObject quote = new JsonObject();
        //Get the second quote
        quote.addProperty("quote", quotes.get(key));
        String jsonResponse = new Gson().toJson(quote);

        return jsonResponse;
    }

    @GET
    @Path("random")
    @Produces("application/json")
    public String get() {
        JsonObject quote = new JsonObject();
        int key = new Random().nextInt(quotes.size()) + 1;//Get the second quote
        quote.addProperty("quote", quotes.get(key));
        String jsonResponse = new Gson().toJson(quote);

        return jsonResponse;
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param json
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes("application/json")
    public String post(String json) {
        //Get the quote text from the provided Json
        JsonObject newQuote = new JsonParser().parse(json).getAsJsonObject();
        String quote = newQuote.get("quote").getAsString();
        int nextId = nextId();
        quotes.put(nextId, quote);

        JsonObject returnQuote = new JsonObject();
        //Get the second quote
        returnQuote.addProperty("id", nextId);
        returnQuote.addProperty("quote", quotes.get(nextId));
        String jsonResponse = new Gson().toJson(returnQuote);

        return jsonResponse;

    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public String put(@PathParam("id") int key, String json) {
        JsonObject newQuote = new JsonParser().parse(json).getAsJsonObject();
        String quote = newQuote.get("quote").getAsString();
        if (quotes.containsKey(key)) {
            quotes.put(key, quote);

            JsonObject returnQuote = new JsonObject();
            //Get the second quote
            returnQuote.addProperty("id", key);
            returnQuote.addProperty("quote", quotes.get(key));
            String jsonResponse = new Gson().toJson(returnQuote);

            return jsonResponse;
        } else {
            return new Gson().toJson("Id doesnt contain a quote, please use Post");
        }
    }

    @DELETE
    @Path("{id}")
    @Consumes("application/json")
    public String delete(@PathParam("id") int key) {

        if (quotes.containsKey(key)) {
            JsonObject deletedQuote = new JsonObject();
            deletedQuote.addProperty("quote", quotes.remove(key));
            String jsonResponse = new Gson().toJson(deletedQuote);
            return jsonResponse;
        }
        return new Gson().toJson("Id is not used, nothing deleted");
    }
}
