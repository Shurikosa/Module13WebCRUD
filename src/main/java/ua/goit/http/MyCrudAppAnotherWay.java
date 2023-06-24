package ua.goit.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyCrudAppAnotherWay {

    static HttpClient client = HttpClient.newHttpClient();

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/users";

    public static String createUser() {
        try {
            String url = BASE_URL;

            String requestBody = "{\"name\":\"Nobody Unknown\"," +
                    "\"username\":\"void\"," +
                    "\"email\":\"nounvo@example.com\"," +
                    "\"address\":\"nowhere\"}";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String updateUserById(int userId, String jsonUserFilePath) {
        try {
            String url = BASE_URL + userId;

            String requestBody = "{\"id\":5," +
                    "\"name\":\"Nobody Unknown\"," +
                    "\"username\":\"void\"," +
                    "\"email\":\"nounvo@example.com\"," +
                    "\"address\":\"nowhere\"}";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public int deleteUserById(int id) {
        try {
            String url = BASE_URL + id;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .DELETE()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode();
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
        return 0;
    }

    public static String getAllUsers(){
       try {
           String url = BASE_URL;
           HttpRequest request = HttpRequest.newBuilder()
                   .uri(URI.create(url))
                   .GET()
                   .build();
           HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
           return response.body();
       }catch (IOException | InterruptedException e){
           e.printStackTrace();
       }
        return null;
    }

    public static String getUserById(int id){
        try {
            String url = BASE_URL + id;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getUserByName(String userName) {
        try {
            String url = BASE_URL + "?username=" + userName;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }
    /*
    --------------------------------------------------------------------------------Task2
     */
    public static void createJsonWithAllCoommentsFromLastPostByUserId(int userId){
        String allPosts = getPostsByUserId(userId);
        int biggestPostId = biggestPostId(allPosts);
        String allComments = getAllCommentsByPostId(biggestPostId);
        final String PATH_TO_JSON_FILE = "src/main/resources/" + "user-" + userId + "-post-" + biggestPostId + "-comments.json";
        createJsonWithComments(allComments,PATH_TO_JSON_FILE);
        System.out.println("JSON filepath: " + PATH_TO_JSON_FILE);
    }

    private static String getPostsByUserId(int userId){
        try {
            String url = "https://jsonplaceholder.typicode.com/users/" + userId + "/posts";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    private static int biggestPostId(String posts){
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\\"id\": \\d+");
        Matcher matcher = pattern.matcher(posts);
        while (matcher.find()){
            matches.add(matcher.group());
        }
        String lastPostId = matches.get(matches.size() - 1);
        int result = getNumberFromString(lastPostId);
        return result;
    }
    private static int getNumberFromString(String text) {
        String[] parts = text.split(" ");
        return Integer.parseInt(parts[1]);
    }

    private static String getAllCommentsByPostId(int postId){
        try{
            String url = "https://jsonplaceholder.typicode.com/posts/" + postId + "/comments";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
            return response.body();
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    private static void createJsonWithComments(String allComments, String jsonFilePath){

        Comments[] comments = createCommentsFromJson(allComments);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String outputString = gson.toJson(comments);
        try (FileWriter output = new FileWriter(jsonFilePath)) {
            output.write(outputString);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
    private static Comments[] createCommentsFromJson(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(json, Comments[].class);
    }

    /*
    *----------------------------------------------------------------------------Task3
     */

    public static void createTodosListAndPrint(int userId){
        String allTodos = getAllTodosByUserId(userId);
        List<Todo> allOpenedTodosJson = getAllOpenedTodosJsonList(allTodos);
        System.out.println(allOpenedTodosJson);
    }

    private static String getAllTodosByUserId(int userId){
        try{
            String url = "https://jsonplaceholder.typicode.com/users/" + userId + "/todos";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        }catch (IOException  | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    private static List<Todo>getAllOpenedTodosJsonList(String allTodos){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Todo[] todosArray = gson.fromJson(allTodos, Todo[].class);
        List<Todo> todoList = new ArrayList<>(Arrays.asList(todosArray));
        List<Todo> openedTodosList = new ArrayList<>();
        for(Todo element : todoList){
            if(!element.isCompleted()){
                openedTodosList.add(element);
            }
        }
        return openedTodosList;
    }
}


