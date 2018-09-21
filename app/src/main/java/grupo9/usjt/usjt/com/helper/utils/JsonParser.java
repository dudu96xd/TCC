package grupo9.usjt.usjt.com.helper.utils;


import android.os.Build;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonParser {
    public static void parse(String cdLinha ){
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        boolean firstLine = true;
        List<String> headers = new ArrayList<>();
        JSONArray result = new JSONArray();
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ClassLoader classLoader = JsonParser.class.getClassLoader();
                Log.i("path do arquivo GTFS",classLoader.toString());
                String path = Paths.get( classLoader.getResource("").toURI()).toString();
                Log.i("path do arquivo GTFS",path);
                br = new BufferedReader(new FileReader(path));
            }
            assert br != null;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    String[] headersCSV = line.split(cvsSplitBy);

                    Collections.addAll(headers, headersCSV);
                } else {
                    String[] valuesCSV = line.split(cvsSplitBy);
                    JSONObject valueObj = new JSONObject();

                    // first 2 values are constant.
                    valueObj.put(headers.get(0), valuesCSV[0]);
                    valueObj.put(headers.get(1), valuesCSV[1]);

                    JSONObject nestedObj = new JSONObject();
                    for (int i = 2; i < valuesCSV.length; i++) {
                        nestedObj.put(headers.get(i), valuesCSV[i]);
                    }

                    valueObj.put("labelC", nestedObj);

                    result.put(valueObj);
                }
            }

            // your output is in result Object.


        } catch (JSONException | URISyntaxException | IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
