package org.example.services;

import org.example.Database;
import org.example.entities.User;

public class UserService {
        private final Database db;

        public UserService(Database db) {
            this.db = db;
        }

        public void register(String name) {
            db.getUsers().putIfAbsent(name, new User(name));
    }

        public boolean login(String name) {
            User user = db.getUsers().get(name);
            if (user != null)
                user.setLoggedIn(true);
            return true;
        }

        public void logout(String name) {
            User user = db.getUsers().get(name);
            if (user != null)
                user.setLoggedIn(false);
        }

        public User getActiveUser() {
            return db.getUsers().values().stream().filter(User::isLoggedIn).findFirst().orElse(null);
        }

}

