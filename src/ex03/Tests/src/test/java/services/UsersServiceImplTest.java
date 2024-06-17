package services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import edu.school21.services.UsersServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class UsersServiceImplTest {
    private static UsersServiceImpl usersService;
    private static User user;
    private static UsersRepository mockRepository;

    @BeforeAll
    static void setUp() {
        mockRepository = Mockito.mock(UsersRepository.class);
        usersService = new UsersServiceImpl(mockRepository);
        user = new User();
        user.setLogin("testuser");
        user.setPassword("password");
        user.setAuthenticateStatus(false);
    }

    @AfterEach
    void tearDown() {
        reset(mockRepository);
    }

    @Test
    void testAuthenticateSuccess() throws AlreadyAuthenticatedException {
        when(mockRepository.findByLogin("testuser")).thenReturn(user);
        boolean result = usersService.authenticate("testuser", "password");
        verify(mockRepository, times(1)).update(user);
        Assertions.assertTrue(result);
    }

    @Test
    void testAuthenticateIncorrectLogin() throws AlreadyAuthenticatedException {
        when(mockRepository.findByLogin("nonexistentuser")).thenThrow(AlreadyAuthenticatedException.class);
        boolean result = usersService.authenticate("nonexistentuser", "password");
        verify(mockRepository, never()).update(any(User.class));
        Assertions.assertFalse(result);
    }

    @Test
    void testRepositoryNull() throws AlreadyAuthenticatedException {
        UsersServiceImpl nullRepositoryService = new UsersServiceImpl(null);
        boolean result = nullRepositoryService.authenticate("testuser", "password");
        Assertions.assertFalse(result);
    }

    @Test
    void testAuthenticateIncorrectPassword() throws AlreadyAuthenticatedException {
        when(mockRepository.findByLogin("testuser")).thenReturn(null);
        boolean result = usersService.authenticate("testuser", "wrongpassword");
        verify(mockRepository, never()).update(any(User.class));
        Assertions.assertFalse(result);
    }
}
