package ua.goit.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public class MyCrudApp {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/users";
    private static final Gson gson = new Gson();

    public static UserInfo createUser(){
        try{
            URL url = new URL(BASE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            String requestBody = "{\"name\":\"Nobody Unknown\"," +
                    "\"username\":\"void\"," +
                    "\"email\":\"nounvo@example.com\"," +
                    "\"address\":\"nowhere\"}";

            try(OutputStream outputStream = connection.getOutputStream()){
                outputStream.write(requestBody.getBytes());
                outputStream.flush();
            }

            int responseCode = connection.getResponseCode();
            if(HttpURLConnection.HTTP_CREATED == responseCode){
                return getUserInfo(connection);
            }
        }catch (IOException e) {
            throw new RuntimeException("Something goes wrong");
        }
        return null;
    }

    public static UserInfo updateUserById(int id){
        try{
            URL url = new URL(BASE_URL + "/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            String requestBody = "{\"id\":5," +
                    "\"name\":\"Nobody Unknown\"," +
                    "\"username\":\"void\"," +
                    "\"email\":\"nounvo@example.com\"," +
                    "\"address\":\"nowhere\"}";

            try(OutputStream outputStream = connection.getOutputStream()){
                outputStream.write(requestBody.getBytes());
                outputStream.flush();
            }

            int responseCode = connection.getResponseCode();
            if(HttpURLConnection.HTTP_CREATED == responseCode){
                return getUserInfo(connection);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static boolean deleteUserById(int id){
        try {
            URL url = new URL(BASE_URL + "/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            int responseCode = connection.getResponseCode();
            connection.disconnect();
            return responseCode >= HttpURLConnection.HTTP_OK && responseCode < HttpURLConnection.HTTP_MULT_CHOICE;
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    public static List<UserInfo> getUsers() {
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Type userListType = new TypeToken<List<UserInfo>>() {
                    }.getType();
                    return gson.fromJson(response.toString(), userListType);
                }
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Optional<UserInfo> getUserById(int id) {
        try {
            URL url = new URL(BASE_URL + "/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    UserInfo user = gson.fromJson(response.toString(), UserInfo.class);
                    return Optional.ofNullable(user);
                }
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public static Optional<UserInfo> getUserByUsername(String username) {
        try {
            URL url = new URL(BASE_URL + "?username=" + username);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Type userListType = new TypeToken<List<UserInfo>>() {
                    }.getType();
                    List<UserInfo> users = gson.fromJson(response.toString(), userListType);
                    if (!users.isEmpty()) {
                        return Optional.of(users.get(0));
                    }
                }
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    private static UserInfo getUserInfo(HttpURLConnection connection) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return gson.fromJson(response.toString(), UserInfo.class);
        }
    }
}
