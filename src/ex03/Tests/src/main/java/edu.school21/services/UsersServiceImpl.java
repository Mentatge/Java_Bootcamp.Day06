package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImpl {
    private UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean authenticate(String login, String password) {
        if (usersRepository == null) {
            return false;
        }
        User user = getUser(login);
        if (user == null) {
            return false;
        }
        if (user.getAuthenticateStatus()) {
            throw new AlreadyAuthenticatedException("User already authenticated");
        }
        if (!user.getPassword().equals(password)) {
            user.setAuthenticateStatus(false);
            return false;
        }
        user.setAuthenticateStatus(true);
        return tryToUpdate(user);
    }

    private boolean tryToUpdate(User user) {
        try {
            usersRepository.update(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while updating user");
            return false;
        }
    }

    private User getUser(String login) {
        User user;
        try {
            user = usersRepository.findByLogin(login);
            return user;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

}
