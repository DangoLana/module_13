package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class CommentsByPost {
    private static final HttpClient CLIENT_POST = HttpClient.newHttpClient();
    private static final Gson GSON_POST = new Gson();
    public static void getAndSaveCommentsForLastPostOfUser(int userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/" + userId + "/posts"))
                .build();

        try {
            HttpResponse<String> response = CLIENT_POST.send(request, HttpResponse.BodyHandlers.ofString());
            List<Post> posts = GSON_POST.fromJson(response.body(), new TypeToken<List<Post>>(){}.getType());
            Post lastPost = posts.get(posts.size() - 1);

            request = HttpRequest.newBuilder()
                    .uri(URI.create("https://jsonplaceholder.typicode.com/posts/" + lastPost.getId() + "/comments"))
                    .build();

            response = CLIENT_POST.send(request, HttpResponse.BodyHandlers.ofString());
            String commentsJson = response.body();

            String fileName = "user-" + userId + "-post-" + lastPost.getId() + "-comments.json";
            try (FileWriter fileWriter = new FileWriter(fileName)) {
                fileWriter.write(commentsJson);
            }

            System.out.println("Comments for last post of user " + userId + " saved to " + fileName);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    }
