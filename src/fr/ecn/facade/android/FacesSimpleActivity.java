package fr.ecn.facade.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import fr.ecn.common.core.imageinfos.ImageInfos;

/**
 * An activity to select faces with the user giving the 4 corners of the face
 * 
 * @author jerome
 *
 */
public class FacesSimpleActivity extends Activity implements OnTouchListener {

	private static final int MENU_ADD_FACE = Menu.FIRST;
	private static final int MENU_REMOVE_LAST_FACE = Menu.FIRST + 1;
	private static final int MENU_VALIDATE = Menu.FIRST + 2;
	
	private static final int MENU_END_FACE = Menu.FIRST + 3;
	private static final int MENU_CANCEL_FACE = Menu.FIRST + 4;

	protected ImageInfos imageInfos;

	protected FacesSimpleController controller;

	protected Matrix matrix;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
		final ImageInfos imageInfos = (ImageInfos) extras
				.getSerializable("ImageInfos");
		this.imageInfos = imageInfos;

		this.controller = (FacesSimpleController) this.getLastNonConfigurationInstance();
		
		if (this.controller == null) {
			this.controller = new FacesSimpleController(imageInfos);
		}

		this.setUp();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onRetainNonConfigurationInstance()
	 */
	@Override
	public Object onRetainNonConfigurationInstance() {
		return this.controller;
	}

	/**
	 * set up views based on controller infos
	 */
	protected void setUp() {
		this.setContentView(R.layout.image);

		ImageView imageView = (ImageView) findViewById(R.id.image);

		this.matrix = null;

		Drawable[] drawables = {
				new BitmapDrawable(this.controller.getBitmap()),
				new FacesSimpleDrawable(this.controller) };
		imageView.setImageDrawable(new LayerDrawable(drawables));
		imageView.setOnTouchListener(this);
	}

	public boolean onTouch(View v, MotionEvent event) {
		if (this.controller.isIdle()) {
			return false;
		}
		
		if (event.getAction() != MotionEvent.ACTION_DOWN) {
			return false;
		}

		if (this.matrix == null) {
			this.matrix = new Matrix();

			((ImageView) findViewById(R.id.image)).getImageMatrix().invert(
					this.matrix);
		}

		float[] point = { event.getX(), event.getY() };

		// Converting the point in image coordinate system
		this.matrix.mapPoints(point);

		this.controller.addPoint(point[0], point[1]);

		this.findViewById(R.id.image).invalidate();

		return true;
	}

	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		
		if (this.controller.isIdle()) {
			// If the controller has at least one face stored
			boolean hasFaces = this.controller.getFaces().size() > 0;

			menu.add(0, MENU_ADD_FACE, 0, R.string.menu_addface);
			menu.add(0, MENU_REMOVE_LAST_FACE, 0, R.string.remove_last_face).setEnabled(hasFaces);
			menu.add(0, MENU_VALIDATE, 0, R.string.menu_validate).setEnabled(hasFaces);
		} else {
			menu.add(0, MENU_END_FACE, 0, R.string.end_face).setEnabled(false);
			menu.add(0, MENU_CANCEL_FACE, 0, R.string.cancel_face);
		}

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ADD_FACE:
			this.controller.startFace();
			
			return true;
		case MENU_REMOVE_LAST_FACE:
			this.controller.removeLastFace();

			this.findViewById(R.id.image).invalidate();
			
			return true;
		case MENU_VALIDATE:
			this.imageInfos.setFaces(this.controller.getFinalFaces());
			
			Intent i = new Intent(this, ResultActivity.class);
			i.putExtra("ImageInfos", this.imageInfos);
			this.startActivity(i);
			
			return true;
			
		case MENU_END_FACE:
			// We can't end face in this mode, we just keep the button for
			// consistency with the over mode
			
			return true;
		case MENU_CANCEL_FACE:
			this.controller.cancelFace();
			
			this.findViewById(R.id.image).invalidate();
			
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}
