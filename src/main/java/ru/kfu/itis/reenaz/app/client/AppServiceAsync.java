package ru.kfu.itis.reenaz.app.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ru.kfu.itis.reenaz.app.shared.UserDTO;

import java.util.List;

public interface AppServiceAsync {
    void list(AsyncCallback<List<UserDTO>> async);

    void save(UserDTO user, AsyncCallback<UserDTO> async);

    void delete(UserDTO user, AsyncCallback<Boolean> async);
}
