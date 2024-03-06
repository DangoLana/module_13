package org.example;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static final String USERS_URL = "https://jsonplaceholder.typicode.com/users";
    private static final String USERS_URL1 = "https://jsonplaceholder.typicode.com/users/";

    public static void main(String[] args) throws IOException, InterruptedException {
        User user = new User();
        user.setName("Oleg");

        final User newUser = HttpUtilsForUser.sendPost(URI.create(USERS_URL), user);
        System.out.println("Created user: " + newUser);



        final User userToUpdate = new User();
        userToUpdate.setId(newUser.getId());
        userToUpdate.setName("Updated Oleg");
        final URI updateUserUri = URI.create(USERS_URL1 + newUser.getId());
        final User updatedUser = HttpUtilsForUser.sendPatch(updateUserUri, userToUpdate);
        System.out.println("Updated user: " + updatedUser);



        final User userById = HttpUtilsForUser.sendGet(URI.create(USERS_URL1 + newUser.getId()));
        System.out.println("User by id: " + userById);


        final List<User> users = HttpUtilsForUser.sendGetAllUsers(URI.create(USERS_URL));
        String usernameToFind = "Bret";
        List<User> usersByUsernameToFind = users.stream()
                .filter(u -> usernameToFind.equals(u.getUsername()))
                .collect(Collectors.toList());
        System.out.println("Users by name: " + usersByUsernameToFind);


        HttpUtilsForUser.sendDeleteUser(URI.create(USERS_URL1 + newUser.getId()), newUser);

//
//        CommentsByPost commentsByPost = new CommentsByPost();
//        commentsByPost. getAndSaveCommentsForLastPostOfUser(1);

        try {
            String[] titles = SortedOpenTask.sortTasks();
            for (String title : titles) {
                System.out.println("Open task: " + title);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}