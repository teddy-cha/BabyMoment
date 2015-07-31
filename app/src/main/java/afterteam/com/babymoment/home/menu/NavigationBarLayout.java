package afterteam.com.babymoment.home.menu;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toolbar;

import afterteam.com.babymoment.home.ActionListActivity;

/**
 * @author chayongbin
 */
public class NavigationBarLayout extends ActionListActivity{


    /**
     * navigation bar item row 에 들어갈 내용
     * list view
     */
    String MENU[] = {"일일 기록","일일 그래프"};
    // 아이 정보를 Database에 저장을 하고 List 형식으로 바꾸어여함.
    String OTHER_ACCOUNT[] = {};
    String SETTING[] = {"설정"};

    /**
     * profile Data
     * TODO : kakao talk user data
     */

    String NAME = "김아이";
    String AGE = "14개월";
    String GENDER = "남";
    int PROFILE;

    private Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    DrawerLayout drawer;
    ActionBarDrawerToggle actionBarDrawerToggle;


}
