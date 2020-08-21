package com.example.myapplication
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.io.IOException

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private val loadingDialog = LoadingDialog(this)
    private val bottomSheetDialog = BottomSheetDialog()
    private var titleStr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        /**
         * Add Zoom out , Zoom in Button in Google Map
         */
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)
        /**
         * Trigger setUpMap() method to create the map
         */
        setUpMap()
    }
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }


    /**
     * This is a Listener that start method setupMap() when permission for location granted in order to show to user the current location
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    map.clear()
                    /**
                     * Trigger method setUpMap() again if permission granted
                     */
                    setUpMap()
                    /**
                     * Here i dismiss address dialog from specific location in order to show to user again the dialog but with current location
                     */
                    bottomSheetDialog.dismiss()
                }
                return
            }
            else -> {
            }
        }
    }


    /**
     * This is a method that first check if user wants to give the permission to access the location if permission is not granted .
     * If permission not granted I setUpMap to a specific location with specific coordinates else If permission granted we access the current location of user and we setUpMap to the current location
     * Also in both cases after map set I show the a bottom sheet dialog with the address
     */
    private fun setUpMap(){
        /**
         * Condition check if permission is not granted
         */
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            /**
             * Set specific coordinates to a specific location
             */
            val Athens = LatLng(37.987044, 23.727696)
            /**
             * Adding a marker to the map
             */
            map.addMarker(MarkerOptions().position(Athens))
            /**
             * Here Camera zooming to a specific value also here we method OnFinish() we set that when the animation finish then show to user the pop up window with address and also after animation place instantly the Marker on map
             */
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(Athens, 15f),4000,object : GoogleMap.CancelableCallback{
                override fun onFinish() {
                    bottomSheetDialog.show(supportFragmentManager,null)
                    /**
                     *Here i use handler to ensure that bottomSheetDialog.show has run and is over in order to use some values from this class to placeMarkerOnMap method()
                     */
                    Handler().postDelayed({
                        placeMarkerOnMap(Athens)
                        //doSomethingHere()
                    }, 0)
                }
                override fun onCancel() {
                }
            })
            /**
             *Here is the pop up windows that request permission from user
             */
            Thread{
                Thread.sleep(3000)
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            }.start()
                return
        }
        /**
         *If permission is granted runs from here and down here we load first loading window in order to inform user that current location loading and show to user the current location with pop up window address
         */
        loadingDialog.startLoadingDialog()
        map.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f),4000,object : GoogleMap.CancelableCallback{
                    override fun onFinish() {
                        loadingDialog.dismissDialog()
                        bottomSheetDialog.show(supportFragmentManager,null)
                        Handler().postDelayed({
                            placeMarkerOnMap(currentLatLng)
                            //doSomethingHere()
                        }, 0)
                    }
                    override fun onCancel() {
                    }
                })
            }
        }
    }




    /**
     * This is a method that taking the specific name address from coordinates either from specific location or from current location
     */
    private fun getAddress(latLng: LatLng): String {
        val geocoder = Geocoder(this)
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses[0]
                if(address.maxAddressLineIndex > 0){
                    for (i in 0 until address.maxAddressLineIndex) {
                        addressText += address.getAddressLine(i)
                    }
                }
                else{
                    addressText += address.getAddressLine(0)
                }
            }
        } catch (e: IOException ) {
            Log.e("MapsActivity", e.localizedMessage )
        }
        return addressText
    }

    /**
     * This is a method that takes the specific address from method getAddress() and place marker on map
     * Also here I take values longitude and latitude from bottomSheetDialog Class in order to use it to display after the address to pop up windows
     */
    private fun placeMarkerOnMap(location: LatLng) {
        val markerOptions = MarkerOptions().position(location)
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(
            BitmapFactory.decodeResource(resources, R.mipmap.ic_user_location)))
        titleStr = getAddress(location)
        bottomSheetDialog.textView.text = titleStr
        bottomSheetDialog.lantude = location.latitude
        bottomSheetDialog.lngtude = location.longitude
        markerOptions.title(titleStr)
        map.addMarker(markerOptions)
    }

    override fun onMarkerClick(p0: Marker?) = false
}
