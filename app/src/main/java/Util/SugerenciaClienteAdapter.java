package Util;

import android.app.Activity;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.filtroslys.filtroslysapp.R;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import DataBase.ProdMantDataBase;
import Model.SugerenciaCliente;

public class SugerenciaClienteAdapter extends ArrayAdapter<SugerenciaCliente>{

    private Context  context ;
    private ArrayList<SugerenciaCliente> data ;
    private int layoutResourceId;
    ProdMantDataBase db ;

    public SugerenciaClienteAdapter(@NonNull Context context, int resource, ArrayList<SugerenciaCliente> data) {
        super(context,resource,data);
        this.context = context;
        this.data = data;
        this.layoutResourceId = resource;
        this.db = new ProdMantDataBase(context);
    }

    public static class ViewHolder{
        TextView lblNroSugerencia;
        TextView lblFechaRegSG;
        TextView lblClienteSG;
        TextView lblTipoSugerenciaSG;
        TextView lblEstadoSG;
        TextView lblDescripcionSG;
        TextView lbltitTipoSugSG;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String sEstadoDescrip="",sFormatFecha="";
        ViewHolder viewHolder;
        SugerenciaCliente oEnt = data.get(position);
        if (convertView==null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.lblNroSugerencia =  convertView.findViewById(R.id.lblNroSugerencia);
            viewHolder.lblFechaRegSG = convertView.findViewById(R.id.lblFechaRegSG);
            viewHolder.lblClienteSG = convertView.findViewById(R.id.lblClienteSG);
            viewHolder.lblTipoSugerenciaSG = convertView.findViewById(R.id.lblTipoSugerenciaSG);
            viewHolder.lblEstadoSG = convertView.findViewById(R.id.lblEstadoSG);
            viewHolder.lblDescripcionSG = convertView.findViewById(R.id.lblDescripcionSG);
            viewHolder.lbltitTipoSugSG = convertView.findViewById(R.id.lbltitTipoSugSG);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        switch (oEnt.getC_estado()){
            case "PE":
                sEstadoDescrip ="Pendiente";
                break;
            case "RE":
                sEstadoDescrip ="Revisado";
                break;
            case "CE":
                sEstadoDescrip ="Cerrado";
                break;
            case "AN":
                sEstadoDescrip ="Anulado";
                break;
        }

        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = parseador.parse(oEnt.getD_fechareg().substring(0,10));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sFormatFecha =(formateador.format(date));

        viewHolder.lblNroSugerencia.setText(String.valueOf(oEnt.getN_identinfo()));
        viewHolder.lblFechaRegSG.setText(sFormatFecha);
        viewHolder.lblEstadoSG.setText(sEstadoDescrip);
        viewHolder.lblClienteSG.setText(String.valueOf(oEnt.getN_cliente()) + " | " + db.GetNombreClienteXCod(String.valueOf(oEnt.getN_cliente())) );
        viewHolder.lblTipoSugerenciaSG.setText(db.GetNombreTipoSugerencia(oEnt.getC_tiposug()));
        viewHolder.lblDescripcionSG.setText(oEnt.getC_descripcion());

        if(!oEnt.getC_tipoinfo().equalsIgnoreCase("SU")){
            viewHolder.lbltitTipoSugSG.setVisibility(View.GONE);
            viewHolder.lblTipoSugerenciaSG.setVisibility(View.GONE);
        }else{
            viewHolder.lblTipoSugerenciaSG.setText(viewHolder.lblTipoSugerenciaSG.getText()+"   ");
        }
        return convertView;
    }

    public  SugerenciaCliente getObject (int pos){
        return  data.get(pos);
    }
}
