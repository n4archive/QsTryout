package org.eu.droid_ng.systemui.qs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.android.systemui.plugins.annotations.Requires;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSFactory;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;

@Requires(target = QSFactory.class, version = QSFactory.VERSION)
@Requires(target = QSTile.class, version = QSTile.VERSION)
@Requires(target = QSTileView.class, version = QSTileView.VERSION)
@Requires(target = QSIconView.class, version = QSIconView.VERSION)
@Requires(target = QSTile.Callback.class, version = QSTile.Callback.VERSION)
@Requires(target = QSTile.Icon.class, version = QSTile.Icon.VERSION)
@Requires(target = QSTile.State.class, version = QSTile.State.VERSION)
@Requires(target = DetailAdapter.class, version = DetailAdapter.VERSION)
public class QsPlugin implements QSFactory {
	@SuppressLint("StaticFieldLeak")
	private static QsPlugin INSTANCE;
	private static final String TAG = "QsPlugin";
	private static final boolean DEBUG = false;
	private Context mPluginContext;
	private Context mSysuiContext;

	@Override
	public void onCreate(Context sysuiContext, Context pluginContext) {
		Log.d(TAG, "onCreate");
		INSTANCE = this;
		mSysuiContext = sysuiContext;
		mPluginContext = pluginContext;
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
		INSTANCE = null;
	}

	public static QsPlugin getInstance() {
		return INSTANCE;
	}

	public static Context getSysUiContext() {
		return getInstance().mSysuiContext;
	}

	public static Context getPluginContext() {
		return getInstance().mPluginContext;
	}

	public static Resources getSysuiResources() {
		return getSysUiContext().getResources();
	}

	public static Resources getPluginResources() {
		return getPluginContext().getResources();
	}

	public static int getSysuiResourceId(String name, String type, String pkg) {
		return getSysuiResources().getIdentifier(name, type, pkg);
	}

	@Override
	public QSTile createTile(String s) {
		return null;
	}

	@Override
	public QSTileView createTileView(Context context, QSTile qsTile, boolean b) {
		Log.d(TAG, "createTileView");
		QSIconViewImpl icon = new QSIconViewImpl(context);
		return new org.eu.droid_ng.systemui.qs.QSTileView(context, icon, b);
	}
}