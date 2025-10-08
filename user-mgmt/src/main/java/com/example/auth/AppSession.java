package com.example.auth;

public final class AppSession {
    private static volatile String currentUser;

    private AppSession() {}
    public static boolean login(String user, char[] pass) {
        boolean ok = "admin".equals(user) && "admin".contentEquals(new String(pass));
        if (ok) currentUser = user;
        return ok;
    }
    public static boolean isLoggedIn() { return currentUser != null; }
    public static void logout() { currentUser = null; }
    public static String user() { return currentUser; }
}