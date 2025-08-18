package service;

import dto.UserDTO;
import mapper.UserMapper;
import model.User;
import persistence.UserDAO;

public class UserService {
    private final UserDAO dao = new UserDAO();

    public boolean register(UserDTO dto) throws Exception {
        User user = UserMapper.toEntity(dto);
        return dao.register(user);
    }

    public UserDTO login(String username, String password) throws Exception {
        User user = dao.login(username, password);
        if (user != null) {
            return UserMapper.toDTO(user);
        }
        return null;
    }
}
