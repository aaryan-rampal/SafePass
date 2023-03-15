package model;

import org.json.JSONObject;
import persistence.Writable;

import java.security.SecureRandom;

// Represents an entry in the password manager including a name, username, password, url, and notes
public class Entry implements Writable {
    private final String name;
    private final String username;
    private final Password password;
    private final String url;
    private final String notes;
    private ByteConvertor bc;

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
        bc = new ByteConvertor();
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
        byte[] saltBytes = createSalt();
        Keyset keySet = new Keyset("A");

        json.put("salt", bc.bytesToString(saltBytes));
        encryptField(name, json, "name", saltBytes, keySet);
        encryptField(username, json, "username", saltBytes, keySet);
        encryptField(password.getPassword(), json, "password", saltBytes, keySet);
        encryptField(url, json, "url", saltBytes, keySet);
        encryptField(notes, json, "notes", saltBytes, keySet);

        return json;
    }

    private void encryptField(String field, JSONObject json, String nameOfField, byte[] salt, Keyset keySet) {
        byte[] cipherBytes = keySet.encrypt(field, salt);
        json.put(nameOfField, bc.bytesToString(cipherBytes));
    }

    private byte[] createSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return saltBytes;
    }

}
