/**
 * 
 */
package fr.ecn.facade.android;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.app.Activity;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import fr.ecn.common.android.DialogHelper;
import fr.ecn.common.core.imageinfos.ImageInfos;

/**
 * @author jerome
 *
 */
public class ResultActivity extends Activity {
	
	private static final int MENU_SAVE = Menu.FIRST;
	
	protected Future<ResultController> futureController;
	protected ResultController controller;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
		ImageInfos imageInfos = (ImageInfos) extras.getSerializable("ImageInfos");
		
		this.futureController = (Future<ResultController>) this.getLastNonConfigurationInstance();
		
		//We create a Callable that will create the ResultController
		if (this.futureController == null) {
			ExecutorService executor = Executors.newSingleThreadExecutor();
			this.futureController = executor.submit(new ResultController.ResultCallable(imageInfos));
		}
		
		this.setContentView(R.layout.computing);
		
		new Thread(new Runnable() {
			public void run() {
				try {
					controller = futureController.get();
					
					runOnUiThread(new Runnable() {
						public void run() {
							setUp();
						}
					});
				} catch (InterruptedException e) {
					Log.w("Ombre", e);
				} catch (ExecutionException e) {
					Log.w("Ombre", e);
					
					runOnUiThread(new Runnable() {
						public void run() {
							DialogHelper.errorDialog(ResultActivity.this, "Une erreur c'est produite lors du calcul du résultat");
						}
					});
				}
			}
		}).start();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onRetainNonConfigurationInstance()
	 */
	@Override
	public Object onRetainNonConfigurationInstance() {
		return this.futureController;
	}
	
	/**
	 * set up views based on controller infos
	 */
	protected void setUp() {
		this.setContentView(R.layout.image);
		
		ImageView imageView = (ImageView) this.findViewById(R.id.image);
		
		imageView.setImageBitmap(this.controller.getResultBitmap());
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_SAVE, 0, R.string.save_image);
		return result;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_SAVE:
			MediaStore.Images.Media.insertImage(this.getContentResolver(), this.controller
					.getResultBitmap(), "Result", "Result");
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

}
