package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public interface Receiver {
    static void receiver(DataInputStream dis, DataOutputStream dos, Server s) {
        try {
            File jsonDbFile = new File("src/server/test.json");
            Gson gson = new Gson();
            JsonMessage jsm = gson.fromJson(dis.readUTF(), JsonMessage.class);

            String inputType = jsm.getType();
            String inputKey = jsm.getKey();
            String inputValue = jsm.getValue();

                switch (inputType) {
                    case "set" -> {
                        try (FileOutputStream fos = new FileOutputStream(jsonDbFile);
                             OutputStreamWriter osr = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {

                            JsonMessage.jsonDbMap.put(inputKey, inputValue);
                            gson.toJson(JsonMessage.jsonDbMap, osr);

                            String responseJSON = gson.toJson(Map.of("response", "OK"));
                            dos.writeUTF(responseJSON);

                        } catch (IOException e) {
                            String responseJSON = gson.toJson(Map.of("response", "ERROR"));
                            dos.writeUTF(responseJSON);
                        }
                    }


                    case "get" -> {
                        try (FileInputStream fis = new FileInputStream(jsonDbFile);
                             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8)) {

                            Type dataType = new TypeToken<LinkedHashMap<String, String>>() {
                            }.getType();
                            LinkedHashMap<String, String> db = gson.fromJson(isr, dataType);

                            if (db.get(inputKey) != null) {
                                String responseJSON = gson.toJson(Map.of("response", "OK",
                                        "value", db.get(inputKey)));
                                dos.writeUTF(responseJSON);

                            } else {
                                String responseJSON = gson.toJson(Map.of("response", "ERROR",
                                        "reason", "No such key"));
                                dos.writeUTF(responseJSON);
                            }

                        } catch (NullPointerException e) {
                            String responseJSON = gson.toJson(Map.of("response", "ERROR",
                                    "reason", "No such key"));
                            dos.writeUTF(responseJSON);
                        }
                    }


                    case "delete" -> {
                        try (FileInputStream fis = new FileInputStream(jsonDbFile);
                             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8)) {

                            Type dataType = new TypeToken<LinkedHashMap<String, String>>() {
                            }.getType();
                            LinkedHashMap<String, String> db = gson.fromJson(isr, dataType);

                            try (FileOutputStream fos = new FileOutputStream(jsonDbFile);
                                 OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {

                                if (db.get(inputKey) != null) {
                                    db.remove(inputKey);
                                    gson.toJson(db, osw);
                                    String responseJSON = gson.toJson(Map.of("response", "OK"));
                                    dos.writeUTF(responseJSON);

                                } else {
                                    String responseJSON = gson.toJson(Map.of("response", "ERROR",
                                            "reason", "No such key"));
                                    dos.writeUTF(responseJSON);
                                }
                            } catch (NullPointerException e) {
                                String responseJSON = gson.toJson(Map.of("response", "ERROR",
                                        "reason", "No such key"));
                                dos.writeUTF(responseJSON);
                            }
                        }
                    }
                    case "exit" -> s.isRunning = false;
                    default -> System.out.println("Wrong input");
                }
            } catch (IOException e) {
            System.out.println(e);

        } catch (IndexOutOfBoundsException oob) {
            System.out.println("ERROR");
        }
    }
}
