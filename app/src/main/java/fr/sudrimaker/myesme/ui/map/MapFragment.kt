package fr.sudrimaker.myesme.ui.map
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import fr.sudrimaker.myesme.R
import fr.sudrimaker.myesme.databinding.FragmentMapBinding
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        Configuration.getInstance().load(
            context,
            context?.getSharedPreferences("osmdroid", Context.MODE_PRIVATE)
        )
//        binding.mapView.setTileSource(TileSourceFactory.MAPNIK)
        binding.mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
//        binding.mapView.setTileSource(TileSourceFactory.PUBLIC_TRANSPORT)
        binding.mapView.setMultiTouchControls(true)


        val mapController = binding.mapView.controller
        mapController.setZoom(20.0)
        val startPoint = GeoPoint(50.6330873, 3.0495969)
        mapController.setCenter(startPoint)

        val compassOverlay =
            CompassOverlay(context, InternalCompassOrientationProvider(context), binding.mapView)
        compassOverlay.enableCompass()
        binding.mapView.overlays.add(compassOverlay)

        val locationOverlay =
            MyLocationNewOverlay(GpsMyLocationProvider(context), binding.mapView)
        locationOverlay.enableMyLocation()
        binding.mapView.overlays.add(locationOverlay)

        val schoolPoint = Marker(binding.mapView)
        schoolPoint.position = startPoint
        schoolPoint.icon = context?.let { ContextCompat.getDrawable(it, R.drawable.esme_loc) }
        schoolPoint.title = "ESME Lille"
        schoolPoint.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        binding.mapView.overlays.add(schoolPoint)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}