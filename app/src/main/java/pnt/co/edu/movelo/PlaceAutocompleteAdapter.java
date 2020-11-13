package pnt.co.edu.movelo;

import android.content.Context;
import android.graphics.Typeface;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;

import java.util.ArrayList;

public class PlaceAutocompleteAdapter extends ArrayAdapter implements Filterable {

    private static final String TAG = "PlaceAutoCompleteAd";
    private static final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.BOLD);
    private ArrayList<AutocompletePrediction> mResultList;
    private GoogleApi mGoogleApiClient;
    private LatLngBounds mBounds;
    //private AutocompleteFilter mPlaceFilter;
    private Places places;

    public PlaceAutocompleteAdapter(Context context, GoogleApi googleApiClient,
                                    LatLngBounds bounds) {
        super(context, android.R.layout.simple_expandable_list_item_2, android.R.id.text1);
        mGoogleApiClient = googleApiClient;
        mBounds = bounds;

    }
}