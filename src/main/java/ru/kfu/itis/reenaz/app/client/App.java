package ru.kfu.itis.reenaz.app.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.DOM;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.ListDataProvider;
import ru.kfu.itis.reenaz.app.shared.UserDTO;

import java.util.List;


public class App implements EntryPoint {

    private final AppServiceAsync userService = GWT.create(AppService.class);
    private final TextBox name = new TextBox();
    private final DateBox birthday = new DateBox();

    private Integer id = -1;
    private Integer userIndex = -1;

    private ListDataProvider<UserDTO> createTable(CellTable<UserDTO> table) {
        TextColumn<UserDTO> nameColumn = new TextColumn<UserDTO>() {
            @Override
            public String getValue(UserDTO user) {
                return user.getName();
            }
        };
        TextColumn<UserDTO> addressColumn = new TextColumn<UserDTO>() {
            @Override
            public String getValue(UserDTO user) {
                return user.getBirthday().toString();
            }
        };
        table.addColumn(nameColumn, "Name");
        table.addColumn(addressColumn, "Birthday");
        ListDataProvider<UserDTO> dataProvider = new ListDataProvider<UserDTO>();
        dataProvider.addDataDisplay(table);
        this.userService.list(new AsyncCallback<List<UserDTO>>() {
            @Override
            public void onFailure(Throwable throwable) {
                GWT.log("error", throwable);
            }

            @Override
            public void onSuccess(List<UserDTO> people) {
                dataProvider.getList().addAll(people);
            }
        });
        return dataProvider;
    }


    public void onModuleLoad() {
        CellTable<UserDTO> table = new CellTable<UserDTO>();
        ListDataProvider<UserDTO> dataProvider = createTable(table);
        DialogBox dialog = editDialog(dataProvider);
        Button add = new Button("Добавить", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                name.setValue("");
                birthday.setValue(null);
                id = -1;
                dialog.setText("Добавить запись");
                dialog.center();
                dialog.show();
            }
        });
        Button edit = new Button("Изменить", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                UserDTO userDTO = dataProvider.getList().get(table.getKeyboardSelectedRow());
                name.setValue(userDTO.getName());
                birthday.setValue(userDTO.getBirthday());
                id = userDTO.getId();
                userIndex = dataProvider.getList().indexOf(userDTO);
                dialog.setText("Изменить запись");
                dialog.show();
                dialog.center();
            }
        });
        Button delete = new Button("Удалить", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                final int index = table.getKeyboardSelectedRow();
                UserDTO user = dataProvider.getList().get(index);
                userService.delete(user, new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        GWT.log("error", throwable);
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        dataProvider.getList().remove(index);
                    }
                });
            }
        });
        HorizontalPanel control = new HorizontalPanel();
        control.add(add);
        control.add(edit);
        control.add(delete);
        VerticalPanel panel = new VerticalPanel();
        panel.add(control);
        panel.add(table);
        RootPanel.get().add(panel);
    }

    private DialogBox editDialog(ListDataProvider<UserDTO> dataProvider) {
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Добавить запись");
        dialogBox.setAnimationEnabled(true);
        VerticalPanel dpanel = new VerticalPanel();
        HorizontalPanel usernamePanel = new HorizontalPanel();
        Label label = new Label("Username");
        label.setWidth("100px");
        usernamePanel.add(label);
        usernamePanel.add(name);
        dpanel.add(usernamePanel);

        HorizontalPanel dbirthdayPanel = new HorizontalPanel();
        label = new Label("Birthday");
        label.setWidth("100px");
        dbirthdayPanel.add(label);
        dbirthdayPanel.add(birthday);
        dpanel.add(dbirthdayPanel);
        HorizontalPanel dcontrol = new HorizontalPanel();
        dcontrol.add(new Button("Изменить", new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (!(birthday.getValue() == null && name.getValue() != null && !(name.toString()).equals(""))) {

                    userService.save(new UserDTO(id, name.getValue(), birthday.getValue()), new AsyncCallback<UserDTO>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            GWT.log("error", throwable);
                        }

                        @Override
                        public void onSuccess(UserDTO user) {
                            if (id != -1) {
                                dataProvider.getList().set( userIndex, user
                                );
                                dialogBox.hide();

                            } else {
                                dataProvider.getList().add(user);
                                dialogBox.hide();
                            }
                            dialogBox.hide();
                        }
                    });
                }
            }
        }));
        dcontrol.add(new Button("Отменить", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                dialogBox.hide();
            }
        }));
        dpanel.add(dcontrol);


        dialogBox.setWidget(dpanel);
        return dialogBox;
    }
}
