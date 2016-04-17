package com.andela.motustracker.model;

import android.util.Log;

import com.andela.motustracker.manager.DbManager;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Spykins on 07/04/16.
 */
public class MockData {

    String address[] = {"36 Wallis Ave, Eastbourne, East Sussex BN23 6LR, UK", "562 De la Providence Avenue, Quebec","4432 2nd Street, Manitoba", "4516 40th Street, Alberta",
    "3460 Essendene Avenue, British Columbia", "61 High St, Sevenoaks, Kent TN13, UK","63 Erica Rd, Saint Ives, Cambridgeshire PE27 3AG, UK"};

    double longitude[] = {50.7894409,-74.36413270000003, -74.36413270000003,-100.5993133, -113.97510169999998, -122.2878581,0.19298950000006698,-0.07019760000002861 };
    double latitude[] = {0.31315349999999853, 45.66965709999999,45.66965709999999,50.1807619,50.9422678,49.0489724,51.2693028, 52.3374976};
    String date[] = {"8/04/2016","9/04/2016","10/04/2016"};

    ArrayList<LocationData> mockDataSend;

    public void addDataToDatabase() {
        int Low = 10000;
        int High = 10000000;
        mockDataSend = new ArrayList<>();
        Random random = new Random();
        Integer number;
        for(String dat : date) {
            for(int i = 0; i<address.length; i++) {
                number = random.nextInt(High-Low) + Low;
                if(number <0) {
                    while (number <=0 ) {
                        number = random.nextInt(High-Low) + Low;
                    }
                }
                mockDataSend.add(new LocationData(address[i], dat, latitude[i], longitude[i], number.longValue()));
            }

        }

        mockDataSend.add(new LocationData("12 Bello M Yusuf", "13/02/2012",34.2981,34900, new Integer(3600000).longValue()));

        for (LocationData loca : mockDataSend){
            DbManager dbManager = new DbManager();
            dbManager.insertIntoDb(loca);
        }

    }

}
