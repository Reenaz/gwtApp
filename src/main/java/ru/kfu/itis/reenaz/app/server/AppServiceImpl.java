package ru.kfu.itis.reenaz.app.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import ru.kfu.itis.reenaz.app.client.AppService;
import ru.kfu.itis.reenaz.app.server.entity.User;
import ru.kfu.itis.reenaz.app.server.repository.UserRepository;
import ru.kfu.itis.reenaz.app.server.service.AppContext;
import ru.kfu.itis.reenaz.app.shared.UserDTO;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class AppServiceImpl extends RemoteServiceServlet implements AppService {
    private final UserRepository users = AppContext.getBean(UserRepository.class);

    @Override
    public List<UserDTO> list() {
        List<UserDTO> users = new ArrayList<>();
        for (User user : this.users.findAll()) {
            users.add(new UserDTO(user.getId(), user.getName(), user.getBirthday()));
        }
        return users;
    }

    @Override
    public UserDTO save(UserDTO data) {
        User user = new User();
        user.setId(data.getId());
        user.setName(data.getName());
        user.setBirthday(data.getBirthday());
        this.users.save(user);
        data.setId(user.getId());
        return data;
    }

    @Override
    public boolean delete(UserDTO user) {
        this.users.delete(user.getId());
        return true;
    }
}