package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.ArrayList;
import DataBase.CentroCostoDB;
import Util.Constans;

/**
 * Creado por dvillanueva el  29/11/2017 (FiltrosLysApp).
 */

public class CentroCostoXCompaniaTask extends AsyncTask<String,String,ArrayList<CentroCostoDB>> {
    ArrayList<CentroCostoDB> result ;
    @Override
    protected ArrayList<CentroCostoDB> doInBackground(String... strings) {
        ArrayList<CentroCostoDB> listCentro  = new ArrayList<>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "GetCentroCostoXCompania";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("compania", strings[0]);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;

        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.bodyIn;
            int num_projects = resSoap.getPropertyCount();
            Log.i("result Centro  Costo x Compania ",resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);
                CentroCostoDB c = new CentroCostoDB();
                c.setC_centrocosto(ic.getProperty(0).toString());
                c.setC_compania(ic.getProperty(1).toString());
                c.setC_descripcion(ic.getProperty(2).toString());
                c.setC_estado(ic.getProperty(3).toString());
                listCentro.add(c);
            }
            if (resSoap.getPropertyCount() > 0) {
                result = listCentro;

            }

        } catch (Exception e) {
            Log.i("CCosto x Compania error---", e.getMessage());
        }

        return result;
    }
}
