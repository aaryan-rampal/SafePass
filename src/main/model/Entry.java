package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represents an entry in the password manager including a name, username, password, url, and notes
public class Entry implements Writable {
    private String name;
    private String username;
    private Password password;
    private String url;
    private String notes;

    /**
     * @REQUIRES: name, username, url, and notes have non-zero length; password is not null
     * @EFFECTS: creates entry object which instantiates all the fields with the parameters that are passed into the
     * constructor
     */
    public Entry(String name, String username, Password password, String url, String notes) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.url = url;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("username", username);
        json.put("password", password.toJson());
        json.put("url", url);
        json.put("notes", notes);
        return json;
    }

}
