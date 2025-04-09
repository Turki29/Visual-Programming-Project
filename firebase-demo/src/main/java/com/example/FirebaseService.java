package com.example;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FirebaseService {

    private DatabaseReference database;

    public FirebaseService() {
        try {
            // مسار التوكن (نقدر نفترض إنه وهمي)
            FileInputStream serviceAccount = new FileInputStream("serviceAccountKey.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://your-project-id.firebaseio.com") // حط عنوان مشروعك هنا
                    .build();

            FirebaseApp.initializeApp(options);

            this.database = FirebaseDatabase.getInstance().getReference();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ميثود يرجع بيانات وهمية (كمثال مستخدم)
    public Map<String, Object> getUserInfo(String userId) {
        // محاكاة بيانات
        Map<String, Object> data = new HashMap<>();
        data.put("id", userId);
        data.put("name", "أحمد");
        data.put("email", "ahmed@example.com");
        return data;
    }

    // ميثود يرجع لستة وهمية من الطلبات
    public Map<String, Object> getOrders() {
        Map<String, Object> orders = new HashMap<>();
        orders.put("order1", "بيتزا");
        orders.put("order2", "برجر");
        return orders;
    }

    // ميثود لكتابة بيانات وهمية في Realtime Database
    public void writeFakeData() {
        Map<String, Object> fakeData = new HashMap<>();
        fakeData.put("title", "اختبار");
        fakeData.put("status", "قيد التنفيذ");

        database.child("tasks/task1").setValueAsync(fakeData);
    }
}
