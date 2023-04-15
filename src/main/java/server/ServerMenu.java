package server;

import com.google.gson.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public interface ServerMenu {
    static void serverMenu(DataInputStream dis, DataOutputStream dos) {
        try {
            ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
            Lock readLock = lock.readLock();
            Lock writeLock = lock.writeLock();

            Gson gson = new Gson();
            String content = new String(Files.readAllBytes(Paths.get("src/main/java/server/data/db.json")));
            JsonObject database = gson.fromJson(content, JsonObject.class);
            JsonObject receivedMessage = gson.fromJson(dis.readUTF(), JsonObject.class);

            switch (receivedMessage.get("type").getAsString()) {

                //Sets JSON by making , adding, or updating it.
                case "set" -> {
                    try {
                        writeLock.lock();
                        set(receivedMessage.get("key"), receivedMessage.get("value"), database);
                        String responseJSON = gson.toJson(Map.of("response", "OK"));
                        dos.writeUTF(responseJSON);

                    } finally {
                        writeLock.unlock();
                    }
                }

                //Gets JSON element.
                    case "get" -> {
                        try {
                            readLock.lock();
                            JsonElement replyMessage = get(receivedMessage.get("key"), database);
                            dos.writeUTF(gson.toJson(Map.of("response", "OK", "value", replyMessage)));

                        } catch (NullPointerException e) {
                            String responseJSON = gson.toJson(Map.of("response", "ERROR",
                                    "reason", "No such key"));
                            dos.writeUTF(responseJSON);

                        } finally {
                            readLock.unlock();
                        }
                    }

                    //Deletes JSON element.
                    case "delete" -> {
                        writeLock.lock();
                        try {
                            delete(receivedMessage.get("key"), database);
                            dos.writeUTF(gson.toJson(Map.of("response", "OK")));

                            } catch (NullPointerException e) {
                                String responseJSON = gson.toJson(Map.of("response", "ERROR",
                                        "reason", "No such key"));
                                dos.writeUTF(responseJSON);

                            } finally {
                            writeLock.unlock();
                        }
                }


                //Exit command to shut down server for the Hyperskill project tests.
                    case "exit" -> {
                        String responseJSON = gson.toJson(Map.of("response", "OK"));
                        dos.writeUTF(responseJSON);
                        Server.isRunning = false;
                        Server.serverKill();
                    }
                    default -> System.out.println("Wrong input");
                }
            } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Writes JSON to db.json
     *
     * @param database Current JSON database entry.
     * */
    private static void writeJson(JsonObject database) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/server/data/db.json"))) {
            bw.write(database.toString());

        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the desired element of the JSON to a new value, or if it does not exist it will add it. Use's either a JSON
     * array, or regular string, as a key from the CLI argument from -k.
     *
     * @param key is either a JSON Array, or basic key. Comes from CLI arguments.
     * @param value CLI arg for what will replace, or be added, to the database.
     * @param database Current JSON database entry.
     * */
    private static void set(JsonElement key, JsonElement value, JsonObject database) {
        try {
            if (database == null) {
                database = new JsonObject();
                database.add(key.getAsString(), value);

            } else {
                if (key.isJsonPrimitive()) {
                    database.add(key.getAsString(),value);

                } else if (key.isJsonArray()){
                    JsonArray keys = key.getAsJsonArray();
                    String toAdd = keys.remove(keys.size() - 1).getAsString();
                    findElement(keys, true, database).getAsJsonObject().add(toAdd, value);

                }
            }

        } finally {
            if(database != null) {
            writeJson(database);}
        }
    }

    /**
     * Gets the desired element of the JSON database entry, chosen by a key JSON array, or regular string, determined
     * from the CLI arguments.
     *
     * @param key is either a JSON Array, or basic key. Comes from CLI arguments.
     * @param database Current JSON database entry.
     * */
    private static JsonElement get(JsonElement key, JsonObject database) {
            if (key.isJsonPrimitive() && database.has(key.getAsString())) {
                return database.get(key.getAsString());

            } else if (key.isJsonArray()) {
                return findElement(key.getAsJsonArray(), false, database);
            }
            throw new NullPointerException();
    }

    /**
     * Deletes an element from the JSON database entry, chosen by a key JSON array, or regular string, determined
     * from the CLI arguments.
     *
     * @param key is either a JSON Array, or basic key. Comes from CLI arguments.
     * @param database Current JSON database entry.
     * */
    private static void delete(JsonElement key, JsonObject database) {
        try {
            if (key.isJsonPrimitive() && database.has(key.getAsString())) {
                database.remove(key.getAsString());

            } else if (key.isJsonArray()) {
                JsonArray keys = key.getAsJsonArray();
                String toDelete = keys.remove(keys.size() - 1).getAsString();
                findElement(keys, true, database).getAsJsonObject().remove(toDelete);
            } else {
                throw new NullPointerException();
            }
        } finally {
            if(database != null) {
                writeJson(database);}
        }
    }

    /**
     * Finds the chosen JSON element in the JSON database entry. If entry does not exist, create it if it does not
     * exist if (createIfAbsent) true.
     * */
    private static JsonElement findElement (JsonArray keys, boolean createIfAbsent, JsonObject dataBase){
        JsonElement tmp = dataBase;
        if (createIfAbsent) {
            for(JsonElement key: keys){
                if(!tmp.getAsJsonObject().has(key.getAsString())){
                    tmp.getAsJsonObject().add(key.getAsString(), new JsonObject());
                }
                tmp = tmp.getAsJsonObject().get(key.getAsString());
            }

        } else {
            for(JsonElement key : keys) {
                if(!key.isJsonPrimitive() || !tmp.getAsJsonObject().has(key.getAsString())) {
                    throw new NullPointerException();
                }
                tmp = tmp.getAsJsonObject().get(key.getAsString());
            }
        }
        return tmp;
    }
}

