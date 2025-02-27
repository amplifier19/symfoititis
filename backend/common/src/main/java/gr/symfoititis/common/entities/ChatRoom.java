package gr.symfoititis.common.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ChatRoom {
    @Positive
    private Integer chat_id;
    @NotNull
    @NotBlank
    private String room;
    @NotNull
    @Positive
    private Integer dep_id;
    @NotNull
    @Positive
    private Integer c_id;
    @NotNull
    @NotBlank
    private String t_id;
    @NotNull
    @NotBlank
    private String s_id;

    public ChatRoom() {}

    public ChatRoom(String room, Integer dep_id, Integer c_id, String t_id, String s_id) {
        this.room = room;
        this.dep_id = dep_id;
        this.c_id = c_id;
        this.t_id = t_id;
        this.s_id = s_id;
    }

    public ChatRoom(Integer chat_id, String room, Integer dep_id, Integer c_id, String t_id, String s_id) {
        this.chat_id = chat_id;
        this.room = room;
        this.dep_id = dep_id;
        this.c_id = c_id;
        this.t_id = t_id;
        this.s_id = s_id;
    }

    public Integer getChat_id() {
        return chat_id;
    }

    public void setChat_id(Integer chat_id) {
        this.chat_id = chat_id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Integer getDep_id() {
        return dep_id;
    }

    public void setDep_id(Integer dep_id) {
        this.dep_id = dep_id;
    }

    public Integer getC_id() {
        return c_id;
    }

    public void setC_id(Integer c_id) {
        this.c_id = c_id;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }
}
