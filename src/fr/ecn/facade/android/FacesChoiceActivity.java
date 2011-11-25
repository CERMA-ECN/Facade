/**
 * 
 */
package fr.ecn.facade.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.ecn.common.core.imageinfos.ImageInfos;

/**
 * @author jerome
 *
 */
public class FacesChoiceActivity extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();
		final ImageInfos imageInfos = (ImageInfos) extras.getSerializable("ImageInfos");
		
		this.setContentView(R.layout.faces_choice);
		
		Button simple = (Button) this.findViewById(R.id.simple);
		Button guided = (Button) this.findViewById(R.id.guided);
		
		simple.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				Intent i = new Intent(FacesChoiceActivity.this, FacesSimpleActivity.class);
				i.putExtra("ImageInfos", imageInfos);
				
				startActivity(i);
			}
		});
		
		guided.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				Intent i = new Intent(FacesChoiceActivity.this, FacesActivity.class);
				i.putExtra("ImageInfos", imageInfos);
				
				startActivity(i);
			}
		});
	}

}
