package ihc.p7.statstips;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class OddListViewAdapter extends ArrayAdapter<Odd> {

    public OddListViewAdapter(@NonNull Context context, ArrayList<Odd> resource) {
        super(context, R.layout.odd_listview, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Odd odd = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.odd_listview, parent, false);

        TextView nome = (TextView) convertView.findViewById(R.id.gameTitle);
        TextView local = (TextView) convertView.findViewById(R.id.localVal);
        TextView data_hora = (TextView) convertView.findViewById(R.id.dataHoraVal);
        TextView win = (TextView) convertView.findViewById(R.id.winVal);
        TextView draw = (TextView) convertView.findViewById(R.id.drawVal);
        TextView loss = (TextView) convertView.findViewById(R.id.lossVal);
        TextView win_draw = (TextView) convertView.findViewById(R.id.winDrawVal);
        TextView total_goals = (TextView) convertView.findViewById(R.id.goalsVal);
        TextView both_score = (TextView) convertView.findViewById(R.id.bothScoredVal);



        nome.setText(odd.nome);
        local.setText(odd.local);
        data_hora.setText(odd.data_hora);
        win.setText(odd.win);
        draw.setText(odd.draw);
        loss.setText(odd.loss);
        win_draw.setText(odd.win_draw);
        total_goals.setText(odd.total_goals);
        both_score.setText(odd.both_scored);
        return convertView;
    }
}
