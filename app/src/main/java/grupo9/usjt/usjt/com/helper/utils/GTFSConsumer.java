package grupo9.usjt.usjt.com.helper.utils;


import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import grupo9.usjt.usjt.com.activities.R;
import grupo9.usjt.usjt.com.dto.TracadoLinhaCSVDTO;

public class GTFSConsumer {

    private Context context;

    public GTFSConsumer(Context context) {
        this.context = context;
    }

    public List<LatLng> findStopPointsByStopId(List<String> list){
        List<LatLng> saida = new ArrayList<>();
        try{
            InputStream is = context.getResources().openRawResource(R.raw.stops);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8")));
            String nextLine;int qntLidos = 0;
            while ((nextLine = reader.readLine()) != null && qntLidos<list.size()) {
                // nextLine[] is an array of values from the line
                String linhaSplit[] = nextLine.replace("\"","").replace("([, 0-9])+","").split(",");
                if(linhaSplit[0].equals(list.get(qntLidos))){
                    saida.add(new LatLng(Double.parseDouble(linhaSplit[3]),Double.parseDouble(linhaSplit[4])));
                    qntLidos++;
                    reader.close();
                    reader = new BufferedReader(
                            new InputStreamReader(is, Charset.forName("UTF-8")));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<String> findStopIdsByLinha(String prefixo){
        List<String>saida = new ArrayList<>() ;
        try{
            InputStream is = context.getResources().openRawResource(R.raw.stop_times);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8")));
            String nextLine;
            boolean parada = false;
            while ((nextLine = reader.readLine()) != null) {
                // nextLine[] is an array of values from the line
                String linhaSplit[] = nextLine.replace("\"","").split(",");
                if(linhaSplit[0].equals(prefixo)){
                    parada = true;
                    saida.add(linhaSplit[3]);
                }
                else if(parada){
                    return saida;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;

    }

    public String readTrips(String input){
        String saida;
        try{
            InputStream is = context.getResources().openRawResource(R.raw.trips);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8")));
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                // nextLine[] is an array of values from the line
                String linhaSplit[] = nextLine.replace("\"","").split(",");
                if(linhaSplit[0].equals(input)){
                    saida = linhaSplit[5]+";"+linhaSplit[2];
                    return saida;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<TracadoLinhaCSVDTO> findPontosLinha(String idTrip){
        List<TracadoLinhaCSVDTO>saida = new ArrayList<>() ;
        try{
            InputStream is = context.getResources().openRawResource(R.raw.shapes);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8")));
            String nextLine;
            boolean parada = false;
            while ((nextLine = reader.readLine()) != null) {
                // nextLine[] is an array of values from the line
                String linhaSplit[] = nextLine.replace("\"","").split(",");
                if(linhaSplit[0].equals(idTrip)){
                    parada = true;
                    TracadoLinhaCSVDTO dto = new TracadoLinhaCSVDTO();
                    dto.setIdTrip(Integer.parseInt(linhaSplit[0]));
                    dto.setPy(Double.parseDouble(linhaSplit[1]));
                    dto.setPx(Double.parseDouble(linhaSplit[2]));
                    dto.setSeqParada(Integer.parseInt(linhaSplit[3]));
                    saida.add(dto);
                }
                else if(parada){
                    return saida;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
