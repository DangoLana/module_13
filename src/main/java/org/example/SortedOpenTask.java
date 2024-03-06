package org.example;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class SortedOpenTask {

    private static final HttpClient CLIENT_OPEN_TASK = HttpClient.newHttpClient();
    private static final Gson GSON_OPEN_TASK = new Gson();
    private static final String OPEN_TASK_URL = "https://jsonplaceholder.typicode.com/users/";

    public static String[] sortTasks() throws IOException, InterruptedException {
        int userId = 1;
        URI uri = URI.create(OPEN_TASK_URL + userId + "/todos");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = CLIENT_OPEN_TASK.send(request, HttpResponse.BodyHandlers.ofString());

        OpenTask[] tasks = GSON_OPEN_TASK.fromJson(response.body(), OpenTask[].class);

        List<String> titles = new ArrayList<>();
        for (OpenTask task : tasks) {
            if (!task.isCompleted()) {
                titles.add(task.getTitle());
            }
        }

        return titles.toArray(new String[0]);
    }
}
