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
        binding.mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        binding.mapView.setMultiTouchControls(true)


        val mapController = binding.mapView.controller
        mapController.setZoom(19.0)  // Ajusté à 19 pour une meilleure vue
        val startPoint = GeoPoint(50.6330873, 3.0495969)
        mapController.setCenter(startPoint)

        val compassOverlay =
            CompassOverlay(context, InternalCompassOrientationProvider(context), binding.mapView)
        compassOverlay.enableCompass()
        binding.mapView.overlays.add(compassOverlay)

        addSvgOverlay()

        val locationOverlay =
            MyLocationNewOverlay(GpsMyLocationProvider(context), binding.mapView)
        locationOverlay.enableMyLocation()
        binding.mapView.overlays.add(locationOverlay)

//        val schoolPoint = Marker(binding.mapView)
//        schoolPoint.position = startPoint
//        schoolPoint.icon = context?.let { ContextCompat.getDrawable(it, R.drawable.esme_loc) }
//        schoolPoint.title = "ESME Lille"
//        schoolPoint.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
//        binding.mapView.overlays.add(schoolPoint)

        return binding.root
    }

    private fun addSvgOverlay() {
        // Coordonnées précises pour le positionnement de l'image
        // Ces coordonnées sont ajustées pour correspondre à l'emplacement exact
        val centerLat = 50.633060
        val centerLon = 3.049990
        val zoomLevel = 19

        // Créer le point central
        val centerPoint = GeoPoint(centerLat, centerLon)

        // Créer et ajouter l'overlay avec l'image SVG
        context?.let { ctx ->
            // Calculer la taille de l'overlay en fonction du zoom
            // Réduire le facteur d'échelle pour un meilleur ajustement
            val spanFactor = SVGOverlay.getSpanFactorForZoom(zoomLevel) * 0.9

            // Utiliser le bon ratio d'aspect pour l'image map_lille
            val aspectRatio =
                1.4595562f  // Vérifiez cette valeur avec les dimensions de votre image

            val imageOverlay = SVGOverlay(
                ctx,
                R.drawable.map_lille0,
                centerPoint,
                spanFactor,
                aspectRatio
            )

            binding.mapView.overlays.add(imageOverlay)
        }

        // Forcer le rafraîchissement de la carte
        binding.mapView.invalidate()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.mapView.onPause()
        _binding = null
    }
}