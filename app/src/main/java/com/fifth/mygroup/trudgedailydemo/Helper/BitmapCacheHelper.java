package com.fifth.mygroup.trudgedailydemo.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.LruCache;
import android.widget.ImageView;

import com.fifth.mygroup.trudgedailydemo.MainActivity;
import com.lidroid.xutils.BitmapUtils;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2016/5/19.
 */
public class BitmapCacheHelper{
    private static final String TAG = "BitmapCacheHelper";
    // 这个map是用来处理软引用的
    Map<String, SoftReference<Bitmap>> softCaches = new HashMap<String, SoftReference<Bitmap>>();
    // 强引用使用自己定义的lrucache
    private MyLruCache lruCache = null;
    private BitmapUtils bitmapUtils;



    public BitmapCacheHelper() {
        // 获取应用程序的内存空间的大小
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        if (lruCache == null) {
            lruCache = new MyLruCache(maxMemory / 3);
        }
        bitmapUtils= MainActivity.bitmapUtils;
    }

    public void getBitmap(final String url, final ImageView imageView) {

        Bitmap bitmap = null;
        // 先去强引用中拿数据
        if (lruCache != null) {
//            Log.i("lruCache", "分支");
            bitmap = lruCache.get(url);

            if (bitmap != null) {
                // 强引用拿到了，返回
                imageView.setImageBitmap(bitmap);
            } else {
//                Log.i("SoftReference", "分支");
                // 获取软引用
                SoftReference<Bitmap> soft = softCaches.get(url);
                if (soft != null) {
                    // 在软引用中获取数据
                    bitmap = soft.get();
                }
                if (bitmap != null) {
                    // 在软引用中获取到了数据
                    soft.clear();
                    lruCache.put(url, bitmap);
                    imageView.setImageBitmap(bitmap);
                } else {
//                    Log.i("SDCardHelper", "分支");
                    // 没获取到，就去磁盘上找.
                    String fileName = SDCardHelper
                            .getSDCardPublicDir(Environment.DIRECTORY_DOWNLOADS)
                            + File.separator + MD5Util.getMD5(url);
                    File file = new File(fileName);
                    if (file.exists()) {
                        // 是否二次采样加载
                        byte[] data = SDCardHelper.loadFileFromSDCard(fileName);
                        bitmap = BitmapFactory.decodeByteArray(data, 0,
                                data.length);
                        lruCache.put(url, bitmap);
                        imageView.setImageBitmap(bitmap);

                    } else {
                        new BitmapAsyncTask(imageView).execute(url);
                    }

                }

                //
            }

        }
    }
    class BitmapAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView imageView = null;

        BitmapAsyncTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
//            Log.i("bitmap", "doInBackground:" + params[0]);
            Bitmap bitmap = null;
            byte[] data = HttpURLConnHelper.loadByteFromURL(params[0]);
            if (data != null) {
                SDCardHelper.saveFileToSDCardPublicDir(data,
                        Environment.DIRECTORY_DOWNLOADS,
                        MD5Util.getMD5(params[0]));
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                lruCache.put(params[0], bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (result != null) {
                imageView.setImageBitmap(result);
            }
        }

    }
    class MyLruCache extends LruCache<String, Bitmap> {

        // 它的构造方法，你需要指定给它一个大小
        public MyLruCache(int maxSize) {
            super(maxSize);
            // TODO Auto-generated constructor stub
        }

        // 这个lrucache计算自己内部每一个对象的大小的方法
        @Override
        protected int sizeOf(String key, Bitmap value) {
            // TODO Auto-generated method stub
            return value.getByteCount();
        }

        // 这个方法在有东西出去的时候会被回调
        // 有东西出去分为两种情况：
        // 1、键值相同，value就要替换，旧value就要出来。
        // 2、满了，value要出来。
        // evicted我们程序需要用到这个boolean值，在它为真的时候，说明lrucache满了
        @Override
        protected void entryRemoved(boolean evicted, String key,
                                    Bitmap oldValue, Bitmap newValue) {
            // TODO Auto-generated method stub
            if (evicted) {
                // 把oldvalue放入软引用。构造一个软引用去指向强引用抛弃的bitmap
                SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(
                        oldValue);
                softCaches.put(key, softReference);
            }
        }

    }
}
