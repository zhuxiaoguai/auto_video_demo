package a733.shareapk.com;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PackageManager packageManager;
    private List<PackageInfo> infos;
    private PackageInfo packageInfo;
    private ApplicationInfo applicationInfo;
    private String packageName;
    private StringBuffer stringBuffer = new StringBuffer();
    private ListView lv;
    private String name;
    private Drawable icon;
    private List<AppInfo> appInfos;
    private AppInfo appInfo;
    private Myadapter myadapter;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);
        init();
        myadapter = new Myadapter();
        lv.setAdapter(myadapter);
    }

    private void init() {
        packageManager = this.getPackageManager();
        infos = packageManager.getInstalledPackages(0);
        appInfos = new ArrayList<>();
        for (int i = 0; i < infos.size(); i++) {
            appInfo = new AppInfo();
            packageInfo = infos.get(i);
            packageName = packageInfo.packageName;
            try {
                applicationInfo = packageManager.getApplicationInfo(packageName, 0);
                name = (String) packageManager.getApplicationLabel(applicationInfo);
                icon = applicationInfo.loadIcon(packageManager);
                path = applicationInfo.sourceDir;

                appInfo.setPath(path);
                appInfo.setIcon(icon);
                appInfo.setName(name);
                appInfos.add(appInfo);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private class AppInfo {
        private String name;
        private Drawable icon;
        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }
    }

    private class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return appInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return appInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyHolder myHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.app_info_layout, null);
                myHolder = new MyHolder(convertView);
                convertView.setTag(myHolder);
            } else {
                myHolder = (MyHolder) convertView.getTag();
                myHolder.iv.setBackground(appInfos.get(position).getIcon());
                myHolder.tv.setText(appInfos.get(position).getName());
                myHolder.path.setText(appInfos.get(position).getPath());
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    File apkFile = new File(appInfos.get(position).getPath());
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("*/*");
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(apkFile));
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }

    private class MyHolder {
        private TextView tv;
        private ImageView iv;
        private TextView path;

        public MyHolder(View view) {
            this.tv = (TextView) view.findViewById(R.id.tv);
            this.iv = (ImageView) view.findViewById(R.id.iv);
            path = (TextView) view.findViewById(R.id.path);
        }
    }
}
