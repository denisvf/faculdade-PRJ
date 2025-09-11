/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calculadora.helpers;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 *
 * @author denis
 */
public class TypeConverter {

    public static double stringToDouble(String number) {
        NumberFormat nf = NumberFormat.getInstance();
        double dv = 0.0;
        try {
            dv = nf.parse(number).doubleValue();
        } catch (ParseException ex) {

        }
        return dv;
    }

    public static String doubleToString(double number) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        DecimalFormat format = new DecimalFormat("###,###,##0.00", symbols);
        format.setParseBigDecimal(true);
        return format.format(number);
    }
}
