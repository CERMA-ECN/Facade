package fr.ecn.facade.android;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import fr.ecn.facade.core.model.ImageInfos;

public class FacadeActivity extends Activity {
	
	private static final int ACTIVITY_LOAD = 0;
	private static final int ACTIVITY_CAPTURE = 1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
        
        Button loadButton = (Button) findViewById(R.id.load_image);
        loadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            	startActivityForResult(i, ACTIVITY_LOAD);
            }
        });
        
        Button takePictureButton = (Button) findViewById(R.id.take_picture);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File("/sdcard/tmp.jpg")));
            	startActivityForResult(i, ACTIVITY_CAPTURE);
			}
		});
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		ImageInfos imageInfos = null;
		
		switch (requestCode) {
		case ACTIVITY_LOAD:
			if (resultCode == Activity.RESULT_OK) {
				//Finding the image absolute file path
				Cursor cursor = this.getContentResolver().query(data.getData(), null, null, null, null);
				cursor.moveToFirst();
				int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
				String absoluteFilePath = cursor.getString(idx);
				
				imageInfos = new ImageInfos(absoluteFilePath);
				
				Intent i = new Intent(this, VanishingPointsActivity.class);
				i.putExtra("ImageInfos", imageInfos);
				this.startActivity(i);
			}
			break;
		case ACTIVITY_CAPTURE:
			if (resultCode == Activity.RESULT_OK) {
				File f = new File("/sdcard/tmp.jpg");
				
				imageInfos = new ImageInfos(f.getAbsolutePath());
				
				Intent i = new Intent(this, VanishingPointsActivity.class);
				i.putExtra("ImageInfos", imageInfos);
				this.startActivity(i);
			}
			break;
		}
	}
}