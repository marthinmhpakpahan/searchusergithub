package marthinmhpakpahan.searchusergithub;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by user on 8/30/2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private List<User> models;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textUsername;
        ImageView imageUser;

        public MyViewHolder(View view) {
            super(view);
            textUsername = (TextView) view.findViewById(R.id.textUsername);
            imageUser = (ImageView) view.findViewById(R.id.imageUser);
        }
    }

    public UserAdapter(Context context, List<User> param_models) {
        this.context = context;
        this.models = param_models;
    }

    public void updateItem(List<User> new_models) {
        models.clear();
        models = new_models;
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final User user = models.get(position);
        holder.textUsername.setText(user.getUsername());

        Picasso.with(context).load(user.getAvatar_url()).into(holder.imageUser);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
