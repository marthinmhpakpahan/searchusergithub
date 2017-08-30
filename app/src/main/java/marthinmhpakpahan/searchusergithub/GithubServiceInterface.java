package marthinmhpakpahan.searchusergithub;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by user on 8/30/2017.
 */

public interface GithubServiceInterface {
    @GET("users?since=1000/")
    Call<List<User>> listRepos();
}