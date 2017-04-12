package com.kozsabynin.createyourself.sms;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.kozsabynin.createyourself.db.CashflowFirebaseService;
import com.kozsabynin.createyourself.domain.CashType;
import com.kozsabynin.createyourself.domain.Cashflow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by Evgeni Developer on 26.03.2017.
 */
public class SmsService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        CashflowFirebaseService cashflowFirebaseService = new CashflowFirebaseService();
        String sms_body = intent.getExtras().getString("sms_body");
        Cashflow cashflow = processSms(sms_body);
        if(cashflow != null){
            cashflowFirebaseService.insertCashflow(cashflow);
        }

        return START_STICKY;
    }

    private Cashflow processSms(String sms_body) {
        Pattern pattern = Pattern.compile("Pokupka: (.+); (\\d+.\\d+) RUR; Data: (\\d+/\\d+/\\d+)");
        Matcher matcher = pattern.matcher(sms_body);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (matcher.find()) {
            String description = matcher.group(1);
            Double cost = Double.parseDouble(matcher.group(2));
            Calendar calendar = null;
            try {
                calendar = Calendar.getInstance();
                calendar.setTime(simpleDateFormat.parse(matcher.group(3)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return new Cashflow(description, CashType.EXPENSE, cost, calendar, null);
        }
        return null;
    }
}
