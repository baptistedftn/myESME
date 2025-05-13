package fr.sudrimaker.myesme.ui.map

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Overlay
import kotlin.math.cos
import kotlin.math.pow

/**
 * Une overlay personnalisée pour afficher une image vectorielle (SVG/XML) sur la carte OSMDroid
 * Supporte soit les coordonnées des coins opposés, soit un point central avec zoom
 */
class SVGOverlay : Overlay {
    private val context: Context
    private val drawableResourceId: Int
    private lateinit var topLeftGeoPoint: GeoPoint
    private lateinit var bottomRightGeoPoint: GeoPoint
    private var drawable: Drawable? = null
    private var centerPoint: GeoPoint? = null
    private var useCenter: Boolean = false
    private var spanFactor: Double = 0.0
    private var aspectRatio: Float = 1.0f

    /**
     * Constructeur utilisant les coins opposés de l'image
     */
    constructor(
        context: Context,
        drawableResourceId: Int,
        topLeftGeoPoint: GeoPoint,
        bottomRightGeoPoint: GeoPoint
    ) {
        this.context = context
        this.drawableResourceId = drawableResourceId
        this.topLeftGeoPoint = topLeftGeoPoint
        this.bottomRightGeoPoint = bottomRightGeoPoint
        this.useCenter = false
        loadDrawable()
    }

    /**
     * Constructeur utilisant un point central, un zoom et un ratio d'aspect
     */
    constructor(
        context: Context,
        drawableResourceId: Int,
        centerPoint: GeoPoint,
        spanFactor: Double,
        aspectRatio: Float = 1.0f  // ratio largeur/hauteur, 1.0 = carré
    ) {
        this.context = context
        this.drawableResourceId = drawableResourceId
        this.centerPoint = centerPoint
        this.spanFactor = spanFactor
        this.aspectRatio = aspectRatio
        this.useCenter = true

        // Calcul initial des coins (sera refait à chaque dessin)
        updateBounds()

        loadDrawable()
    }

    private fun updateBounds() {
        if (useCenter && centerPoint != null) {
            // Calculer les coins en fonction du point central, du span et du ratio d'aspect
            // Ajuster pour la projection de Mercator et la distorsion aux différentes latitudes
            val latSpan = spanFactor / 2.0

            // Ajustement pour compenser la distorsion de la projection de Mercator
            // La distorsion augmente avec la distance à l'équateur
            val latCorrectionFactor = cos(Math.toRadians(centerPoint!!.latitude))
            val lonSpan = (spanFactor * aspectRatio) / (2.0 * latCorrectionFactor)

            this.topLeftGeoPoint = GeoPoint(
                centerPoint!!.latitude + latSpan,
                centerPoint!!.longitude - lonSpan
            )

            this.bottomRightGeoPoint = GeoPoint(
                centerPoint!!.latitude - latSpan,
                centerPoint!!.longitude + lonSpan
            )
        }
    }

    private fun loadDrawable() {
        // Charge le drawable (SVG en XML ou PNG) depuis les ressources
        try {
            drawable = ContextCompat.getDrawable(context, drawableResourceId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun draw(canvas: Canvas, mapView: MapView, shadow: Boolean) {
        if (shadow || drawable == null) return

        // Recalculer les limites à chaque dessin pour prendre en compte les changements de zoom
        if (useCenter) {
            updateBounds()
        }

        // Convertit les points géographiques en pixels
        val topLeftPixel = mapView.projection.toPixels(topLeftGeoPoint, null)
        val bottomRightPixel = mapView.projection.toPixels(bottomRightGeoPoint, null)

        // Calcule les dimensions en pixels
        val left = topLeftPixel.x.toFloat()
        val top = topLeftPixel.y.toFloat()
        val right = bottomRightPixel.x.toFloat()
        val bottom = bottomRightPixel.y.toFloat()

        // Calculer la largeur et hauteur correctes en respectant le ratio d'aspect
        val width = right - left
        val height = bottom - top

        // Appliquer le ratio d'aspect correctement pour éviter la déformation
        // Nous calculons la nouvelle hauteur ou largeur pour maintenir le ratio d'aspect
        var newLeft = left
        var newTop = top
        var newRight = right
        var newBottom = bottom

        if (useCenter) {
            // Récupérer le point central réel de l'image sur la carte
            val centerPixel = mapView.projection.toPixels(centerPoint, null)
            val centerX = centerPixel.x.toFloat()
            val centerY = centerPixel.y.toFloat()

            if (width / height > aspectRatio) {
                // Trop large, ajuster la largeur
                val newWidth = height * aspectRatio
                newLeft = centerX - newWidth / 2
                newRight = centerX + newWidth / 2
            } else {
                // Trop haute, ajuster la hauteur
                val newHeight = width / aspectRatio
                newTop = centerY - newHeight / 2
                newBottom = centerY + newHeight / 2
            }
        }

        // Définir les limites pour le drawable
        drawable!!.setBounds(
            newLeft.toInt(),
            newTop.toInt(),
            newRight.toInt(),
            newBottom.toInt()
        )

        // Dessiner le drawable
        drawable!!.draw(canvas)
    }

    /**
     * Méthode statique utilitaire pour calculer un facteur d'échelle approprié en fonction du niveau de zoom
     */
    companion object {
        fun getSpanFactorForZoom(zoomLevel: Int): Double {
            // Formule ajustée pour une meilleure échelle à tous les niveaux de zoom
            // Pour ajuster la taille de l'image, modifiez le coefficient (0.018)
            return 0.027 / 2.0.pow((zoomLevel - 15).toDouble())
        }
    }
}