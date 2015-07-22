package afterteam.com.babymoment.base;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;

import afterteam.com.babymoment.utils.LogUtils;

/**
 * The application objects for managing images in the app -level cache.
 * @author chayongbin
 */
public class GlobalApplication extends Application {

    private final String TAG = LogUtils.makeTag(this.getClass().getSimpleName());

    private static volatile GlobalApplication instance = null;

    // Use Volley library
    private ImageLoader imageLoader;

    /**
     * It gets a singleton application object .
     * @return singleton Application objects
     */
    public static GlobalApplication getGlobalApplicationContext() {
        if(instance == null)
            throw new IllegalStateException("this application does not inherit afterteam.com.babymoment.Base.GlobalApplication");
        return instance;
    }

    /**
     * Initializes the request queue, Image loader and the image cache.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // Initialize the session.
        Session.initialize(this);

        // @test session for Sign in Button
//        Session.initialize(this, AuthType.KAKAO_TALK_EXCLUDE_NATIVE_LOGIN);

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            final LruCache<String, Bitmap> imageCache = new LruCache<String, Bitmap>(3);

            @Override
            public void putBitmap(String key, Bitmap value) {
                imageCache.put(key, value);
            }

            @Override
            public Bitmap getBitmap(String key) {
                return imageCache.get(key);
            }
        };

        imageLoader = new ImageLoader(requestQueue, imageCache);
    }

    /**
     * Return the image loader .
     * @return Image Loader
     */
    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    /**
     * When you exit the application , singleton Application object is initialized .
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }
}
