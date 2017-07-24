package ru.kfu.itis.reenaz.app.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ru.kfu.itis.reenaz.app.server.entity.User;
import ru.kfu.itis.reenaz.app.shared.UserDTO;

import java.util.List;

@RemoteServiceRelativePath("AppService")
public interface AppService extends RemoteService {
    List<UserDTO> list();

    UserDTO save(UserDTO user);

    boolean delete(UserDTO user);
}
